package com.chesstrainer.chess

enum class GameResult {
    ONGOING, WHITE_WINS, BLACK_WINS, DRAW
}

data class GameState(
    val board: Board = Board.initialPosition(),
    val currentPlayer: Color = Color.WHITE,
    val whiteCanCastleKingside: Boolean = true,
    val whiteCanCastleQueenside: Boolean = true,
    val blackCanCastleKingside: Boolean = true,
    val blackCanCastleQueenside: Boolean = true,
    val enPassantTarget: Square? = null,
    val halfMoveClock: Int = 0,
    val fullMoveNumber: Int = 1,
    val moveHistory: List<Move> = emptyList(),
    val positionHashes: List<Long> = listOf(
        computePositionHash(
            board = board,
            currentPlayer = currentPlayer,
            whiteCanCastleKingside = whiteCanCastleKingside,
            whiteCanCastleQueenside = whiteCanCastleQueenside,
            blackCanCastleKingside = blackCanCastleKingside,
            blackCanCastleQueenside = blackCanCastleQueenside,
            enPassantTarget = enPassantTarget
        )
    ),
    val gameResult: GameResult = GameResult.ONGOING
) {
    companion object {
        private const val ZOBRIST_SEED = 0L
        private const val PIECE_TYPE_COUNT = 6
        private const val COLOR_COUNT = 2

        private val pieceSquareKeys: Array<LongArray> = Array(PIECE_TYPE_COUNT * COLOR_COUNT) { LongArray(64) }
        private val sideToMoveKey: Long
        private val castlingKeys: LongArray
        private val enPassantFileKeys: LongArray

        init {
            val random = java.util.Random(ZOBRIST_SEED)
            for (pieceIndex in pieceSquareKeys.indices) {
                for (squareIndex in 0 until 64) {
                    pieceSquareKeys[pieceIndex][squareIndex] = random.nextLong()
                }
            }
            sideToMoveKey = random.nextLong()
            castlingKeys = LongArray(4) { random.nextLong() }
            enPassantFileKeys = LongArray(8) { random.nextLong() }
        }

        fun fromFen(fen: String): GameState {
            val parts = fen.split(" ")
            if (parts.size < 6) throw IllegalArgumentException("Invalid FEN string")

            val board = Board.fromFen(fen)
            val currentPlayer = if (parts[1] == "w") Color.WHITE else Color.BLACK

            // Castling rights
            val castling = parts[2]
            val whiteCanCastleKingside = castling.contains('K')
            val whiteCanCastleQueenside = castling.contains('Q')
            val blackCanCastleKingside = castling.contains('k')
            val blackCanCastleQueenside = castling.contains('q')

            // En passant target
            val enPassantTarget = if (parts[3] != "-") Square.fromAlgebraic(parts[3]) else null

            // Half move clock and full move number
            val halfMoveClock = parts[4].toIntOrNull() ?: 0
            val fullMoveNumber = parts[5].toIntOrNull() ?: 1

            return GameState(
                board = board,
                currentPlayer = currentPlayer,
                whiteCanCastleKingside = whiteCanCastleKingside,
                whiteCanCastleQueenside = whiteCanCastleQueenside,
                blackCanCastleKingside = blackCanCastleKingside,
                blackCanCastleQueenside = blackCanCastleQueenside,
                enPassantTarget = enPassantTarget,
                halfMoveClock = halfMoveClock,
                fullMoveNumber = fullMoveNumber
            )
        }

        private fun computePositionHash(
            board: Board,
            currentPlayer: Color,
            whiteCanCastleKingside: Boolean,
            whiteCanCastleQueenside: Boolean,
            blackCanCastleKingside: Boolean,
            blackCanCastleQueenside: Boolean,
            enPassantTarget: Square?
        ): Long {
            var hash = 0L
            for ((square, piece) in board.getAllPieces()) {
                val pieceIndex = pieceIndex(piece)
                hash = hash xor pieceSquareKeys[pieceIndex][square.index]
            }
            if (currentPlayer == Color.BLACK) {
                hash = hash xor sideToMoveKey
            }
            if (whiteCanCastleKingside) hash = hash xor castlingKeys[0]
            if (whiteCanCastleQueenside) hash = hash xor castlingKeys[1]
            if (blackCanCastleKingside) hash = hash xor castlingKeys[2]
            if (blackCanCastleQueenside) hash = hash xor castlingKeys[3]
            if (enPassantTarget != null) {
                hash = hash xor enPassantFileKeys[enPassantTarget.file]
            }
            return hash
        }

        private fun pieceIndex(piece: Piece): Int {
            val colorOffset = if (piece.color == Color.WHITE) 0 else PIECE_TYPE_COUNT
            val typeIndex = when (piece.type) {
                PieceType.PAWN -> 0
                PieceType.KNIGHT -> 1
                PieceType.BISHOP -> 2
                PieceType.ROOK -> 3
                PieceType.QUEEN -> 4
                PieceType.KING -> 5
            }
            return colorOffset + typeIndex
        }
    }

    fun toFen(): String {
        val sb = StringBuilder()
        sb.append(board.toFen())
        sb.append(" ")
        sb.append(if (currentPlayer == Color.WHITE) "w" else "b")
        sb.append(" ")

        // Castling rights
        val castlingRights = StringBuilder()
        if (whiteCanCastleKingside) castlingRights.append('K')
        if (whiteCanCastleQueenside) castlingRights.append('Q')
        if (blackCanCastleKingside) castlingRights.append('k')
        if (blackCanCastleQueenside) castlingRights.append('q')
        if (castlingRights.isEmpty()) castlingRights.append('-')
        sb.append(castlingRights)
        sb.append(" ")

        // En passant target
        sb.append(enPassantTarget?.algebraic ?: "-")
        sb.append(" ")
        sb.append(halfMoveClock)
        sb.append(" ")
        sb.append(fullMoveNumber)

        return sb.toString()
    }

    fun makeMove(move: Move): GameState {
        val newBoard = board.copy()
        val movingPiece = board.getPiece(move.from)!!

        // Move the piece
        newBoard.setPiece(move.from, null)

        // Handle special moves
        var newEnPassantTarget: Square? = null
        var newHalfMoveClock = halfMoveClock + 1

        when {
            move.isCastling -> {
                // Castling is handled in MoveValidator.performCastling
                MoveValidator.performCastling(newBoard, move)
                newHalfMoveClock = 0
            }
            move.isEnPassant -> {
                // En passant capture is handled in MoveValidator.performEnPassant
                MoveValidator.performEnPassant(newBoard, move, this)
                newHalfMoveClock = 0
            }
            move.promotion != null -> {
                newBoard.setPiece(move.to, Piece(move.promotion, movingPiece.color))
                newHalfMoveClock = 0
            }
            movingPiece.type == PieceType.PAWN -> {
                // Check for double pawn push (en passant opportunity)
                if (Math.abs(move.to.rank - move.from.rank) == 2) {
                    val direction = if (movingPiece.color == Color.WHITE) 1 else -1
                    newEnPassantTarget = Square(move.from.file, move.from.rank + direction)
                }
                newHalfMoveClock = 0
            }
            else -> {
                newBoard.setPiece(move.to, movingPiece)
            }
        }

        // Update castling rights
        var newWhiteCanCastleKingside = whiteCanCastleKingside
        var newWhiteCanCastleQueenside = whiteCanCastleQueenside
        var newBlackCanCastleKingside = blackCanCastleKingside
        var newBlackCanCastleQueenside = blackCanCastleQueenside

        if (movingPiece.type == PieceType.KING) {
            if (movingPiece.color == Color.WHITE) {
                newWhiteCanCastleKingside = false
                newWhiteCanCastleQueenside = false
            } else {
                newBlackCanCastleKingside = false
                newBlackCanCastleQueenside = false
            }
        } else if (movingPiece.type == PieceType.ROOK) {
            when (move.from) {
                Square(0, 0) -> newWhiteCanCastleQueenside = false
                Square(7, 0) -> newWhiteCanCastleKingside = false
                Square(0, 7) -> newBlackCanCastleQueenside = false
                Square(7, 7) -> newBlackCanCastleKingside = false
            }
        }

        // Check if rook was captured, affecting castling rights
        val capturedPiece = board.getPiece(move.to)
        if (capturedPiece?.type == PieceType.ROOK) {
            when (move.to) {
                Square(0, 0) -> newWhiteCanCastleQueenside = false
                Square(7, 0) -> newWhiteCanCastleKingside = false
                Square(0, 7) -> newBlackCanCastleQueenside = false
                Square(7, 7) -> newBlackCanCastleKingside = false
            }
        }

        // Update turn and move numbers
        val newCurrentPlayer = currentPlayer.opposite()
        val newFullMoveNumber = if (currentPlayer == Color.BLACK) fullMoveNumber + 1 else fullMoveNumber

        val newMoveHistory = moveHistory + move

        val newPositionHash = computePositionHash(
            board = newBoard,
            currentPlayer = newCurrentPlayer,
            whiteCanCastleKingside = newWhiteCanCastleKingside,
            whiteCanCastleQueenside = newWhiteCanCastleQueenside,
            blackCanCastleKingside = newBlackCanCastleKingside,
            blackCanCastleQueenside = newBlackCanCastleQueenside,
            enPassantTarget = newEnPassantTarget
        )
        val newPositionHashes = positionHashes + newPositionHash

        val provisionalState = GameState(
            board = newBoard,
            currentPlayer = newCurrentPlayer,
            whiteCanCastleKingside = newWhiteCanCastleKingside,
            whiteCanCastleQueenside = newWhiteCanCastleQueenside,
            blackCanCastleKingside = newBlackCanCastleKingside,
            blackCanCastleQueenside = newBlackCanCastleQueenside,
            enPassantTarget = newEnPassantTarget,
            halfMoveClock = newHalfMoveClock,
            fullMoveNumber = newFullMoveNumber,
            moveHistory = newMoveHistory,
            positionHashes = newPositionHashes,
            gameResult = GameResult.ONGOING
        )

        // Check for game end conditions
        val newGameResult = provisionalState.checkGameResult()

        return provisionalState.copy(gameResult = newGameResult)
    }

    private fun checkGameResult(): GameResult {
        // Check for checkmate/stalemate
        val legalMoves = MoveValidator.generateLegalMoves(board, this)
        val isInCheck = MoveValidator.isKingInCheck(board, currentPlayer)

        return when {
            legalMoves.isEmpty() && isInCheck -> {
                if (currentPlayer == Color.WHITE) GameResult.BLACK_WINS else GameResult.WHITE_WINS
            }
            legalMoves.isEmpty() -> GameResult.DRAW // Stalemate
            halfMoveClock >= 100 -> GameResult.DRAW // 50-move rule
            isThreefoldRepetition(positionHashes) -> GameResult.DRAW
            isInsufficientMaterial(board) -> GameResult.DRAW
            else -> GameResult.ONGOING
        }
    }

    private fun isThreefoldRepetition(positionHashes: List<Long>): Boolean {
        val seen = mutableMapOf<Long, Int>()
        for (hash in positionHashes) {
            val count = (seen[hash] ?: 0) + 1
            if (count >= 3) {
                return true
            }
            seen[hash] = count
        }
        return false
    }

    private fun isInsufficientMaterial(board: Board): Boolean {
        val pieces = board.getAllPieces()
        val nonKingPieces = pieces.filter { it.second.type != PieceType.KING }

        // King vs King
        if (nonKingPieces.isEmpty()) return true

        // King and Bishop vs King
        if (nonKingPieces.size == 1 && nonKingPieces[0].second.type == PieceType.BISHOP) return true

        // King and Knight vs King
        if (nonKingPieces.size == 1 && nonKingPieces[0].second.type == PieceType.KNIGHT) return true

        // King and Bishop vs King and Bishop (same color bishops)
        if (nonKingPieces.size == 2 &&
            nonKingPieces.all { it.second.type == PieceType.BISHOP } &&
            nonKingPieces[0].second.color != nonKingPieces[1].second.color) {
            // Check if bishops are on same color squares
            val bishop1Square = nonKingPieces[0].first
            val bishop2Square = nonKingPieces[1].first
            val bishop1Color = (bishop1Square.file + bishop1Square.rank) % 2
            val bishop2Color = (bishop2Square.file + bishop2Square.rank) % 2
            return bishop1Color == bishop2Color
        }

        return false
    }

    fun isGameOver(): Boolean = gameResult != GameResult.ONGOING

    fun getWinner(): Color? = when (gameResult) {
        GameResult.WHITE_WINS -> Color.WHITE
        GameResult.BLACK_WINS -> Color.BLACK
        else -> null
    }

    private fun Color.opposite(): Color = if (this == Color.WHITE) Color.BLACK else Color.WHITE
}
