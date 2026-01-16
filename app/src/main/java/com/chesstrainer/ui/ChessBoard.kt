package com.chesstrainer.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chesstrainer.chess.*
import kotlin.math.min

data class BoardTheme(
    val lightSquare: ComposeColor = ComposeColor(0xFFF0D9B5),
    val darkSquare: ComposeColor = ComposeColor(0xFFB58863),
    val selectedSquare: ComposeColor = ComposeColor(0xFF7B68EE),
    val availableMove: ComposeColor = ComposeColor(0xFF32CD32),
    val lastMove: ComposeColor = ComposeColor(0xFFFFA500),
    val checkSquare: ComposeColor = ComposeColor(0xFFDC143C),
    val borderColor: ComposeColor = ComposeColor(0xFF8B4513)
)

@Composable
fun ChessBoard(
    gameState: GameState,
    selectedSquare: Square? = null,
    availableMoves: List<Move> = emptyList(),
    lastMove: Move? = null,
    boardOrientation: com.chesstrainer.chess.Color = com.chesstrainer.chess.Color.WHITE,
    onSquareClick: (Square) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val theme = BoardTheme()

    Canvas(
        modifier = modifier
            .aspectRatio(1f)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val position = event.changes.first().position

                        // Convert screen coordinates to board coordinates
                        val boardSize = min(size.width, size.height).toFloat()
                        val squareSize = boardSize / 8f
                        val offsetX = (size.width - boardSize) / 2f
                        val offsetY = (size.height - boardSize) / 2f

                        val file = ((position.x - offsetX) / squareSize).toInt()
                        val rank = ((position.y - offsetY) / squareSize).toInt()

                        // Flip rank if black is at bottom
                        val actualRank = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) {
                            7 - rank
                        } else {
                            rank
                        }

                        if (file in 0..7 && actualRank in 0..7) {
                            val square = Square(file, actualRank)
                            onSquareClick(square)
                        }
                    }
                }
            }
    ) {
        drawBoard(gameState, theme, selectedSquare, availableMoves, lastMove, boardOrientation)
    }
}

private fun DrawScope.drawBoard(
    gameState: GameState,
    theme: BoardTheme,
    selectedSquare: Square?,
    availableMoves: List<Move>,
    lastMove: Move?,
    boardOrientation: com.chesstrainer.chess.Color
) {
    val boardSize = min(size.width, size.height)
    val squareSize = boardSize / 8f
    val offsetX = (size.width - boardSize) / 2f
    val offsetY = (size.height - boardSize) / 2f

    // Draw squares and pieces
    for (rank in 0..7) {
        for (file in 0..7) {
            val square = Square(file, rank)

            // Determine square color
            val isLightSquare = (file + rank) % 2 == 0
            var squareColor = if (isLightSquare) theme.lightSquare else theme.darkSquare

            // Highlight special squares
            when {
                square == selectedSquare -> squareColor = theme.selectedSquare
                availableMoves.any { it.to == square } -> squareColor = theme.availableMove.copy(alpha = 0.8f)
                lastMove != null && (square == lastMove.from || square == lastMove.to) -> squareColor = theme.lastMove.copy(alpha = 0.6f)
            }

            // Calculate position (accounting for orientation)
            val displayRank = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) 7 - rank else rank
            val x = offsetX + file * squareSize
            val y = offsetY + displayRank * squareSize

            // Draw square
            drawRect(
                color = squareColor,
                topLeft = Offset(x, y),
                size = Size(squareSize, squareSize)
            )

            // Draw piece using Unicode chess symbols
            val piece = gameState.board.getPiece(square)
            if (piece != null) {
                drawChessPiece(piece, x + squareSize / 2, y + squareSize / 2, squareSize * 0.8f)
            }

            // Draw available move indicators
            if (availableMoves.any { it.to == square }) {
                drawCircle(
                    color = theme.availableMove.copy(alpha = 0.7f),
                    center = Offset(x + squareSize / 2, y + squareSize / 2),
                    radius = squareSize * 0.1f
                )
            }
        }
    }

    // Draw board border
    drawRect(
        color = theme.borderColor,
        topLeft = Offset(offsetX - 2, offsetY - 2),
        size = Size(boardSize + 4, boardSize + 4),
        style = Stroke(width = 4f)
    )
}

private fun DrawScope.drawChessPiece(piece: Piece, centerX: Float, centerY: Float, size: Float) {
    // Draw chess piece using Unicode symbols
    val pieceSymbol = getChessPieceSymbol(piece)
    val textSize = size * 0.7f

    // Create text layout for the piece symbol
    val textMeasurer = androidx.compose.ui.text.TextMeasurer.Default
    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(pieceSymbol),
        style = androidx.compose.ui.text.TextStyle(
            fontSize = textSize.sp,
            color = ComposeColor.Black
        )
    )

    // Draw piece symbol centered
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(
            centerX - textLayoutResult.size.width / 2f,
            centerY - textLayoutResult.size.height / 2f
        )
    )
}

private fun getChessPieceSymbol(piece: Piece): String {
    return when (piece.color) {
        com.chesstrainer.chess.Color.WHITE -> when (piece.type) {
            PieceType.KING -> "♔"
            PieceType.QUEEN -> "♕"
            PieceType.ROOK -> "♖"
            PieceType.BISHOP -> "♗"
            PieceType.KNIGHT -> "♘"
            PieceType.PAWN -> "♙"
        }
        com.chesstrainer.chess.Color.BLACK -> when (piece.type) {
            PieceType.KING -> "♚"
            PieceType.QUEEN -> "♛"
            PieceType.ROOK -> "♜"
            PieceType.BISHOP -> "♝"
            PieceType.KNIGHT -> "♞"
            PieceType.PAWN -> "♟"
        }
    }
}