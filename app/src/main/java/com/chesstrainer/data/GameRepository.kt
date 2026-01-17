package com.chesstrainer.data

import android.content.Context
import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow

class GameRepository(context: Context) {
    private val database = ChessTrainerDatabase.getInstance(context)
    private val dao = database.gameDao()

    val games: Flow<List<GameEntity>> = dao.getGames()
    val results: Flow<List<GameResultEntity>> = dao.getResults()
    val ratings: Flow<List<PlayerRatingEntity>> = dao.getRatings()

    suspend fun getLatestRating(): PlayerRatingEntity? = dao.getLatestRating()

    suspend fun recordCompletedGame(
        game: GameEntity,
        result: GameResultEntity,
        rating: PlayerRatingEntity
    ) {
        database.withTransaction {
            val gameId = dao.insertGame(game)
            dao.insertGameResult(result.copy(gameId = gameId))
            dao.insertPlayerRating(rating.copy(gameId = gameId))
        }
    }
}
