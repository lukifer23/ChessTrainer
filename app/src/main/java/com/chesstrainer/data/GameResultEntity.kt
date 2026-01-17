package com.chesstrainer.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "game_results",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("gameId")]
)
data class GameResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: Long = 0,
    val outcome: String,
    val score: Double,
    val analysisDepth: String,
    val timeControl: String,
    val whiteElo: Int?,
    val blackElo: Int?,
    val engineVersion: String
)
