package com.chesstrainer.engine;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J:\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00030\b2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00030\bH&J\b\u0010\f\u001a\u00020\u0003H&\u00a8\u0006\r"}, d2 = {"Lcom/chesstrainer/engine/ChessEngine;", "", "cleanup", "", "getBestMove", "gameState", "Lcom/chesstrainer/chess/GameState;", "callback", "Lkotlin/Function1;", "Lcom/chesstrainer/chess/Move;", "onError", "", "startNewGame", "app_debug"})
public abstract interface ChessEngine {
    
    public abstract void getBestMove(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.GameState gameState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.chesstrainer.chess.Move, kotlin.Unit> callback, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onError);
    
    public abstract void startNewGame();
    
    public abstract void cleanup();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}