package com.chesstrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chesstrainer.ui.GameScreen
import com.chesstrainer.ui.SettingsScreen
import com.chesstrainer.ui.AnalysisScreen
import com.chesstrainer.ui.LessonsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ChessTrainerApp()
                }
            }
        }
    }
}

@Composable
fun ChessTrainerApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onStartGame = { navController.navigate("game") },
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToAnalysis = { navController.navigate("analysis") },
                onNavigateToLessons = { navController.navigate("lessons") }
            )
        }
        composable("game") {
            GameScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToAnalysis = { navController.navigate("analysis") },
                onNavigateToLessons = { navController.navigate("lessons") }
            )
        }
        composable("settings") {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("analysis") {
            AnalysisScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("lessons") {
            LessonsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun HomeScreen(
    onStartGame: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAnalysis: () -> Unit,
    onNavigateToLessons: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "♟️ Chess Trainer ♟️",
            style = MaterialTheme.typography.h4
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Professional Chess Training App",
            style = MaterialTheme.typography.subtitle1
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onStartGame,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("🎮 Start Game")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = onNavigateToAnalysis) {
                Text("📊 Analysis")
            }
            OutlinedButton(onClick = onNavigateToLessons) {
                Text("📚 Learn")
            }
            OutlinedButton(onClick = onNavigateToSettings) {
                Text("⚙️ Settings")
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "✅ Active Features:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.align(Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("• ✅ Complete chess rules implementation")
            Text("• ✅ Interactive chess board with touch controls")
            Text("• ✅ Move validation and legal move generation")
            Text("• ✅ Check/checkmate detection")
            Text("• ✅ Special moves (castling, en passant, promotion)")
            Text("• ✅ Human vs Engine gameplay (random moves)")
            Text("• ✅ Move history and game state tracking")
            Text("• ✅ Multiple game modes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "🚧 Coming Soon:",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.align(Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("• Real LeelaChess0/Stockfish engine integration")
            Text("• Position analysis and evaluation")
            Text("• Interactive chess lessons")
            Text("• PGN export functionality")
            Text("• Advanced settings and preferences")
        }
    }
}