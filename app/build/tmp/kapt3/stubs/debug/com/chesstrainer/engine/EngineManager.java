package com.chesstrainer.engine;

/**
 * Manages chess engine processes and UCI communication.
 * Handles engine lifecycle, binary extraction, and thread-safe communication.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0003NOPB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\"\u001a\u00020#J\u001c\u0010$\u001a\b\u0012\u0004\u0012\u00020#0%H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b&\u0010\'J\u0006\u0010(\u001a\u00020\u000bJ\b\u0010)\u001a\u0004\u0018\u00010\u0011J\u000e\u0010*\u001a\u00020#H\u0082@\u00a2\u0006\u0002\u0010\'J\u0010\u0010+\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0006\u0010,\u001a\u00020\u000fJ\u001c\u0010-\u001a\b\u0012\u0004\u0012\u00020#0%H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b.\u0010\'J\u0016\u0010/\u001a\u00020#2\u0006\u00100\u001a\u00020\u000bH\u0082@\u00a2\u0006\u0002\u00101J$\u00102\u001a\b\u0012\u0004\u0012\u00020#0%2\u0006\u00103\u001a\u00020\u000bH\u0082@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b4\u00101J,\u00105\u001a\b\u0012\u0004\u0012\u00020#0%2\u0006\u00106\u001a\u00020\u000b2\u0006\u00107\u001a\u00020\u0001H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b8\u00109J$\u0010:\u001a\b\u0012\u0004\u0012\u00020#0%2\u0006\u0010;\u001a\u00020<H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b=\u0010>J\u001c\u0010?\u001a\b\u0012\u0004\u0012\u00020#0%H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b@\u0010\'J\b\u0010A\u001a\u00020#H\u0002JX\u0010B\u001a\b\u0012\u0004\u0012\u00020#0%2\u0006\u0010;\u001a\u00020<2\u0012\u0010C\u001a\u000e\u0012\u0004\u0012\u00020E\u0012\u0004\u0012\u00020#0D2\u0014\b\u0002\u0010F\u001a\u000e\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020#0D2\b\b\u0002\u0010H\u001a\u00020IH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bJ\u0010KJ\u001c\u0010L\u001a\b\u0012\u0004\u0012\u00020#0%H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bM\u0010\'R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006Q"}, d2 = {"Lcom/chesstrainer/engine/EngineManager;", "", "context", "Landroid/content/Context;", "settings", "Lcom/chesstrainer/utils/Settings;", "(Landroid/content/Context;Lcom/chesstrainer/utils/Settings;)V", "_responses", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse;", "engineName", "", "installer", "Lcom/chesstrainer/engine/EngineInstaller;", "isEngineReady", "", "lc0WeightsFile", "Ljava/io/File;", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "outputJob", "Lkotlinx/coroutines/Job;", "process", "Ljava/lang/Process;", "reader", "Ljava/io/BufferedReader;", "responses", "Lkotlinx/coroutines/flow/Flow;", "getResponses", "()Lkotlinx/coroutines/flow/Flow;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "writer", "Ljava/io/BufferedWriter;", "cleanup", "", "configureEngine", "Lkotlin/Result;", "configureEngine-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEngineName", "getLc0WeightsFile", "initializeEngine", "isProcessTerminated", "isReady", "newGame", "newGame-IoAF18A", "processLine", "line", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendCommand", "command", "sendCommand-gIAlu-s", "setOption", "name", "value", "setOption-0E7RQCE", "(Ljava/lang/String;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setPosition", "gameState", "Lcom/chesstrainer/chess/GameState;", "setPosition-gIAlu-s", "(Lcom/chesstrainer/chess/GameState;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startEngine", "startEngine-IoAF18A", "startOutputMonitoring", "startSearch", "onBestMove", "Lkotlin/Function1;", "Lcom/chesstrainer/chess/Move;", "onInfo", "Lcom/chesstrainer/engine/UCIParser$EngineInfo;", "searchParams", "Lcom/chesstrainer/engine/EngineManager$SearchParams;", "startSearch-yxL6bBk", "(Lcom/chesstrainer/chess/GameState;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lcom/chesstrainer/engine/EngineManager$SearchParams;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stopSearch", "stopSearch-IoAF18A", "InitializationCompleteException", "SearchCompleteException", "SearchParams", "app_debug"})
public final class EngineManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.utils.Settings settings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.sync.Mutex mutex = null;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Process process;
    @org.jetbrains.annotations.Nullable()
    private java.io.BufferedWriter writer;
    @org.jetbrains.annotations.Nullable()
    private java.io.BufferedReader reader;
    private boolean isEngineReady = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String engineName = "Unknown Engine";
    @org.jetbrains.annotations.Nullable()
    private java.io.File lc0WeightsFile;
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.engine.EngineInstaller installer = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.chesstrainer.engine.UCIParser.UCIResponse> _responses = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.chesstrainer.engine.UCIParser.UCIResponse> responses = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job outputJob;
    
    public EngineManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.utils.Settings settings) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.chesstrainer.engine.UCIParser.UCIResponse> getResponses() {
        return null;
    }
    
    /**
     * Check if a process has terminated (compatible with API 24+)
     */
    private final boolean isProcessTerminated(java.lang.Process process) {
        return false;
    }
    
    /**
     * Start monitoring engine output in background
     */
    private final void startOutputMonitoring() {
    }
    
    /**
     * Process a line of engine output
     */
    private final java.lang.Object processLine(java.lang.String line, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Initialize UCI protocol with the engine
     */
    private final java.lang.Object initializeEngine(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Check if engine is ready for commands
     */
    public final boolean isReady() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File getLc0WeightsFile() {
        return null;
    }
    
    /**
     * Get engine information
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEngineName() {
        return null;
    }
    
    /**
     * Clean up resources
     */
    public final void cleanup() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2 = {"Lcom/chesstrainer/engine/EngineManager$InitializationCompleteException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "()V", "app_debug"})
    static final class InitializationCompleteException extends java.lang.Exception {
        
        public InitializationCompleteException() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2 = {"Lcom/chesstrainer/engine/EngineManager$SearchCompleteException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "()V", "app_debug"})
    static final class SearchCompleteException extends java.lang.Exception {
        
        public SearchCompleteException() {
            super();
        }
    }
    
    /**
     * Search parameters for engine analysis
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0018\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\t\u0010\u0019\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0005H\u00c6\u0003JF\u0010\u001b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00d6\u0001J\t\u0010 \u001a\u00020!H\u00d6\u0001R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\""}, d2 = {"Lcom/chesstrainer/engine/EngineManager$SearchParams;", "", "depth", "", "moveTime", "", "nodes", "infinite", "", "timeoutMs", "(Ljava/lang/Integer;Ljava/lang/Long;Ljava/lang/Long;ZJ)V", "getDepth", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getInfinite", "()Z", "getMoveTime", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getNodes", "getTimeoutMs", "()J", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/Integer;Ljava/lang/Long;Ljava/lang/Long;ZJ)Lcom/chesstrainer/engine/EngineManager$SearchParams;", "equals", "other", "hashCode", "toString", "", "app_debug"})
    public static final class SearchParams {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer depth = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long moveTime = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long nodes = null;
        private final boolean infinite = false;
        private final long timeoutMs = 0L;
        
        public SearchParams(@org.jetbrains.annotations.Nullable()
        java.lang.Integer depth, @org.jetbrains.annotations.Nullable()
        java.lang.Long moveTime, @org.jetbrains.annotations.Nullable()
        java.lang.Long nodes, boolean infinite, long timeoutMs) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getDepth() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getMoveTime() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getNodes() {
            return null;
        }
        
        public final boolean getInfinite() {
            return false;
        }
        
        public final long getTimeoutMs() {
            return 0L;
        }
        
        public SearchParams() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component3() {
            return null;
        }
        
        public final boolean component4() {
            return false;
        }
        
        public final long component5() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.EngineManager.SearchParams copy(@org.jetbrains.annotations.Nullable()
        java.lang.Integer depth, @org.jetbrains.annotations.Nullable()
        java.lang.Long moveTime, @org.jetbrains.annotations.Nullable()
        java.lang.Long nodes, boolean infinite, long timeoutMs) {
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