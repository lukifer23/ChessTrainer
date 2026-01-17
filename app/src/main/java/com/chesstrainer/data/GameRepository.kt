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

    suspend fun getGameSummaries(): List<GameSummary> {
        val games = dao.getGamesList()
        val results = dao.getResultsList().associateBy { it.gameId }
        return games.map { game ->
            val result = results[game.id]
            GameSummary(
                gameId = game.id,
                playedAt = game.playedAt,
                mode = game.mode,
                engineType = game.engineType,
                engineConfig = game.engineConfig,
                engineVersion = game.engineVersion,
                timeControl = game.timeControl,
                analysisDepth = game.analysisDepth,
                whiteElo = game.whiteElo,
                blackElo = game.blackElo,
                result = game.result,
                outcome = result?.outcome,
                score = result?.score,
                moveCount = game.moveCount,
                moves = game.moves
            )
        }
    }
}
