package com.chesstrainer.chess;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000  2\u00020\u0001:\u0001 B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\bH\u00c6\u0003J=\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\b\u0010\u001f\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\rR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\rR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\u0011\u001a\u00020\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006!"}, d2 = {"Lcom/chesstrainer/chess/Move;", "", "from", "Lcom/chesstrainer/chess/Square;", "to", "promotion", "Lcom/chesstrainer/chess/PieceType;", "isEnPassant", "", "isCastling", "(Lcom/chesstrainer/chess/Square;Lcom/chesstrainer/chess/Square;Lcom/chesstrainer/chess/PieceType;ZZ)V", "getFrom", "()Lcom/chesstrainer/chess/Square;", "()Z", "getPromotion", "()Lcom/chesstrainer/chess/PieceType;", "getTo", "uci", "", "getUci", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "Companion", "app_debug"})
public final class Move {
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.chess.Square from = null;
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.chess.Square to = null;
    @org.jetbrains.annotations.Nullable()
    private final com.chesstrainer.chess.PieceType promotion = null;
    private final boolean isEnPassant = false;
    private final boolean isCastling = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.chesstrainer.chess.Move.Companion Companion = null;
    
    public Move(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Square from, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Square to, @org.jetbrains.annotations.Nullable()
    com.chesstrainer.chess.PieceType promotion, boolean isEnPassant, boolean isCastling) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.Square getFrom() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.Square getTo() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.chesstrainer.chess.PieceType getPromotion() {
        return null;
    }
    
    public final boolean isEnPassant() {
        return false;
    }
    
    public final boolean isCastling() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUci() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.Square component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.Square component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.chesstrainer.chess.PieceType component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.Move copy(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Square from, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Square to, @org.jetbrains.annotations.Nullable()
    com.chesstrainer.chess.PieceType promotion, boolean isEnPassant, boolean isCastling) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/chesstrainer/chess/Move$Companion;", "", "()V", "fromUci", "Lcom/chesstrainer/chess/Move;", "uci", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.chesstrainer.chess.Move fromUci(@org.jetbrains.annotations.NotNull()
        java.lang.String uci) {
            return null;
        }
    }
}