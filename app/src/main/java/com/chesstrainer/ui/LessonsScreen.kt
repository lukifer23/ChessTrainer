package com.chesstrainer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.chesstrainer.chess.GameState
import com.chesstrainer.chess.Move
import com.chesstrainer.chess.MoveValidator
import com.chesstrainer.chess.Square
import com.chesstrainer.chess.Color

private data class LessonExercise(
    val id: String,
    val title: String,
    val prompt: String,
    val fen: String,
    val expectedMoves: Set<String>,
    val explanation: String
)

private data class LessonModule(
    val title: String,
    val summary: String,
    val focusAreas: List<String>,
    val exercises: List<LessonExercise>
)

@Composable
fun LessonsScreen(onNavigateBack: () -> Unit) {
    val modules = remember {
        listOf(
            LessonModule(
                title = "Openings",
                summary = "Build a strong foundation by developing pieces, controlling the center, and safeguarding the king.",
                focusAreas = listOf(
                    "Center control with pawns",
                    "Fast minor piece development",
                    "Early king safety"
                ),
                exercises = listOf(
                    LessonExercise(
                        id = "openings-1",
                        title = "Claim the center",
                        prompt = "Choose a central pawn advance that stakes space and opens development.",
                        fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
                        expectedMoves = setOf("e2e4", "d2d4"),
                        explanation = "Great start! Central pawns open lines for the bishops and queen."
                    ),
                    LessonExercise(
                        id = "openings-2",
                        title = "Develop a minor piece",
                        prompt = "Bring a knight into play without moving the same piece twice.",
                        fen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1",
                        expectedMoves = setOf("g1f3", "b1c3"),
                        explanation = "Perfect! Knights develop naturally to f3 or c3."
                    )
                )
            ),
            LessonModule(
                title = "Tactics",
                summary = "Spot forcing moves to win material or deliver checkmate.",
                focusAreas = listOf(
                    "Checks, captures, threats",
                    "Mate patterns",
                    "Tactical motifs"
                ),
                exercises = listOf(
                    LessonExercise(
                        id = "tactics-1",
                        title = "Mate in one",
                        prompt = "Find the immediate checkmate against the exposed king.",
                        fen = "6k1/5Q2/6K1/8/8/8/8/8 w - - 0 1",
                        expectedMoves = setOf("f7g7"),
                        explanation = "Mate! The queen covers all escape squares with Qg7#."
                    ),
                    LessonExercise(
                        id = "tactics-2",
                        title = "Win the queen",
                        prompt = "Play the skewer that wins material.",
                        fen = "4k3/8/4q3/8/4Q3/8/4K3/8 w - - 0 1",
                        expectedMoves = setOf("e4c6"),
                        explanation = "Nice! Qc6+ forces the queen to block, winning it next."
                    )
                )
            ),
            LessonModule(
                title = "Endgames",
                summary = "Convert small advantages with king activity and precise pawn play.",
                focusAreas = listOf(
                    "Opposition",
                    "Passed pawns",
                    "King activity"
                ),
                exercises = listOf(
                    LessonExercise(
                        id = "endgames-1",
                        title = "Advance the passer",
                        prompt = "Push the passed pawn to gain ground while your king supports.",
                        fen = "8/8/4k3/8/4P3/4K3/8/8 w - - 0 1",
                        expectedMoves = setOf("e4e5"),
                        explanation = "Great! The pawn advance gains space and limits the king."
                    ),
                    LessonExercise(
                        id = "endgames-2",
                        title = "Activate the king",
                        prompt = "Centralize your king to support the pawn race.",
                        fen = "8/4k3/8/8/8/4K3/4P3/8 w - - 0 1",
                        expectedMoves = setOf("e3d4", "e3f4"),
                        explanation = "Nice king move! Centralizing helps escort the pawn."
                    )
                )
            )
        )
    }

    var selectedModuleIndex by remember { mutableStateOf(0) }
    val selectedModule = modules[selectedModuleIndex]

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

        TabRow(selectedTabIndex = selectedModuleIndex) {
            modules.forEachIndexed { index, module ->
                Tab(
                    selected = selectedModuleIndex == index,
                    onClick = { selectedModuleIndex = index },
                    text = { Text(module.title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = selectedModule.title, style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = selectedModule.summary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Focus areas", style = MaterialTheme.typography.subtitle1)
                    Spacer(modifier = Modifier.height(8.dp))
                    selectedModule.focusAreas.forEach { focus ->
                        Text(text = "• $focus")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Interactive Exercises", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))

            selectedModule.exercises.forEach { exercise ->
                LessonExerciseCard(exercise = exercise)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { selectedModuleIndex = (selectedModuleIndex - 1).coerceAtLeast(0) },
                enabled = selectedModuleIndex > 0
            ) {
                Text("Previous")
            }
            OutlinedButton(
                onClick = { selectedModuleIndex = (selectedModuleIndex + 1).coerceAtMost(modules.lastIndex) },
                enabled = selectedModuleIndex < modules.lastIndex
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
private fun LessonExerciseCard(exercise: LessonExercise) {
    var gameState by remember(exercise.id) { mutableStateOf(GameState.fromFen(exercise.fen)) }
    var selectedSquare by remember(exercise.id) { mutableStateOf<Square?>(null) }
    var availableMoves by remember(exercise.id) { mutableStateOf<List<Move>>(emptyList()) }
    var lastMove by remember(exercise.id) { mutableStateOf<Move?>(null) }
    var feedback by remember(exercise.id) { mutableStateOf("Make your move.") }
    var draggedPiece by remember(exercise.id) { mutableStateOf<Square?>(null) }
    var dragOffset by remember(exercise.id) { mutableStateOf(Offset.Zero) }

    fun resetExercise() {
        gameState = GameState.fromFen(exercise.fen)
        selectedSquare = null
        availableMoves = emptyList()
        lastMove = null
        feedback = "Make your move."
        draggedPiece = null
        dragOffset = Offset.Zero
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

            feedback = if (exercise.expectedMoves.contains(move.uci)) {
                exercise.explanation
            } else {
                "Not quite. Try a different move or reset the position."
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

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = exercise.title, style = MaterialTheme.typography.subtitle1)
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
                boardOrientation = Color.WHITE,
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
                TextButton(onClick = { resetExercise() }) {
                    Text("Reset")
                }
            }
        }
    }
}
