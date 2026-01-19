package com.chesstrainer.engine;

/**
 * Stockfish chess engine implementation using UCI protocol.
 * Provides Stockfish-specific configuration and search capabilities.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\bH\u0094@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\bH\u0082@\u00a2\u0006\u0002\u0010\tJ\b\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\u000eH\u0014\u00a8\u0006\u000f"}, d2 = {"Lcom/chesstrainer/engine/StockfishEngine;", "Lcom/chesstrainer/engine/BaseEngine;", "context", "Landroid/content/Context;", "settings", "Lcom/chesstrainer/utils/Settings;", "(Landroid/content/Context;Lcom/chesstrainer/utils/Settings;)V", "configureEngine", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "configureStockfish", "createSearchParams", "Lcom/chesstrainer/engine/EngineManager$SearchParams;", "getStartErrorMessage", "", "app_debug"})
public final class StockfishEngine extends com.chesstrainer.engine.BaseEngine {
    
    public StockfishEngine(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.utils.Settings settings) {
        super(null, null, null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    protected java.lang.Object configureEngine(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected com.chesstrainer.engine.EngineManager.SearchParams createSearchParams() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected java.lang.String getStartErrorMessage() {
        return null;
    }
    
    private final java.lang.Object configureStockfish(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}