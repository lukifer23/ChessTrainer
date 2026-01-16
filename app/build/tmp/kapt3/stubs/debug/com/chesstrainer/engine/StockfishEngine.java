package com.chesstrainer.engine;

/**
 * Stockfish chess engine implementation using UCI protocol.
 * Provides Stockfish-specific configuration and search capabilities.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0002)*B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\r\u001a\u00020\u000eH\u0016J\u000e\u0010\u000f\u001a\u00020\u000eH\u0082@\u00a2\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\u000eH\u0082@\u00a2\u0006\u0002\u0010\u0010J.\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJ>\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u001c0\u00132\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u001e\u001a\u00020\u0018H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001f\u0010 J$\u0010!\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u000e0#H\u0016J\u0006\u0010%\u001a\u00020&J\u0006\u0010\'\u001a\u00020\nJ\b\u0010(\u001a\u00020\u000eH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006+"}, d2 = {"Lcom/chesstrainer/engine/StockfishEngine;", "Lcom/chesstrainer/engine/ChessEngine;", "context", "Landroid/content/Context;", "settings", "Lcom/chesstrainer/utils/Settings;", "(Landroid/content/Context;Lcom/chesstrainer/utils/Settings;)V", "engineManager", "Lcom/chesstrainer/engine/EngineManager;", "isInitialized", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "cleanup", "", "configureStockfish", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ensureInitialized", "evaluatePosition", "Lkotlin/Result;", "Lcom/chesstrainer/engine/StockfishEngine$PositionEvaluation;", "gameState", "Lcom/chesstrainer/chess/GameState;", "depth", "", "evaluatePosition-0E7RQCE", "(Lcom/chesstrainer/chess/GameState;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAnalysis", "", "Lcom/chesstrainer/engine/StockfishEngine$AnalysisResult;", "multiPV", "getAnalysis-BWLJW6A", "(Lcom/chesstrainer/chess/GameState;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBestMove", "callback", "Lkotlin/Function1;", "Lcom/chesstrainer/chess/Move;", "getEngineInfo", "", "isReady", "startNewGame", "AnalysisResult", "PositionEvaluation", "app_debug"})
public final class StockfishEngine implements com.chesstrainer.engine.ChessEngine {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.utils.Settings settings = null;
    @org.jetbrains.annotations.Nullable()
    private com.chesstrainer.engine.EngineManager engineManager;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    private boolean isInitialized = false;
    
    public StockfishEngine(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.utils.Settings settings) {
        super();
    }
    
    @java.lang.Override()
    public void getBestMove(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.GameState gameState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.chesstrainer.chess.Move, kotlin.Unit> callback) {
    }
    
    @java.lang.Override()
    public void startNewGame() {
    }
    
    @java.lang.Override()
    public void cleanup() {
    }
    
    /**
     * Ensure the engine is initialized and ready
     */
    private final java.lang.Object ensureInitialized(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Configure Stockfish-specific options
     */
    private final java.lang.Object configureStockfish(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get engine information
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEngineInfo() {
        return null;
    }
    
    /**
     * Check if engine is ready
     */
    public final boolean isReady() {
        return false;
    }
    
    /**
     * Analysis result for a position
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\nH\u00c6\u0003JM\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001J\t\u0010#\u001a\u00020$H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011\u00a8\u0006%"}, d2 = {"Lcom/chesstrainer/engine/StockfishEngine$AnalysisResult;", "", "depth", "", "score", "Lcom/chesstrainer/engine/UCIParser$Score;", "principalVariation", "", "Lcom/chesstrainer/chess/Move;", "time", "", "nodes", "nodesPerSecond", "(ILcom/chesstrainer/engine/UCIParser$Score;Ljava/util/List;JJJ)V", "getDepth", "()I", "getNodes", "()J", "getNodesPerSecond", "getPrincipalVariation", "()Ljava/util/List;", "getScore", "()Lcom/chesstrainer/engine/UCIParser$Score;", "getTime", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class AnalysisResult {
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
        public final com.chesstrainer.engine.StockfishEngine.AnalysisResult copy(int depth, @org.jetbrains.annotations.Nullable()
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
    
    /**
     * Position evaluation result
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\bH\u00c6\u0003J3\u0010\u0015\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\bH\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r\u00a8\u0006\u001c"}, d2 = {"Lcom/chesstrainer/engine/StockfishEngine$PositionEvaluation;", "", "score", "Lcom/chesstrainer/engine/UCIParser$Score;", "time", "", "nodes", "depth", "", "(Lcom/chesstrainer/engine/UCIParser$Score;JJI)V", "getDepth", "()I", "getNodes", "()J", "getScore", "()Lcom/chesstrainer/engine/UCIParser$Score;", "getTime", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
    public static final class PositionEvaluation {
        @org.jetbrains.annotations.Nullable()
        private final com.chesstrainer.engine.UCIParser.Score score = null;
        private final long time = 0L;
        private final long nodes = 0L;
        private final int depth = 0;
        
        public PositionEvaluation(@org.jetbrains.annotations.Nullable()
        com.chesstrainer.engine.UCIParser.Score score, long time, long nodes, int depth) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.chesstrainer.engine.UCIParser.Score getScore() {
            return null;
        }
        
        public final long getTime() {
            return 0L;
        }
        
        public final long getNodes() {
            return 0L;
        }
        
        public final int getDepth() {
            return 0;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.chesstrainer.engine.UCIParser.Score component1() {
            return null;
        }
        
        public final long component2() {
            return 0L;
        }
        
        public final long component3() {
            return 0L;
        }
        
        public final int component4() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.StockfishEngine.PositionEvaluation copy(@org.jetbrains.annotations.Nullable()
        com.chesstrainer.engine.UCIParser.Score score, long time, long nodes, int depth) {
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
}