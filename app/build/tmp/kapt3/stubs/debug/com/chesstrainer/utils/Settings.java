package com.chesstrainer.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR$\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R$\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u00128F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R$\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u00188F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR$\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u00188F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u001f\u0010\u001b\"\u0004\b \u0010\u001dR\u000e\u0010!\u001a\u00020\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u0010#\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u00188F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b$\u0010\u001b\"\u0004\b%\u0010\u001d\u00a8\u0006&"}, d2 = {"Lcom/chesstrainer/utils/Settings;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "value", "Lcom/chesstrainer/chess/Color;", "boardOrientation", "getBoardOrientation", "()Lcom/chesstrainer/chess/Color;", "setBoardOrientation", "(Lcom/chesstrainer/chess/Color;)V", "Lcom/chesstrainer/utils/EngineType;", "engineType", "getEngineType", "()Lcom/chesstrainer/utils/EngineType;", "setEngineType", "(Lcom/chesstrainer/utils/EngineType;)V", "", "lc0Backend", "getLc0Backend", "()Ljava/lang/String;", "setLc0Backend", "(Ljava/lang/String;)V", "", "lc0Threads", "getLc0Threads", "()I", "setLc0Threads", "(I)V", "leelaNodes", "getLeelaNodes", "setLeelaNodes", "preferences", "Landroid/content/SharedPreferences;", "stockfishDepth", "getStockfishDepth", "setStockfishDepth", "app_debug"})
public final class Settings {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences preferences = null;
    
    public Settings(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.utils.EngineType getEngineType() {
        return null;
    }
    
    public final void setEngineType(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.utils.EngineType value) {
    }
    
    public final int getLeelaNodes() {
        return 0;
    }
    
    public final void setLeelaNodes(int value) {
    }
    
    public final int getStockfishDepth() {
        return 0;
    }
    
    public final void setStockfishDepth(int value) {
    }
    
    public final int getLc0Threads() {
        return 0;
    }
    
    public final void setLc0Threads(int value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLc0Backend() {
        return null;
    }
    
    public final void setLc0Backend(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.chesstrainer.chess.Color getBoardOrientation() {
        return null;
    }
    
    public final void setBoardOrientation(@org.jetbrains.annotations.NotNull()
    com.chesstrainer.chess.Color value) {
    }
}