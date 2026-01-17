package com.chesstrainer.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0016J&\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001cR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000e\u00a8\u0006\u001d"}, d2 = {"Lcom/chesstrainer/data/GameRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "dao", "Lcom/chesstrainer/data/GameDao;", "database", "Lcom/chesstrainer/data/ChessTrainerDatabase;", "games", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/chesstrainer/data/GameEntity;", "getGames", "()Lkotlinx/coroutines/flow/Flow;", "ratings", "Lcom/chesstrainer/data/PlayerRatingEntity;", "getRatings", "results", "Lcom/chesstrainer/data/GameResultEntity;", "getResults", "getLatestRating", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recordCompletedGame", "", "game", "result", "rating", "(Lcom/chesstrainer/data/GameEntity;Lcom/chesstrainer/data/GameResultEntity;Lcom/chesstrainer/data/PlayerRatingEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class GameRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.data.ChessTrainerDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.chesstrainer.data.GameDao dao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.chesstrainer.data.GameEntity>> games = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.chesstrainer.data.GameResultEntity>> results = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.chesstrainer.data.PlayerRatingEntity>> ratings = null;
    
    public GameRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.chesstrainer.data.GameEntity>> getGames() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.chesstrainer.data.GameResultEntity>> getResults() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.chesstrainer.data.PlayerRatingEntity>> getRatings() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getLatestRating(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.chesstrainer.data.PlayerRatingEntity> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recordCompletedGame(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.data.GameEntity game, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.data.GameResultEntity result, @org.jetbrains.annotations.NotNull()
    com.chesstrainer.data.PlayerRatingEntity rating, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}