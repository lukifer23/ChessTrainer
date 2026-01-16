package com.chesstrainer.engine;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J$\u0010\n\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00060\fH\u0016J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J(\u0010\u0013\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004H\u0002J\b\u0010\u0017\u001a\u00020\u0006H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/chesstrainer/engine/SimpleChessEngine;", "Lcom/chesstrainer/engine/ChessEngine;", "()V", "searchDepth", "", "cleanup", "", "evaluatePosition", "gameState", "Lcom/chesstrainer/chess/GameState;", "getBestMove", "callback", "Lkotlin/Function1;", "Lcom/chesstrainer/chess/Move;", "getPositionalBonus", "piece", "Lcom/chesstrainer/chess/Piece;", "square", "Lcom/chesstrainer/chess/Square;", "minimax", "depth", "alpha", "beta", "startNewGame", "app_debug"})
public final class SimpleChessEngine implements com.chesstrainer.engine.ChessEngine {
    private int searchDepth = 3;
    
    public SimpleChessEngine() {
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
    
    private final int minimax(com.chesstrainer.chess.GameState gameState, int depth, int alpha, int beta) {
        return 0;
    }
    
    private final int evaluatePosition(com.chesstrainer.chess.GameState gameState) {
        return 0;
    }
    
    private final int getPositionalBonus(com.chesstrainer.chess.Piece piece, com.chesstrainer.chess.Square square) {
        return 0;
    }
}