package com.chesstrainer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.chesstrainer.chess.Square
import com.chesstrainer.chess.Color
import com.chesstrainer.data.LessonExercise
import com.chesstrainer.data.LessonExerciseProgress
import com.chesstrainer.data.LessonModule
import com.chesstrainer.data.LessonRepository
import com.chesstrainer.engine.LeelaEngine
import com.chesstrainer.engine.StockfishEngine
import com.chesstrainer.engine.UCIParser
import com.chesstrainer.utils.EngineType
import com.chesstrainer.utils.Settings
import kotlinx.coroutines.launch

@Composable
fun LessonsScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val settings = remember { Settings(context) }
    val repository = remember { LessonRepository(context) }
    val stockfishEngine = remember { StockfishEngine(context, settings) }
    val leelaEngine = remember { LeelaEngine(context, settings) }
    val modules by produceState<List<LessonModule>>(initialValue = emptyList(), repository) {
        value = repository.loadModules()
    }
    val completedExercises by repository.observeCompletedExercises().collectAsState(initial = emptySet())
    val exerciseProgress by repository.observeExerciseProgress().collectAsState(initial = emptyMap())
    val coroutineScope = rememberCoroutineScope()

    var selectedModuleIndex by remember { mutableStateOf(0) }
    LaunchedEffect(modules) {
        if (selectedModuleIndex > modules.lastIndex) {
            selectedModuleIndex = modules.lastIndex.coerceAtLeast(0)
        }
    }
    val selectedModule = modules.getOrNull(selectedModuleIndex)

    DisposableEffect(Unit) {
        onDispose {
            stockfishEngine.cleanup()
            leelaEngine.cleanup()
        }
    }

    val analysisProvider: suspend (GameState) -> LessonAnalysis? = { state ->
        when (settings.engineType) {
            EngineType.STOCKFISH -> {
                stockfishEngine.getAnalysis(state).getOrNull()?.firstOrNull()?.let { analysis ->
                    LessonAnalysis(
                        bestMove = analysis.principalVariation.firstOrNull(),
                        score = analysis.score
                    )
                }
            }
            EngineType.LEELA_CHESS_ZERO -> {
                leelaEngine.getAnalysis(state).getOrNull()?.let { analysis ->
                    LessonAnalysis(
                        bestMove = analysis.bestMove,
                        score = analysis.evaluation
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Chess Lessons",
            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (modules.isEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            TabRow(selectedTabIndex = selectedModuleIndex) {
                modules.forEachIndexed { index, module ->
                    Tab(
                        selected = selectedModuleIndex == index,
                        onClick = { selectedModuleIndex = index },
                        text = { Text(module.title) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            selectedModule?.let { module ->
                val completedCount = module.exercises.count { completedExercises.contains(it.id) }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = module.title, style = MaterialTheme.typography.h6)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Progress: $completedCount/${module.exercises.size} completed",
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = module.summary)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Focus areas", style = MaterialTheme.typography.subtitle1)
                        Spacer(modifier = Modifier.height(8.dp))
                        module.focusAreas.forEach { focus ->
                            Text(text = "• $focus")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Interactive Exercises", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))

                module.exercises.forEachIndexed { index, exercise ->
                    val isCompleted = completedExercises.contains(exercise.id)
                    val isLocked = !isCompleted && module.exercises.take(index).any { previous ->
                        !completedExercises.contains(previous.id)
                    }
                    LessonExerciseCard(
                        exercise = exercise,
                        boardOrientation = settings.boardOrientation,
                        isCompleted = isCompleted,
                        isLocked = isLocked,
                        exerciseProgress = exerciseProgress[exercise.id],
                        analysisProvider = analysisProvider,
                        onExerciseCompleted = { id, score ->
                            coroutineScope.launch {
                                repository.markExerciseCompleted(id, score)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { selectedModuleIndex = (selectedModuleIndex - 1).coerceAtLeast(0) },
                enabled = selectedModuleIndex > 0 && modules.isNotEmpty()
            ) {
                Text("Previous")
            }
            OutlinedButton(
                onClick = { selectedModuleIndex = (selectedModuleIndex + 1).coerceAtMost(modules.lastIndex) },
                enabled = modules.isNotEmpty() && selectedModuleIndex < modules.lastIndex
            ) {
                Text("Next")
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
private fun LessonExerciseCard(
    exercise: LessonExercise,
    boardOrientation: Color,
    isCompleted: Boolean,
    isLocked: Boolean,
    exerciseProgress: LessonExerciseProgress?,
    analysisProvider: suspend (GameState) -> LessonAnalysis?,
    onExerciseCompleted: (String, Int) -> Unit
) {
    var gameState by remember(exercise.id) { mutableStateOf(GameState.fromFen(exercise.fen)) }
    var selectedSquare by remember(exercise.id) { mutableStateOf<Square?>(null) }
    var availableMoves by remember(exercise.id) { mutableStateOf<List<Move>>(emptyList()) }
    var lastMove by remember(exercise.id) { mutableStateOf<Move?>(null) }
    var currentStep by remember(exercise.id, exerciseProgress?.completed) {
        mutableStateOf(if (exerciseProgress?.completed == true) exercise.solutionLine.size else 0)
    }
    var score by remember(exercise.id, exerciseProgress?.score) { mutableStateOf(exerciseProgress?.score ?: 0) }
    var feedback by remember(exercise.id, isLocked) {
        mutableStateOf(
            if (isLocked) {
                "Complete the previous exercise to unlock this lesson."
            } else {
                "Make your move."
            }
        )
    }
    var draggedPiece by remember(exercise.id) { mutableStateOf<Square?>(null) }
    var dragOffset by remember(exercise.id) { mutableStateOf(Offset.Zero) }
    var isEvaluating by remember(exercise.id) { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    fun resetExercise() {
        gameState = GameState.fromFen(exercise.fen)
        selectedSquare = null
        availableMoves = emptyList()
        lastMove = null
        currentStep = 0
        score = 0
        feedback = if (isLocked) {
            "Complete the previous exercise to unlock this lesson."
        } else {
            "Make your move."
        }
        draggedPiece = null
        dragOffset = Offset.Zero
        isEvaluating = false
    }

    fun makeMove(move: Move) {
        if (isLocked || isEvaluating) return
        if (MoveValidator.isValidMove(gameState.board, move, gameState)) {
            coroutineScope.launch {
                isEvaluating = true
                feedback = "Checking your move..."
                val playerColor = gameState.currentPlayer
                val expectedMove = exercise.solutionLine.getOrNull(currentStep)
                val analysisBefore = analysisProvider(gameState)
                val bestScore = toCentipawns(
                    analysisBefore?.score,
                    perspective = playerColor,
                    sideToMove = gameState.currentPlayer
                )
                val newState = gameState.makeMove(move)
                val analysisAfter = analysisProvider(newState)
                val moveScore = toCentipawns(
                    analysisAfter?.score,
                    perspective = playerColor,
                    sideToMove = newState.currentPlayer
                )
                val evalOk = isEvaluationAcceptable(
                    moveScore = moveScore,
                    bestScore = bestScore,
                    minEval = exercise.minEval,
                    maxMistake = exercise.maxMistake
                )
                val isCorrect = expectedMove != null && expectedMove == move.uci && evalOk
                if (isCorrect) {
                    val updatedScore = (score + exercise.scorePerMove).coerceAtMost(exercise.resolvedMaxScore())
                    score = updatedScore
                    val newStep = currentStep + 1
                    currentStep = newStep
                    gameState = newState
                    lastMove = move
                    selectedSquare = null
                    availableMoves = emptyList()
                    draggedPiece = null
                    dragOffset = Offset.Zero
                    feedback = if (newStep >= exercise.solutionLine.size) {
                        onExerciseCompleted(exercise.id, updatedScore)
                        "Exercise complete! Score: $updatedScore/${exercise.resolvedMaxScore()}"
                    } else {
                        "Good move! Step $newStep/${exercise.solutionLine.size}."
                    }
                } else {
                    val reason = buildString {
                        if (expectedMove == null) {
                            append("This exercise has no solution line.")
                        } else if (expectedMove != move.uci) {
                            append("That move doesn't match the lesson line.")
                        } else {
                            append("That move doesn't meet the evaluation target.")
                        }
                        if (exercise.minEval != null || exercise.maxMistake != null) {
                            append(" ")
                            append(formatEvalHint(moveScore, bestScore, exercise.minEval, exercise.maxMistake))
                        }
                    }
                    feedback = reason
                }
                selectedSquare = null
                availableMoves = emptyList()
                draggedPiece = null
                dragOffset = Offset.Zero
                isEvaluating = false
            }
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

    fun onDragStart(square: Square) {
        if (isLocked || isEvaluating) return
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
        if (isLocked || isEvaluating) return
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
        if (isLocked || isEvaluating) return
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

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = exercise.title, style = MaterialTheme.typography.subtitle1)
                val statusText = when {
                    isCompleted -> "Completed (${exerciseProgress?.score ?: score}/${exercise.resolvedMaxScore()})"
                    isLocked -> "Locked"
                    else -> "In progress"
                }
                val statusColor = when {
                    isCompleted -> MaterialTheme.colors.primary
                    isLocked -> MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    else -> MaterialTheme.colors.secondary
                }
                Text(
                    text = statusText,
                    style = MaterialTheme.typography.caption,
                    color = statusColor,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = exercise.prompt)
            if (exercise.solutionLine.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                val displayStep = currentStep.coerceAtMost(exercise.solutionLine.size)
                Text(
                    text = "Step $displayStep/${exercise.solutionLine.size}",
                    style = MaterialTheme.typography.caption
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            ChessBoard(
                gameState = gameState,
                selectedSquare = selectedSquare,
                availableMoves = availableMoves,
                lastMove = lastMove,
                draggedPiece = draggedPiece,
                dragOffset = dragOffset,
                boardOrientation = boardOrientation,
                onSquareClick = { square -> onSquareClick(square) },
                onDragStart = { square -> onDragStart(square) },
                onDragEnd = { square -> onDragEnd(square) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 220.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = feedback, style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { resetExercise() }, enabled = !isLocked && !isEvaluating) {
                    Text("Reset")
                }
            }
        }
    }
}

private data class LessonAnalysis(
    val bestMove: Move?,
    val score: UCIParser.Score?
)

private fun toCentipawns(
    score: UCIParser.Score?,
    perspective: Color,
    sideToMove: Color
): Int? {
    val raw = when {
        score?.mate != null -> if (score.mate > 0) 100000 else -100000
        score?.centipawns != null -> score.centipawns
        else -> null
    } ?: return null
    return if (sideToMove == perspective) raw else -raw
}

private fun isEvaluationAcceptable(
    moveScore: Int?,
    bestScore: Int?,
    minEval: Int?,
    maxMistake: Int?
): Boolean {
    if (minEval != null && moveScore != null && moveScore < minEval) return false
    if (maxMistake != null && moveScore != null && bestScore != null) {
        val drop = bestScore - moveScore
        if (drop > maxMistake) return false
    }
    return true
}

private fun formatEvalHint(
    moveScore: Int?,
    bestScore: Int?,
    minEval: Int?,
    maxMistake: Int?
): String {
    val parts = mutableListOf<String>()
    if (moveScore != null) {
        parts.add("Eval: ${moveScore}cp")
    }
    if (minEval != null) {
        parts.add("Min target: ${minEval}cp")
    }
    if (maxMistake != null && bestScore != null) {
        parts.add("Best: ${bestScore}cp (max drop ${maxMistake}cp)")
    }
    return parts.joinToString(" · ")
}
