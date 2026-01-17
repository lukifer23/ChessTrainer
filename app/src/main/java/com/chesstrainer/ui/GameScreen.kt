package com.chesstrainer.ui

import android.content.Intent
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
import androidx.core.content.FileProvider
import com.chesstrainer.chess.*
import com.chesstrainer.data.GameEntity
import com.chesstrainer.data.GameRepository
import com.chesstrainer.data.GameResultEntity
import com.chesstrainer.data.PlayerOutcome
import com.chesstrainer.data.PlayerRatingEntity
import com.chesstrainer.engine.LeelaEngine
import com.chesstrainer.engine.StockfishEngine
import com.chesstrainer.export.PgnExporter
import com.chesstrainer.utils.EngineType
import com.chesstrainer.utils.EloCalculator
import com.chesstrainer.utils.Settings
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

private sealed class EngineStatus {
    object Ready : EngineStatus()
    object Loading : EngineStatus()
    data class Error(val message: String) : EngineStatus()
}

@Composable
fun GameScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToAnalysis: () -> Unit,
    onNavigateToLessons: () -> Unit,
    onNavigateToScorecard: () -> Unit
) {
    val context = LocalContext.current
    val settings = remember { Settings(context) }
    val repository = remember { GameRepository(context.applicationContext) }

    val stockfishEngine = remember { StockfishEngine(context, settings) }
    val leelaEngine = remember { LeelaEngine(context, settings) }
    val uiScope = rememberCoroutineScope()

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
    var hasRecordedGame by remember { mutableStateOf(false) }
    var engineStatus by remember { mutableStateOf<EngineStatus>(EngineStatus.Ready) }
    var engineSearchToken by remember { mutableStateOf(0) }
    val timestampFormatter = remember { SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()) }

    fun cancelEngineSearches() {
        engineSearchToken += 1
        stockfishEngine.stopSearch()
        leelaEngine.stopSearch()
    }

    suspend fun prepareEngineForGame() {
        if (gameMode == GameMode.FREE_PLAY) {
            engineStatus = EngineStatus.Ready
            return
        }
        engineStatus = EngineStatus.Loading
        engineStatus = when (currentEngine) {
            EngineType.STOCKFISH -> {
                stockfishEngine.prepareNewGame().fold(
                    onSuccess = { EngineStatus.Ready },
                    onFailure = { error ->
                        EngineStatus.Error(error.message ?: "Stockfish failed to initialize")
                    }
                )
            }
            EngineType.LEELA_CHESS_ZERO -> {
                leelaEngine.prepareNewGame().fold(
                    onSuccess = { EngineStatus.Ready },
                    onFailure = { error ->
                        EngineStatus.Error(error.message ?: "LeelaChess0 failed to initialize")
                    }
                )
            }
        }
    }

    fun makeMove(move: Move) {
        try {
            if (MoveValidator.isValidMove(gameState.board, move, gameState)) {
                val newGameState = gameState.makeMove(move)
                gameState = newGameState
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

    fun resetBoardState() {
        gameState = GameState()
        selectedSquare = null
        availableMoves = emptyList()
        lastMove = null
        draggedPiece = null
        dragOffset = Offset.Zero
        hasRecordedGame = false
    }

    fun startNewGame() {
        if (gameMode != GameMode.FREE_PLAY) {
            currentEngine = settings.engineType
        }
        resetBoardState()
        cancelEngineSearches()
        stockfishEngine.startNewGame()
        leelaEngine.startNewGame()
        uiScope.launch {
            prepareEngineForGame()
        }
    }

    fun exportPgn() {
        val (whiteName, blackName) = when (gameMode) {
            GameMode.HUMAN_VS_ENGINE -> "Human" to currentEngine.name.lowercase().replace("_", " ").capitalize()
            GameMode.ENGINE_VS_ENGINE -> currentEngine.name.lowercase().replace("_", " ").capitalize() to
                currentEngine.name.lowercase().replace("_", " ").capitalize()
            GameMode.FREE_PLAY -> "White" to "Black"
        }
        val eventName = when (gameMode) {
            GameMode.HUMAN_VS_ENGINE -> "Human vs Engine"
            GameMode.ENGINE_VS_ENGINE -> "Engine vs Engine"
            GameMode.FREE_PLAY -> "Free Play"
        }
        val pgnText = PgnExporter.export(
            gameState = gameState,
            whiteName = whiteName,
            blackName = blackName,
            event = "Chess Trainer - $eventName",
            site = "Android"
        )
        val fileName = "chess_trainer_${timestampFormatter.format(Date())}.pgn"
        val file = File(context.cacheDir, fileName)
        file.writeText(pgnText)
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/x-chess-pgn"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Chess Trainer PGN")
            putExtra(Intent.EXTRA_TEXT, "PGN export from Chess Trainer.")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share PGN"))
    }

    DisposableEffect(Unit) {
        onDispose {
            cancelEngineSearches()
            stockfishEngine.cleanup()
            leelaEngine.cleanup()
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
            if (!hasRecordedGame) {
                hasRecordedGame = true
                val moveList = MoveHistoryFormatter.formatMoveHistory(gameState)
                val outcome = when (gameState.gameResult) {
                    GameResult.WHITE_WINS -> PlayerOutcome.WIN
                    GameResult.BLACK_WINS -> PlayerOutcome.LOSS
                    GameResult.DRAW -> PlayerOutcome.DRAW
                    else -> PlayerOutcome.DRAW
                }
                val score = when (outcome) {
                    PlayerOutcome.WIN -> 1.0
                    PlayerOutcome.DRAW -> 0.5
                    PlayerOutcome.LOSS -> 0.0
                }
                val opponentRating = when (gameMode) {
                    GameMode.FREE_PLAY -> 1200
                    GameMode.HUMAN_VS_ENGINE,
                    GameMode.ENGINE_VS_ENGINE -> when (currentEngine) {
                        EngineType.STOCKFISH -> 1600
                        EngineType.LEELA_CHESS_ZERO -> 1700
                    }
                }
                val timestamp = System.currentTimeMillis()
                val latestRating = repository.getLatestRating()?.ratingAfter ?: 1200
                val updatedRating = EloCalculator.updateRating(
                    playerRating = latestRating,
                    opponentRating = opponentRating,
                    score = score
                )
                val delta = updatedRating - latestRating
                val engineLabel = if (gameMode == GameMode.FREE_PLAY) \"NONE\" else currentEngine.name

                repository.recordCompletedGame(
                    game = GameEntity(
                        playedAt = timestamp,
                        mode = gameMode.name,
                        engineType = engineLabel,
                        result = gameState.gameResult.name,
                        moves = moveList.joinToString(\" \"),
                        moveCount = gameState.moveHistory.size
                    ),
                    result = GameResultEntity(
                        outcome = outcome.name,
                        score = score
                    ),
                    rating = PlayerRatingEntity(
                        ratingBefore = latestRating,
                        ratingAfter = updatedRating,
                        delta = delta,
                        recordedAt = timestamp
                    )
                )
            }
        }
    }

    LaunchedEffect(gameState.currentPlayer, gameMode, currentEngine, engineStatus) {
        val isEngineReady = engineStatus is EngineStatus.Ready
        if (!gameState.isGameOver() && shouldEngineMove(gameState.currentPlayer, gameMode) && isEngineReady) {
            delay(1000) // Delay for better UX

            val searchToken = engineSearchToken
            val stateSnapshot = gameState
            val engine = when (currentEngine) {
                EngineType.STOCKFISH -> stockfishEngine
                EngineType.LEELA_CHESS_ZERO -> leelaEngine
            }
            engine.getBestMove(gameState) { bestMove ->
                uiScope.launch {
                    if (engineSearchToken != searchToken || stateSnapshot != gameState) {
                        return@launch
                    }
                    makeMove(bestMove)
                }
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
                    showBoard = true
                    startNewGame()
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
                    showBoard = true
                    startNewGame()
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
                    showBoard = true
                    startNewGame()
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
                OutlinedButton(onClick = onNavigateToScorecard) {
                    Text("Scorecard")
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
                        when (val status = engineStatus) {
                            EngineStatus.Loading -> {
                                Text(
                                    text = "Engine initializing...",
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                                )
                            }
                            is EngineStatus.Error -> {
                                Text(
                                    text = status.message,
                                    style = MaterialTheme.typography.caption,
                                    color = androidx.compose.ui.graphics.Color.Red
                                )
                            }
                            EngineStatus.Ready -> Unit
                        }
                    }

                    // Action buttons
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(onClick = { /* Undo move */ }, modifier = Modifier.size(36.dp)) {
                            Text("↶", fontSize = 18.sp)
                        }
                        IconButton(onClick = { exportPgn() }, modifier = Modifier.size(36.dp)) {
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
                        IconButton(onClick = onNavigateToScorecard, modifier = Modifier.size(36.dp)) {
                            Text("🏆", fontSize = 18.sp)
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
                                startNewGame()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("🔄 New Game")
                        }

                        OutlinedButton(
                            onClick = {
                                cancelEngineSearches()
                                gameStarted = false
                                showBoard = false
                                hasRecordedGame = false
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
                        startNewGame()
                    }) {
                        Text("New Game")
                    }
                }
            }
        )
    }
}
