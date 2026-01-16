package com.chesstrainer.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.chesstrainer.R
import com.chesstrainer.chess.Piece
import com.chesstrainer.chess.PieceType
import com.chesstrainer.chess.Color as ChessColor

/**
 * Efficient piece renderer using vector drawables for chess pieces.
 * Provides caching and optimized rendering for game performance.
 */
object PieceRenderer {

    /**
     * Get the drawable resource ID for a chess piece
     */
    fun getPieceResourceId(piece: Piece): Int {
        return when (piece.color) {
            ChessColor.WHITE -> when (piece.type) {
                PieceType.KING -> R.drawable.ic_chess_king_white
                PieceType.QUEEN -> R.drawable.ic_chess_queen_white
                PieceType.ROOK -> R.drawable.ic_chess_rook_white
                PieceType.BISHOP -> R.drawable.ic_chess_bishop_white
                PieceType.KNIGHT -> R.drawable.ic_chess_knight_white
                PieceType.PAWN -> R.drawable.ic_chess_pawn_white
            }
            ChessColor.BLACK -> when (piece.type) {
                PieceType.KING -> R.drawable.ic_chess_king_black
                PieceType.QUEEN -> R.drawable.ic_chess_queen_black
                PieceType.ROOK -> R.drawable.ic_chess_rook_black
                PieceType.BISHOP -> R.drawable.ic_chess_bishop_black
                PieceType.KNIGHT -> R.drawable.ic_chess_knight_black
                PieceType.PAWN -> R.drawable.ic_chess_pawn_black
            }
        }
    }

    /**
     * Render a chess piece using vector drawable
     */
    @Composable
    fun ChessPieceImage(
        piece: Piece,
        modifier: Modifier = Modifier,
        contentDescription: String? = null
    ) {
        val resourceId = getPieceResourceId(piece)
        Image(
            painter = painterResource(id = resourceId),
            contentDescription = contentDescription ?: "${piece.color.name.lowercase()} ${piece.type.name.lowercase()}",
            modifier = modifier
        )
    }

    /**
     * Get piece symbol for fallback or text-based rendering
     */
    fun getPieceSymbol(piece: Piece): String {
        return when (piece.color) {
            ChessColor.WHITE -> when (piece.type) {
                PieceType.KING -> "♔"
                PieceType.QUEEN -> "♕"
                PieceType.ROOK -> "♖"
                PieceType.BISHOP -> "♗"
                PieceType.KNIGHT -> "♘"
                PieceType.PAWN -> "♙"
            }
            ChessColor.BLACK -> when (piece.type) {
                PieceType.KING -> "♚"
                PieceType.QUEEN -> "♛"
                PieceType.ROOK -> "♜"
                PieceType.BISHOP -> "♝"
                PieceType.KNIGHT -> "♞"
                PieceType.PAWN -> "♟"
            }
        }
    }
}