package com.chesstrainer.data

data class GameSummary(
    val gameId: Long,
    val playedAt: Long,
    val mode: String,
    val engineType: String,
    val engineConfig: String,
    val engineVersion: String,
    val timeControl: String,
    val analysisDepth: String,
    val whiteElo: Int?,
    val blackElo: Int?,
    val result: String,
    val outcome: String?,
    val score: Double?,
    val moveCount: Int,
    val moves: String
)
