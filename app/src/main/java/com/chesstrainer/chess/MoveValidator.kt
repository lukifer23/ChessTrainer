package com.chesstrainer.chess

class MoveValidator {
    companion object {
        fun isValidMove(board: Board, move: Move, gameState: GameState): Boolean {
            val piece = board.getPiece(move.from) ?: return false

            // Check if it's the correct color to move
            if (piece.color != gameState.currentPlayer) return false

            // Check if the destination is valid for this piece
            if (!isValidDestination(board, move, piece)) return false

            // Check if the move would leave the king in check
            val newBoard = board.copy()
            newBoard.setPiece(move.from, null)
            newBoard.setPiece(move.to, piece)

            // Handle special moves
            when {
                move.isCastling -> {
                    if (!isValidCastling(board, move, gameState)) return false
                    performCastling(newBoard, move)
                }
                move.isEnPassant -> {
                    if (!isValidEnPassant(board, move, gameState)) return false
                    performEnPassant(newBoard, move, gameState)
                }
                move.promotion != null -> {
                    newBoard.setPiece(move.to, Piece(move.promotion, piece.color))
                }
            }

            // Check if the move leaves own king in check
            return !isKingInCheck(newBoard, piece.color)
        }

        fun generateLegalMoves(board: Board, gameState: GameState): List<Move> {
            val moves = mutableListOf<Move>()
            val pieces = board.getPieces(gameState.currentPlayer)

            for ((square, piece) in pieces) {
                val pieceMoves = generatePieceMoves(board, square, piece, gameState)
                moves.addAll(pieceMoves)
            }

            return moves.filter { isValidMove(board, it, gameState) }
        }

        private fun generatePieceMoves(board: Board, from: Square, piece: Piece, gameState: GameState): List<Move> {
            return when (piece.type) {
                PieceType.PAWN -> generatePawnMoves(board, from, piece, gameState)
                PieceType.ROOK -> generateRookMoves(board, from, piece)
                PieceType.KNIGHT -> generateKnightMoves(board, from, piece)
                PieceType.BISHOP -> generateBishopMoves(board, from, piece)
                PieceType.QUEEN -> generateQueenMoves(board, from, piece)
                PieceType.KING -> generateKingMoves(board, from, piece, gameState)
            }
        }

        private fun generatePawnMoves(board: Board, from: Square, piece: Piece, gameState: GameState): List<Move> {
            val moves = mutableListOf<Move>()
            val direction = if (piece.color == Color.WHITE) 1 else -1
            val startRank = if (piece.color == Color.WHITE) 1 else 6

            // Forward moves
            val oneStep = Square(from.file, from.rank + direction)
            if (oneStep.isValid() && board.isEmpty(oneStep)) {
                if (oneStep.rank == 7 || oneStep.rank == 0) {
                    // Promotion
                    PieceType.values().filter { it != PieceType.PAWN && it != PieceType.KING }.forEach { promotion ->
                        moves.add(Move(from, oneStep, promotion))
                    }
                } else {
                    moves.add(Move(from, oneStep))
                }

                // Two-step move from starting position
                if (from.rank == startRank) {
                    val twoStep = Square(from.file, from.rank + 2 * direction)
                    if (twoStep.isValid() && board.isEmpty(twoStep)) {
                        moves.add(Move(from, twoStep))
                    }
                }
            }

            // Captures
            for (fileOffset in listOf(-1, 1)) {
                val captureSquare = Square(from.file + fileOffset, from.rank + direction)
                if (captureSquare.isValid()) {
                    val capturedPiece = board.getPiece(captureSquare)
                    if (capturedPiece != null && capturedPiece.color != piece.color) {
                        if (captureSquare.rank == 7 || captureSquare.rank == 0) {
                            // Promotion capture
                            PieceType.values().filter { it != PieceType.PAWN && it != PieceType.KING }.forEach { promotion ->
                                moves.add(Move(from, captureSquare, promotion))
                            }
                        } else {
                            moves.add(Move(from, captureSquare))
                        }
                    } else if (captureSquare == gameState.enPassantTarget) {
                        // En passant
                        moves.add(Move(from, captureSquare, isEnPassant = true))
                    }
                }
            }

            return moves
        }

        private fun generateRookMoves(board: Board, from: Square, piece: Piece): List<Move> {
            return generateSlidingMoves(board, from, piece, listOf(
                Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0)
            ))
        }

