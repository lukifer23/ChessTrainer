package com.chesstrainer.chess;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b*\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\b\u0018\u0000 B2\u00020\u0001:\u0001BB{\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\u0002\u0010\u0015J&\u0010(\u001a\u00020\u00142\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u00c6\u0003J\t\u0010+\u001a\u00020\u0014H\u00c6\u0003J\t\u0010,\u001a\u00020\u0005H\u00c6\u0003J\t\u0010-\u001a\u00020\u0007H\u00c6\u0003J\t\u0010.\u001a\u00020\u0007H\u00c6\u0003J\t\u0010/\u001a\u00020\u0007H\u00c6\u0003J\t\u00100\u001a\u00020\u0007H\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\t\u00102\u001a\u00020\u000eH\u00c6\u0003J\t\u00103\u001a\u00020\u000eH\u00c6\u0003J\u007f\u00104\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u00c6\u0001J\u0013\u00105\u001a\u00020\u00072\b\u00106\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\b\u00107\u001a\u0004\u0018\u00010\u0005J\t\u00108\u001a\u00020\u000eH\u00d6\u0001J\u0006\u00109\u001a\u00020\u0007J\u0010\u0010:\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0016\u0010;\u001a\u00020\u00072\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u000e\u0010<\u001a\u00020\u00002\u0006\u0010=\u001a\u00020\u0012J\u0006\u0010>\u001a\u00020?J\t\u0010@\u001a\u00020?H\u00d6\u0001J\f\u0010A\u001a\u00020\u0005*\u00020\u0005H\u0002R\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010 R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0017R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0017\u00a8\u0006C"}, d2 = {"Lcom/chesstrainer/chess/GameState;", "", "board", "Lcom/chesstrainer/chess/Board;", "currentPlayer", "Lcom/chesstrainer/chess/Color;", "whiteCanCastleKingside", "", "whiteCanCastleQueenside", "blackCanCastleKingside", "blackCanCastleQueenside", "enPassantTarget", "Lcom/chesstrainer/chess/Square;", "halfMoveClock", "", "fullMoveNumber", "moveHistory", "", "Lcom/chesstrainer/chess/Move;", "gameResult", "Lcom/chesstrainer/chess/GameResult;", "(Lcom/chesstrainer/chess/Board;Lcom/chesstrainer/chess/Color;ZZZZLcom/chesstrainer/chess/Square;IILjava/util/List;Lcom/chesstrainer/chess/GameResult;)V", "getBlackCanCastleKingside", "()Z", "getBlackCanCastleQueenside", "getBoard", "()Lcom/chesstrainer/chess/Board;", "getCurrentPlayer", "()Lcom/chesstrainer/chess/Color;", "getEnPassantTarget", "()Lcom/chesstrainer/chess/Square;", "getFullMoveNumber", "()I", "getGameResult", "()Lcom/chesstrainer/chess/GameResult;", "getHalfMoveClock", "getMoveHistory", "()Ljava/util/List;", "getWhiteCanCastleKingside", "getWhiteCanCastleQueenside", "checkGameResult", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "getWinner", "hashCode", "isGameOver", "isInsufficientMaterial", "isThreefoldRepetition", "makeMove", "move", "toFen", "", "toString", "opposite", "Companion", "app_debug"})
public final class GameState {
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.chess.Board board = null;
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.chess.Color currentPlayer = null;
    private final boolean whiteCanCastleKingside = false;
    private final boolean whiteCanCastleQueenside = false;
    private final boolean blackCanCastleKingside = false;
    private final boolean blackCanCastleQueenside = false;
    @org.jetbrains.annotations.Nullable()
    private final com.chesstrainer.chess.Square enPassantTarget = null;
    private final int halfMoveClock = 0;
    private final int fullMoveNumber = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.chesstrainer.chess.Move> moveHistory = null;
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.chess.GameResult gameResult = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.chesstrainer.chess.GameState.Companion Companion = null;
    
    public GameState(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Board board, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Color currentPlayer, boolean whiteCanCastleKingside, boolean whiteCanCastleQueenside, boolean blackCanCastleKingside, boolean blackCanCastleQueenside, @org.jetbrains.annotations.Nullable()
    com.chesstrainer.chess.Square enPassantTarget, int halfMoveClock, int fullMoveNumber, @org.jetbrains.annotations.NotNull()
    java.util.List<com.chesstrainer.chess.Move> moveHistory, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.GameResult gameResult) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.Board getBoard() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.Color getCurrentPlayer() {
        return null;
    }
    
    public final boolean getWhiteCanCastleKingside() {
        return false;
    }
    
    public final boolean getWhiteCanCastleQueenside() {
        return false;
    }
    
    public final boolean getBlackCanCastleKingside() {
        return false;
    }
    
    public final boolean getBlackCanCastleQueenside() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.chesstrainer.chess.Square getEnPassantTarget() {
        return null;
    }
    
    public final int getHalfMoveClock() {
        return 0;
    }
    
    public final int getFullMoveNumber() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.chesstrainer.chess.Move> getMoveHistory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.GameResult getGameResult() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String toFen() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.GameState makeMove(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Move move) {
        return null;
    }
    
    private final com.chesstrainer.chess.GameResult checkGameResult(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Color currentPlayer, java.util.List<com.chesstrainer.chess.Move> moveHistory) {
        return null;
    }
    
    private final boolean isThreefoldRepetition(java.util.List<com.chesstrainer.chess.Move> moveHistory) {
        return false;
    }
    
    private final boolean isInsufficientMaterial(com.chesstrainer.chess.Board board) {
        return false;
    }
    
    public final boolean isGameOver() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.chesstrainer.chess.Color getWinner() {
        return null;
    }
    
    private final com.chesstrainer.chess.Color opposite(com.chesstrainer.chess.Color $this$opposite) {
        return null;
    }
    
    public GameState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.Board component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.chesstrainer.chess.Move> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.GameResult component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.Color component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.chesstrainer.chess.Square component7() {
        return null;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.GameState copy(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Board board, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Color currentPlayer, boolean whiteCanCastleKingside, boolean whiteCanCastleQueenside, boolean blackCanCastleKingside, boolean blackCanCastleQueenside, @org.jetbrains.annotations.Nullable()
    com.chesstrainer.chess.Square enPassantTarget, int halfMoveClock, int fullMoveNumber, @org.jetbrains.annotations.NotNull()
    java.util.List<com.chesstrainer.chess.Move> moveHistory, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.GameResult gameResult) {
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
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/chesstrainer/chess/GameState$Companion;", "", "()V", "fromFen", "Lcom/chesstrainer/chess/GameState;", "fen", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.chess.GameState fromFen(@org.jetbrains.annotations.NotNull()
        java.lang.String fen) {
            return null;
        }
    }
}