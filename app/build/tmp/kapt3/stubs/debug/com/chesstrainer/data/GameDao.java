package com.chesstrainer.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u0004\u0018\u00010\tH\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00040\u0003H\'J\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00040\u0003H\'J\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u0016\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0018\u00a8\u0006\u0019"}, d2 = {"Lcom/chesstrainer/data/GameDao;", "", "getGames", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/chesstrainer/data/GameEntity;", "getGamesList", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLatestRating", "Lcom/chesstrainer/data/PlayerRatingEntity;", "getRatings", "getResults", "Lcom/chesstrainer/data/GameResultEntity;", "getResultsList", "insertGame", "", "game", "(Lcom/chesstrainer/data/GameEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertGameResult", "", "result", "(Lcom/chesstrainer/data/GameResultEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertPlayerRating", "rating", "(Lcom/chesstrainer/data/PlayerRatingEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface GameDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertGame(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.data.GameEntity game, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertGameResult(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.data.GameResultEntity result, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPlayerRating(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.data.PlayerRatingEntity rating, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM games ORDER BY playedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.chesstrainer.data.GameEntity>> getGames();
    
    @androidx.room.Query(value = "SELECT * FROM game_results ORDER BY id DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.chesstrainer.data.GameResultEntity>> getResults();
    
    @androidx.room.Query(value = "SELECT * FROM games ORDER BY playedAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGamesList(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.chesstrainer.data.GameEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM game_results ORDER BY id DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getResultsList(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.chesstrainer.data.GameResultEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM player_ratings ORDER BY recordedAt ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.chesstrainer.data.PlayerRatingEntity>> getRatings();
    
    @androidx.room.Query(value = "SELECT * FROM player_ratings ORDER BY recordedAt DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLatestRating(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.chesstrainer.data.PlayerRatingEntity> $completion);
}