        private fun generateBishopMoves(board: Board, from: Square, piece: Piece): List<Move> {
            return generateSlidingMoves(board, from, piece, listOf(
                Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1)
            ))
        }

        private fun generateQueenMoves(board: Board, from: Square, piece: Piece): List<Move> {
            return generateSlidingMoves(board, from, piece, listOf(
                Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0),
                Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1)
            ))
        }

        private fun generateSlidingMoves(board: Board, from: Square, piece: Piece, directions: List<Pair<Int, Int>>): List<Move> {
            val moves = mutableListOf<Move>()

            for ((deltaFile, deltaRank) in directions) {
                var file = from.file + deltaFile
                var rank = from.rank + deltaRank

                while (file in 0..7 && rank in 0..7) {
                    val square = Square(file, rank)
                    val occupyingPiece = board.getPiece(square)

                    if (occupyingPiece == null) {
                        moves.add(Move(from, square))
                    } else {
                        if (occupyingPiece.color != piece.color) {
                            moves.add(Move(from, square))
                        }
                        break
                    }

                    file += deltaFile
                    rank += deltaRank
                }
            }

            return moves
        }

        private fun generateKnightMoves(board: Board, from: Square, piece: Piece): List<Move> {
            val moves = mutableListOf<Move>()
            val knightMoves = listOf(
                Pair(2, 1), Pair(2, -1), Pair(-2, 1), Pair(-2, -1),
                Pair(1, 2), Pair(1, -2), Pair(-1, 2), Pair(-1, -2)
            )

            for ((deltaFile, deltaRank) in knightMoves) {
                val file = from.file + deltaFile
                val rank = from.rank + deltaRank

                if (file in 0..7 && rank in 0..7) {
                    val square = Square(file, rank)
                    val occupyingPiece = board.getPiece(square)

                    if (occupyingPiece == null || occupyingPiece.color != piece.color) {
                        moves.add(Move(from, square))
                    }
                }
            }

            return moves
        }

        private fun generateKingMoves(board: Board, from: Square, piece: Piece, gameState: GameState): List<Move> {
            val moves = mutableListOf<Move>()
            val kingMoves = listOf(
                Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0),
                Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1)
            )

            for ((deltaFile, deltaRank) in kingMoves) {
                val file = from.file + deltaFile
                val rank = from.rank + deltaRank

                if (file in 0..7 && rank in 0..7) {
                    val square = Square(file, rank)
                    val occupyingPiece = board.getPiece(square)

                    if (occupyingPiece == null || occupyingPiece.color != piece.color) {
                        moves.add(Move(from, square))
                    }
                }
            }

            // Castling moves
            if (piece.color == Color.WHITE) {
                if (gameState.whiteCanCastleKingside && !isKingInCheck(board, Color.WHITE)) {
                    val kingsideRookSquare = Square(7, 0)
                    val kingsideRook = board.getPiece(kingsideRookSquare)
                    if (kingsideRook?.type == PieceType.ROOK && kingsideRook.color == Color.WHITE) {
                        val f1 = Square(5, 0)
                        val g1 = Square(6, 0)
                        if (board.isEmpty(f1) && board.isEmpty(g1)) {
                            // Check if squares are attacked
                            val tempBoard = board.copy()
                            tempBoard.setPiece(from, null)
                            tempBoard.setPiece(f1, Piece(PieceType.KING, Color.WHITE))
                            if (!isSquareAttacked(tempBoard, f1, Color.BLACK)) {
                                tempBoard.setPiece(f1, null)
                                tempBoard.setPiece(g1, Piece(PieceType.KING, Color.WHITE))
                                if (!isSquareAttacked(tempBoard, g1, Color.BLACK)) {
                                    moves.add(Move(from, g1, isCastling = true))
                                }
                            }
                        }
                    }
                }
                if (gameState.whiteCanCastleQueenside && !isKingInCheck(board, Color.WHITE)) {
                    val queensideRookSquare = Square(0, 0)
                    val queensideRook = board.getPiece(queensideRookSquare)
                    if (queensideRook?.type == PieceType.ROOK && queensideRook.color == Color.WHITE) {
                        val b1 = Square(1, 0)
                        val c1 = Square(2, 0)
                        val d1 = Square(3, 0)
                        if (board.isEmpty(b1) && board.isEmpty(c1) && board.isEmpty(d1)) {
                            val tempBoard = board.copy()
                            tempBoard.setPiece(from, null)
                            tempBoard.setPiece(d1, Piece(PieceType.KING, Color.WHITE))
                            if (!isSquareAttacked(tempBoard, d1, Color.BLACK)) {
                                tempBoard.setPiece(d1, null)
                                tempBoard.setPiece(c1, Piece(PieceType.KING, Color.WHITE))
                                if (!isSquareAttacked(tempBoard, c1, Color.BLACK)) {
                                    moves.add(Move(from, c1, isCastling = true))
                                }
                            }
                        }
                    }
                }
            } else {
                if (gameState.blackCanCastleKingside && !isKingInCheck(board, Color.BLACK)) {
                    val kingsideRookSquare = Square(7, 7)
                    val kingsideRook = board.getPiece(kingsideRookSquare)
                    if (kingsideRook?.type == PieceType.ROOK && kingsideRook.color == Color.BLACK) {
                        val f8 = Square(5, 7)
                        val g8 = Square(6, 7)
                        if (board.isEmpty(f8) && board.isEmpty(g8)) {
                            val tempBoard = board.copy()
                            tempBoard.setPiece(from, null)
                            tempBoard.setPiece(f8, Piece(PieceType.KING, Color.BLACK))
                            if (!isSquareAttacked(tempBoard, f8, Color.WHITE)) {
                                tempBoard.setPiece(f8, null)
                                tempBoard.setPiece(g8, Piece(PieceType.KING, Color.BLACK))
                                if (!isSquareAttacked(tempBoard, g8, Color.WHITE)) {
                                    moves.add(Move(from, g8, isCastling = true))
                                }
                            }
                        }
                    }
                }
                if (gameState.blackCanCastleQueenside && !isKingInCheck(board, Color.BLACK)) {
                    val queensideRookSquare = Square(0, 7)
                    val queensideRook = board.getPiece(queensideRookSquare)
                    if (queensideRook?.type == PieceType.ROOK && queensideRook.color == Color.BLACK) {
                        val b8 = Square(1, 7)
                        val c8 = Square(2, 7)
                        val d8 = Square(3, 7)
                        if (board.isEmpty(b8) && board.isEmpty(c8) && board.isEmpty(d8)) {
                            val tempBoard = board.copy()
                            tempBoard.setPiece(from, null)
                            tempBoard.setPiece(d8, Piece(PieceType.KING, Color.BLACK))
                            if (!isSquareAttacked(tempBoard, d8, Color.WHITE)) {
                                tempBoard.setPiece(d8, null)
                                tempBoard.setPiece(c8, Piece(PieceType.KING, Color.BLACK))
                                if (!isSquareAttacked(tempBoard, c8, Color.WHITE)) {
                                    moves.add(Move(from, c8, isCastling = true))
                                }
                            }
                        }
                    }
                }
            }

            return moves
        }

        private fun isValidDestination(board: Board, move: Move, piece: Piece): Boolean {
            val destinationPiece = board.getPiece(move.to)
            return destinationPiece == null || destinationPiece.color != piece.color
        }

        private fun isValidCastling(board: Board, move: Move, gameState: GameState): Boolean {
            // Castling validation is handled in generateKingMoves
            return true
        }

        private fun isValidEnPassant(board: Board, move: Move, gameState: GameState): Boolean {
            return move.to == gameState.enPassantTarget
        }

        fun performCastling(board: Board, move: Move) {
            val king = board.getPiece(move.from)!!
            val isWhite = king.color == Color.WHITE
            val isKingside = move.to.file == 6

            if (isWhite) {
                if (isKingside) {
                    // White kingside: e1-g1, h1-f1
                    board.setPiece(Square(7, 0), null) // Remove rook from h1
                    board.setPiece(Square(5, 0), Piece(PieceType.ROOK, Color.WHITE)) // Place rook on f1
                } else {
                    // White queenside: e1-c1, a1-d1
                    board.setPiece(Square(0, 0), null) // Remove rook from a1
                    board.setPiece(Square(3, 0), Piece(PieceType.ROOK, Color.WHITE)) // Place rook on d1
                }
            } else {
                if (isKingside) {
                    // Black kingside: e8-g8, h8-f8
                    board.setPiece(Square(7, 7), null) // Remove rook from h8
                    board.setPiece(Square(5, 7), Piece(PieceType.ROOK, Color.BLACK)) // Place rook on f8
                } else {
                    // Black queenside: e8-c8, a8-d8
                    board.setPiece(Square(0, 7), null) // Remove rook from a8
                    board.setPiece(Square(3, 7), Piece(PieceType.ROOK, Color.BLACK)) // Place rook on d8
                }
            }
        }

        fun performEnPassant(board: Board, move: Move, gameState: GameState) {
            val capturedPawnSquare = Square(move.to.file, move.from.rank)
            board.setPiece(capturedPawnSquare, null)
        }

        fun isKingInCheck(board: Board, color: Color): Boolean {
            val kingSquare = findKing(board, color) ?: return false
            return isSquareAttacked(board, kingSquare, color.opposite())
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

        private fun isSquareAttacked(board: Board, square: Square, byColor: Color): Boolean {
            // Check for attacks from all piece types
            val attackingPieces = board.getPieces(byColor)

            for ((attackerSquare, piece) in attackingPieces) {
                when (piece.type) {
                    PieceType.PAWN -> if (canPawnAttack(board, attackerSquare, square, piece.color)) return true
                    PieceType.ROOK -> if (canRookAttack(board, attackerSquare, square)) return true
                    PieceType.KNIGHT -> if (canKnightAttack(attackerSquare, square)) return true
                    PieceType.BISHOP -> if (canBishopAttack(board, attackerSquare, square)) return true
                    PieceType.QUEEN -> if (canQueenAttack(board, attackerSquare, square)) return true
                    PieceType.KING -> if (canKingAttack(attackerSquare, square)) return true
                }
            }

            return false
        }

        private fun canPawnAttack(board: Board, from: Square, to: Square, color: Color): Boolean {
            val direction = if (color == Color.WHITE) 1 else -1
            return to.rank == from.rank + direction && Math.abs(to.file - from.file) == 1
        }

        private fun canRookAttack(board: Board, from: Square, to: Square): Boolean {
            if (from.file != to.file && from.rank != to.rank) return false
            return isPathClear(board, from, to)
        }

        private fun canBishopAttack(board: Board, from: Square, to: Square): Boolean {
            if (Math.abs(from.file - to.file) != Math.abs(from.rank - to.rank)) return false
            return isPathClear(board, from, to)
        }

        private fun canQueenAttack(board: Board, from: Square, to: Square): Boolean {
            return canRookAttack(board, from, to) || canBishopAttack(board, from, to)
        }

        private fun canKnightAttack(from: Square, to: Square): Boolean {
            val fileDiff = Math.abs(from.file - to.file)
            val rankDiff = Math.abs(from.rank - to.rank)
            return (fileDiff == 2 && rankDiff == 1) || (fileDiff == 1 && rankDiff == 2)
        }

        private fun canKingAttack(from: Square, to: Square): Boolean {
            val fileDiff = Math.abs(from.file - to.file)
            val rankDiff = Math.abs(from.rank - to.rank)
            return fileDiff <= 1 && rankDiff <= 1 && !(fileDiff == 0 && rankDiff == 0)
        }

        private fun isPathClear(board: Board, from: Square, to: Square): Boolean {
            val fileStep = when {
                to.file > from.file -> 1
                to.file < from.file -> -1
                else -> 0
            }
            val rankStep = when {
                to.rank > from.rank -> 1
                to.rank < from.rank -> -1
                else -> 0
            }

            var file = from.file + fileStep
            var rank = from.rank + rankStep

            while (file != to.file || rank != to.rank) {
                if (board.isOccupied(Square(file, rank))) return false
                file += fileStep
                rank += rankStep
            }

            return true
        }

        private fun Color.opposite(): Color = if (this == Color.WHITE) Color.BLACK else Color.WHITE
    }
}