package com.chesstrainer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: GameEntity): Long

    @Insert
    suspend fun insertGameResult(result: GameResultEntity)

    @Insert
    suspend fun insertPlayerRating(rating: PlayerRatingEntity)

    @Query("SELECT * FROM games ORDER BY playedAt DESC")
    fun getGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM game_results ORDER BY id DESC")
    fun getResults(): Flow<List<GameResultEntity>>

    @Query("SELECT * FROM player_ratings ORDER BY recordedAt ASC")
    fun getRatings(): Flow<List<PlayerRatingEntity>>

    @Query("SELECT * FROM player_ratings ORDER BY recordedAt DESC LIMIT 1")
    suspend fun getLatestRating(): PlayerRatingEntity?
}
