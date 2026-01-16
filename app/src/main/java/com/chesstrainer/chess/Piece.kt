package com.chesstrainer.chess

enum class PieceType {
    PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
}

enum class Color {
    WHITE, BLACK
}

data class Piece(
    val type: PieceType,
    val color: Color
) {
    companion object {
        // Piece value for evaluation (centipawns)
        fun getValue(pieceType: PieceType): Int {
            return when (pieceType) {
                PieceType.PAWN -> 100
                PieceType.KNIGHT -> 320
                PieceType.BISHOP -> 330
                PieceType.ROOK -> 500
                PieceType.QUEEN -> 900
                PieceType.KING -> 20000
            }
        }

        // Unicode symbols for pieces
        fun getSymbol(piece: Piece): String {
            return when (piece.color) {
                Color.WHITE -> when (piece.type) {
                    PieceType.PAWN -> "♙"
                    PieceType.ROOK -> "♖"
                    PieceType.KNIGHT -> "♘"
                    PieceType.BISHOP -> "♗"
                    PieceType.QUEEN -> "♕"
                    PieceType.KING -> "♔"
                }
                Color.BLACK -> when (piece.type) {
                    PieceType.PAWN -> "♟"
                    PieceType.ROOK -> "♜"
                    PieceType.KNIGHT -> "♞"
                    PieceType.BISHOP -> "♝"
                    PieceType.QUEEN -> "♛"
                    PieceType.KING -> "♚"
                }
            }
        }

        // FEN notation characters
        fun getFenChar(piece: Piece): Char {
            val char = when (piece.type) {
                PieceType.PAWN -> 'p'
                PieceType.ROOK -> 'r'
                PieceType.KNIGHT -> 'n'
                PieceType.BISHOP -> 'b'
                PieceType.QUEEN -> 'q'
                PieceType.KING -> 'k'
            }
            return if (piece.color == Color.WHITE) char.uppercaseChar() else char
        }

        fun fromFenChar(char: Char): Piece? {
            val color = if (char.isUpperCase()) Color.WHITE else Color.BLACK
            val type = when (char.lowercaseChar()) {
                'p' -> PieceType.PAWN
                'r' -> PieceType.ROOK
                'n' -> PieceType.KNIGHT
                'b' -> PieceType.BISHOP
                'q' -> PieceType.QUEEN
                'k' -> PieceType.KING
                else -> return null
            }
            return Piece(type, color)
        }
    }

    val value: Int
        get() = getValue(type)

    val symbol: String
        get() = getSymbol(this)

    val fenChar: Char
        get() = getFenChar(this)
}