package com.chesstrainer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chesstrainer.data.GameRepository
import com.chesstrainer.data.PlayerOutcome
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ScorecardScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val repository = remember { GameRepository(context.applicationContext) }
    val results by repository.results.collectAsState(initial = emptyList())
    val games by repository.games.collectAsState(initial = emptyList())
    val ratings by repository.ratings.collectAsState(initial = emptyList())

    val wins = results.count { it.outcome == PlayerOutcome.WIN.name }
    val losses = results.count { it.outcome == PlayerOutcome.LOSS.name }
    val draws = results.count { it.outcome == PlayerOutcome.DRAW.name }
    val latestRating = ratings.lastOrNull()?.ratingAfter ?: 1200
    val formatter = remember { SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()) }
    val gamesById = remember(games) { games.associateBy { it.id } }

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
            OutlinedButton(onClick = onNavigateBack) {
                Text("Back")
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
                        val game = gamesById[rating.gameId]
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(formatter.format(Date(rating.recordedAt)))
                                if (game != null) {
                                    val engineLabel = if (game.engineType == "NONE") {
                                        "Free play"
                                    } else {
                                        game.engineType.lowercase().replace("_", " ").replaceFirstChar { it.titlecase() }
                                    }
                                    val engineDetail = when {
                                        game.engineDepth != null -> "$engineLabel • Depth ${game.engineDepth}"
                                        game.leelaNodes != null -> "$engineLabel • ${game.leelaNodes} nodes"
                                        else -> engineLabel
                                    }
                                    Text("Opponent ${game.opponentRating} • $engineDetail")
                                }
                            }
                            Text("${rating.ratingAfter} (${rating.delta})")
                        }
                    }
                }
            }
        }
    }
}
