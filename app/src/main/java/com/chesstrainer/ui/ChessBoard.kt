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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.chesstrainer.R
import com.chesstrainer.chess.*
import kotlin.math.min
import kotlin.math.roundToInt

private fun loadVectorAsBitmap(context: android.content.Context, resId: Int, density: Float): ImageBitmap? {
    return try {
        val drawable = ContextCompat.getDrawable(context, resId)
        if (drawable == null) {
            android.util.Log.e("ChessBoard", "Failed to load drawable for resource $resId")
            return null
        }
        // Create bitmap at a reasonable size - pieces will be scaled to fit squares
        val size = (72 * density).toInt() // 72dp - reasonable size for chess pieces
        android.util.Log.d("ChessBoard", "Loading bitmap for resource $resId with size ${size}x${size}")
        val bitmap = drawable.toBitmap(size, size)
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        android.util.Log.e("ChessBoard", "Failed to load bitmap for resource $resId", e)
        null
    }
}

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

    // Fixed board size for consistent appearance
    val boardSize = 320f // Fixed 320dp board size
    val squareSize = boardSize / 8f
    val pieceSize = squareSize * 0.85f // Pieces should fill most of the square

    Canvas(
        modifier = modifier
            .requiredSize(boardSize.dp, boardSize.dp) // Use requiredSize for exact sizing
            .pointerInput(gameState, boardOrientation) { // Include all dependencies for recomposition
                detectTapGestures(
                    onTap = { offset ->
                        val square = positionToSquare(offset, IntSize(boardSize.toInt(), boardSize.toInt()), boardOrientation)
                        square?.let { onSquareClick(it) }
                    }
                )
            }
            .pointerInput(gameState, boardOrientation) { // Include all dependencies for recomposition
                detectDragGestures(
                    onDragStart = { offset ->
                        val square = positionToSquare(offset, IntSize(boardSize.toInt(), boardSize.toInt()), boardOrientation)
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
                        val square = positionToSquare(dragPosition, IntSize(boardSize.toInt(), boardSize.toInt()), boardOrientation)
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
private fun rememberPieceImages(): PieceAssets {
    val context = LocalContext.current
    return remember {
        val density = context.resources.displayMetrics.density
        try {
            android.util.Log.d("ChessBoard", "Loading piece images with density: $density")
            val missingAsset = loadVectorAsBitmap(context, R.drawable.ic_chess_missing, density)
                ?: run {
                    android.util.Log.e("ChessBoard", "Missing required placeholder asset: ic_chess_missing")
                    ImageBitmap(1, 1)
                }
            val images = mapOf(
                PieceKey(Color.WHITE, PieceType.KING) to R.drawable.ic_chess_king_white,
                PieceKey(Color.WHITE, PieceType.QUEEN) to R.drawable.ic_chess_queen_white,
                PieceKey(Color.WHITE, PieceType.ROOK) to R.drawable.ic_chess_rook_white,
                PieceKey(Color.WHITE, PieceType.BISHOP) to R.drawable.ic_chess_bishop_white,
                PieceKey(Color.WHITE, PieceType.KNIGHT) to R.drawable.ic_chess_knight_white,
                PieceKey(Color.WHITE, PieceType.PAWN) to R.drawable.ic_chess_pawn_white,
                PieceKey(Color.BLACK, PieceType.KING) to R.drawable.ic_chess_king_black,
                PieceKey(Color.BLACK, PieceType.QUEEN) to R.drawable.ic_chess_queen_black,
                PieceKey(Color.BLACK, PieceType.ROOK) to R.drawable.ic_chess_rook_black,
                PieceKey(Color.BLACK, PieceType.BISHOP) to R.drawable.ic_chess_bishop_black,
                PieceKey(Color.BLACK, PieceType.KNIGHT) to R.drawable.ic_chess_knight_black,
                PieceKey(Color.BLACK, PieceType.PAWN) to R.drawable.ic_chess_pawn_black
            ).mapValues { (key, resId) ->
                loadVectorAsBitmap(context, resId, density) ?: run {
                    android.util.Log.e("ChessBoard", "Missing required piece asset for $key (resId=$resId)")
                    missingAsset
                }
            }
            android.util.Log.d("ChessBoard", "Successfully loaded ${images.size} piece images")
            PieceAssets(images, missingAsset)
        } catch (e: Exception) {
            android.util.Log.e("ChessBoard", "Failed to load piece images", e)
            PieceAssets(emptyMap(), ImageBitmap(1, 1))
        }
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
    pieceImages: PieceAssets,
    draggedPiece: Square? = null,
    dragPosition: Offset = Offset.Zero
) {
    val boardSize = min(size.width, size.height).toFloat()
    val squareSize = boardSize / 8f
    val pieceSize = squareSize * 0.76f

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
                val centerX = x + squareSize / 2
                val centerY = y + squareSize / 2
                drawChessPiece(pieceImages, piece, centerX, centerY, pieceSize)
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
    pieceImages: PieceAssets,
    piece: Piece,
    centerX: Float,
    centerY: Float,
    size: Float
) {
    val key = PieceKey(piece.color, piece.type)
    val image = pieceImages.pieces[key] ?: run {
        android.util.Log.e("ChessBoard", "Missing piece image for $key; using placeholder asset")
        pieceImages.missingAsset
    }

    val targetSize = size.roundToInt().coerceAtLeast(1)
    val topLeftX = (centerX - size / 2f).roundToInt()
    val topLeftY = (centerY - size / 2f).roundToInt()

    drawImage(
        image = image,
        dstSize = IntSize(targetSize, targetSize),
        dstOffset = IntOffset(topLeftX, topLeftY),
        alpha = 1.0f
    )
}

private fun DrawScope.drawCoordinates(
    boardSize: Float,
    squareSize: Float,
    boardOrientation: com.chesstrainer.chess.Color,
    theme: BoardTheme
) {
    val coordinateSize = squareSize * 0.2f

    // Draw file indicators (a-h) at the bottom - simple dots
    val fileY = boardSize + coordinateSize * 0.5f
    for (file in 0..7) {
        val displayFile = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) file else 7 - file
        val fileX = file * squareSize + squareSize / 2f
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
        val rankY = displayRank * squareSize + squareSize / 2f
        drawRect(
            color = theme.coordinateColor,
            topLeft = Offset(rankX - coordinateSize * 0.2f, rankY - coordinateSize * 0.2f),
            size = Size(coordinateSize * 0.4f, coordinateSize * 0.4f)
        )
    }
}

private data class PieceKey(val color: com.chesstrainer.chess.Color, val type: PieceType)

private data class PieceAssets(
    val pieces: Map<PieceKey, ImageBitmap>,
    val missingAsset: ImageBitmap
)

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
