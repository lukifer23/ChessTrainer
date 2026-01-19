package com.chesstrainer.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/chesstrainer/ui/AnalysisStatus;", "", "()V", "Error", "Loading", "Ready", "Lcom/chesstrainer/ui/AnalysisStatus$Error;", "Lcom/chesstrainer/ui/AnalysisStatus$Loading;", "Lcom/chesstrainer/ui/AnalysisStatus$Ready;", "app_debug"})
abstract class AnalysisStatus {
    
    private AnalysisStatus() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/chesstrainer/ui/AnalysisStatus$Error;", "Lcom/chesstrainer/ui/AnalysisStatus;", "message", "", "retryable", "", "(Ljava/lang/String;Z)V", "getMessage", "()Ljava/lang/String;", "getRetryable", "()Z", "component1", "component2", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Error extends com.chesstrainer.ui.AnalysisStatus {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        private final boolean retryable = false;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.String message, boolean retryable) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        public final boolean getRetryable() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.ui.AnalysisStatus.Error copy(@org.jetbrains.annotations.NotNull()
        java.lang.String message, boolean retryable) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/chesstrainer/ui/AnalysisStatus$Loading;", "Lcom/chesstrainer/ui/AnalysisStatus;", "()V", "app_debug"})
    public static final class Loading extends com.chesstrainer.ui.AnalysisStatus {
        @org.jetbrains.annotations.NotNull()
        public static final com.chesstrainer.ui.AnalysisStatus.Loading INSTANCE = null;
        
        private Loading() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u000bH\u00c6\u0003JA\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006#"}, d2 = {"Lcom/chesstrainer/ui/AnalysisStatus$Ready;", "Lcom/chesstrainer/ui/AnalysisStatus;", "engineName", "", "engineType", "Lcom/chesstrainer/utils/EngineType;", "lines", "", "Lcom/chesstrainer/ui/AnalysisLine;", "fen", "gameStateInfo", "Lcom/chesstrainer/ui/GameStateInfo;", "(Ljava/lang/String;Lcom/chesstrainer/utils/EngineType;Ljava/util/List;Ljava/lang/String;Lcom/chesstrainer/ui/GameStateInfo;)V", "getEngineName", "()Ljava/lang/String;", "getEngineType", "()Lcom/chesstrainer/utils/EngineType;", "getFen", "getGameStateInfo", "()Lcom/chesstrainer/ui/GameStateInfo;", "getLines", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class Ready extends com.chesstrainer.ui.AnalysisStatus {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String engineName = null;
        @org.jetbrains.annotations.NotNull()
        private final com.chesstrainer.utils.EngineType engineType = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.chesstrainer.ui.AnalysisLine> lines = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String fen = null;
        @org.jetbrains.annotations.NotNull()
        private final com.chesstrainer.ui.GameStateInfo gameStateInfo = null;
        
        public Ready(@org.jetbrains.annotations.NotNull()
        java.lang.String engineName, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.utils.EngineType engineType, @org.jetbrains.annotations.NotNull()
        java.util.List<com.chesstrainer.ui.AnalysisLine> lines, @org.jetbrains.annotations.NotNull()
        java.lang.String fen, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.ui.GameStateInfo gameStateInfo) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getEngineName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.utils.EngineType getEngineType() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.chesstrainer.ui.AnalysisLine> getLines() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFen() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.ui.GameStateInfo getGameStateInfo() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.utils.EngineType component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.chesstrainer.ui.AnalysisLine> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.ui.GameStateInfo component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.ui.AnalysisStatus.Ready copy(@org.jetbrains.annotations.NotNull()
        java.lang.String engineName, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.utils.EngineType engineType, @org.jetbrains.annotations.NotNull()
        java.util.List<com.chesstrainer.ui.AnalysisLine> lines, @org.jetbrains.annotations.NotNull()
        java.lang.String fen, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.ui.GameStateInfo gameStateInfo) {
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
}