package com.chesstrainer.utils

import kotlin.math.pow

object EloCalculator {
    fun expectedScore(playerRating: Int, opponentRating: Int): Double {
        val exponent = (opponentRating - playerRating) / 400.0
        return 1.0 / (1.0 + 10.0.pow(exponent))
    }

    fun updateRating(playerRating: Int, opponentRating: Int, score: Double, kFactor: Int = 32): Int {
        val expected = expectedScore(playerRating, opponentRating)
        return (playerRating + kFactor * (score - expected)).toInt()
    }
}
