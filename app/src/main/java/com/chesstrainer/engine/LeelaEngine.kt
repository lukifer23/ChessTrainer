package com.chesstrainer.engine

import android.content.Context
import com.chesstrainer.utils.Settings

/**
 * LeelaChess0 neural network chess engine implementation using UCI protocol.
 * Provides Leela-specific configuration and search capabilities.
 */
class LeelaEngine(context: Context, settings: Settings) : BaseEngine(context, settings, "LeelaEngine") {

    override suspend fun configureEngine() {
        configureLeela()
    }

    override fun createSearchParams(): EngineManager.SearchParams {
        return EngineManager.SearchParams(
            nodes = settings.leelaNodes.takeIf { it > 0 }?.toLong(),
            moveTime = 3000L // 3 second default for neural network evaluation
        )
    }

    override fun getStartErrorMessage(): String = "Failed to start LeelaChess0 engine"

    private suspend fun configureLeela() {
        val opts = mutableMapOf<String, String>()
        opts["Threads"] = settings.lc0Threads.toString()
        opts["Backend"] = settings.lc0Backend
        
        settings.customLc0WeightsPath?.let { path ->
            if (path.isNotEmpty()) {
                opts["WeightsFile"] = path
            }
        }
        
        // Apply settings
        opts.forEach { (key, value) ->
            try {
                engineManager?.setOption(key, value)
            } catch (e: Exception) {
                android.util.Log.w("LeelaEngine", "Failed to set option $key", e)
            }
        }
    }
}
