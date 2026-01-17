package com.chesstrainer.export

import com.chesstrainer.chess.GameResult
import com.chesstrainer.chess.GameState
import com.chesstrainer.chess.MoveHistoryFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PgnExporter {
    fun export(
        gameState: GameState,
        whiteName: String = "White",
        blackName: String = "Black",
        event: String = "Chess Trainer",
        site: String = "Local",
        round: String = "1",
        date: Date = Date()
    ): String {
        val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val resultTag = resultTag(gameState)
        val headers = buildList {
            add("[Event \"$event\"]")
            add("[Site \"$site\"]")
            add("[Date \"${formatter.format(date)}\"]")
            add("[Round \"$round\"]")
            add("[White \"$whiteName\"]")
            add("[Black \"$blackName\"]")
            add("[Result \"$resultTag\"]")
            add("[Termination \"${termination(gameState)}\"]")
        }

        val moves = formatMoves(gameState)
        val pgnBody = buildString {
            if (moves.isNotBlank()) {
                append(moves)
                append(" ")
            }
            append(resultTag)
        }

        return headers.joinToString("\n") + "\n\n" + pgnBody.trim()
    }

    private fun formatMoves(gameState: GameState): String {
        val moves = MoveHistoryFormatter.formatMoveHistory(gameState)
        if (moves.isEmpty()) return ""

        val builder = StringBuilder()
        moves.forEachIndexed { index, move ->
            val moveNumber = index / 2 + 1
            if (index % 2 == 0) {
                if (builder.isNotEmpty()) builder.append(" ")
                builder.append("$moveNumber. $move")
            } else {
                builder.append(" $move")
            }
        }
        return builder.toString()
    }

    private fun resultTag(gameState: GameState): String = when (gameState.gameResult) {
        GameResult.WHITE_WINS -> "1-0"
        GameResult.BLACK_WINS -> "0-1"
        GameResult.DRAW -> "1/2-1/2"
        GameResult.ONGOING -> "*"
    }

    private fun termination(gameState: GameState): String = when (gameState.gameResult) {
        GameResult.WHITE_WINS,
        GameResult.BLACK_WINS,
        GameResult.DRAW -> "Normal"
        GameResult.ONGOING -> "Unterminated"
    }
}
