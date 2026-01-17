package com.chesstrainer.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00004\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a@\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u0007\u001a*\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00032\u0018\u0010\u000e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0001H\u0002\u001a\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\u0016\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u0012\" \u0010\u0000\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\" \u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"leelaNodesRatingTable", "", "Lkotlin/Pair;", "", "stockfishDepthRatingTable", "GameScreen", "", "onNavigateToSettings", "Lkotlin/Function0;", "onNavigateToAnalysis", "onNavigateToLessons", "onNavigateToScorecard", "ratingFromTable", "setting", "table", "requiresEngine", "", "mode", "Lcom/chesstrainer/ui/GameMode;", "shouldEngineMove", "player", "Lcom/chesstrainer/chess/Color;", "app_debug"})
public final class GameScreenKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<java.lang.Integer, java.lang.Integer>> stockfishDepthRatingTable = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<java.lang.Integer, java.lang.Integer>> leelaNodesRatingTable = null;
    
    public static final boolean shouldEngineMove(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Color player, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.ui.GameMode mode) {
        return false;
    }
    
    private static final boolean requiresEngine(com.chesstrainer.ui.GameMode mode) {
        return false;
    }
    
    private static final int ratingFromTable(int setting, java.util.List<kotlin.Pair<java.lang.Integer, java.lang.Integer>> table) {
        return 0;
    }
    
    @androidx.compose.runtime.Composable()
    public static final void GameScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToSettings, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToAnalysis, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToLessons, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToScorecard) {
    }
}