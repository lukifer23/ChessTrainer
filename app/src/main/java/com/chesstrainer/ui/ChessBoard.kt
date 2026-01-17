package com.chesstrainer.ui

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
        // Create bitmap at a higher resolution for crisp rendering
        val size = (192 * density).toInt() // 192dp - high res for scaling
        android.util.Log.d("ChessBoard", "Loading bitmap for resource $resId with size ${size}x${size}")
        val bitmap = drawable.toBitmap(size, size)
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        android.util.Log.e("ChessBoard", "Failed to load bitmap for resource $resId", e)
        null
    }
}

data class BoardTheme(
    val lightSquare: ComposeColor = ComposeColor(0xFFE8D090), // Warm Wood Light
    val darkSquare: ComposeColor = ComposeColor(0xFFA66D4F), // Warm Wood Dark
    val selectedSquare: ComposeColor = ComposeColor(0x80547936), // Selection Green
    val availableMove: ComposeColor = ComposeColor(0x80547936), // Highlight for move (same as selection but transparent)
    val availableCapture: ComposeColor = ComposeColor(0x80D21404), // Soft Red for capture
    val lastMoveFrom: ComposeColor = ComposeColor(0x80CDDC39), // Highlight for from square
    val lastMoveTo: ComposeColor = ComposeColor(0x80CDDC39), // Highlight for to square
    val checkSquare: ComposeColor = ComposeColor(0xFFFF4444), // Red gradient base
    val borderColor: ComposeColor = ComposeColor(0xFF2C1B10), // Dark Wood Border
    val coordinateColor: ComposeColor = ComposeColor(0xFFE8D090),
    val coordinateBackground: ComposeColor = ComposeColor(0xFF2C1B10)
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
    val density = LocalDensity.current

    // Animation state
    val animatable = remember { Animatable(0f, Float.VectorConverter) }
    var animatingMove by remember { mutableStateOf<Move?>(null) }
    
    // Trigger animation when lastMove changes
    LaunchedEffect(lastMove) {
        if (lastMove != null) {
            animatingMove = lastMove
            animatable.snapTo(0f)
            animatable.animateTo(1f, animationSpec = tween(durationMillis = 200))
            animatingMove = null
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        val availableSize = min(maxWidth.value, maxHeight.value).dp
        val boardSize = availableSize
        val boardSizePx = with(density) { boardSize.toPx() }
        
        // Debug log for board size
        SideEffect {
             android.util.Log.d("ChessBoard", "Rendering board: size=$boardSizePx, draggedPiece=$draggedPiece, dragPosition=$dragPosition")
        }

        Canvas(
            modifier = Modifier
                .size(boardSize, boardSize)
                .pointerInput(gameState, boardOrientation, boardSizePx, selectedSquare, availableMoves) {
                    val boardSizeInt = IntSize(boardSizePx.toInt(), boardSizePx.toInt())
                    
                    detectTapGestures { offset ->
                        val square = positionToSquare(offset, boardSizeInt, boardOrientation)
                        if (square != null) {
                            onSquareClick(square)
                        }
                    }
                }
                .pointerInput(gameState, boardOrientation, boardSizePx, selectedSquare, availableMoves) {
                    val boardSizeInt = IntSize(boardSizePx.toInt(), boardSizePx.toInt())
                    
                    detectDragGestures(
                        onDragStart = { offset ->
                             val square = positionToSquare(offset, boardSizeInt, boardOrientation)
                             if (square != null) {
                                 val piece = gameState.board.getPiece(square)
                                 if (piece != null && piece.color == gameState.currentPlayer) {
                                     android.util.Log.d("ChessBoard", "Drag start: $square at $offset")
                                     onDragStart(square)
                                     dragPosition = offset
                                 }
                             }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            dragPosition = change.position
                        },
                        onDragEnd = {
                             val square = positionToSquare(dragPosition, boardSizeInt, boardOrientation)
                             android.util.Log.d("ChessBoard", "Drag end: dropped at $square")
                             onDragEnd(square)
                             dragPosition = Offset.Zero
                        },
                        onDragCancel = {
                             android.util.Log.d("ChessBoard", "Drag cancel")
                             onDragEnd(null)
                             dragPosition = Offset.Zero
                        }
                    )
                },
            onDraw = {
                drawBoard(
                    gameState = gameState,
                    theme = theme,
                    selectedSquare = selectedSquare,
                    availableMoves = availableMoves,
                    lastMove = lastMove,
                    boardOrientation = boardOrientation,
                    pieceImages = pieceImages,
                    draggedPiece = draggedPiece,
                    dragPosition = dragPosition,
                    animatingMove = animatingMove,
                    animationProgress = animatable.value
                )
            }
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
    dragPosition: Offset = Offset.Zero,
    animatingMove: Move? = null,
    animationProgress: Float = 1f
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
            // Highlight special squares
            when {
                square == selectedSquare -> squareColor = theme.selectedSquare
                lastMove != null && square == lastMove.from -> squareColor = theme.lastMoveFrom
                lastMove != null && square == lastMove.to -> squareColor = theme.lastMoveTo
                MoveValidator.isKingInCheck(gameState.board, gameState.currentPlayer) &&
                gameState.board.getPiece(square)?.type == PieceType.KING &&
                gameState.board.getPiece(square)?.color == gameState.currentPlayer -> {
                    // Draw radial gradient for check later, keeps base color
                }
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

            // Draw Check Highlight
            if (MoveValidator.isKingInCheck(gameState.board, gameState.currentPlayer) &&
                gameState.board.getPiece(square)?.type == PieceType.KING &&
                gameState.board.getPiece(square)?.color == gameState.currentPlayer) {
                 drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(theme.checkSquare, ComposeColor.Transparent),
                        center = Offset(x + squareSize/2, y + squareSize/2),
                        radius = squareSize * 0.8f
                    ),
                    topLeft = Offset(x, y),
                    size = Size(squareSize, squareSize),
                    alpha = 0.8f
                )
            }

            // Draw piece (skip if being dragged OR animating)
            val piece = gameState.board.getPiece(square)
            val isAnimatingToHere = animatingMove != null && animatingMove.to == square
            
            if (piece != null && square != draggedPiece && !isAnimatingToHere) {
                val centerX = x + squareSize / 2
                val centerY = y + squareSize / 2
                drawChessPiece(pieceImages, piece, centerX, centerY, pieceSize)
            }

            // Draw available move indicators
            if (availableMoves.any { it.to == square }) {
                val isCapture = gameState.board.getPiece(square) != null
                val center = Offset(x + squareSize / 2, y + squareSize / 2)
                
                if (isCapture) {
                    // Capture Ring
                    drawCircle(
                        color = theme.availableCapture,
                        center = center,
                        radius = squareSize * 0.4f,
                        style = Stroke(width = squareSize * 0.1f)
                    )
                } else {
                    // Move Indicator (Ring instead of dot)
                    drawCircle(
                        color = theme.availableMove,
                        center = center,
                        radius = squareSize * 0.15f
                    )
                }
            }
        }
    }

    // Draw coordinate labels
    drawCoordinates(boardSize, squareSize, boardOrientation, theme)

    // Draw animating piece
    if (animatingMove != null) {
        val piece = gameState.board.getPiece(animatingMove.to)
        if (piece != null) {
            val fromFile = animatingMove.from.file
            val fromRank = animatingMove.from.rank
            val toFile = animatingMove.to.file
            val toRank = animatingMove.to.rank

            val fromDisplayFile = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) fromFile else 7 - fromFile
            val fromDisplayRank = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) 7 - fromRank else fromRank
            val toDisplayFile = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) toFile else 7 - toFile
            val toDisplayRank = if (boardOrientation == com.chesstrainer.chess.Color.WHITE) 7 - toRank else toRank

            val startX = fromDisplayFile * squareSize + squareSize / 2
            val startY = fromDisplayRank * squareSize + squareSize / 2
            val endX = toDisplayFile * squareSize + squareSize / 2
            val endY = toDisplayRank * squareSize + squareSize / 2

            val currentX = androidx.compose.ui.util.lerp(startX, endX, animationProgress)
            val currentY = androidx.compose.ui.util.lerp(startY, endY, animationProgress)

            drawChessPiece(pieceImages, piece, currentX, currentY, pieceSize)
        }
    }

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
    @Suppress("UNUSED_PARAMETER") boardSize: Float,
    @Suppress("UNUSED_PARAMETER") squareSize: Float,
    @Suppress("UNUSED_PARAMETER") boardOrientation: com.chesstrainer.chess.Color,
    @Suppress("UNUSED_PARAMETER") theme: BoardTheme
) {
    // Coordinate drawing temporarily disabled due to nativeCanvas API changes
    // TODO: Implement coordinate drawing using Compose Text components
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
