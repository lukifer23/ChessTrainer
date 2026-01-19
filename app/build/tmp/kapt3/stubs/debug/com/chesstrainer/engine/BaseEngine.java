package com.chesstrainer.engine;

/**
 * Abstract base class for chess engines.
 * Handles lifecycle, process management, and common UCI operations.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u000e\u0010\u001e\u001a\u00020\u001dH\u00a4@\u00a2\u0006\u0002\u0010\u001fJ\b\u0010 \u001a\u00020!H$J$\u0010\"\u001a\u00020\u001d2\u0014\b\u0002\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u001d0$H\u0084@\u00a2\u0006\u0002\u0010%J.\u0010&\u001a\b\u0012\u0004\u0012\u00020(0\'2\u0006\u0010)\u001a\u00020*2\b\b\u0002\u0010+\u001a\u00020,H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b-\u0010.JL\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u000201000\'2\u0006\u0010)\u001a\u00020*2\n\b\u0002\u0010+\u001a\u0004\u0018\u00010,2\n\b\u0002\u00102\u001a\u0004\u0018\u0001032\b\b\u0002\u00104\u001a\u00020,H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b5\u00106J8\u00107\u001a\u00020\u001d2\u0006\u0010)\u001a\u00020*2\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020\u001d0$2\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020;\u0012\u0004\u0012\u00020\u001d0$H\u0016J\u0006\u0010<\u001a\u00020\u0007J\b\u0010=\u001a\u0004\u0018\u00010\u0007J\b\u0010>\u001a\u00020\u0007H$J2\u0010?\u001a\b\u0012\u0004\u0012\u00020\u001d0\'2\u0014\b\u0002\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u001d0$H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b@\u0010%J\b\u0010A\u001a\u00020\u001dH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u00020\u0012X\u0084\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u0017X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006B"}, d2 = {"Lcom/chesstrainer/engine/BaseEngine;", "Lcom/chesstrainer/engine/ChessEngine;", "context", "Landroid/content/Context;", "settings", "Lcom/chesstrainer/utils/Settings;", "engineTag", "", "(Landroid/content/Context;Lcom/chesstrainer/utils/Settings;Ljava/lang/String;)V", "getContext", "()Landroid/content/Context;", "engineManager", "Lcom/chesstrainer/engine/EngineManager;", "getEngineManager", "()Lcom/chesstrainer/engine/EngineManager;", "setEngineManager", "(Lcom/chesstrainer/engine/EngineManager;)V", "isInitialized", "", "()Z", "setInitialized", "(Z)V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "getScope", "()Lkotlinx/coroutines/CoroutineScope;", "getSettings", "()Lcom/chesstrainer/utils/Settings;", "cleanup", "", "configureEngine", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createSearchParams", "Lcom/chesstrainer/engine/EngineManager$SearchParams;", "ensureInitialized", "onStatusUpdate", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "evaluatePosition", "Lkotlin/Result;", "Lcom/chesstrainer/engine/PositionEvaluation;", "gameState", "Lcom/chesstrainer/chess/GameState;", "depth", "", "evaluatePosition-0E7RQCE", "(Lcom/chesstrainer/chess/GameState;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAnalysis", "", "Lcom/chesstrainer/engine/AnalysisResult;", "nodes", "", "multiPV", "getAnalysis-yxL6bBk", "(Lcom/chesstrainer/chess/GameState;Ljava/lang/Integer;Ljava/lang/Long;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBestMove", "callback", "Lcom/chesstrainer/chess/Move;", "onError", "", "getEngineInfo", "getEngineName", "getStartErrorMessage", "initialize", "initialize-gIAlu-s", "startNewGame", "app_debug"})
public abstract class BaseEngine implements com.chesstrainer.engine.ChessEngine {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.utils.Settings settings = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String engineTag = null;
    @org.jetbrains.annotations.Nullable()
    private com.chesstrainer.engine.EngineManager engineManager;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    private boolean isInitialized = false;
    
    public BaseEngine(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.utils.Settings settings, @org.jetbrains.annotations.NotNull()
    java.lang.String engineTag) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final com.chesstrainer.utils.Settings getSettings() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    protected final com.chesstrainer.engine.EngineManager getEngineManager() {
        return null;
    }
    
    protected final void setEngineManager(@org.jetbrains.annotations.Nullable()
    com.chesstrainer.engine.EngineManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final kotlinx.coroutines.CoroutineScope getScope() {
        return null;
    }
    
    protected final boolean isInitialized() {
        return false;
    }
    
    protected final void setInitialized(boolean p0) {
    }
    
    @java.lang.Override()
    public void startNewGame() {
    }
    
    @java.lang.Override()
    public void cleanup() {
    }
    
    @org.jetbrains.annotations.Nullable()
    protected final java.lang.Object ensureInitialized(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onStatusUpdate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    public void getBestMove(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.GameState gameState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.chesstrainer.chess.Move, kotlin.Unit> callback, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onError) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEngineName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    protected abstract java.lang.Object configureEngine(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.NotNull()
    protected abstract com.chesstrainer.engine.EngineManager.SearchParams createSearchParams();
    
    @org.jetbrains.annotations.NotNull()
    protected abstract java.lang.String getStartErrorMessage();
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEngineInfo() {
        return null;
    }
}