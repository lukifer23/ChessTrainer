package com.chesstrainer.engine

import com.chesstrainer.chess.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

interface ChessEngine {
    fun getBestMove(gameState: GameState, callback: (Move) -> Unit)
    fun startNewGame()
    fun cleanup()
}

class SimpleChessEngine : ChessEngine {
    private var searchDepth = 3

    override fun getBestMove(gameState: GameState, callback: (Move) -> Unit) {
        // Use minimax algorithm to find the best move
        val legalMoves = MoveValidator.generateLegalMoves(gameState.board, gameState)

        if (legalMoves.isEmpty()) {
            callback(Move(Square(0, 0), Square(0, 0))) // Invalid move, should not happen
            return
        }

        var bestMove = legalMoves.first()
        var bestScore = Int.MIN_VALUE

        for (move in legalMoves) {
            val newGameState = gameState.makeMove(move)
            val score = -minimax(newGameState, searchDepth - 1, Int.MIN_VALUE, Int.MAX_VALUE)

            if (score > bestScore) {
                bestScore = score
                bestMove = move
            }
        }

        callback(bestMove)
    }

    override fun startNewGame() {
        // Reset any game-specific state
    }

    override fun cleanup() {
        // Cleanup resources if needed
    }

    private fun minimax(gameState: GameState, depth: Int, alpha: Int, beta: Int): Int {
        if (depth == 0 || gameState.isGameOver()) {
            return evaluatePosition(gameState)
        }

        val legalMoves = MoveValidator.generateLegalMoves(gameState.board, gameState)

        if (legalMoves.isEmpty()) {
            return if (MoveValidator.isKingInCheck(gameState.board, gameState.currentPlayer)) {
                // Checkmate
                if (gameState.currentPlayer == Color.WHITE) Int.MIN_VALUE + 1000 else Int.MAX_VALUE - 1000
            } else {
                // Stalemate
                0
            }
        }

        var alphaVar = alpha
        var betaVar = beta

        for (move in legalMoves) {
            val newGameState = gameState.makeMove(move)
            val score = -minimax(newGameState, depth - 1, -betaVar, -alphaVar)

            if (score >= betaVar) {
                return betaVar // Beta cutoff
            }

            if (score > alphaVar) {
                alphaVar = score
            }
        }

        return alphaVar
    }

    private fun evaluatePosition(gameState: GameState): Int {
        if (gameState.isGameOver()) {
            return when (gameState.gameResult) {
                GameResult.WHITE_WINS -> Int.MAX_VALUE - 100
                GameResult.BLACK_WINS -> Int.MIN_VALUE + 100
                else -> 0
            }
        }

        var score = 0

        // Material evaluation
        for ((square, piece) in gameState.board.getAllPieces()) {
            val pieceValue = when (piece.type) {
                PieceType.PAWN -> 100
                PieceType.KNIGHT -> 320
                PieceType.BISHOP -> 330
                PieceType.ROOK -> 500
                PieceType.QUEEN -> 900
                PieceType.KING -> 20000
            }

            val positionalBonus = getPositionalBonus(piece, square)
            val totalValue = pieceValue + positionalBonus

            score += if (piece.color == Color.WHITE) totalValue else -totalValue
        }

        // Check bonus
        if (MoveValidator.isKingInCheck(gameState.board, Color.BLACK)) {
            score += 50
        }
        if (MoveValidator.isKingInCheck(gameState.board, Color.WHITE)) {
            score -= 50
        }

        // Mobility bonus
        val whiteMoves = MoveValidator.generateLegalMoves(gameState.board, gameState.copy(currentPlayer = Color.WHITE)).size
        val blackMoves = MoveValidator.generateLegalMoves(gameState.board, gameState.copy(currentPlayer = Color.BLACK)).size
        score += (whiteMoves - blackMoves) * 10

        return score
    }

    private fun getPositionalBonus(piece: Piece, square: Square): Int {
        val centerSquares = listOf(Square(3, 3), Square(3, 4), Square(4, 3), Square(4, 4))
        val extendedCenter = (3..4).flatMap { file -> (2..5).map { rank -> Square(file, rank) } }

        return when (piece.type) {
            PieceType.PAWN -> {
                val advancement = if (piece.color == Color.WHITE) square.rank else 7 - square.rank
                advancement * 10 + if (centerSquares.contains(square)) 20 else if (extendedCenter.contains(square)) 10 else 0
            }
            PieceType.KNIGHT -> {
                // Knights are better in the center
                if (centerSquares.contains(square)) 30 else if (extendedCenter.contains(square)) 15 else 0
            }
            PieceType.BISHOP -> {
                // Bishops like open diagonals and center
                if (centerSquares.contains(square)) 20 else if (extendedCenter.contains(square)) 10 else 0
            }
            PieceType.ROOK -> {
                // Rooks like open files and 7th rank
                val seventhRank = if (piece.color == Color.WHITE) 6 else 1
                if (square.rank == seventhRank) 20 else 0
            }
            PieceType.QUEEN -> {
                // Queens like center and mobility
                if (centerSquares.contains(square)) 10 else 0
            }
            PieceType.KING -> {
                // King safety - prefer corners in endgame, center in opening
                if (square.file in listOf(2, 3, 4, 5) && square.rank in listOf(2, 3, 4, 5)) -10 else 0
            }
        }
    }
}