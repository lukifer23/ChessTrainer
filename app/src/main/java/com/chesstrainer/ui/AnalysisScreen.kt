package com.chesstrainer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chesstrainer.chess.GameState
import com.chesstrainer.chess.Move
import com.chesstrainer.chess.MoveHistoryFormatter
import com.chesstrainer.chess.MoveValidator
import com.chesstrainer.chess.Square
import com.chesstrainer.engine.LeelaEngine
import com.chesstrainer.engine.StockfishEngine
import com.chesstrainer.engine.UCIParser
import com.chesstrainer.utils.EngineType
import com.chesstrainer.utils.Settings

private data class AnalysisLine(
    val index: Int,
    val score: UCIParser.Score?,
    val depth: Int,
    val moves: List<Move>
)

private sealed class AnalysisStatus {
    object Loading : AnalysisStatus()
    data class Ready(
        val engineName: String,
        val lines: List<AnalysisLine>
    ) : AnalysisStatus()

    data class Error(val message: String) : AnalysisStatus()
}

@Composable
fun AnalysisScreen(onNavigateBack: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val settings = remember { Settings(context) }
    val engineType = settings.engineType

    val stockfishEngine = remember { StockfishEngine(context, settings) }
    val leelaEngine = remember { LeelaEngine(context, settings) }

    var gameState by remember { mutableStateOf(GameState()) }
    var selectedSquare by remember { mutableStateOf<Square?>(null) }
    var availableMoves by remember { mutableStateOf<List<Move>>(emptyList()) }
    var lastMove by remember { mutableStateOf<Move?>(null) }
    var draggedPiece by remember { mutableStateOf<Square?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var analysisStatus by remember { mutableStateOf<AnalysisStatus>(AnalysisStatus.Loading) }

    DisposableEffect(Unit) {
        onDispose {
            stockfishEngine.cleanup()
            leelaEngine.cleanup()
        }
    }

    fun makeMove(move: Move) {
        if (MoveValidator.isValidMove(gameState.board, move, gameState)) {
            val newState = gameState.makeMove(move)
            gameState = newState
            lastMove = move
            selectedSquare = null
            availableMoves = emptyList()
            draggedPiece = null
            dragOffset = Offset.Zero
        }
    }

    fun resetBoard() {
        gameState = GameState()
        selectedSquare = null
        availableMoves = emptyList()
        lastMove = null
        draggedPiece = null
        dragOffset = Offset.Zero
    }

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

    LaunchedEffect(gameState, engineType) {
        analysisStatus = AnalysisStatus.Loading
        analysisStatus = when (engineType) {
            EngineType.STOCKFISH -> {
                val result = stockfishEngine.getAnalysis(
                    gameState = gameState,
                    depth = settings.stockfishDepth,
                    multiPV = 3
                )
                result.fold(
                    onSuccess = { lines ->
                        val mappedLines = lines
                            .sortedByDescending { it.depth }
                            .distinctBy { it.principalVariation }
                            .take(3)
                            .mapIndexed { index, line ->
                                AnalysisLine(
                                    index = index + 1,
                                    score = line.score,
                                    depth = line.depth,
                                    moves = line.principalVariation
                                )
                            }
                        AnalysisStatus.Ready(
                            engineName = stockfishEngine.getEngineInfo(),
                            lines = mappedLines
                        )
                    },
                    onFailure = { error ->
                        AnalysisStatus.Error(error.message ?: "Stockfish analysis failed")
                    }
                )
            }
            EngineType.LEELA_CHESS_ZERO -> {
                val result = leelaEngine.getAnalysis(
                    gameState = gameState,
                    maxNodes = settings.leelaNodes
                )
                result.fold(
                    onSuccess = { line ->
                        val moves = if (line.principalVariation.isNotEmpty()) {
                            line.principalVariation
                        } else {
                            listOf(line.bestMove)
                        }
                        AnalysisStatus.Ready(
                            engineName = leelaEngine.getEngineInfo(),
                            lines = listOf(
                                AnalysisLine(
                                    index = 1,
                                    score = line.evaluation,
                                    depth = settings.leelaNodes,
                                    moves = moves
                                )
                            )
                        )
                    },
                    onFailure = { error ->
                        AnalysisStatus.Error(error.message ?: "Leela analysis failed")
                    }
                )
            }
        }
    }

    fun onDragStart(square: Square) {
        val piece = gameState.board.getPiece(square)
        if (piece != null && piece.color == gameState.currentPlayer) {
            draggedPiece = square
            selectedSquare = square
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
                draggedPiece = null
                dragOffset = Offset.Zero
                selectedSquare = null
                availableMoves = emptyList()
            }
        } else {
            draggedPiece = null
            dragOffset = Offset.Zero
            selectedSquare = null
            availableMoves = emptyList()
        }
    }

    fun onSquareClick(square: Square) {
        if (draggedPiece != null) {
            onDragEnd(square)
            return
        }
        val piece = gameState.board.getPiece(square)
        if (selectedSquare == square) {
            selectedSquare = null
            availableMoves = emptyList()
        } else if (piece != null && piece.color == gameState.currentPlayer) {
            selectedSquare = square
            availableMoves = try {
                MoveValidator.generateLegalMoves(gameState.board, gameState).filter { it.from == square }
            } catch (e: Exception) {
                emptyList()
            }
        } else if (selectedSquare != null) {
            val move = availableMoves.find { it.to == square }
            if (move != null) {
                makeMove(move)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Position Analysis",
            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Engine: ${engineType.name.replace('_', ' ')}",
            style = MaterialTheme.typography.subtitle1
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Card(elevation = 4.dp) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Interactive board", style = MaterialTheme.typography.subtitle1)
                        Spacer(modifier = Modifier.height(12.dp))
                        ChessBoard(
                            gameState = gameState,
                            selectedSquare = selectedSquare,
                            availableMoves = availableMoves,
                            lastMove = lastMove,
                            draggedPiece = draggedPiece,
                            dragOffset = dragOffset,
                            boardOrientation = settings.boardOrientation,
                            pieceTheme = settings.pieceTheme,
                            onSquareClick = { square -> onSquareClick(square) },
                            onDragStart = { square -> onDragStart(square) },
                            onDragEnd = { square -> onDragEnd(square) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 260.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedButton(onClick = { resetBoard() }) {
                                Text("Reset")
                            }
                            Button(onClick = {
                                analysisStatus = AnalysisStatus.Loading
                                gameState = gameState.copy()
                            }) {
                                Text("Re-analyze")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(elevation = 4.dp) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Move list", style = MaterialTheme.typography.subtitle1)
                        Spacer(modifier = Modifier.height(8.dp))
                        val formattedMoves = MoveHistoryFormatter.formatMoveHistory(gameState)
                        if (formattedMoves.isEmpty()) {
                            Text("No moves yet. Play a move to build the list.")
                        } else {
                            formattedMoves.chunked(2).forEachIndexed { index, pair ->
                                val moveNumber = index + 1
                                val whiteMove = pair.getOrNull(0) ?: ""
                                val blackMove = pair.getOrNull(1) ?: ""
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "$moveNumber.", fontWeight = FontWeight.SemiBold)
                                    Text(text = whiteMove, modifier = Modifier.weight(1f))
                                    Text(text = blackMove, modifier = Modifier.weight(1f))
                                }
                                Divider(modifier = Modifier.padding(vertical = 4.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Card(elevation = 4.dp) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Engine evaluation", style = MaterialTheme.typography.subtitle1)
                        Spacer(modifier = Modifier.height(12.dp))
                        when (val status = analysisStatus) {
                            AnalysisStatus.Loading -> {
                                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Analyzing with ${engineType.name.replace('_', ' ')}...")
                            }
                            is AnalysisStatus.Error -> {
                                Text(text = status.message, color = MaterialTheme.colors.error)
                            }
                            is AnalysisStatus.Ready -> {
                                val topLine = status.lines.firstOrNull()
                                if (topLine == null) {
                                    Text("No evaluation returned yet.")
                                } else {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        EvaluationBar(score = topLine.score)
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(text = "Eval: ${formatScore(topLine.score)}")
                                            Text(text = "Depth: ${topLine.depth}")
                                            Text(text = "Engine: ${status.engineName}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(elevation = 4.dp) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Key move suggestions", style = MaterialTheme.typography.subtitle1)
                        Spacer(modifier = Modifier.height(8.dp))
                        val lines = (analysisStatus as? AnalysisStatus.Ready)?.lines.orEmpty()
                        if (lines.isEmpty()) {
                            Text("No suggestions yet. Make a move to refresh analysis.")
                        } else {
                            lines.forEach { line ->
                                val firstMove = line.moves.firstOrNull()?.uci ?: "--"
                                Text(text = "Line ${line.index}: $firstMove (${formatScore(line.score)})")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(elevation = 4.dp) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Multi-PV lines", style = MaterialTheme.typography.subtitle1)
                        Spacer(modifier = Modifier.height(8.dp))
                        val lines = (analysisStatus as? AnalysisStatus.Ready)?.lines.orEmpty()
                        if (lines.isEmpty()) {
                            Text("No PV lines yet. Analysis will appear here.")
                        } else {
                            lines.forEach { line ->
                                Text(
                                    text = "${line.index}. ${formatScore(line.score)} · Depth ${line.depth}",
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = line.moves.joinToString(" ") { it.uci })
                                Spacer(modifier = Modifier.height(8.dp))
                                Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = onNavigateBack
        ) {
            Text("Back")
        }
    }
}

@Composable
private fun EvaluationBar(score: UCIParser.Score?, modifier: Modifier = Modifier) {
    val pawns = when {
        score?.mate != null -> if (score.mate > 0) 10f else -10f
        score?.centipawns != null -> score.centipawns / 100f
        else -> 0f
    }
    val clamped = pawns.coerceIn(-10f, 10f)
    val whiteRatio = (clamped + 10f) / 20f
    val whiteWeight = whiteRatio.coerceIn(0f, 1f)
    val blackWeight = 1f - whiteWeight

    Column(
        modifier = modifier
            .height(120.dp)
            .width(28.dp)
            .border(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.2f))
    ) {
        Box(
            modifier = Modifier
                .weight(whiteWeight)
                .fillMaxWidth()
                .background(Color.White)
        )
        Box(
            modifier = Modifier
                .weight(blackWeight)
                .fillMaxWidth()
                .background(Color.Black)
        )
    }
}

private fun formatScore(score: UCIParser.Score?): String {
    return when {
        score?.mate != null -> if (score.mate > 0) "M+${score.mate}" else "M${score.mate}"
        score?.centipawns != null -> {
            val pawns = score.centipawns / 100.0
            String.format("%+.2f", pawns)
        }
        else -> "+0.00"
    }
}
