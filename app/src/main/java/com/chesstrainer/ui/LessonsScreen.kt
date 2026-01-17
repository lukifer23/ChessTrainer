package com.chesstrainer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.chesstrainer.lessons.Lesson
import com.chesstrainer.lessons.LessonsRepository

@Composable
fun LessonsScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val repository = remember { LessonsRepository(context) }
    val lessons by remember { mutableStateOf(repository.loadLessons()) }
    var selectedLessonIndex by remember { mutableStateOf(0) }
    var selectedStepIndex by remember { mutableStateOf(0) }
    var stepGameState by remember { mutableStateOf(GameState()) }
    var selectedSquare by remember { mutableStateOf<Square?>(null) }
    var availableMoves by remember { mutableStateOf<List<Move>>(emptyList()) }
    var lastMove by remember { mutableStateOf<Move?>(null) }
    var feedbackMessage by remember { mutableStateOf("Select a move to solve the task.") }
    val lessonProgress = remember { mutableStateMapOf<String, Set<Int>>() }

    fun currentLesson(): Lesson? = lessons.getOrNull(selectedLessonIndex)
    val lesson = currentLesson()
    val step = lesson?.steps?.getOrNull(selectedStepIndex)

    fun loadStep(lessonToLoad: Lesson?, stepIndex: Int) {
        val stepToLoad = lessonToLoad?.steps?.getOrNull(stepIndex)
        if (stepToLoad != null) {
            stepGameState = GameState.fromFen(stepToLoad.fen)
            selectedSquare = null
            availableMoves = emptyList()
            lastMove = null
            feedbackMessage = "Select a move to solve the task."
        }
    }

    LaunchedEffect(lesson?.id, selectedStepIndex) {
        loadStep(lesson, selectedStepIndex)
    }

    fun updateAvailableMoves() {
        availableMoves = if (selectedSquare != null) {
            try {
                MoveValidator.generateLegalMoves(stepGameState.board, stepGameState).filter { it.from == selectedSquare }
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    fun onSquareClick(square: Square) {
        val piece = stepGameState.board.getPiece(square)
        when {
            selectedSquare != null -> {
                val selectedMove = availableMoves.find { it.to == square }
                if (selectedMove != null && step?.bestMove != null) {
                    val expectedMove = Move.fromUci(step.bestMove)
                    if (expectedMove != null && selectedMove.uci == expectedMove.uci) {
                        stepGameState = stepGameState.makeMove(selectedMove)
                        lastMove = selectedMove
                        val progress = lessonProgress[lesson?.id] ?: emptySet()
                        if (lesson != null) {
                            lessonProgress[lesson.id] = progress + selectedStepIndex
                        }
                        feedbackMessage = "✅ Correct! ${step.explanation}"
                    } else {
                        feedbackMessage = "❌ Not quite. Try again."
                    }
                }
                selectedSquare = null
                availableMoves = emptyList()
            }
            piece != null && piece.color == stepGameState.currentPlayer -> {
                selectedSquare = square
                updateAvailableMoves()
            }
            else -> {
                selectedSquare = null
                availableMoves = emptyList()
            }
        }
    }

    val progressSet = lesson?.let { lessonProgress[it.id] } ?: emptySet()
    val progressText = if (lesson != null) "${progressSet.size}/${lesson.steps.size} steps completed" else ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Chess Lessons",
            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = progressText,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = {
                        if (selectedLessonIndex > 0) {
                            selectedLessonIndex -= 1
                            selectedStepIndex = 0
                        }
                    },
                    enabled = selectedLessonIndex > 0
                ) {
                    Text("Prev Lesson")
                }
                OutlinedButton(
                    onClick = {
                        if (selectedLessonIndex < lessons.lastIndex) {
                            selectedLessonIndex += 1
                            selectedStepIndex = 0
                        }
                    },
                    enabled = selectedLessonIndex < lessons.lastIndex
                ) {
                    Text("Next Lesson")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (lessons.isEmpty()) {
            Text("No lessons available.")
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(lessons) { item ->
                        val isSelected = item.id == lesson?.id
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            backgroundColor = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.1f)
                            else MaterialTheme.colors.surface,
                            elevation = if (isSelected) 4.dp else 1.dp
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.subtitle1,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = item.description,
                                    style = MaterialTheme.typography.body2,
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = "${(lessonProgress[item.id]?.size ?: 0)}/${item.steps.size} completed",
                                    style = MaterialTheme.typography.caption
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        selectedLessonIndex = lessons.indexOf(item)
                                        selectedStepIndex = 0
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(if (isSelected) "Current Lesson" else "Start Lesson")
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(MaterialTheme.colors.surface)
                ) {
                    Text(
                        text = lesson?.title ?: "Lesson",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = lesson?.description ?: "",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (step != null) {
                        Text(
                            text = "Step ${selectedStepIndex + 1}: ${step.prompt}",
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        ChessBoard(
                            gameState = stepGameState,
                            selectedSquare = selectedSquare,
                            availableMoves = availableMoves,
                            lastMove = lastMove,
                            draggedPiece = null,
                            dragOffset = Offset.Zero,
                            onSquareClick = { square -> onSquareClick(square) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = feedbackMessage,
                            style = MaterialTheme.typography.body2
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    if (selectedStepIndex > 0) {
                                        selectedStepIndex -= 1
                                    }
                                },
                                enabled = selectedStepIndex > 0,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Prev Step")
                            }
                            OutlinedButton(
                                onClick = {
                                    loadStep(lesson, selectedStepIndex)
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Reset")
                            }
                            Button(
                                onClick = {
                                    if (lesson != null && selectedStepIndex < lesson.steps.lastIndex) {
                                        selectedStepIndex += 1
                                    }
                                },
                                enabled = lesson != null &&
                                    selectedStepIndex < lesson.steps.lastIndex &&
                                    progressSet.contains(selectedStepIndex),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Next Step")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onNavigateBack) {
            Text("Back")
        }
    }
}
