package com.chesstrainer.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.chesstrainer.chess.*
import kotlin.math.min

data class BoardTheme(
    val lightSquare: ComposeColor = ComposeColor(0xFFF0D9B5),
    val darkSquare: ComposeColor = ComposeColor(0xFFB58863),
    val selectedSquare: ComposeColor = ComposeColor(0xFF4CAF50),
    val availableMove: ComposeColor = ComposeColor(0xFF2196F3),
    val lastMove: ComposeColor = ComposeColor(0xFFFF9800),
    val checkSquare: ComposeColor = ComposeColor(0xFFF44336),
    val borderColor: ComposeColor = ComposeColor(0xFF795548),
    val coordinateColor: ComposeColor = ComposeColor(0xFF424242),
    val coordinateBackground: ComposeColor = ComposeColor(0xFFE0E0E0)
)

@Composable
fun ChessBoard(
    gameState: GameState,
    selectedSquare: Square? = null,
    availableMoves: List<Move> = emptyList(),
    lastMove: Move? = null,
    draggedPiece: Square? = null,
    dragOffset: Offset = Offset.Zero,
    boardOrientation: com.chesstrainer.chess.Color = com.chesstrainer.chess.Color.WHITE,
    onSquareClick: (Square) -> Unit = {},
    onDragStart: (Square) -> Unit = {},
    onDragEnd: (Square?) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val theme = BoardTheme()

    var dragPosition by remember { mutableStateOf(Offset.Zero) }

    Canvas(
        modifier = modifier
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val square = positionToSquare(offset, IntSize(size.width.toInt(), size.height.toInt()), boardOrientation)
                        square?.let { onSquareClick(it) }
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val square = positionToSquare(offset, IntSize(size.width.toInt(), size.height.toInt()), boardOrientation)
                        if (square != null) {
                            val piece = gameState.board.getPiece(square)
                            if (piece != null && piece.color == gameState.currentPlayer) {
                                onDragStart(square)
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        dragPosition = change.position
                    },
                    onDragEnd = {
                        val square = positionToSquare(dragPosition, IntSize(size.width.toInt(), size.height.toInt()), boardOrientation)
                        onDragEnd(square)
                        dragPosition = Offset.Zero
                    },
                    onDragCancel = {
                        onDragEnd(null)
                        dragPosition = Offset.Zero
                    }
                )
            }
    ) {
        drawBoard(gameState, theme, selectedSquare, availableMoves, lastMove, boardOrientation, draggedPiece, dragPosition)
    }
}

private fun positionToSquare(position: Offset, canvasSize: IntSize, boardOrientation: com.chesstrainer.chess.Color): Square? {
    val boardSize = min(canvasSize.width.toFloat(), canvasSize.height.toFloat())
    val squareSize = boardSize / 8f
    val offsetX = (canvasSize.width.toFloat() - boardSize) / 2f
    val offsetY = (canvasSize.height.toFloat() - boardSize) / 2f

    val file = ((position.x - offsetX) / squareSize).toInt()
    val rank = ((position.y - offsetY) / squareSize).toInt()

    // Flip rank if black is at bottom
    val actualRank = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) {
        7 - rank
    } else {
        rank
    }

    return if (file in 0..7 && actualRank in 0..7) {
        Square(file, actualRank)
    } else {
        null
    }
}

private fun DrawScope.drawBoard(
    gameState: GameState,
    theme: BoardTheme,
    selectedSquare: Square?,
    availableMoves: List<Move>,
    lastMove: Move?,
    boardOrientation: com.chesstrainer.chess.Color,
    draggedPiece: Square? = null,
    dragPosition: Offset = Offset.Zero
) {
    val boardSize = min(size.width, size.height).toFloat()
    val squareSize = boardSize / 8f

    // Draw squares
    for (rank in 0..7) {
        for (file in 0..7) {
            val square = Square(file, rank)

            // Determine square color
            val isLightSquare = (file + rank) % 2 == 0
            var squareColor = if (isLightSquare) theme.lightSquare else theme.darkSquare

            // Highlight special squares
            when {
                square == selectedSquare -> squareColor = theme.selectedSquare.copy(alpha = 0.8f)
                availableMoves.any { it.to == square } -> {
                    // Keep original color but we'll add highlight overlay
                }
                lastMove != null && (square == lastMove.from || square == lastMove.to) -> squareColor = theme.lastMove.copy(alpha = 0.7f)
                MoveValidator.isKingInCheck(gameState.board, gameState.currentPlayer) &&
                gameState.board.getPiece(square)?.type == PieceType.KING &&
                gameState.board.getPiece(square)?.color == gameState.currentPlayer -> squareColor = theme.checkSquare.copy(alpha = 0.8f)
            }

            // Calculate position (accounting for orientation)
            val displayRank = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) 7 - rank else rank
            val x = file * squareSize
            val y = displayRank * squareSize

            // Draw square
            drawRect(
                color = squareColor,
                topLeft = Offset(x, y),
                size = Size(squareSize, squareSize)
            )

            // Draw piece (skip if being dragged)
            val piece = gameState.board.getPiece(square)
            if (piece != null && square != draggedPiece) {
                drawChessPiece(piece, x + squareSize / 2, y + squareSize / 2, squareSize * 0.8f)
            }

            // Draw available move indicators
            if (availableMoves.any { it.to == square }) {
                // Draw a ring around the square
                drawCircle(
                    color = theme.availableMove.copy(alpha = 0.8f),
                    center = Offset(x + squareSize / 2, y + squareSize / 2),
                    radius = squareSize * 0.15f,
                    style = Stroke(width = 3f)
                )

                // If square is empty, draw a dot in the center
                if (gameState.board.getPiece(square) == null) {
                    drawCircle(
                        color = theme.availableMove.copy(alpha = 0.6f),
                        center = Offset(x + squareSize / 2, y + squareSize / 2),
                        radius = squareSize * 0.08f
                    )
                }
            }
        }
    }

    // Draw coordinate labels
    drawCoordinates(boardSize, squareSize, boardOrientation, theme)

    // Draw board border
    drawRect(
        color = theme.borderColor,
        topLeft = Offset(-2f, -2f),
        size = Size(boardSize + 4, boardSize + 4),
        style = Stroke(width = 4f)
    )

    // Draw dragged piece
    if (draggedPiece != null) {
        val draggedPieceObj = gameState.board.getPiece(draggedPiece)
        if (draggedPieceObj != null) {
            drawChessPiece(draggedPieceObj, dragPosition.x, dragPosition.y, boardSize * 0.1f)
        }
    }
}

