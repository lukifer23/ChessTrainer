package com.chesstrainer.chess;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b,\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\b\u0018\u0000 F2\u00020\u0001:\u0001FB\u008b\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\u0002\u0010\u0017J\b\u0010+\u001a\u00020\u0016H\u0002J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u00c6\u0003J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011H\u00c6\u0003J\t\u0010/\u001a\u00020\u0016H\u00c6\u0003J\t\u00100\u001a\u00020\u0005H\u00c6\u0003J\t\u00101\u001a\u00020\u0007H\u00c6\u0003J\t\u00102\u001a\u00020\u0007H\u00c6\u0003J\t\u00103\u001a\u00020\u0007H\u00c6\u0003J\t\u00104\u001a\u00020\u0007H\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\t\u00106\u001a\u00020\u000eH\u00c6\u0003J\t\u00107\u001a\u00020\u000eH\u00c6\u0003J\u008f\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00112\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u00c6\u0001J\u0013\u00109\u001a\u00020\u00072\b\u0010:\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\b\u0010;\u001a\u0004\u0018\u00010\u0005J\t\u0010<\u001a\u00020\u000eH\u00d6\u0001J\u0006\u0010=\u001a\u00020\u0007J\u0010\u0010>\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0016\u0010?\u001a\u00020\u00072\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011H\u0002J\u000e\u0010@\u001a\u00020\u00002\u0006\u0010A\u001a\u00020\u0012J\u0006\u0010B\u001a\u00020CJ\t\u0010D\u001a\u00020CH\u00d6\u0001J\f\u0010E\u001a\u00020\u0005*\u00020\u0005H\u0002R\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0015\u001a\u00020\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\"R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\'R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0019R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0019\u00a8\u0006G"}, d2 = {"Lcom/chesstrainer/chess/GameState;", "", "board", "Lcom/chesstrainer/chess/Board;", "currentPlayer", "Lcom/chesstrainer/chess/Color;", "whiteCanCastleKingside", "", "whiteCanCastleQueenside", "blackCanCastleKingside", "blackCanCastleQueenside", "enPassantTarget", "Lcom/chesstrainer/chess/Square;", "halfMoveClock", "", "fullMoveNumber", "moveHistory", "", "Lcom/chesstrainer/chess/Move;", "positionHashes", "", "gameResult", "Lcom/chesstrainer/chess/GameResult;", "(Lcom/chesstrainer/chess/Board;Lcom/chesstrainer/chess/Color;ZZZZLcom/chesstrainer/chess/Square;IILjava/util/List;Ljava/util/List;Lcom/chesstrainer/chess/GameResult;)V", "getBlackCanCastleKingside", "()Z", "getBlackCanCastleQueenside", "getBoard", "()Lcom/chesstrainer/chess/Board;", "getCurrentPlayer", "()Lcom/chesstrainer/chess/Color;", "getEnPassantTarget", "()Lcom/chesstrainer/chess/Square;", "getFullMoveNumber", "()I", "getGameResult", "()Lcom/chesstrainer/chess/GameResult;", "getHalfMoveClock", "getMoveHistory", "()Ljava/util/List;", "getPositionHashes", "getWhiteCanCastleKingside", "getWhiteCanCastleQueenside", "checkGameResult", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "getWinner", "hashCode", "isGameOver", "isInsufficientMaterial", "isThreefoldRepetition", "makeMove", "move", "toFen", "", "toString", "opposite", "Companion", "app_debug"})
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
    private final java.util.List<java.lang.Long> positionHashes = null;
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.chess.GameResult gameResult = null;
    private static final long ZOBRIST_SEED = 0L;
    private static final int PIECE_TYPE_COUNT = 6;
    private static final int COLOR_COUNT = 2;
    @org.jetbrains.annotations.NotNull()
    private static final long[][] pieceSquareKeys = null;
    private static final long sideToMoveKey = 0L;
    @org.jetbrains.annotations.NotNull()
    private static final long[] castlingKeys = null;
    @org.jetbrains.annotations.NotNull()
    private static final long[] enPassantFileKeys = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.chesstrainer.chess.GameState.Companion Companion = null;
    
    public GameState(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Board board, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Color currentPlayer, boolean whiteCanCastleKingside, boolean whiteCanCastleQueenside, boolean blackCanCastleKingside, boolean blackCanCastleQueenside, @org.jetbrains.annotations.Nullable()
    com.chesstrainer.chess.Square enPassantTarget, int halfMoveClock, int fullMoveNumber, @org.jetbrains.annotations.NotNull()
    java.util.List<com.chesstrainer.chess.Move> moveHistory, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Long> positionHashes, @org.jetbrains.annotations.NotNull()
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
    public final java.util.List<java.lang.Long> getPositionHashes() {
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
    
    private final com.chesstrainer.chess.GameResult checkGameResult() {
        return null;
    }
    
    private final boolean isThreefoldRepetition(java.util.List<java.lang.Long> positionHashes) {
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
    public final java.util.List<java.lang.Long> component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.GameResult component12() {
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
    java.util.List<java.lang.Long> positionHashes, @org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0016\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JB\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0010\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020!H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\fX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/chesstrainer/chess/GameState$Companion;", "", "()V", "COLOR_COUNT", "", "PIECE_TYPE_COUNT", "ZOBRIST_SEED", "", "castlingKeys", "", "enPassantFileKeys", "pieceSquareKeys", "", "[[J", "sideToMoveKey", "computePositionHash", "board", "Lcom/chesstrainer/chess/Board;", "currentPlayer", "Lcom/chesstrainer/chess/Color;", "whiteCanCastleKingside", "", "whiteCanCastleQueenside", "blackCanCastleKingside", "blackCanCastleQueenside", "enPassantTarget", "Lcom/chesstrainer/chess/Square;", "fromFen", "Lcom/chesstrainer/chess/GameState;", "fen", "", "pieceIndex", "piece", "Lcom/chesstrainer/chess/Piece;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.chess.GameState fromFen(@org.jetbrains.annotations.NotNull()
        java.lang.String fen) {
            return null;
        }
        
        private final long computePositionHash(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Color currentPlayer, boolean whiteCanCastleKingside, boolean whiteCanCastleQueenside, boolean blackCanCastleKingside, boolean blackCanCastleQueenside, com.chesstrainer.chess.Square enPassantTarget) {
            return 0L;
        }
        
        private final int pieceIndex(com.chesstrainer.chess.Piece piece) {
            return 0;
        }
    }
}