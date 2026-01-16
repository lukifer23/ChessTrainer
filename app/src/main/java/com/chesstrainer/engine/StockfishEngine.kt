package com.chesstrainer.engine

import android.content.Context
import com.chesstrainer.chess.GameState
import com.chesstrainer.chess.Move
import com.chesstrainer.utils.Settings
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Stockfish chess engine implementation using UCI protocol.
 * Provides Stockfish-specific configuration and search capabilities.
 */
class StockfishEngine(private val context: Context, private val settings: Settings) : ChessEngine {

    private var engineManager: EngineManager? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var isInitialized = false

    override fun getBestMove(gameState: GameState, callback: (Move) -> Unit) {
        scope.launch {
            try {
                ensureInitialized()

                val searchParams = EngineManager.SearchParams(
                    depth = settings.stockfishDepth.takeIf { it > 0 },
                    moveTime = 2000L // 2 second default move time
                )

                engineManager?.startSearch(
                    gameState = gameState,
                    onBestMove = { move ->
                        callback(move)
                    },
                    searchParams = searchParams
                )
            } catch (e: Exception) {
                // Fallback to simple engine if Stockfish fails
                SimpleChessEngine().getBestMove(gameState, callback)
            }
        }
    }

    override fun startNewGame() {
        scope.launch {
            try {
                ensureInitialized()
                engineManager?.newGame()
            } catch (e: Exception) {
                // Log error but don't throw
            }
        }
    }

    override fun cleanup() {
        scope.launch {
            engineManager?.cleanup()
            engineManager = null
            isInitialized = false
            scope.cancel()
        }
    }

    /**
     * Ensure the engine is initialized and ready
     */
    private suspend fun ensureInitialized() {
        if (isInitialized && engineManager?.isReady() == true) {
            return
        }

        try {
            engineManager = EngineManager(context, settings)

            // Start the engine
            engineManager?.startEngine()?.getOrElse { error ->
                throw Exception("Failed to start Stockfish engine: ${error.message}")
            }

            // Configure Stockfish-specific options
            configureStockfish()

            isInitialized = true
        } catch (e: Exception) {
            isInitialized = false
            throw Exception("Failed to initialize Stockfish engine: ${e.message}", e)
        }
    }

    /**
     * Configure Stockfish-specific options
     */
    private suspend fun configureStockfish() {
        engineManager?.configureEngine()

        // Additional Stockfish-specific configuration
        val options = listOf(
            "Threads" to Runtime.getRuntime().availableProcessors(),
            "Hash" to 128, // MB
            "Skill Level" to (settings.stockfishDepth.takeIf { it in 1..20 } ?: 15),
            "UCI_LimitStrength" to false,
            "UCI_Elo" to null // Use full strength
        )

        for ((option, value) in options) {
            try {
                if (value != null) {
                    engineManager?.setOption(option, value)
                }
            } catch (e: Exception) {
                // Some options might not be supported in all Stockfish versions
            }
        }
    }

    /**
     * Get detailed analysis for a position
     */
    suspend fun getAnalysis(
        gameState: GameState,
        depth: Int = settings.stockfishDepth,
        multiPV: Int = 1
    ): Result<List<AnalysisResult>> = suspendCoroutine { continuation ->
        scope.launch {
            try {
                ensureInitialized()

                val results = mutableListOf<AnalysisResult>()
                var bestMove: Move? = null

                val searchParams = EngineManager.SearchParams(
                    depth = depth,
                    infinite = false
                )

                engineManager?.startSearch(
                    gameState = gameState,
                    onBestMove = { move ->
                        bestMove = move
                        continuation.resume(Result.success(results))
                    },
                    onInfo = { info ->
                        // Collect principal variations
                        if (info.principalVariation.isNotEmpty()) {
                            val analysis = AnalysisResult(
                                depth = info.depth ?: 0,
                                score = info.score,
                                principalVariation = info.principalVariation,
                                time = info.time ?: 0,
                                nodes = info.nodes ?: 0,
                                nodesPerSecond = info.nodesPerSecond ?: 0
                            )
                            results.add(analysis)

                            // Limit to requested multiPV
                            if (results.size >= multiPV) {
                                scope.launch {
                                    engineManager?.stopSearch()
                                }
                            }
                        }
                    },
                    searchParams = searchParams
                )

            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    /**
     * Evaluate a position without searching for moves
     */
    suspend fun evaluatePosition(gameState: GameState, depth: Int = 8): Result<PositionEvaluation> = suspendCoroutine { continuation ->
        scope.launch {
            try {
                ensureInitialized()

                var evaluation: UCIParser.Score? = null
                var time = 0L
                var nodes = 0L

                // Set position
                engineManager?.setPosition(gameState)

                // Start evaluation (short search)
                val searchParams = EngineManager.SearchParams(
                    depth = depth,
                    moveTime = 500 // 500ms should be enough for evaluation
                )

                engineManager?.startSearch(
                    gameState = gameState,
                    onBestMove = { _ ->
                        // Evaluation complete
                        val result = PositionEvaluation(
                            score = evaluation,
                            time = time,
                            nodes = nodes,
                            depth = depth
                        )
                        continuation.resume(Result.success(result))
                    },
                    onInfo = { info ->
                        evaluation = info.score
                        time = info.time ?: 0
                        nodes = info.nodes ?: 0
                    },
                    searchParams = searchParams
                )

            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    /**
     * Analysis result for a position
     */
    data class AnalysisResult(
        val depth: Int,
        val score: UCIParser.Score?,
        val principalVariation: List<Move>,
        val time: Long,
        val nodes: Long,
        val nodesPerSecond: Long
    )

    /**
     * Position evaluation result
     */
    data class PositionEvaluation(
        val score: UCIParser.Score?,
        val time: Long,
        val nodes: Long,
        val depth: Int
    )

    /**
     * Get engine information
     */
    fun getEngineInfo(): String {
        return engineManager?.getEngineName() ?: "Stockfish"
    }

    /**
     * Check if engine is ready
     */
    fun isReady(): Boolean {
        return isInitialized && engineManager?.isReady() == true
    }
}