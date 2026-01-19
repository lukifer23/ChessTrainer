package com.chesstrainer.engine;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\nH\u00c6\u0003JM\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001J\t\u0010#\u001a\u00020$H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011\u00a8\u0006%"}, d2 = {"Lcom/chesstrainer/engine/AnalysisResult;", "", "depth", "", "score", "Lcom/chesstrainer/engine/UCIParser$Score;", "principalVariation", "", "Lcom/chesstrainer/chess/Move;", "time", "", "nodes", "nodesPerSecond", "(ILcom/chesstrainer/engine/UCIParser$Score;Ljava/util/List;JJJ)V", "getDepth", "()I", "getNodes", "()J", "getNodesPerSecond", "getPrincipalVariation", "()Ljava/util/List;", "getScore", "()Lcom/chesstrainer/engine/UCIParser$Score;", "getTime", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class AnalysisResult {
    private final int depth = 0;
    @org.jetbrains.annotations.Nullable()
    private final com.chesstrainer.engine.UCIParser.Score score = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.chesstrainer.chess.Move> principalVariation = null;
    private final long time = 0L;
    private final long nodes = 0L;
    private final long nodesPerSecond = 0L;
    
    public AnalysisResult(int depth, @org.jetbrains.annotations.Nullable()
    com.chesstrainer.engine.UCIParser.Score score, @org.jetbrains.annotations.NotNull()
    java.util.List<com.chesstrainer.chess.Move> principalVariation, long time, long nodes, long nodesPerSecond) {
        super();
    }
    
    public final int getDepth() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.chesstrainer.engine.UCIParser.Score getScore() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.chesstrainer.chess.Move> getPrincipalVariation() {
        return null;
    }
    
    public final long getTime() {
        return 0L;
    }
    
    public final long getNodes() {
        return 0L;
    }
    
    public final long getNodesPerSecond() {
        return 0L;
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.chesstrainer.engine.UCIParser.Score component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.chesstrainer.chess.Move> component3() {
        return null;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final long component5() {
        return 0L;
    }
    
    public final long component6() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.engine.AnalysisResult copy(int depth, @org.jetbrains.annotations.Nullable()
    com.chesstrainer.engine.UCIParser.Score score, @org.jetbrains.annotations.NotNull()
    java.util.List<com.chesstrainer.chess.Move> principalVariation, long time, long nodes, long nodesPerSecond) {
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