package com.chesstrainer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chesstrainer.chess.*

enum class GameMode {
    HUMAN_VS_ENGINE,
    ENGINE_VS_ENGINE,
    FREE_PLAY
}

fun shouldEngineMove(player: com.chesstrainer.chess.Color, mode: GameMode): Boolean {
    return when (mode) {
        GameMode.HUMAN_VS_ENGINE -> player != com.chesstrainer.chess.Color.WHITE // Human is white, engine is black
        GameMode.ENGINE_VS_ENGINE -> true // Both players are engines
        GameMode.FREE_PLAY -> false // Human vs human
    }
}

@Composable
fun GameScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToAnalysis: () -> Unit,
    onNavigateToLessons: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var gameState by remember { mutableStateOf(GameState()) }
    var gameMode by remember { mutableStateOf(GameMode.HUMAN_VS_ENGINE) }
    var selectedSquare by remember { mutableStateOf<Square?>(null) }
    var availableMoves by remember { mutableStateOf<List<Move>>(emptyList()) }
    var lastMove by remember { mutableStateOf<Move?>(null) }
    var showGameOverDialog by remember { mutableStateOf(false) }
    var gameOverMessage by remember { mutableStateOf("") }
    var showBoard by remember { mutableStateOf(false) }
    var gameStarted by remember { mutableStateOf(false) }

    fun makeMove(move: Move) {
        try {
            if (MoveValidator.isValidMove(gameState.board, move, gameState)) {
                val newGameState = gameState.makeMove(move)
                gameState = newGameState
                lastMove = move
                selectedSquare = null
                availableMoves = emptyList()
            }
        } catch (e: Exception) {
            // Ignore move errors for now
        }
    }

    // Update available moves when selected square changes
    LaunchedEffect(selectedSquare, gameState) {
        availableMoves = if (selectedSquare != null) {
            try {
                MoveValidator.generateLegalMoves(gameState.board, gameState).filter { it.from == selectedSquare }
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    // Handle game over
    LaunchedEffect(gameState.gameResult) {
        if (gameState.isGameOver()) {
            gameOverMessage = when (gameState.gameResult) {
                GameResult.WHITE_WINS -> "White wins!"
                GameResult.BLACK_WINS -> "Black wins!"
                GameResult.DRAW -> "Draw!"
                else -> "Game over"
            }
            showGameOverDialog = true
        }
    }

    // Engine move logic (simplified - just make random legal moves for demo)
    LaunchedEffect(gameState.currentPlayer, gameMode) {
        if (!gameState.isGameOver() && shouldEngineMove(gameState.currentPlayer, gameMode)) {
            kotlinx.coroutines.delay(1000) // Delay for better UX

            try {
                val legalMoves = MoveValidator.generateLegalMoves(gameState.board, gameState)
                if (legalMoves.isNotEmpty()) {
                    val randomMove = legalMoves.random()
                    makeMove(randomMove)
                }
            } catch (e: Exception) {
                // Ignore errors for now
            }
        }
    }

    fun onSquareClick(square: Square) {
        val piece = gameState.board.getPiece(square)

        when {
            // If a square is already selected
            selectedSquare != null -> {
                val selectedMove = availableMoves.find { it.to == square }
                if (selectedMove != null) {
                    makeMove(selectedMove)
                } else if (piece != null && piece.color == gameState.currentPlayer) {
                    // Select different piece of same color
                    selectedSquare = square
                } else {
                    // Deselect
                    selectedSquare = null
                    availableMoves = emptyList()
                }
            }
            // If clicking on a piece of current player
            piece != null && piece.color == gameState.currentPlayer -> {
                selectedSquare = square
            }
            // If clicking on empty square or opponent piece
            else -> {
                selectedSquare = null
                availableMoves = emptyList()
            }
        }
    }

    if (!gameStarted) {
        // Game setup screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🎯 Chess Game Setup",
                style = MaterialTheme.typography.h4
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text("Choose your game mode:", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    gameMode = GameMode.HUMAN_VS_ENGINE
                    gameStarted = true
                    showBoard = true
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("🎮 Start Human vs Engine")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    gameMode = GameMode.ENGINE_VS_ENGINE
                    gameStarted = true
                    showBoard = true
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("🤖 Watch Engine vs Engine")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    gameMode = GameMode.FREE_PLAY
                    gameStarted = true
                    showBoard = true
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("👥 Free Play (Two Players)")
            }

            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedButton(onClick = onNavigateToSettings) {
                    Text("Settings")
                }
                OutlinedButton(onClick = onNavigateToAnalysis) {
                    Text("Analysis")
                }
                OutlinedButton(onClick = onNavigateToLessons) {
                    Text("Learn")
                }
            }
        }
    } else if (showBoard) {
        // Interactive chess board
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            // Top bar with game controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Game mode indicator
                Text(
                    text = when (gameMode) {
                        GameMode.HUMAN_VS_ENGINE -> "Human vs Engine"
                        GameMode.ENGINE_VS_ENGINE -> "Engine vs Engine"
                        GameMode.FREE_PLAY -> "Free Play"
                    },
                    style = MaterialTheme.typography.h6
                )

                // Action buttons
                Row {
                    IconButton(onClick = { /* Undo move */ }) {
                        Text("↶")
                    }
                    IconButton(onClick = { /* Export game */ }) {
                        Text("📄")
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Text("⚙")
                    }
                    IconButton(onClick = onNavigateToAnalysis) {
                        Text("📊")
                    }
                    IconButton(onClick = onNavigateToLessons) {
                        Text("📚")
                    }
                }
            }

            // Chess board
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                ChessBoard(
                    gameState = gameState,
                    selectedSquare = selectedSquare,
                    availableMoves = availableMoves,
                    lastMove = lastMove,
                    boardOrientation = Color.WHITE,
                    onSquareClick = ::onSquareClick
                )
            }

            // Game status and controls
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Current player indicator
                Text(
                    text = when {
                        gameState.isGameOver() -> gameOverMessage
                        gameState.currentPlayer == Color.WHITE -> "White to move"
                        else -> "Black to move"
                    },
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                if (try { MoveValidator.isKingInCheck(gameState.board, gameState.currentPlayer) } catch (e: Exception) { false }) {
                    Text(
                        text = "Check!",
                        color = androidx.compose.ui.graphics.Color.Red,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Move history
                if (gameState.moveHistory.isNotEmpty()) {
                    Text(
                        text = "Moves: ${gameState.moveHistory.size}",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Game control buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        gameState = GameState()
                        selectedSquare = null
                        availableMoves = emptyList()
                        lastMove = null
                        // Note: Engine restart would go here
                    }) {
                        Text("New Game")
                    }

                    Button(onClick = {
                        gameStarted = false
                        showBoard = false
                    }) {
                        Text("Back to Setup")
                    }
                }
            }
        }
    }

    // Game over dialog
    if (showGameOverDialog) {
        AlertDialog(
            onDismissRequest = { showGameOverDialog = false },
            title = { Text("Game Over") },
            text = { Text(gameOverMessage) },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        showGameOverDialog = false
                        gameState = GameState()
                        selectedSquare = null
                        availableMoves = emptyList()
                        lastMove = null
                    }) {
                        Text("New Game")
                    }
                }
            }
        )
    }
}