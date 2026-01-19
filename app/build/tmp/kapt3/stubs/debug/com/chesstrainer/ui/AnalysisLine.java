package com.chesstrainer.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\bH\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003JM\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001J\t\u0010#\u001a\u00020$H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014\u00a8\u0006%"}, d2 = {"Lcom/chesstrainer/ui/AnalysisLine;", "", "index", "", "score", "Lcom/chesstrainer/engine/UCIParser$Score;", "depth", "time", "", "nodes", "moves", "", "Lcom/chesstrainer/chess/Move;", "(ILcom/chesstrainer/engine/UCIParser$Score;IJJLjava/util/List;)V", "getDepth", "()I", "getIndex", "getMoves", "()Ljava/util/List;", "getNodes", "()J", "getScore", "()Lcom/chesstrainer/engine/UCIParser$Score;", "getTime", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
final class AnalysisLine {
    private final int index = 0;
    @org.jetbrains.annotations.Nullable()
    private final com.chesstrainer.engine.UCIParser.Score score = null;
    private final int depth = 0;
    private final long time = 0L;
    private final long nodes = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.chesstrainer.chess.Move> moves = null;
    
    public AnalysisLine(int index, @org.jetbrains.annotations.Nullable()
    com.chesstrainer.engine.UCIParser.Score score, int depth, long time, long nodes, @org.jetbrains.annotations.NotNull()
    java.util.List<com.chesstrainer.chess.Move> moves) {
        super();
    }
    
    public final int getIndex() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.chesstrainer.engine.UCIParser.Score getScore() {
        return null;
    }
    
    public final int getDepth() {
        return 0;
    }
    
    public final long getTime() {
        return 0L;
    }
    
    public final long getNodes() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.chesstrainer.chess.Move> getMoves() {
        return null;
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.chesstrainer.engine.UCIParser.Score component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final long component5() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.chesstrainer.chess.Move> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.ui.AnalysisLine copy(int index, @org.jetbrains.annotations.Nullable()
    com.chesstrainer.engine.UCIParser.Score score, int depth, long time, long nodes, @org.jetbrains.annotations.NotNull()
    java.util.List<com.chesstrainer.chess.Move> moves) {
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
}