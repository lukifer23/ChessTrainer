package com.chesstrainer.chess

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GameStateTest {
    @Test
    fun threefoldRepetitionIsDetectedFromKnightLoop() {
        var state = GameState()
        val loopMoves = listOf("g1f3", "g8f6", "f3g1", "f6g8")

        repeat(2) {
            for (uci in loopMoves) {
                val move = Move.fromUci(uci) ?: error("Invalid UCI: $uci")
                state = state.makeMove(move)
            }
        }

        assertEquals(GameResult.DRAW, state.gameResult)
    }

    @Test
    fun positionHashIncludesCastlingRights() {
        var state = GameState.fromFen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1")
        val initialHash = state.positionHashes.last()
        val moves = listOf("h1h2", "h8h7", "h2h1", "h7h8")

        for (uci in moves) {
            val move = Move.fromUci(uci) ?: error("Invalid UCI: $uci")
            state = state.makeMove(move)
        }

        val finalHash = state.positionHashes.last()
        assertNotEquals(initialHash, finalHash)
        assertEquals(GameResult.ONGOING, state.gameResult)
    }

    @Test
    fun positionHashIncludesEnPassantTarget() {
        val withEnPassant = GameState.fromFen("8/8/8/8/4P3/8/8/4k2K b - e3 0 1")
        val withoutEnPassant = GameState.fromFen("8/8/8/8/4P3/8/8/4k2K b - - 0 1")

        assertNotEquals(
            withEnPassant.positionHashes.last(),
            withoutEnPassant.positionHashes.last()
        )
    }
}
