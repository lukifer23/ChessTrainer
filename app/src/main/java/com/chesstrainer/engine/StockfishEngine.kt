package com.chesstrainer.engine

import android.content.Context
import com.chesstrainer.utils.Settings

/**
 * Stockfish chess engine implementation using UCI protocol.
 * Provides Stockfish-specific configuration and search capabilities.
 */
class StockfishEngine(context: Context, settings: Settings) : BaseEngine(context, settings, "StockfishEngine") {

    override suspend fun configureEngine() {
        configureStockfish()
    }

    override fun createSearchParams(): EngineManager.SearchParams {
        return EngineManager.SearchParams(
            depth = settings.stockfishDepth.takeIf { it > 0 },
            moveTime = 2000L
        )
    }

    override fun getStartErrorMessage(): String = "Failed to start Stockfish engine"

    private suspend fun configureStockfish() {
        val opts = mutableMapOf<String, String>()
        opts["Threads"] = Runtime.getRuntime().availableProcessors().toString()
        opts["Hash"] = "16" // Default hash
        
        // Apply settings
        opts.forEach { (key, value) ->
            try {
                engineManager?.setOption(key, value)
            } catch (e: Exception) {
                android.util.Log.w("StockfishEngine", "Failed to set option $key", e)
            }
        }
    }
}
