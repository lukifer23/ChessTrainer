package com.chesstrainer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chesstrainer.chess.*
import com.chesstrainer.state.GameSessionState
import com.chesstrainer.utils.EngineType
import com.chesstrainer.utils.Settings
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val context = LocalContext.current
    val settings = remember { Settings(context) }
    val coroutineScope = rememberCoroutineScope()

    var gameState by remember { mutableStateOf(GameState()) }
    var gameMode by remember { mutableStateOf(GameMode.HUMAN_VS_ENGINE) }
    var selectedSquare by remember { mutableStateOf<Square?>(null) }
    var availableMoves by remember { mutableStateOf<List<Move>>(emptyList()) }
    var lastMove by remember { mutableStateOf<Move?>(null) }
    var draggedPiece by remember { mutableStateOf<Square?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var showGameOverDialog by remember { mutableStateOf(false) }
    var gameOverMessage by remember { mutableStateOf("") }
    var showBoard by remember { mutableStateOf(false) }
    var gameStarted by remember { mutableStateOf(false) }
    var currentEngine by remember { mutableStateOf(settings.engineType) }

    fun makeMove(move: Move) {
        try {
            if (MoveValidator.isValidMove(gameState.board, move, gameState)) {
                val newGameState = gameState.makeMove(move)
                gameState = newGameState
                GameSessionState.currentGameState = newGameState
                lastMove = move
                selectedSquare = null
                availableMoves = emptyList()
                draggedPiece = null
                dragOffset = Offset.Zero
            }
        } catch (e: Exception) {
            // Ignore move errors for now
            draggedPiece = null
            dragOffset = Offset.Zero
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

    fun onDragStart(square: Square) {
        val piece = gameState.board.getPiece(square)
        if (piece != null && piece.color == gameState.currentPlayer) {
            draggedPiece = square
            selectedSquare = square
            // Calculate available moves for the dragged piece
            availableMoves = try {
                MoveValidator.generateLegalMoves(gameState.board, gameState).filter { it.from == square }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    fun onDragEnd(dropSquare: Square?) {
        if (draggedPiece == null) return

        if (dropSquare != null) {
            val dragMove = availableMoves.find { it.to == dropSquare }
            if (dragMove != null) {
                makeMove(dragMove)
            } else {
                // Invalid drop - reset drag state
                draggedPiece = null
                dragOffset = Offset.Zero
                selectedSquare = null
                availableMoves = emptyList()
            }
        } else {
            // Dropped outside board - reset drag state
            draggedPiece = null
            dragOffset = Offset.Zero
            selectedSquare = null
            availableMoves = emptyList()
        }
    }

    fun onSquareClick(square: Square) {
        if (draggedPiece != null) {
            // If we're dragging, handle as drop
            onDragEnd(square)
            return
        }

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
                    currentEngine = settings.engineType
                    gameStarted = true
                    GameSessionState.currentGameState = gameState
                    showBoard = true
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("🎮 Human vs ${settings.engineType.name.lowercase().replace("_", " ").capitalize()}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    gameMode = GameMode.ENGINE_VS_ENGINE
                    currentEngine = settings.engineType
                    gameStarted = true
                    GameSessionState.currentGameState = gameState
                    showBoard = true
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("🤖 ${settings.engineType.name.lowercase().replace("_", " ").capitalize()} vs ${settings.engineType.name.lowercase().replace("_", " ").capitalize()}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    gameMode = GameMode.FREE_PLAY
                    gameStarted = true
                    GameSessionState.currentGameState = gameState
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
            // Top status bar
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                elevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Game mode and engine info
                    Column {
                        Text(
                            text = when (gameMode) {
                                GameMode.HUMAN_VS_ENGINE -> "Human vs ${currentEngine.name.lowercase().replace("_", " ").capitalize()}"
                                GameMode.ENGINE_VS_ENGINE -> "${currentEngine.name.lowercase().replace("_", " ").capitalize()} vs ${currentEngine.name.lowercase().replace("_", " ").capitalize()}"
                                GameMode.FREE_PLAY -> "Free Play"
                            },
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Text(
                            text = when (currentEngine) {
                                EngineType.LEELA_CHESS_ZERO -> "Neural Network • ${settings.leelaNodes} nodes/move"
                                EngineType.STOCKFISH -> "Traditional Engine • Depth ${settings.stockfishDepth}"
                            },
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    // Action buttons
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(onClick = { /* Undo move */ }, modifier = Modifier.size(36.dp)) {
                            Text("↶", fontSize = 18.sp)
                        }
                        IconButton(onClick = { /* Export game */ }, modifier = Modifier.size(36.dp)) {
                            Text("📄", fontSize = 18.sp)
                        }
                        IconButton(onClick = onNavigateToSettings, modifier = Modifier.size(36.dp)) {
                            Text("⚙", fontSize = 18.sp)
                        }
                        IconButton(onClick = onNavigateToAnalysis, modifier = Modifier.size(36.dp)) {
                            Text("📊", fontSize = 18.sp)
                        }
                        IconButton(onClick = onNavigateToLessons, modifier = Modifier.size(36.dp)) {
                            Text("📚", fontSize = 18.sp)
                        }
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
                    draggedPiece = draggedPiece,
                    dragOffset = dragOffset,
                    boardOrientation = Color.WHITE,
                    onSquareClick = ::onSquareClick,
                    onDragStart = ::onDragStart,
                    onDragEnd = ::onDragEnd
                )
            }

            // Game status and controls
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Current player indicator and status
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = when {
                                    gameState.isGameOver() -> gameOverMessage
                                    gameState.currentPlayer == Color.WHITE -> "⚪ White to move"
                                    else -> "⚫ Black to move"
                                },
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold
                            )

                            if (try { MoveValidator.isKingInCheck(gameState.board, gameState.currentPlayer) } catch (e: Exception) { false }) {
                                Text(
                                    text = "🔴 Check!",
                                    color = androidx.compose.ui.graphics.Color.Red,
                                    style = MaterialTheme.typography.body2,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Game statistics
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Move ${gameState.fullMoveNumber}",
                                style = MaterialTheme.typography.body2
                            )
                            Text(
                                text = "${gameState.moveHistory.size} ply",
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Game control buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                gameState = GameState()
                                GameSessionState.currentGameState = GameState()
                                selectedSquare = null
                                availableMoves = emptyList()
                                lastMove = null
                                draggedPiece = null
                                dragOffset = Offset.Zero
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("🔄 New Game")
                        }

                        OutlinedButton(
                            onClick = {
                                gameStarted = false
                                showBoard = false
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("⬅️ Back")
                        }
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
                        GameSessionState.currentGameState = GameState()
                        selectedSquare = null
                        availableMoves = emptyList()
                        lastMove = null
                        draggedPiece = null
                        dragOffset = Offset.Zero
                    }) {
                        Text("New Game")
                    }
                }
            }
        )
    }
}
