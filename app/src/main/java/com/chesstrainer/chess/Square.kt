package com.chesstrainer.chess

data class Square(val file: Int, val rank: Int) {
    companion object {
        fun fromAlgebraic(algebraic: String): Square? {
            if (algebraic.length != 2) return null
            val file = algebraic[0] - 'a'
            val rank = algebraic[1] - '1'
            if (file !in 0..7 || rank !in 0..7) return null
            return Square(file, rank)
        }

        fun fromIndex(index: Int): Square {
            return Square(index % 8, index / 8)
        }
    }

    val index: Int
        get() = rank * 8 + file

    val algebraic: String
        get() = "${('a' + file)}${('1' + rank)}"

    override fun toString(): String = algebraic

    fun isValid(): Boolean = file in 0..7 && rank in 0..7
}