package com.chesstrainer.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "player_ratings",
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
data class PlayerRatingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: Long = 0,
    val ratingBefore: Int,
    val ratingAfter: Int,
    val delta: Int,
    val recordedAt: Long
)
