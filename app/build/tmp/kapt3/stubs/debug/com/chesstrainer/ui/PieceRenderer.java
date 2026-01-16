package com.chesstrainer.ui;

/**
 * Efficient piece renderer using vector drawables for chess pieces.
 * Provides caching and optimized rendering for game performance.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nH\u0007J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\r\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u000e"}, d2 = {"Lcom/chesstrainer/ui/PieceRenderer;", "", "()V", "ChessPieceImage", "", "piece", "Lcom/chesstrainer/chess/Piece;", "modifier", "Landroidx/compose/ui/Modifier;", "contentDescription", "", "getPieceResourceId", "", "getPieceSymbol", "app_debug"})
public final class PieceRenderer {
    @org.jetbrains.annotations.NotNull()
    public static final com.chesstrainer.ui.PieceRenderer INSTANCE = null;
    
    private PieceRenderer() {
        super();
    }
    
    /**
     * Get the drawable resource ID for a chess piece
     */
    public final int getPieceResourceId(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Piece piece) {
        return 0;
    }
    
    /**
     * Render a chess piece using vector drawable
     */
    @androidx.compose.runtime.Composable()
    public final void ChessPieceImage(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Piece piece, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.Nullable()
    java.lang.String contentDescription) {
    }
    
    /**
     * Get piece symbol for fallback or text-based rendering
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPieceSymbol(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Piece piece) {
        return null;
    }
}