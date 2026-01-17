package com.chesstrainer.utils

import android.content.Context
import android.content.SharedPreferences
import com.chesstrainer.chess.Color as ChessColor

enum class EngineType {
    LEELA_CHESS_ZERO,
    STOCKFISH
}

class Settings(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("chess_trainer_settings", Context.MODE_PRIVATE)

    var engineType: EngineType
        get() = EngineType.valueOf(preferences.getString("engine_type", EngineType.LEELA_CHESS_ZERO.name)!!)
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

    var boardOrientation: ChessColor
        get() = ChessColor.valueOf(preferences.getString("board_orientation", ChessColor.WHITE.name)!!)
        set(value) = preferences.edit().putString("board_orientation", value.name).apply()
}
