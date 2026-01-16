package com.chesstrainer.chess

data class Move(
    val from: Square,
    val to: Square,
    val promotion: PieceType? = null,
    val isEnPassant: Boolean = false,
    val isCastling: Boolean = false
) {
    companion object {
        fun fromUci(uci: String): Move? {
            if (uci.length < 4 || uci.length > 5) return null

            val fromSquare = Square.fromAlgebraic(uci.substring(0, 2)) ?: return null
            val toSquare = Square.fromAlgebraic(uci.substring(2, 4)) ?: return null

            var promotion: PieceType? = null
            if (uci.length == 5) {
                promotion = when (uci[4]) {
                    'q' -> PieceType.QUEEN
                    'r' -> PieceType.ROOK
                    'b' -> PieceType.BISHOP
                    'n' -> PieceType.KNIGHT
                    else -> return null
                }
            }

            return Move(fromSquare, toSquare, promotion)
        }
    }

    val uci: String
        get() {
            val base = "${from.algebraic}${to.algebraic}"
            return if (promotion != null) {
                base + when (promotion) {
                    PieceType.QUEEN -> 'q'
                    PieceType.ROOK -> 'r'
                    PieceType.BISHOP -> 'b'
                    PieceType.KNIGHT -> 'n'
                    else -> ""
                }
            } else base
        }

    override fun toString(): String = uci
}