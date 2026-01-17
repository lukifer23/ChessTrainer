package com.chesstrainer.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J(\u0010\b\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00042\b\b\u0002\u0010\n\u001a\u00020\u0006\u00a8\u0006\u000b"}, d2 = {"Lcom/chesstrainer/utils/EloCalculator;", "", "()V", "expectedScore", "", "playerRating", "", "opponentRating", "updateRating", "score", "kFactor", "app_debug"})
public final class EloCalculator {
    @org.jetbrains.annotations.NotNull()
    public static final com.chesstrainer.utils.EloCalculator INSTANCE = null;
    
    private EloCalculator() {
        super();
    }
    
    public final double expectedScore(int playerRating, int opponentRating) {
        return 0.0;
    }
    
    public final int updateRating(int playerRating, int opponentRating, double score, int kFactor) {
        return 0;
    }
}