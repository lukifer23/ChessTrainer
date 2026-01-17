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
    val result: String,
    val moves: String,
    val moveCount: Int
)
