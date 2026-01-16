package com.chesstrainer.engine

import android.content.Context
import com.chesstrainer.chess.GameState
import com.chesstrainer.chess.Move
import com.chesstrainer.utils.Settings
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * LeelaChess0 neural network chess engine implementation using UCI protocol.
 * Provides Leela-specific configuration and search capabilities.
 */
class LeelaEngine(private val context: Context, private val settings: Settings) : ChessEngine {

    private var engineManager: EngineManager? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var isInitialized = false

    override fun getBestMove(gameState: GameState, callback: (Move) -> Unit) {
        scope.launch {
            try {
                ensureInitialized()

                val searchParams = EngineManager.SearchParams(
                    nodes = settings.leelaNodes.takeIf { it > 0 }?.toLong(),
                    moveTime = 3000L // 3 second default for neural network evaluation
                )

                engineManager?.startSearch(
                    gameState = gameState,
                    onBestMove = { move ->
                        callback(move)
                    },
                    searchParams = searchParams
                )
            } catch (e: Exception) {
                // Fallback to simple engine if Leela fails
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
                throw Exception("Failed to start LeelaChess0 engine: ${error.message}")
            }

            // Configure Leela-specific options
            configureLeela()

            isInitialized = true
        } catch (e: Exception) {
            isInitialized = false
            throw Exception("Failed to initialize LeelaChess0 engine: ${e.message}", e)
        }
    }

    /**
     * Configure LeelaChess0-specific options
     */
    private suspend fun configureLeela() {
        engineManager?.configureEngine()

        // Leela-specific configuration
        val options = listOf(
            "MaxNodes" to (settings.leelaNodes.takeIf { it > 0 } ?: 1000),
            "Threads" to Runtime.getRuntime().availableProcessors(),
            "NNCacheSize" to 200000, // Neural network cache size
            "Backend" to "eigen", // CPU backend for Android
            "WeightsFile" to "" // Use default weights
        )

        for ((option, value) in options) {
            try {
                engineManager?.setOption(option, value)
            } catch (e: Exception) {
                // Some options might not be supported or available
            }
        }
    }

    /**
     * Get detailed analysis for a position using neural network evaluation
     */
    suspend fun getAnalysis(
        gameState: GameState,
        maxNodes: Int = settings.leelaNodes
    ): Result<AnalysisResult> = suspendCoroutine { continuation ->
        scope.launch {
            try {
                ensureInitialized()

                var bestMove: Move? = null
                var evaluation: UCIParser.Score? = null
                var time = 0L
                var nodes = 0L
                var principalVariation = emptyList<Move>()

                val searchParams = EngineManager.SearchParams(
                    nodes = maxNodes.toLong(),
                    infinite = false
                )

                engineManager?.startSearch(
                    gameState = gameState,
                    onBestMove = { move ->
                        bestMove = move
                        val result = AnalysisResult(
                            bestMove = move,
                            evaluation = evaluation,
                            principalVariation = principalVariation,
                            time = time,
                            nodes = nodes,
                            maxNodes = maxNodes
                        )
                        continuation.resume(Result.success(result))
                    },
                    onInfo = { info ->
                        evaluation = info.score
                        time = info.time ?: 0
                        nodes = info.nodes ?: 0
                        if (info.principalVariation.isNotEmpty()) {
                            principalVariation = info.principalVariation
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
     * Evaluate a position using neural network
     */
    suspend fun evaluatePosition(gameState: GameState, maxNodes: Int = 1000): Result<PositionEvaluation> = suspendCoroutine { continuation ->
        scope.launch {
            try {
                ensureInitialized()

                var evaluation: UCIParser.Score? = null
                var time = 0L
                var nodes = 0L

                // Set position
                engineManager?.setPosition(gameState)

                // Start evaluation
                val searchParams = EngineManager.SearchParams(
                    nodes = maxNodes.toLong(),
                    moveTime = 1000 // 1 second for evaluation
                )

                engineManager?.startSearch(
                    gameState = gameState,
                    onBestMove = { _ ->
                        // Evaluation complete
                        val result = PositionEvaluation(
                            score = evaluation,
                            time = time,
                            nodes = nodes,
                            maxNodes = maxNodes
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
        val bestMove: Move,
        val evaluation: UCIParser.Score?,
        val principalVariation: List<Move>,
        val time: Long,
        val nodes: Long,
        val maxNodes: Int
    )

    /**
     * Position evaluation result
     */
    data class PositionEvaluation(
        val score: UCIParser.Score?,
        val time: Long,
        val nodes: Long,
        val maxNodes: Int
    )

    /**
     * Get engine information
     */
    fun getEngineInfo(): String {
        return engineManager?.getEngineName() ?: "LeelaChess0"
    }

    /**
     * Check if engine is ready
     */
    fun isReady(): Boolean {
        return isInitialized && engineManager?.isReady() == true
    }
}