package com.chesstrainer.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import android.graphics.Paint
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.chesstrainer.R
import com.chesstrainer.chess.*
import kotlin.math.min
import kotlin.math.roundToInt

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
    val pieceImages = rememberPieceImages()

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
        drawBoard(
            gameState,
            theme,
            selectedSquare,
            availableMoves,
            lastMove,
            boardOrientation,
            pieceImages,
            draggedPiece,
            dragPosition
        )
    }
}

@Composable
private fun rememberPieceImages(): Map<PieceKey, ImageBitmap> {
    val resources = LocalContext.current.resources
    return remember(resources) {
        mapOf(
            PieceKey(Color.WHITE, PieceType.KING) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_king_white),
            PieceKey(Color.WHITE, PieceType.QUEEN) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_queen_white),
            PieceKey(Color.WHITE, PieceType.ROOK) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_rook_white),
            PieceKey(Color.WHITE, PieceType.BISHOP) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_bishop_white),
            PieceKey(Color.WHITE, PieceType.KNIGHT) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_knight_white),
            PieceKey(Color.WHITE, PieceType.PAWN) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_pawn_white),
            PieceKey(Color.BLACK, PieceType.KING) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_king_black),
            PieceKey(Color.BLACK, PieceType.QUEEN) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_queen_black),
            PieceKey(Color.BLACK, PieceType.ROOK) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_rook_black),
            PieceKey(Color.BLACK, PieceType.BISHOP) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_bishop_black),
            PieceKey(Color.BLACK, PieceType.KNIGHT) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_knight_black),
            PieceKey(Color.BLACK, PieceType.PAWN) to ImageBitmap.imageResource(resources, R.drawable.ic_chess_pawn_black)
        )
    }
}

private fun positionToSquare(position: Offset, canvasSize: IntSize, boardOrientation: com.chesstrainer.chess.Color): Square? {
    val boardSize = min(canvasSize.width.toFloat(), canvasSize.height.toFloat())
    val squareSize = boardSize / 8f
    val offsetX = (canvasSize.width.toFloat() - boardSize) / 2f
    val offsetY = (canvasSize.height.toFloat() - boardSize) / 2f

    val displayFile = ((position.x - offsetX) / squareSize).toInt()
    val displayRank = ((position.y - offsetY) / squareSize).toInt()

    val actualFile = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) {
        displayFile
    } else {
        7 - displayFile
    }
    val actualRank = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) {
        7 - displayRank
    } else {
        displayRank
    }

    return if (actualFile in 0..7 && actualRank in 0..7) {
        Square(actualFile, actualRank)
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
    pieceImages: Map<PieceKey, ImageBitmap>,
    draggedPiece: Square? = null,
    dragPosition: Offset = Offset.Zero
) {
    val boardSize = min(size.width, size.height).toFloat()
    val squareSize = boardSize / 8f
    val pieceSize = squareSize * 0.8f

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
            val displayFile = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) file else 7 - file
            val displayRank = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) 7 - rank else rank
            val x = displayFile * squareSize
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
                drawChessPiece(pieceImages, piece, x + squareSize / 2, y + squareSize / 2, pieceSize)
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
            drawChessPiece(pieceImages, draggedPieceObj, dragPosition.x, dragPosition.y, pieceSize)
        }
    }
}

private fun DrawScope.drawChessPiece(
    pieceImages: Map<PieceKey, ImageBitmap>,
    piece: Piece,
    centerX: Float,
    centerY: Float,
    size: Float
) {
    val image = pieceImages[PieceKey(piece.color, piece.type)] ?: return
    val topLeft = Offset(centerX - size / 2f, centerY - size / 2f)
    val dstSize = IntSize(size.roundToInt(), size.roundToInt())
    drawImage(image = image, topLeft = topLeft, dstSize = dstSize)
}

private fun DrawScope.drawCoordinates(
    boardSize: Float,
    squareSize: Float,
    boardOrientation: com.chesstrainer.chess.Color,
    theme: BoardTheme
) {
    val textSize = squareSize * 0.25f
    val paint = Paint().apply {
        isAntiAlias = true
        color = theme.coordinateColor.toArgb()
        this.textSize = textSize
        textAlign = Paint.Align.CENTER
    }
    val fontMetrics = paint.fontMetrics
    val centerYOffset = (fontMetrics.ascent + fontMetrics.descent) / 2f

    drawIntoCanvas { canvas ->
        val fileY = boardSize + textSize
        for (file in 0..7) {
            val displayFile = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) file else 7 - file
            val fileX = file * squareSize + squareSize / 2f
            val label = ('a' + displayFile).toString()
            canvas.nativeCanvas.drawText(label, fileX, fileY, paint)
        }

        paint.textAlign = Paint.Align.RIGHT
        val rankX = -textSize * 0.1f
        for (rank in 0..7) {
            val displayRank = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) 7 - rank else rank
            val rankY = displayRank * squareSize + squareSize / 2f - centerYOffset
            val label = (rank + 1).toString()
            canvas.nativeCanvas.drawText(label, rankX, rankY, paint)
        }
    }
}

private data class PieceKey(val color: com.chesstrainer.chess.Color, val type: PieceType)

@Preview(showBackground = true, name = "Piece Asset Mapping")
@Composable
private fun PieceAssetMappingPreview() {
    val board = Board().apply {
        setPiece(Square(0, 0), Piece(PieceType.ROOK, Color.WHITE))
        setPiece(Square(1, 0), Piece(PieceType.KNIGHT, Color.WHITE))
        setPiece(Square(2, 0), Piece(PieceType.BISHOP, Color.WHITE))
        setPiece(Square(3, 0), Piece(PieceType.QUEEN, Color.WHITE))
        setPiece(Square(4, 0), Piece(PieceType.KING, Color.WHITE))
        setPiece(Square(5, 0), Piece(PieceType.PAWN, Color.WHITE))
        setPiece(Square(0, 7), Piece(PieceType.ROOK, Color.BLACK))
        setPiece(Square(1, 7), Piece(PieceType.KNIGHT, Color.BLACK))
        setPiece(Square(2, 7), Piece(PieceType.BISHOP, Color.BLACK))
        setPiece(Square(3, 7), Piece(PieceType.QUEEN, Color.BLACK))
        setPiece(Square(4, 7), Piece(PieceType.KING, Color.BLACK))
        setPiece(Square(5, 7), Piece(PieceType.PAWN, Color.BLACK))
    }
    ChessBoard(
        gameState = GameState(board = board),
        modifier = Modifier
            .size(360.dp)
            .padding(12.dp)
    )
}
