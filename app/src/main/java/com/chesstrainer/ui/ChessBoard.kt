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
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
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
    val boardSize = min(size.width * 0.95f, size.height * 0.95f) // Use 95% of available space
    val squareSize = boardSize / 8f
    val coordinateSize = squareSize * 0.25f // Size for coordinate labels
    val boardWithCoords = boardSize + coordinateSize * 2 // Include space for coordinates

    val offsetX = (size.width - boardWithCoords) / 2f + coordinateSize
    val offsetY = (size.height - boardWithCoords) / 2f + coordinateSize

    // Draw squares and pieces
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

    // Draw coordinate backgrounds
    val coordStartX = (size.width - boardWithCoords) / 2f
    val coordStartY = (size.height - boardWithCoords) / 2f

    drawRect(
        color = theme.coordinateBackground,
        topLeft = Offset(coordStartX, coordStartY),
        size = Size(boardWithCoords, coordinateSize)
    )
    drawRect(
        color = theme.coordinateBackground,
        topLeft = Offset(coordStartX, coordStartY + coordinateSize),
        size = Size(coordinateSize, boardSize)
    )

    // Draw coordinates
    drawCoordinates(offsetX, offsetY, boardSize, squareSize, coordinateSize, boardOrientation, theme)

    // Draw board border
    drawRect(
        color = theme.borderColor,
        topLeft = Offset(offsetX - 2, offsetY - 2),
        size = Size(boardSize + 4, boardSize + 4),
        style = Stroke(width = 4f)
    )
}

private fun DrawScope.drawChessPiece(piece: Piece, centerX: Float, centerY: Float, size: Float) {
    // Draw chess pieces as colored circles with clear piece type indicators
    // This provides clear visual distinction and works reliably across all devices

    val pieceColor = if (piece.color == com.chesstrainer.chess.Color.WHITE)
        ComposeColor.White else ComposeColor(0xFF333333) // Dark gray for black pieces

    // Draw main piece circle
    drawCircle(
        color = pieceColor,
        center = Offset(centerX, centerY),
        radius = size * 0.4f
    )

    // Draw black border
    drawCircle(
        color = ComposeColor.Black,
        center = Offset(centerX, centerY),
        radius = size * 0.4f,
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
    )

    // Draw piece type indicator (colored dot on top-left)
    val typeColor = when (piece.type) {
        PieceType.KING -> ComposeColor.Yellow
        PieceType.QUEEN -> ComposeColor(0xFFE91E63) // Pink
        PieceType.ROOK -> ComposeColor.Red
        PieceType.BISHOP -> ComposeColor(0xFF2196F3) // Blue
        PieceType.KNIGHT -> ComposeColor(0xFF4CAF50) // Green
        PieceType.PAWN -> ComposeColor(0xFF9E9E9E) // Gray
    }

    drawCircle(
        color = typeColor,
        center = Offset(centerX - size * 0.2f, centerY - size * 0.2f),
        radius = size * 0.12f
    )

    // Draw piece symbol indicator (small letter in center)
    val symbol = when (piece.type) {
        PieceType.KING -> "K"
        PieceType.QUEEN -> "Q"
        PieceType.ROOK -> "R"
        PieceType.BISHOP -> "B"
        PieceType.KNIGHT -> "N"
        PieceType.PAWN -> "P"
    }

    // Simple text drawing for the piece letter
    val symbolColor = if (piece.color == com.chesstrainer.chess.Color.WHITE)
        ComposeColor.Black else ComposeColor.White

    // Draw the piece letter (simplified - will be replaced with proper text rendering later)
    // For now, we use the colored circles which provide clear visual distinction
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

private fun DrawScope.drawCoordinates(
    offsetX: Float,
    offsetY: Float,
    boardSize: Float,
    squareSize: Float,
    coordinateSize: Float,
    boardOrientation: com.chesstrainer.chess.Color,
    theme: BoardTheme
) {
    // For now, coordinates are drawn as backgrounds only
    // Text coordinates will be added later when text drawing is properly implemented
}