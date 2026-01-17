package com.chesstrainer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import android.content.Intent
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.chesstrainer.data.GameRepository
import com.chesstrainer.data.GameSummary
import com.chesstrainer.data.PlayerOutcome
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ScorecardScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val repository = remember { GameRepository(context.applicationContext) }
    val games by repository.games.collectAsState(initial = emptyList())
    val results by repository.results.collectAsState(initial = emptyList())
    val ratings by repository.ratings.collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    val wins = results.count { it.outcome == PlayerOutcome.WIN.name }
    val losses = results.count { it.outcome == PlayerOutcome.LOSS.name }
    val draws = results.count { it.outcome == PlayerOutcome.DRAW.name }
    val latestRating = ratings.lastOrNull()?.ratingAfter ?: 1200
    val formatter = remember { SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()) }
    fun exportSummaries(
        formatExtension: String,
        mimeType: String,
        buildContent: (List<GameSummary>) -> String
    ) {
        coroutineScope.launch {
            val summaries = withContext(Dispatchers.IO) {
                repository.getGameSummaries()
            }
            val content = buildContent(summaries)
            val fileName = "chess_trainer_scorecard_${System.currentTimeMillis()}.$formatExtension"
            val file = withContext(Dispatchers.IO) {
                File(context.cacheDir, fileName).apply { writeText(content) }
            }
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, "Chess Trainer Scorecard")
                putExtra(Intent.EXTRA_TEXT, "Scorecard export from Chess Trainer.")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share scorecard"))
        }
    }

    fun toCsvValue(value: String?): String {
        if (value == null) return ""
        val needsEscaping = value.contains(",") || value.contains("\"") || value.contains("\n")
        return if (needsEscaping) "\"${value.replace("\"", "\"\"")}\"" else value
    }

    fun buildCsv(summaries: List<GameSummary>): String {
        val header = listOf(
            "GameId",
            "PlayedAt",
            "Mode",
            "EngineType",
            "EngineConfig",
            "EngineVersion",
            "TimeControl",
            "AnalysisDepth",
            "WhiteElo",
            "BlackElo",
            "Result",
            "Outcome",
            "Score",
            "MoveCount",
            "Moves"
        ).joinToString(",")
        val rows = summaries.joinToString("\n") { summary ->
            listOf(
                summary.gameId.toString(),
                summary.playedAt.toString(),
                summary.mode,
                summary.engineType,
                summary.engineConfig,
                summary.engineVersion,
                summary.timeControl,
                summary.analysisDepth,
                summary.whiteElo?.toString(),
                summary.blackElo?.toString(),
                summary.result,
                summary.outcome,
                summary.score?.toString(),
                summary.moveCount.toString(),
                summary.moves
            ).joinToString(",") { toCsvValue(it) }
        }
        return header + if (rows.isNotBlank()) "\n$rows" else ""
    }

    fun escapeJson(value: String?): String? {
        return value?.replace("\\", "\\\\")?.replace("\"", "\\\"")?.replace("\n", "\\n")
    }

    fun buildJson(summaries: List<GameSummary>): String {
        val items = summaries.joinToString(",") { summary ->
            buildString {
                append("{")
                append("\"gameId\":${summary.gameId},")
                append("\"playedAt\":${summary.playedAt},")
                append("\"mode\":\"${escapeJson(summary.mode)}\",")
                append("\"engineType\":\"${escapeJson(summary.engineType)}\",")
                append("\"engineConfig\":\"${escapeJson(summary.engineConfig)}\",")
                append("\"engineVersion\":\"${escapeJson(summary.engineVersion)}\",")
                append("\"timeControl\":\"${escapeJson(summary.timeControl)}\",")
                append("\"analysisDepth\":\"${escapeJson(summary.analysisDepth)}\",")
                append("\"whiteElo\":${summary.whiteElo ?: "null"},")
                append("\"blackElo\":${summary.blackElo ?: "null"},")
                append("\"result\":\"${escapeJson(summary.result)}\",")
                append("\"outcome\":${summary.outcome?.let { "\"${escapeJson(it)}\"" } ?: "null"},")
                append("\"score\":${summary.score ?: "null"},")
                append("\"moveCount\":${summary.moveCount},")
                append("\"moves\":\"${escapeJson(summary.moves)}\"")
                append("}")
            }
        }
        return "[$items]"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🏆 Scorecard",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onNavigateBack) {
                    Text("Back")
                }
                OutlinedButton(onClick = {
                    exportSummaries(
                        formatExtension = "csv",
                        mimeType = "text/csv",
                        buildContent = ::buildCsv
                    )
                }) {
                    Text("Export CSV")
                }
                OutlinedButton(onClick = {
                    exportSummaries(
                        formatExtension = "json",
                        mimeType = "application/json",
                        buildContent = ::buildJson
                    )
                }) {
                    Text("Export JSON")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Overall Record", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Wins: $wins")
                Text("Losses: $losses")
                Text("Draws: $draws")
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Current Rating", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("$latestRating")
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Rating Over Time", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                if (ratings.isEmpty()) {
                    Text("No games recorded yet.")
                } else {
                    ratings.forEach { rating ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(formatter.format(Date(rating.recordedAt)))
                            Text("${rating.ratingAfter} (${rating.delta})")
                        }
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Recent Games", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                if (games.isEmpty()) {
                    Text("No games recorded yet.")
                } else {
                    games.take(10).forEach { game ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = formatter.format(Date(game.playedAt)),
                                style = MaterialTheme.typography.body2,
                                fontWeight = FontWeight.Bold
                            )
                            Text("Mode: ${game.mode.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }}")
                            Text("Result: ${game.result.replace("_", " ").lowercase().replaceFirstChar { it.uppercase() }}")
                            Text("Engine: ${game.engineType} (${game.engineConfig})")
                            Text("Time Control: ${game.timeControl}")
                            Text("Analysis Depth: ${game.analysisDepth}")
                            Text("White Elo: ${game.whiteElo ?: "N/A"}")
                            Text("Black Elo: ${game.blackElo ?: "N/A"}")
                            Text("Engine Version: ${game.engineVersion}")
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}
