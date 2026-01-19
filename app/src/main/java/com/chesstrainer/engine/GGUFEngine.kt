package com.chesstrainer.engine

import android.content.Context
import com.chesstrainer.utils.Settings

/**
 * GGUF/LLM chess engine implementation.
 * Wraps a local LLM runtime to performing inference on GGUF models.
 */
class GGUFEngine(context: Context, settings: Settings) : BaseEngine(context, settings, "GGUFEngine") {

    override suspend fun configureEngine() {
        // No specific configuration for GGUF stub yet
    }

    override fun createSearchParams(): EngineManager.SearchParams {
        return EngineManager.SearchParams(
            moveTime = 5000L // LLMs are slow
        )
    }

    override fun getStartErrorMessage(): String = "Failed to start GGUF engine"
}