private fun DrawScope.drawChessPiece(piece: Piece, centerX: Float, centerY: Float, size: Float) {
    // Simple colored rectangle representation for now
    // TODO: Replace with proper vector graphics or bitmap rendering
    val color = when (piece.color) {
        com.chesstrainer.chess.Color.WHITE -> androidx.compose.ui.graphics.Color(0xFFE8E8E8)
        com.chesstrainer.chess.Color.BLACK -> androidx.compose.ui.graphics.Color(0xFF2A2A2A)
    }

    val borderColor = when (piece.color) {
        com.chesstrainer.chess.Color.WHITE -> androidx.compose.ui.graphics.Color.Black
        com.chesstrainer.chess.Color.BLACK -> androidx.compose.ui.graphics.Color.White
    }

    // Draw piece body
    drawRect(
        color = color,
        topLeft = Offset(centerX - size * 0.3f, centerY - size * 0.3f),
        size = Size(size * 0.6f, size * 0.6f)
    )

    // Draw border
    drawRect(
        color = borderColor,
        topLeft = Offset(centerX - size * 0.3f, centerY - size * 0.3f),
        size = Size(size * 0.6f, size * 0.6f),
        style = Stroke(width = 2f)
    )

    // Draw simple piece type indicator
    when (piece.type) {
        PieceType.KING -> {
            // Crown-like shape
            drawRect(
                color = borderColor,
                topLeft = Offset(centerX - size * 0.15f, centerY - size * 0.25f),
                size = Size(size * 0.3f, size * 0.1f)
            )
        }
        PieceType.QUEEN -> {
            // Cross shape
            drawRect(
                color = borderColor,
                topLeft = Offset(centerX - size * 0.02f, centerY - size * 0.25f),
                size = Size(size * 0.04f, size * 0.2f)
            )
            drawRect(
                color = borderColor,
                topLeft = Offset(centerX - size * 0.1f, centerY - size * 0.17f),
                size = Size(size * 0.2f, size * 0.04f)
            )
        }
        PieceType.ROOK -> {
            // Battlements
            for (i in -1..1) {
                drawRect(
                    color = borderColor,
                    topLeft = Offset(centerX + i * size * 0.08f - size * 0.04f, centerY - size * 0.25f),
                    size = Size(size * 0.08f, size * 0.1f)
                )
            }
        }
        PieceType.BISHOP -> {
            // Mitre
            drawRect(
                color = borderColor,
                topLeft = Offset(centerX - size * 0.05f, centerY - size * 0.25f),
                size = Size(size * 0.1f, size * 0.15f)
            )
        }
        PieceType.KNIGHT -> {
            // Horse head shape (simplified)
            drawRect(
                color = borderColor,
                topLeft = Offset(centerX - size * 0.08f, centerY - size * 0.25f),
                size = Size(size * 0.16f, size * 0.1f)
            )
        }
        PieceType.PAWN -> {
            // Simple pawn (no special shape needed)
        }
    }
}

private fun DrawScope.drawCoordinates(
    boardSize: Float,
    squareSize: Float,
    boardOrientation: com.chesstrainer.chess.Color,
    theme: BoardTheme
) {
    val coordinateSize = squareSize * 0.2f

    // Draw file indicators (a-h) at the bottom - simple dots for now
    val fileY = boardSize + coordinateSize * 0.5f
    for (file in 0..7) {
        val fileX = file * squareSize + squareSize / 2
        // Draw a small dot for each file
        drawCircle(
            color = theme.coordinateColor,
            center = Offset(fileX, fileY),
            radius = coordinateSize * 0.3f
        )
    }

    // Draw rank indicators (1-8) on the left side - small rectangles
    val rankX = -coordinateSize * 0.4f
    for (rank in 0..7) {
        val displayRank = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) 7 - rank else rank
        val rankY = displayRank * squareSize + squareSize / 2
        // Draw a small rectangle for each rank
        drawRect(
            color = theme.coordinateColor,
            topLeft = Offset(rankX - coordinateSize * 0.2f, rankY - coordinateSize * 0.2f),
            size = Size(coordinateSize * 0.4f, coordinateSize * 0.4f)
        )
    }
}
