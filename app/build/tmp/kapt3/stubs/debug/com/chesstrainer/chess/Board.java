package com.chesstrainer.chess;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\u0000J\u0018\u0010\b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00050\n0\tJ\u0010\u0010\f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u000bJ \u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00050\n0\t2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000bJ\u000e\u0010\u0013\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000bJ\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\r\u001a\u00020\u000b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0005J\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u0019\u001a\u00020\u0018H\u0016R\u0018\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006\u00a8\u0006\u001b"}, d2 = {"Lcom/chesstrainer/chess/Board;", "", "()V", "squares", "", "Lcom/chesstrainer/chess/Piece;", "[Lcom/chesstrainer/chess/Piece;", "copy", "getAllPieces", "", "Lkotlin/Pair;", "Lcom/chesstrainer/chess/Square;", "getPiece", "square", "getPieces", "color", "Lcom/chesstrainer/chess/Color;", "isEmpty", "", "isOccupied", "setPiece", "", "piece", "toFen", "", "toString", "Companion", "app_debug"})
public final class Board {
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.chess.Piece[] squares = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.chesstrainer.chess.Board.Companion Companion = null;
    
    public Board() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.chesstrainer.chess.Piece getPiece(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Square square) {
        return null;
    }
    
    public final void setPiece(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Square square, @org.jetbrains.annotations.Nullable()
    com.chesstrainer.chess.Piece piece) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.Board copy() {
        return null;
    }
    
    public final boolean isEmpty(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Square square) {
        return false;
    }
    
    public final boolean isOccupied(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Square square) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.chesstrainer.chess.Square, com.chesstrainer.chess.Piece>> getPieces(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Color color) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<com.chesstrainer.chess.Square, com.chesstrainer.chess.Piece>> getAllPieces() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String toFen() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0004\u00a8\u0006\b"}, d2 = {"Lcom/chesstrainer/chess/Board$Companion;", "", "()V", "fromFen", "Lcom/chesstrainer/chess/Board;", "fen", "", "initialPosition", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.chess.Board fromFen(@org.jetbrains.annotations.NotNull()
        java.lang.String fen) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.chess.Board initialPosition() {
            return null;
        }
    }
}