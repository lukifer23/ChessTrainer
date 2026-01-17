package com.chesstrainer.chess

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class PgnMetadata(
    val event: String = "ChessTrainer",
    val site: String = "Local",
    val date: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
    val round: String = "-",
    val white: String = "White",
    val black: String = "Black",
    val result: String? = null
)

object PgnWriter {
    fun write(
        gameState: GameState,
        initialState: GameState = GameState(),
        metadata: PgnMetadata = PgnMetadata()
    ): String {
        val resultTag = metadata.result ?: resultFromGameResult(gameState.gameResult)
        val headerLines = listOf(
            """[Event "${metadata.event}"]""",
            """[Site "${metadata.site}"]""",
            """[Date "${metadata.date}"]""",
            """[Round "${metadata.round}"]""",
            """[White "${metadata.white}"]""",
            """[Black "${metadata.black}"]""",
            """[Result "$resultTag"]"""
        )
        val moves = formatMoves(initialState, gameState.moveHistory, resultTag)
        return headerLines.joinToString("\n") + "\n\n" + moves
    }

    private fun formatMoves(initialState: GameState, moveHistory: List<Move>, result: String): String {
        val builder = StringBuilder()
        var state = initialState.copy(moveHistory = emptyList(), gameResult = GameResult.ONGOING)

        for (move in moveHistory) {
            if (state.currentPlayer == Color.WHITE) {
                builder.append("${state.fullMoveNumber}. ")
            } else if (builder.isEmpty()) {
                builder.append("${state.fullMoveNumber}... ")
            }

            val san = AlgebraicNotation.moveToAlgebraic(move, state)
            val nextState = state.makeMove(move)
            val annotated = AlgebraicNotation.addCheckAnnotations(san, move, nextState)
            builder.append(annotated).append(' ')
            state = nextState
        }

        builder.append(result)
        return builder.toString().trim()
    }

    private fun resultFromGameResult(gameResult: GameResult): String {
        return when (gameResult) {
            GameResult.WHITE_WINS -> "1-0"
            GameResult.BLACK_WINS -> "0-1"
            GameResult.DRAW -> "1/2-1/2"
            GameResult.ONGOING -> "*"
        }
    }
}
