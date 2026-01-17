package com.chesstrainer.chess

object MoveHistoryFormatter {
    fun formatMoveHistory(gameState: GameState): List<String> {
        var tempState = GameState()
        val formattedMoves = mutableListOf<String>()

        for (move in gameState.moveHistory) {
            val algebraic = AlgebraicNotation.moveToAlgebraic(move, tempState)
            val nextState = tempState.makeMove(move)
            val annotated = AlgebraicNotation.addCheckAnnotations(algebraic, move, nextState)
            formattedMoves.add(annotated)
            tempState = nextState
        }

        return formattedMoves
    }
}
