package com.chesstrainer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chesstrainer.chess.GameState
import com.chesstrainer.chess.Move
import com.chesstrainer.chess.MoveValidator
import com.chesstrainer.engine.EngineManager
import com.chesstrainer.engine.UCIParser
import com.chesstrainer.state.GameSessionState
import com.chesstrainer.utils.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AnalysisScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val settings = remember { Settings(context) }
    val engineManager = remember { EngineManager(context, settings) }
    val coroutineScope = rememberCoroutineScope()

    var analysisGameState by remember { mutableStateOf(GameSessionState.currentGameState) }
    var selectedSquare by remember { mutableStateOf<com.chesstrainer.chess.Square?>(null) }
    var availableMoves by remember { mutableStateOf<List<Move>>(emptyList()) }
    var lastMove by remember { mutableStateOf<Move?>(null) }

    var engineStatus by remember { mutableStateOf("Starting engine...") }
    var isSearching by remember { mutableStateOf(false) }
    var bestMove by remember { mutableStateOf<Move?>(null) }
    var depthInput by remember { mutableStateOf("14") }
    var analysisLines by remember { mutableStateOf(listOf<String>()) }
    var fenInput by remember { mutableStateOf("") }
    var moveListInput by remember { mutableStateOf("") }
    var importMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val result = engineManager.startEngine()
        if (result.isSuccess) {
            engineManager.configureEngine()
            engineStatus = "Engine ready: ${engineManager.getEngineName()}"
        } else {
            engineStatus = "Engine failed: ${result.exceptionOrNull()?.message}"
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            engineManager.cleanup()
        }
    }

    fun updateAvailableMoves() {
        availableMoves = if (selectedSquare != null) {
            try {
                MoveValidator.generateLegalMoves(analysisGameState.board, analysisGameState).filter { it.from == selectedSquare }
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    fun onSquareClick(square: com.chesstrainer.chess.Square) {
        val piece = analysisGameState.board.getPiece(square)
        when {
            selectedSquare != null -> {
                val selectedMove = availableMoves.find { it.to == square }
                if (selectedMove != null) {
                    analysisGameState = analysisGameState.makeMove(selectedMove)
                    lastMove = selectedMove
                }
                selectedSquare = null
                availableMoves = emptyList()
            }
            piece != null && piece.color == analysisGameState.currentPlayer -> {
                selectedSquare = square
                updateAvailableMoves()
            }
            else -> {
                selectedSquare = null
                availableMoves = emptyList()
            }
        }
    }

    fun loadFen(fen: String) {
        try {
            analysisGameState = GameState.fromFen(fen)
            importMessage = "Loaded FEN position."
        } catch (e: Exception) {
            importMessage = "Failed to load FEN: ${e.message}"
        }
    }

    fun loadMoveList(moveList: String) {
        val tokens = moveList
            .replace("\n", " ")
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .filter { it.matches(Regex("^[a-h][1-8][a-h][1-8][qrbn]?$")) }

        var state = GameState()
        try {
            for (token in tokens) {
                val move = Move.fromUci(token) ?: continue
                if (!MoveValidator.isValidMove(state.board, move, state)) {
                    throw IllegalArgumentException("Illegal move: $token")
                }
                state = state.makeMove(move)
            }
            analysisGameState = state
            importMessage = "Loaded move list (${tokens.size} moves)."
        } catch (e: Exception) {
            importMessage = "Failed to load moves: ${e.message}"
        }
    }

    fun formatScore(score: UCIParser.Score?): String {
        return when {
            score == null -> "?"
            score.mate != null -> "Mate ${score.mate}"
            score.centipawns != null -> {
                val value = score.centipawns / 100.0
                if (value > 0) "+$value" else "$value"
            }
            else -> "?"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Position Analysis",
            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = engineStatus,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ChessBoard(
            gameState = analysisGameState,
            selectedSquare = selectedSquare,
            availableMoves = availableMoves,
            lastMove = lastMove,
            draggedPiece = null,
            dragOffset = Offset.Zero,
            onSquareClick = { square -> onSquareClick(square) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = {
                    analysisGameState = GameSessionState.currentGameState
                    importMessage = "Loaded current game position."
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Load Current Game")
            }
            OutlinedButton(
                onClick = {
                    analysisGameState = GameState()
                    importMessage = "Reset to initial position."
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Reset Board")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Import Position",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = fenInput,
            onValueChange = { fenInput = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("FEN") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { loadFen(fenInput) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Load FEN")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = moveListInput,
            onValueChange = { moveListInput = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("PGN / UCI move list (UCI only)") },
            placeholder = { Text("e2e4 e7e5 g1f3 ...") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { loadMoveList(moveListInput) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Load Move List")
        }

        if (importMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = importMessage,
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Engine Analysis",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = depthInput,
            onValueChange = { depthInput = it },
            label = { Text("Search depth") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    if (isSearching) return@Button
                    val depth = depthInput.toIntOrNull()
                    isSearching = true
                    analysisLines = emptyList()
                    bestMove = null
                    coroutineScope.launch {
                        engineManager.startSearch(
                            gameState = analysisGameState,
                            onBestMove = { move ->
                                coroutineScope.launch {
                                    withContext(Dispatchers.Main) {
                                        bestMove = move
                                        isSearching = false
                                    }
                                }
                            },
                            onInfo = { info ->
                                coroutineScope.launch {
                                    withContext(Dispatchers.Main) {
                                        val pv = if (info.principalVariation.isNotEmpty()) {
                                            info.principalVariation.joinToString(" ") { it.uci }
                                        } else {
                                            "-"
                                        }
                                        val line = "d${info.depth ?: "?"} | ${formatScore(info.score)} | pv $pv"
                                        analysisLines = (analysisLines + line).takeLast(6)
                                    }
                                }
                            },
                            searchParams = EngineManager.SearchParams(depth = depth)
                        )
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isSearching) "Searching..." else "Start Analysis")
            }
            OutlinedButton(
                onClick = {
                    coroutineScope.launch {
                        engineManager.stopSearch()
                        isSearching = false
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = isSearching
            ) {
                Text("Stop")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (bestMove != null) {
            Text(
                text = "Best move: ${bestMove?.uci}",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        }

        analysisLines.forEach { line ->
            Text(text = line, style = MaterialTheme.typography.caption)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onNavigateBack) {
            Text("Back")
        }
    }
}
