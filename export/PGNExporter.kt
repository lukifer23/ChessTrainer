package com.chesstrainer.export

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.chesstrainer.chess.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Professional PGN (Portable Game Notation) exporter.
 * Handles complete PGN format with metadata, algebraic notation, and proper formatting.
 */
class PGNExporter(private val context: Context) {

    data class GameMetadata(
        val event: String = "Chess Trainer Game",
        val site: String = "Chess Trainer App",
        val date: String = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(Date()),
        val round: String = "-",
        val white: String = "Human",
        val black: String = "Engine",
        val result: String = "*",
        val timeControl: String = "-",
        val termination: String = "unterminated",
        val annotator: String = "Chess Trainer",
        val whiteElo: String = "-",
        val blackElo: String = "-",
        val eco: String = "-" // Encyclopedia of Chess Openings code
    )

    /**
     * Export a complete game to PGN format string
     */
    fun exportGame(gameState: GameState, metadata: GameMetadata = createDefaultMetadata(gameState)): String {
        val sb = StringBuilder()

        // Write metadata headers
        writeMetadata(sb, metadata)

        // Write moves
        writeMoves(sb, gameState)

        // Write result
        sb.appendLine(metadata.result)

        return sb.toString()
    }

    /**
     * Export game to a file and return the File object
     */
    fun exportGameToFile(gameState: GameState, metadata: GameMetadata = createDefaultMetadata(gameState)): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val filename = "chess_game_$timestamp.pgn"
        val file = File(context.filesDir, filename)

        file.writeText(exportGame(gameState, metadata))

        return file
    }

    /**
     * Share the game via Android's share intent
     */
    fun shareGame(gameState: GameState, metadata: GameMetadata = createDefaultMetadata(gameState)) {
        val pgnContent = exportGame(gameState, metadata)
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val filename = "chess_game_$timestamp.pgn"

        // Create a temporary file for sharing
        val tempFile = File(context.cacheDir, filename)
        tempFile.writeText(pgnContent)

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            tempFile
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Chess Game - ${metadata.white} vs ${metadata.black}")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share Chess Game"))
    }

    private fun writeMetadata(sb: StringBuilder, metadata: GameMetadata) {
        sb.appendLine("[Event \"${metadata.event}\"]")
        sb.appendLine("[Site \"${metadata.site}\"]")
        sb.appendLine("[Date \"${metadata.date}\"]")
        sb.appendLine("[Round \"${metadata.round}\"]")
        sb.appendLine("[White \"${metadata.white}\"]")
        sb.appendLine("[Black \"${metadata.black}\"]")
        sb.appendLine("[Result \"${metadata.result}\"]")
        sb.appendLine("[TimeControl \"${metadata.timeControl}\"]")
        sb.appendLine("[Termination \"${metadata.termination}\"]")

        if (metadata.annotator.isNotEmpty()) {
            sb.appendLine("[Annotator \"${metadata.annotator}\"]")
        }

        if (metadata.whiteElo != "-") {
            sb.appendLine("[WhiteElo \"${metadata.whiteElo}\"]")
        }

        if (metadata.blackElo != "-") {
            sb.appendLine("[BlackElo \"${metadata.blackElo}\"]")
        }

        if (metadata.eco != "-") {
            sb.appendLine("[ECO \"${metadata.eco}\"]")
        }

        sb.appendLine()
    }

    private fun writeMoves(sb: StringBuilder, gameState: GameState) {
        val moves = gameState.moveHistory
        if (moves.isEmpty()) return

        var currentGameState = GameState() // Start from initial position
        var moveNumber = 1

        for ((index, move) in moves.withIndex()) {
            // Write move number for white moves
            if (index % 2 == 0) {
                sb.append("$moveNumber. ")
            }

            // Convert move to algebraic notation
            val algebraicMove = AlgebraicNotation.moveToAlgebraic(move, currentGameState)

            // Create the game state after this move to check for check/checkmate
            val nextGameState = currentGameState.makeMove(move)
            val annotatedMove = AlgebraicNotation.addCheckAnnotations(algebraicMove, move, nextGameState)

            sb.append(annotatedMove)

            // Add space between moves
            if (index % 2 == 0) {
                sb.append(" ")
            } else {
                sb.append(" ")
                moveNumber++
            }

            currentGameState = nextGameState
        }
    }

    private fun createDefaultMetadata(gameState: GameState): GameMetadata {
        val result = when (gameState.gameResult) {
            GameResult.WHITE_WINS -> "1-0"
            GameResult.BLACK_WINS -> "0-1"
            GameResult.DRAW -> "1/2-1/2"
            else -> "*"
        }

        return GameMetadata(result = result)
    }

    companion object {

        /**
         * Create metadata for a game between two players
         */
        fun createMetadataForGame(
            whitePlayer: String = "Human",
            blackPlayer: String = "Engine",
            gameState: GameState,
            whiteElo: String = "-",
            blackElo: String = "-"
        ): GameMetadata {
            val result = when (gameState.gameResult) {
                GameResult.WHITE_WINS -> "1-0"
                GameResult.BLACK_WINS -> "0-1"
                GameResult.DRAW -> "1/2-1/2"
                else -> "*"
            }

            val termination = when (gameState.gameResult) {
                GameResult.WHITE_WINS -> "checkmate"
                GameResult.BLACK_WINS -> "checkmate"
                GameResult.DRAW -> {
                    when {
                        gameState.halfMoveClock >= 100 -> "50-move rule"
                        isThreefoldRepetition(gameState.moveHistory) -> "threefold repetition"
                        isInsufficientMaterial(gameState.board) -> "insufficient material"
                        else -> "stalemate"
                    }
                }
                else -> "unterminated"
            }

            return GameMetadata(
                white = whitePlayer,
                black = blackPlayer,
                result = result,
                termination = termination,
                whiteElo = whiteElo,
                blackElo = blackElo
            )
        }

        /**
         * Check for threefold repetition (simplified)
         */
        private fun isThreefoldRepetition(moveHistory: List<Move>): Boolean {
            // This is a simplified check - a real implementation would need
            // to track position history properly
            return false
        }

        /**
         * Check for insufficient material
         */
        private fun isInsufficientMaterial(board: Board): Boolean {
            val pieces = board.getAllPieces()
            val nonKingPieces = pieces.filter { it.second.type != PieceType.KING }

            // King vs King
            if (nonKingPieces.isEmpty()) return true

            // King and Bishop vs King
            if (nonKingPieces.size == 1 && nonKingPieces[0].second.type == PieceType.BISHOP) return true

            // King and Knight vs King
            if (nonKingPieces.size == 1 && nonKingPieces[0].second.type == PieceType.KNIGHT) return true

            // King and Bishop vs King and Bishop (same color bishops)
            if (nonKingPieces.size == 2 &&
                nonKingPieces.all { it.second.type == PieceType.BISHOP } &&
                nonKingPieces[0].second.color != nonKingPieces[1].second.color) {
                // Check if bishops are on same color squares
                val bishop1Square = nonKingPieces[0].first
                val bishop2Square = nonKingPieces[1].first
                val bishop1Color = (bishop1Square.file + bishop1Square.rank) % 2
                val bishop2Color = (bishop2Square.file + bishop2Square.rank) % 2
                return bishop1Color == bishop2Color
            }

            return false
        }
    }
}