package com.chesstrainer.chess;

/**
 * Utilities for converting chess moves to and from standard algebraic notation.
 * Handles all special cases including disambiguation, checks, checkmates, etc.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0018\u0010\n\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\tJ\u001a\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001a\u0010\u0013\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\tH\u0002J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J \u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\f\u001a\u00020\tH\u0002J \u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\f\u001a\u00020\tH\u0002J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0016\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\tJ\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\tH\u0002\u00a8\u0006\u001e"}, d2 = {"Lcom/chesstrainer/chess/AlgebraicNotation;", "", "()V", "addCheckAnnotations", "", "moveAlgebraic", "move", "Lcom/chesstrainer/chess/Move;", "resultingGameState", "Lcom/chesstrainer/chess/GameState;", "algebraicToMove", "algebraic", "gameState", "findKing", "Lcom/chesstrainer/chess/Square;", "board", "Lcom/chesstrainer/chess/Board;", "color", "Lcom/chesstrainer/chess/Color;", "getCastlingMove", "getCastlingNotation", "getDisambiguation", "piece", "Lcom/chesstrainer/chess/Piece;", "getPieceMoveNotation", "getPieceSymbol", "pieceType", "Lcom/chesstrainer/chess/PieceType;", "moveToAlgebraic", "parseRegularMove", "app_debug"})
public final class AlgebraicNotation {
    @org.jetbrains.annotations.NotNull()
    public static final com.chesstrainer.chess.AlgebraicNotation INSTANCE = null;
    
    private AlgebraicNotation() {
        super();
    }
    
    /**
     * Convert a move to standard algebraic notation
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String moveToAlgebraic(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Move move, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.GameState gameState) {
        return null;
    }
    
    private final java.lang.String getCastlingNotation(com.chesstrainer.chess.Move move) {
        return null;
    }
    
    private final java.lang.String getPieceMoveNotation(com.chesstrainer.chess.Move move, com.chesstrainer.chess.Piece piece, com.chesstrainer.chess.GameState gameState) {
        return null;
    }
    
    private final java.lang.String getPieceSymbol(com.chesstrainer.chess.PieceType pieceType) {
        return null;
    }
    
    /**
     * Get disambiguation characters when multiple pieces of the same type
     * can move to the same square
     */
    private final java.lang.String getDisambiguation(com.chesstrainer.chess.Move move, com.chesstrainer.chess.Piece piece, com.chesstrainer.chess.GameState gameState) {
        return null;
    }
    
    /**
     * Add check/checkmate annotations to a move
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String addCheckAnnotations(@org.jetbrains.annotations.NotNull()
    java.lang.String moveAlgebraic, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Move move, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.GameState resultingGameState) {
        return null;
    }
    
    /**
     * Parse algebraic notation to a move (simplified implementation)
     */
    @org.jetbrains.annotations.Nullable()
    public final com.chesstrainer.chess.Move algebraicToMove(@org.jetbrains.annotations.NotNull()
    java.lang.String algebraic, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.GameState gameState) {
        return null;
    }
    
    private final com.chesstrainer.chess.Move getCastlingMove(java.lang.String algebraic, com.chesstrainer.chess.GameState gameState) {
        return null;
    }
    
    private final com.chesstrainer.chess.Move parseRegularMove(java.lang.String algebraic, com.chesstrainer.chess.GameState gameState) {
        return null;
    }
    
    private final com.chesstrainer.chess.Square findKing(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Color color) {
        return null;
    }
}