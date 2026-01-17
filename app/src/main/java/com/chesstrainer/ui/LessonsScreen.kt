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
import com.chesstrainer.data.LessonModule
import com.chesstrainer.data.LessonRepository
import com.chesstrainer.utils.Settings
import kotlinx.coroutines.launch

@Composable
fun LessonsScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val settings = remember { Settings(context) }
    val repository = remember { LessonRepository(context) }
    val modules by produceState<List<LessonModule>>(initialValue = emptyList(), repository) {
        value = repository.loadModules()
    }
    val completedExercises by repository.observeCompletedExercises().collectAsState(initial = emptySet())
    val coroutineScope = rememberCoroutineScope()

    var selectedModuleIndex by remember { mutableStateOf(0) }
    LaunchedEffect(modules) {
        if (selectedModuleIndex > modules.lastIndex) {
            selectedModuleIndex = modules.lastIndex.coerceAtLeast(0)
        }
    }
    val selectedModule = modules.getOrNull(selectedModuleIndex)

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
                        onExerciseCompleted = { id ->
                            coroutineScope.launch {
                                repository.markExerciseCompleted(id)
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
    onExerciseCompleted: (String) -> Unit
) {
    var gameState by remember(exercise.id) { mutableStateOf(GameState.fromFen(exercise.fen)) }
    var selectedSquare by remember(exercise.id) { mutableStateOf<Square?>(null) }
    var availableMoves by remember(exercise.id) { mutableStateOf<List<Move>>(emptyList()) }
    var lastMove by remember(exercise.id) { mutableStateOf<Move?>(null) }
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

    fun resetExercise() {
        gameState = GameState.fromFen(exercise.fen)
        selectedSquare = null
        availableMoves = emptyList()
        lastMove = null
        feedback = if (isLocked) {
            "Complete the previous exercise to unlock this lesson."
        } else {
            "Make your move."
        }
        draggedPiece = null
        dragOffset = Offset.Zero
    }

    fun makeMove(move: Move) {
        if (isLocked) return
        if (MoveValidator.isValidMove(gameState.board, move, gameState)) {
            val newState = gameState.makeMove(move)
            gameState = newState
            lastMove = move
            selectedSquare = null
            availableMoves = emptyList()
            draggedPiece = null
            dragOffset = Offset.Zero

            val isCorrect = exercise.expectedMoves.contains(move.uci)
            feedback = if (isCorrect) {
                exercise.explanation
            } else {
                "Not quite. Try a different move or reset the position."
            }
            if (isCorrect) {
                onExerciseCompleted(exercise.id)
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
        if (isLocked) return
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
        if (isLocked) return
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
        if (isLocked) return
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
                    isCompleted -> "Completed"
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
                TextButton(onClick = { resetExercise() }, enabled = !isLocked) {
                    Text("Reset")
                }
            }
        }
    }
}
