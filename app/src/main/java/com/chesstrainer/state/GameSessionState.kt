package com.chesstrainer.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chesstrainer.chess.GameState

object GameSessionState {
    var currentGameState by mutableStateOf(GameState())
}
