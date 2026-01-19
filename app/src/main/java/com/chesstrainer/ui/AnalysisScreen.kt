package com.chesstrainer.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Undo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
import kotlinx.coroutines.launch

private data class AnalysisLine(
    val index: Int,
    val score: UCIParser.Score?,
    val depth: Int,
    val time: Long,
    val nodes: Long,
    val moves: List<Move>
)

private sealed class AnalysisStatus {
    object Loading : AnalysisStatus()
    data class Ready(
        val engineName: String,
        val engineType: EngineType,
        val lines: List<AnalysisLine>,
        val fen: String,
        val gameStateInfo: GameStateInfo
    ) : AnalysisStatus()

    data class Error(val message: String, val retryable: Boolean = true) : AnalysisStatus()
}

data class GameStateInfo(
    val isCheck: Boolean,
    val isCheckmate: Boolean,
    val isStalemate: Boolean,
    val isDrawByFiftyMoves: Boolean,
    val moveNumber: Int
)

@Composable
fun AnalysisScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val settings = remember { Settings(context) }
    val engineType = settings.engineType
    val configuration = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()

    val stockfishEngine = remember { StockfishEngine(context, settings) }
    val leelaEngine = remember { LeelaEngine(context, settings) }

    var gameState by remember { mutableStateOf(GameState()) }
    var selectedSquare by remember { mutableStateOf<Square?>(null) }
    var selectedPvMoves by remember { mutableStateOf<List<Move>>(emptyList()) }
    var availableMoves by remember { mutableStateOf<List<Move>>(emptyList()) }
    var lastMove by remember { mutableStateOf<Move?>(null) }
    var draggedPiece by remember { mutableStateOf<Square?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var analysisStatus by remember { mutableStateOf<AnalysisStatus>(AnalysisStatus.Loading) }
    var moveHistory by remember { mutableStateOf<List<GameState>>(emptyList()) }
    var historyIndex by remember { mutableStateOf(-1) }
    var showFenDialog by remember { mutableStateOf(false) }
    var fenInput by remember { mutableStateOf(TextFieldValue()) }
    var userMessage by remember { mutableStateOf<String?>(null) }

    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

    DisposableEffect(Unit) {
        onDispose {
            stockfishEngine.cleanup()
            leelaEngine.cleanup()
        }
    }

    fun getUserMessage(): String? {
        val status = analysisStatus
        return when {
            status is AnalysisStatus.Error -> status.message
            else -> userMessage
        }
    }

    fun getGameStateInfo(): GameStateInfo {
        val inCheck = MoveValidator.isKingInCheck(gameState.board, gameState.currentPlayer)
        val legalMoves = try {
            MoveValidator.generateLegalMoves(gameState.board, gameState)
        } catch (e: Exception) {
            emptyList()
        }
        val isCheckmate = inCheck && legalMoves.isEmpty()
        val isStalemate = !inCheck && legalMoves.isEmpty()
        val isDrawByFiftyMoves = gameState.halfMoveClock >= 100
        val moveNumber = gameState.fullMoveNumber

        return GameStateInfo(
            isCheck = inCheck,
            isCheckmate = isCheckmate,
            isStalemate = isStalemate,
            isDrawByFiftyMoves = isDrawByFiftyMoves,
            moveNumber = moveNumber
        )
    }

    fun makeMove(move: Move, recordToHistory: Boolean = true) {
        if (!MoveValidator.isValidMove(gameState.board, move, gameState)) {
            userMessage = "Invalid move: ${move.uci}"
            coroutineScope.launch {
                kotlinx.coroutines.delay(2000)
                if (userMessage?.contains("Invalid move") == true) {
                    userMessage = null
                }
            }
            return
        }
        
        val newState = gameState.makeMove(move)
        
        if (recordToHistory) {
            if (historyIndex < moveHistory.size - 1) {
                moveHistory = moveHistory.take(historyIndex + 1).toMutableList()
            }
            moveHistory = moveHistory + newState
            historyIndex = moveHistory.size - 1
        }
        
        gameState = newState
        lastMove = move
        selectedSquare = null
        availableMoves = emptyList()
        selectedPvMoves = emptyList()
        draggedPiece = null
        dragOffset = Offset.Zero
        userMessage = null
    }

    fun resetBoard() {
        gameState = GameState()
        moveHistory = emptyList()
        historyIndex = -1
        selectedSquare = null
        availableMoves = emptyList()
        selectedPvMoves = emptyList()
        lastMove = null
        draggedPiece = null
        dragOffset = Offset.Zero
        userMessage = null
    }

    fun undoMove() {
        if (historyIndex > 0) {
            historyIndex--
            gameState = moveHistory[historyIndex]
            lastMove = if (historyIndex > 0) gameState.moveHistory.lastOrNull() else null
            selectedSquare = null
            availableMoves = emptyList()
            selectedPvMoves = emptyList()
            draggedPiece = null
            dragOffset = Offset.Zero
            userMessage = null
        }
    }

    fun redoMove() {
        if (historyIndex < moveHistory.size - 1) {
            historyIndex++
            gameState = moveHistory[historyIndex]
            lastMove = gameState.moveHistory.lastOrNull()
            selectedSquare = null
            availableMoves = emptyList()
            selectedPvMoves = emptyList()
            draggedPiece = null
            dragOffset = Offset.Zero
            userMessage = null
        }
    }

    fun loadFen(fen: String): Boolean {
        return try {
            val parsedState = GameState.fromFen(fen)
            resetBoard()
            gameState = parsedState
            userMessage = "Position loaded successfully"
            coroutineScope.launch {
                kotlinx.coroutines.delay(2000)
                userMessage = null
            }
            true
        } catch (e: Exception) {
            userMessage = "Invalid FEN: ${e.message}"
            coroutineScope.launch {
                kotlinx.coroutines.delay(3000)
                userMessage = null
            }
            false
        }
    }

    fun copyFen() {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Chess Position", gameState.toFen())
        clipboard.setPrimaryClip(clip)
        userMessage = "FEN copied to clipboard"
        coroutineScope.launch {
            kotlinx.coroutines.delay(2000)
            userMessage = null
        }
    }
    
    fun selectPvLine(moves: List<Move>) {
        selectedPvMoves = moves
    }

    fun explorePvLine(moves: List<Move>) {
        resetBoard()
        moves.forEach { move ->
            if (MoveValidator.isValidMove(gameState.board, move, gameState)) {
                gameState = gameState.makeMove(move)
            }
        }
        selectedPvMoves = emptyList()
        userMessage = "Exploring PV line"
        coroutineScope.launch {
            kotlinx.coroutines.delay(2000)
            userMessage = null
        }
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
        val gameStateInfo = getGameStateInfo()
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
                                    time = line.time,
                                    nodes = line.nodes,
                                    moves = line.principalVariation
                                )
                            }
                        AnalysisStatus.Ready(
                            engineName = stockfishEngine.getEngineInfo() ?: "Stockfish",
                            engineType = EngineType.STOCKFISH,
                            lines = mappedLines,
                            fen = gameState.toFen(),
                            gameStateInfo = gameStateInfo
                        )
                    },
                    onFailure = { error ->
                        AnalysisStatus.Error(error.message ?: "Stockfish analysis failed")
                    }
                )
            }
            EngineType.GGUF -> {
                AnalysisStatus.Error(
                    "GGUF engine is not yet implemented. Please use Stockfish or LeelaChess0 for analysis.",
                    retryable = false
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
                            engineName = leelaEngine.getEngineInfo() ?: "LeelaChess0",
                            engineType = EngineType.LEELA_CHESS_ZERO,
                            lines = listOf(
                                AnalysisLine(
                                    index = 1,
                                    score = line.evaluation,
                                    depth = 0,
                                    time = line.time,
                                    nodes = line.nodes,
                                    moves = moves
                                )
                            ),
                            fen = gameState.toFen(),
                            gameStateInfo = gameStateInfo
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

    fun retryAnalysis() {
        // Trigger re-analysis by refreshing state
        analysisStatus = AnalysisStatus.Loading
        gameState = gameState.copy()
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed)),
        topBar = {
            TopAppBar(
                title = { Text("Position Analysis") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showFenDialog = true }) {
                        Icon(Icons.Default.ContentPaste, contentDescription = "Load FEN")
                    }
                    IconButton(onClick = { copyFen() }) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy FEN")
                    }
                }
            )
        },
        snackbarHost = { state ->
            SnackbarHost(state) { data ->
                Snackbar(
                    snackbarData = data,
                    actionColor = MaterialTheme.colors.primary
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                getUserMessage()?.let { message ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = 4.dp,
                        backgroundColor = if (message.contains("Invalid") || message.contains("Error")) {
                            MaterialTheme.colors.error.copy(alpha = 0.1f)
                        } else {
                            MaterialTheme.colors.primary.copy(alpha = 0.1f)
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = message,
                                color = when {
                                    message.contains("Invalid") || message.contains("Error") -> MaterialTheme.colors.error
                                    message.contains("successfully") -> MaterialTheme.colors.primary
                                    else -> MaterialTheme.colors.onSurface
                                },
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

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
