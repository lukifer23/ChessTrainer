package com.chesstrainer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val playedAt: Long,
    val mode: String,
    val engineType: String,
    val opponentRating: Int,
    val engineDepth: Int? = null,
    val leelaNodes: Int? = null,
    val result: String,
    val moves: String,
    val moveCount: Int
)
