package com.chesstrainer.chess

class Board {
    private val squares = Array<Piece?>(64) { null }

    companion object {
        fun fromFen(fen: String): Board {
            val board = Board()
            val parts = fen.split(" ")
            val piecePlacement = parts[0]

            var file = 0
            var rank = 7

            for (char in piecePlacement) {
                when {
                    char.isDigit() -> {
                        file += char.digitToInt()
                    }
                    char == '/' -> {
                        rank--
                        file = 0
                    }
                    else -> {
                        val piece = Piece.fromFenChar(char)
                        if (piece != null) {
                            board.setPiece(Square(file, rank), piece)
                            file++
                        }
                    }
                }
            }

            return board
        }

        fun initialPosition(): Board {
            return fromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
        }
    }

    fun getPiece(square: Square): Piece? {
        return squares[square.index]
    }

    fun setPiece(square: Square, piece: Piece?) {
        squares[square.index] = piece
    }

    fun copy(): Board {
        val newBoard = Board()
        for (i in 0..63) {
            newBoard.squares[i] = squares[i]
        }
        return newBoard
    }

    fun isEmpty(square: Square): Boolean {
        return getPiece(square) == null
    }

    fun isOccupied(square: Square): Boolean {
        return !isEmpty(square)
    }

    fun getPieces(color: Color): List<Pair<Square, Piece>> {
        return squares.mapIndexedNotNull { index, piece ->
            piece?.let { Pair(Square.fromIndex(index), it) }
        }.filter { it.second.color == color }
    }

    fun getAllPieces(): List<Pair<Square, Piece>> {
        return squares.mapIndexedNotNull { index, piece ->
            piece?.let { Pair(Square.fromIndex(index), it) }
        }
    }

    fun toFen(): String {
        val sb = StringBuilder()

        for (rank in 7 downTo 0) {
            var emptyCount = 0
            for (file in 0..7) {
                val square = Square(file, rank)
                val piece = getPiece(square)
                if (piece != null) {
                    if (emptyCount > 0) {
                        sb.append(emptyCount)
                        emptyCount = 0
                    }
                    sb.append(piece.fenChar)
                } else {
                    emptyCount++
                }
            }
            if (emptyCount > 0) {
                sb.append(emptyCount)
            }
            if (rank > 0) {
                sb.append('/')
            }
        }

        return sb.toString()
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("  a b c d e f g h\n")
        sb.append(" ┌────────────────┐\n")

        for (rank in 7 downTo 0) {
            sb.append("${rank + 1}│")
            for (file in 0..7) {
                val square = Square(file, rank)
                val piece = getPiece(square)
                val symbol = piece?.symbol ?: " "
                sb.append("$symbol│")
            }
            sb.append("${rank + 1}\n")
            if (rank > 0) {
                sb.append(" ├────────────────┤\n")
            }
        }

        sb.append(" └────────────────┘\n")
        sb.append("  a b c d e f g h\n")
        return sb.toString()
    }
}