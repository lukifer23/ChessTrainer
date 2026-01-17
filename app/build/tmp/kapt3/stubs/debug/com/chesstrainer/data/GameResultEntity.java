package com.chesstrainer.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001BU\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0006\u0010\n\u001a\u00020\u0006\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\u000e\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0006H\u00c6\u0003J\t\u0010!\u001a\u00020\bH\u00c6\u0003J\t\u0010\"\u001a\u00020\u0006H\u00c6\u0003J\t\u0010#\u001a\u00020\u0006H\u00c6\u0003J\u0010\u0010$\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010%\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\t\u0010&\u001a\u00020\u0006H\u00c6\u0003Jl\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\u000e\u001a\u00020\u0006H\u00c6\u0001\u00a2\u0006\u0002\u0010(J\u0013\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010,\u001a\u00020\fH\u00d6\u0001J\t\u0010-\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0015\u0010\r\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000e\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\n\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u001d\u0010\u0013\u00a8\u0006."}, d2 = {"Lcom/chesstrainer/data/GameResultEntity;", "", "id", "", "gameId", "outcome", "", "score", "", "analysisDepth", "timeControl", "whiteElo", "", "blackElo", "engineVersion", "(JJLjava/lang/String;DLjava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;)V", "getAnalysisDepth", "()Ljava/lang/String;", "getBlackElo", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getEngineVersion", "getGameId", "()J", "getId", "getOutcome", "getScore", "()D", "getTimeControl", "getWhiteElo", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JJLjava/lang/String;DLjava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;)Lcom/chesstrainer/data/GameResultEntity;", "equals", "", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "game_results", foreignKeys = {@androidx.room.ForeignKey(entity = com.chesstrainer.data.GameEntity.class, parentColumns = {"id"}, childColumns = {"gameId"}, onDelete = 5)}, indices = {@androidx.room.Index(value = {"gameId"})})
public final class GameResultEntity {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long gameId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String outcome = null;
    private final double score = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String analysisDepth = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String timeControl = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer whiteElo = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer blackElo = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String engineVersion = null;
    
    public GameResultEntity(long id, long gameId, @org.jetbrains.annotations.NotNull()
    java.lang.String outcome, double score, @org.jetbrains.annotations.NotNull()
    java.lang.String analysisDepth, @org.jetbrains.annotations.NotNull()
    java.lang.String timeControl, @org.jetbrains.annotations.Nullable()
    java.lang.Integer whiteElo, @org.jetbrains.annotations.Nullable()
    java.lang.Integer blackElo, @org.jetbrains.annotations.NotNull()
    java.lang.String engineVersion) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getGameId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOutcome() {
        return null;
    }
    
    public final double getScore() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAnalysisDepth() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimeControl() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getWhiteElo() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getBlackElo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEngineVersion() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.data.GameResultEntity copy(long id, long gameId, @org.jetbrains.annotations.NotNull()
    java.lang.String outcome, double score, @org.jetbrains.annotations.NotNull()
    java.lang.String analysisDepth, @org.jetbrains.annotations.NotNull()
    java.lang.String timeControl, @org.jetbrains.annotations.Nullable()
    java.lang.Integer whiteElo, @org.jetbrains.annotations.Nullable()
    java.lang.Integer blackElo, @org.jetbrains.annotations.NotNull()
    java.lang.String engineVersion) {
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