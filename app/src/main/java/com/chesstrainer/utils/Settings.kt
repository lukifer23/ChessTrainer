package com.chesstrainer.utils

import android.content.Context
import android.content.SharedPreferences
import com.chesstrainer.chess.Color as ChessColor

enum class EngineType {
    LEELA_CHESS_ZERO,
    STOCKFISH,
    GGUF
}

class Settings(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("chess_trainer_settings", Context.MODE_PRIVATE)

    var engineType: EngineType
        get() = try {
            EngineType.valueOf(preferences.getString("engine_type", EngineType.LEELA_CHESS_ZERO.name)!!)
        } catch (e: Exception) {
            EngineType.LEELA_CHESS_ZERO
        }
        set(value) = preferences.edit().putString("engine_type", value.name).apply()

    var leelaNodes: Int
        get() = preferences.getInt("leela_nodes", 1000)
        set(value) = preferences.edit().putInt("leela_nodes", value).apply()

    var stockfishDepth: Int
        get() = preferences.getInt("stockfish_depth", 15)
        set(value) = preferences.edit().putInt("stockfish_depth", value).apply()

    var lc0Threads: Int
        get() = preferences.getInt("lc0_threads", 2)
        set(value) = preferences.edit().putInt("lc0_threads", value).apply()

    var lc0Backend: String
        get() = preferences.getString("lc0_backend", "cpu")!!
        set(value) = preferences.edit().putString("lc0_backend", value).apply()

    var lc0BackendOptions: Set<String>
        get() = preferences.getStringSet("lc0_backend_options", emptySet()) ?: emptySet()
        set(value) = preferences.edit().putStringSet("lc0_backend_options", value).apply()

    var boardOrientation: ChessColor
        get() = ChessColor.valueOf(preferences.getString("board_orientation", ChessColor.WHITE.name)!!)
        set(value) = preferences.edit().putString("board_orientation", value.name).apply()

    var customLc0WeightsPath: String?
        get() = preferences.getString("custom_lc0_weights_path", null)
        set(value) = preferences.edit().putString("custom_lc0_weights_path", value).apply()

    var ggufModelPath: String?
        get() = preferences.getString("gguf_model_path", null)
        set(value) = preferences.edit().putString("gguf_model_path", value).apply()
}
