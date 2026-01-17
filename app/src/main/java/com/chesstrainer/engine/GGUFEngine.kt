package com.chesstrainer.engine

import android.content.Context
import com.chesstrainer.chess.GameState
import com.chesstrainer.chess.Move
import com.chesstrainer.utils.Settings
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * GGUF/LLM chess engine implementation.
 * Wraps a local LLM runtime (e.g. llama-cli) to performing inference on GGUF models.
 */
class GGUFEngine(private val context: Context, private val settings: Settings) : ChessEngine {

    private var engineManager: EngineManager? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var isInitialized = false

    fun getEngineName(): String? = engineManager?.getEngineName()

    override fun getBestMove(
        gameState: GameState,
        callback: (Move) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        scope.launch {
            try {
                ensureInitialized()
                // LLM inference needs specific prompt formatting
                // For now, we reuse the generic EngineManager search which sends UCI 'position' and 'go'
                // This assumes the LLM binary speaks UCI or we wrap it.
                // If it's raw llama-cli, we might need a custom 'Connector'.
                // For this "Real" implementation, we assume a UCI-compatible wrapper or adapter.
                
                val searchParams = EngineManager.SearchParams(
                    moveTime = 5000L // LLMs are slow
                )

                engineManager?.startSearch(
                    gameState = gameState,
                    onBestMove = { move -> callback(move) },
                    searchParams = searchParams
                )
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    override fun startNewGame() {
        scope.launch {
            try {
                ensureInitialized()
                engineManager?.newGame()
            } catch (e: Exception) { }
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

    private suspend fun ensureInitialized(onStatusUpdate: (String) -> Unit = {}) {
        if (isInitialized && engineManager?.isReady() == true) return

        try {
            engineManager = EngineManager(context, settings)
            // Start engine (checks for GGUF binary/model availability)
            engineManager?.startEngine(onStatusUpdate)?.getOrElse { error ->
                throw Exception("Failed to start GGUF engine: ${error.message}")
            }
            isInitialized = true
        } catch (e: Exception) {
            isInitialized = false
            throw Exception("Failed to initialize GGUF engine: ${e.message}", e)
        }
    }

    fun isReady(): Boolean = isInitialized && engineManager?.isReady() == true
}
