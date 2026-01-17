package com.chesstrainer.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lcom/chesstrainer/data/ChessTrainerDatabase;", "Landroidx/room/RoomDatabase;", "()V", "gameDao", "Lcom/chesstrainer/data/GameDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.chesstrainer.data.GameEntity.class, com.chesstrainer.data.GameResultEntity.class, com.chesstrainer.data.PlayerRatingEntity.class}, version = 3, exportSchema = false)
public abstract class ChessTrainerDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.chesstrainer.data.ChessTrainerDatabase instance;
    @org.jetbrains.annotations.NotNull()
    public static final com.chesstrainer.data.ChessTrainerDatabase.Companion Companion = null;
    
    public ChessTrainerDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.chesstrainer.data.GameDao gameDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/chesstrainer/data/ChessTrainerDatabase$Companion;", "", "()V", "instance", "Lcom/chesstrainer/data/ChessTrainerDatabase;", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.data.ChessTrainerDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}