package com.chesstrainer.chess

/**
 * Utilities for converting chess moves to and from standard algebraic notation.
 * Handles all special cases including disambiguation, checks, checkmates, etc.
 */
object AlgebraicNotation {

    /**
     * Convert a move to standard algebraic notation
     */
    fun moveToAlgebraic(move: Move, gameState: GameState): String {
        val movingPiece = gameState.board.getPiece(move.from) ?: return "?"

        return when {
            move.isCastling -> getCastlingNotation(move)
            else -> getPieceMoveNotation(move, movingPiece, gameState)
        }
    }

    private fun getCastlingNotation(move: Move): String {
        return when {
            move.to.file == 6 -> "O-O"   // Kingside castling
            move.to.file == 2 -> "O-O-O" // Queenside castling
            else -> "O-O" // Fallback
        }
    }

    private fun getPieceMoveNotation(move: Move, piece: Piece, gameState: GameState): String {
        val pieceSymbol = getPieceSymbol(piece.type)
        val capture = gameState.board.getPiece(move.to) != null || move.isEnPassant
        val captureSymbol = if (capture) "x" else ""
        val destination = move.to.algebraic
        val promotion = move.promotion?.let { "=${getPieceSymbol(it)}" } ?: ""

        // For pawn moves, don't include piece symbol
        val piecePart = if (piece.type == PieceType.PAWN) "" else pieceSymbol

        // For pawn captures, include file from which pawn came
        val disambiguation = when {
            piece.type == PieceType.PAWN && capture -> "${move.from.algebraic[0]}"
            else -> getDisambiguation(move, piece, gameState)
        }

        return "$piecePart$disambiguation$captureSymbol$destination$promotion"
    }

    private fun getPieceSymbol(pieceType: PieceType): String {
        return when (pieceType) {
            PieceType.KING -> "K"
            PieceType.QUEEN -> "Q"
            PieceType.ROOK -> "R"
            PieceType.BISHOP -> "B"
            PieceType.KNIGHT -> "N"
            PieceType.PAWN -> "" // Pawns don't have a symbol in algebraic notation
        }
    }

    /**
     * Get disambiguation characters when multiple pieces of the same type
     * can move to the same square
     */
    private fun getDisambiguation(move: Move, piece: Piece, gameState: GameState): String {
        if (piece.type == PieceType.PAWN) return ""

        // Find all pieces of the same type and color that can move to the target square
        val sameTypePieces = gameState.board.getPieces(piece.color)
            .filter { (square, p) -> p.type == piece.type }

        val ambiguousPieces = sameTypePieces.filter { (square, _) ->
            val testMove = Move(square, move.to)
            MoveValidator.isValidMove(gameState.board, testMove, gameState)
        }

        if (ambiguousPieces.size <= 1) return ""

        // Check if files are different
        val sameFilePieces = ambiguousPieces.filter { it.first.file == move.from.file }
        if (sameFilePieces.size <= 1) {
            return move.from.algebraic[0].toString() // File disambiguation
        }

        // Check if ranks are different
        val sameRankPieces = ambiguousPieces.filter { it.first.rank == move.from.rank }
        if (sameRankPieces.size <= 1) {
            return move.from.algebraic[1].toString() // Rank disambiguation
        }

        // Need both file and rank
        return move.from.algebraic
    }

    /**
     * Add check/checkmate annotations to a move
     */
    fun addCheckAnnotations(moveAlgebraic: String, move: Move, resultingGameState: GameState): String {
        return when {
            resultingGameState.isGameOver() -> {
                when (resultingGameState.gameResult) {
                    GameResult.WHITE_WINS -> "$moveAlgebraic#"
                    GameResult.BLACK_WINS -> "$moveAlgebraic#"
                    else -> moveAlgebraic // Draw, no annotation
                }
            }
            MoveValidator.isKingInCheck(resultingGameState.board, resultingGameState.currentPlayer) -> {
                "$moveAlgebraic+"
            }
            else -> moveAlgebraic
        }
    }

    /**
     * Parse algebraic notation to a move (simplified implementation)
     */
    fun algebraicToMove(algebraic: String, gameState: GameState): Move? {
        // Remove check/checkmate symbols
        val cleanAlgebraic = algebraic.replace("+", "").replace("#", "")

        // Handle castling
        if (cleanAlgebraic == "O-O" || cleanAlgebraic == "O-O-O") {
            return getCastlingMove(cleanAlgebraic, gameState)
        }

        // Parse regular moves
        return parseRegularMove(cleanAlgebraic, gameState)
    }

    private fun getCastlingMove(algebraic: String, gameState: GameState): Move? {
        val kingSquare = findKing(gameState.board, gameState.currentPlayer) ?: return null

        return when (algebraic) {
            "O-O" -> { // Kingside
                val targetFile = if (gameState.currentPlayer == Color.WHITE) 6 else 6
                Move(kingSquare, Square(targetFile, kingSquare.rank), isCastling = true)
            }
            "O-O-O" -> { // Queenside
                val targetFile = if (gameState.currentPlayer == Color.WHITE) 2 else 2
                Move(kingSquare, Square(targetFile, kingSquare.rank), isCastling = true)
            }
            else -> null
        }
    }

    private fun parseRegularMove(algebraic: String, gameState: GameState): Move? {
        // This is a simplified parser - a full implementation would be much more complex
        // For now, just handle basic cases

        val toSquare = Square.fromAlgebraic(algebraic.takeLast(2)) ?: return null
        val pieceType = when (algebraic.first()) {
            'K' -> PieceType.KING
            'Q' -> PieceType.QUEEN
            'R' -> PieceType.ROOK
            'B' -> PieceType.BISHOP
            'N' -> PieceType.KNIGHT
            else -> PieceType.PAWN // No piece symbol means pawn
        }

        // Find possible pieces that can move to this square
        val legalMoves = MoveValidator.generateLegalMoves(gameState.board, gameState)
        val matchingMoves = legalMoves.filter { move ->
            val piece = gameState.board.getPiece(move.from)
            piece?.type == pieceType && move.to == toSquare
        }

        return matchingMoves.firstOrNull()
    }

    private fun findKing(board: Board, color: Color): Square? {
        for (rank in 0..7) {
            for (file in 0..7) {
                val square = Square(file, rank)
                val piece = board.getPiece(square)
                if (piece?.type == PieceType.KING && piece.color == color) {
                    return square
                }
            }
        }
        return null
    }
}