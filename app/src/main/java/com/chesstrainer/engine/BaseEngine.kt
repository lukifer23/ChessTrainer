package com.chesstrainer.engine

import android.content.Context
import com.chesstrainer.chess.GameState
import com.chesstrainer.chess.Move
import com.chesstrainer.utils.Settings
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.chesstrainer.engine.EngineManager
import com.chesstrainer.engine.UCIParser

/**
 * Abstract base class for chess engines.
 * Handles lifecycle, process management, and common UCI operations.
 */
abstract class BaseEngine(
    protected val context: Context,
    protected val settings: Settings,
    private val engineTag: String
) : ChessEngine {

    protected var engineManager: EngineManager? = null
    protected val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    protected var isInitialized = false

    override fun startNewGame() {
        scope.launch {
            try {
                ensureInitialized()
                engineManager?.cancelActiveSearch()
                engineManager?.newGame()
            } catch (e: Exception) {
                android.util.Log.e(engineTag, "Error starting new game", e)
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

    suspend fun initialize(onStatusUpdate: (String) -> Unit = {}): Result<Unit> {
        return runCatching { ensureInitialized(onStatusUpdate) }
    }

    protected suspend fun ensureInitialized(onStatusUpdate: (String) -> Unit = {}) {
        if (isInitialized && engineManager?.isReady() == true) return

        try {
            engineManager = EngineManager(context, settings)
            engineManager?.startEngine(onStatusUpdate)?.getOrElse { error ->
                throw Exception("${getStartErrorMessage()}: ${error.message}")
            }
            
            configureEngine()
            isInitialized = true
        } catch (e: Exception) {
            isInitialized = false
            throw Exception("Failed to initialize $engineTag: ${e.message}", e)
        }
    }

    override fun getBestMove(
        gameState: GameState,
        callback: (Move) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        scope.launch {
            try {
                android.util.Log.d(engineTag, "Starting getBestMove")
                ensureInitialized()
                
                val searchParams = createSearchParams()
                
                engineManager?.startSearch(
                    gameState = gameState,
                    onBestMove = { move ->
                        android.util.Log.d(engineTag, "Best move found: $move")
                        callback(move)
                    },
                    searchParams = searchParams
                )
            } catch (e: Exception) {
                android.util.Log.e(engineTag, "Error in getBestMove", e)
                onError(e)
            }
        }
    }
    
    fun getEngineName(): String? = engineManager?.getEngineName()

    protected abstract suspend fun configureEngine()
    protected abstract fun createSearchParams(): EngineManager.SearchParams
    protected abstract fun getStartErrorMessage(): String

    /**
     * Get detailed analysis for a position
     */
    suspend fun getAnalysis(
        gameState: GameState,
        depth: Int? = null,
        nodes: Long? = null,
        @Suppress("UNUSED_PARAMETER") multiPV: Int = 1
    ): Result<List<AnalysisResult>> = suspendCancellableCoroutine { continuation ->
        scope.launch {
            try {
                ensureInitialized()

                val results = mutableListOf<AnalysisResult>()
                var bestMove: Move? = null

                // Determine search limits based on engine type/settings
                // This logic might need to be refined per engine, but for now we take optional overrides
                // or default to "infinite" if not specified, but usually analysis is specific.
                // Re-using createSearchParams() might be limiting if it enforces time/depth.
                // Let's create a generic search param here.
                val searchParams = EngineManager.SearchParams(
                    depth = depth,
                    nodes = nodes,
                    infinite = depth == null && nodes == null
                )

                engineManager?.startSearch(
                    gameState = gameState,
                    onBestMove = { _ ->
                        if (continuation.isActive) {
                            continuation.resume(Result.success(results))
                        }
                    },
                    onInfo = { info ->
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
                            // Note: real multiPV logic usually comes in one info line or multiple lines per depth
                            // This simple accumulation might need helpers from EngineManager to structure it correctly.
                            // For now, restoring previous logic structure.
                        }
                    },
                    searchParams = searchParams
                )
             } catch (e: Exception) {
                if (continuation.isActive) {
                    continuation.resumeWithException(e)
                }
            }
        }
    }

    /**
     * Evaluate a position without searching for moves
     */
    suspend fun evaluatePosition(gameState: GameState, depth: Int = 8): Result<PositionEvaluation> = suspendCancellableCoroutine { continuation ->
        scope.launch {
            try {
                ensureInitialized()

                var evaluation: UCIParser.Score? = null
                var time = 0L
                var nodes = 0L

                engineManager?.setPosition(gameState)

                val searchParams = EngineManager.SearchParams(
                    depth = depth,
                    moveTime = 500
                )

                engineManager?.startSearch(
                    gameState = gameState,
                    onBestMove = { _ ->
                        val result = PositionEvaluation(
                            score = evaluation,
                            time = time,
                            nodes = nodes,
                            depth = depth
                        )
                        if (continuation.isActive) {
                            continuation.resume(Result.success(result))
                        }
                    },
                    onInfo = { info ->
                        evaluation = info.score
                        time = info.time ?: 0
                        nodes = info.nodes ?: 0
                    },
                    searchParams = searchParams
                )
            } catch (e: Exception) {
                if (continuation.isActive) {
                    continuation.resumeWithException(e)
                }
            }
        }
    }

    fun getEngineInfo(): String {
        return engineManager?.getEngineName() ?: engineTag
    }
}

data class AnalysisResult(
    val depth: Int,
    val score: UCIParser.Score?,
    val principalVariation: List<Move>,
    val time: Long,
    val nodes: Long,
    val nodesPerSecond: Long
)

data class PositionEvaluation(
    val score: UCIParser.Score?,
    val time: Long,
    val nodes: Long,
    val depth: Int
)
