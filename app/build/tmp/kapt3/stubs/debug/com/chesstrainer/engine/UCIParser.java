package com.chesstrainer.engine;

/**
 * UCI (Universal Chess Interface) protocol parser and command builder.
 * Handles all communication between the chess engine and the application.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\t\u0018\u0000 \u00032\u00020\u0001:\u0007\u0003\u0004\u0005\u0006\u0007\b\tB\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\n"}, d2 = {"Lcom/chesstrainer/engine/UCIParser;", "", "()V", "Companion", "EngineInfo", "OptionType", "ResponseType", "Score", "UCIOption", "UCIResponse", "app_debug"})
public final class UCIParser {
    @org.jetbrains.annotations.NotNull()
    public static final com.chesstrainer.engine.UCIParser.Companion Companion = null;
    
    public UCIParser() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u009d\u0001\u0010\b\u001a\u00020\u00042\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u00062\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u0018\u001a\u00020\f\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u0004J\u0006\u0010\u001c\u001a\u00020\u0004J\u0006\u0010\u001d\u001a\u00020\u0004J\u0016\u0010\u001e\u001a\u00020\u00072\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0002J\u0016\u0010 \u001a\u00020\u00072\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0002J\u0016\u0010!\u001a\u00020\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0002J\u0016\u0010$\u001a\u00020\u00072\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0002J\u000e\u0010%\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0004J\u0016\u0010&\u001a\u00020\u00072\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0002J \u0010\'\u001a\u0004\u0018\u00010(2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010)\u001a\u00020\u0013H\u0002J\u0006\u0010*\u001a\u00020\u0004J\"\u0010+\u001a\u00020\u00042\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\u00042\u000e\b\u0002\u0010-\u001a\b\u0012\u0004\u0012\u00020\n0\u0006J\u0006\u0010.\u001a\u00020\u0004J\u0016\u0010/\u001a\u00020\u00042\u0006\u00100\u001a\u00020\u00042\u0006\u00101\u001a\u00020\u0001J\u0006\u00102\u001a\u00020\u0004J\u0006\u00103\u001a\u00020\u0004\u00a8\u00064"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$Companion;", "", "()V", "extractEngineName", "", "responses", "", "Lcom/chesstrainer/engine/UCIParser$UCIResponse;", "goCommand", "searchMoves", "Lcom/chesstrainer/chess/Move;", "ponder", "", "whiteTime", "", "blackTime", "whiteIncrement", "blackIncrement", "movesToGo", "", "depth", "nodes", "mate", "moveTime", "infinite", "(Ljava/util/List;ZLjava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Long;Ljava/lang/Integer;Ljava/lang/Long;Z)Ljava/lang/String;", "isErrorLine", "line", "isReadyCommand", "newGameCommand", "parseBestMoveResponse", "parts", "parseIdResponse", "parseInfoParameters", "Lcom/chesstrainer/engine/UCIParser$EngineInfo;", "params", "parseInfoResponse", "parseLine", "parseOptionResponse", "parseScore", "Lcom/chesstrainer/engine/UCIParser$Score;", "startIndex", "ponderHitCommand", "positionCommand", "fen", "moves", "quitCommand", "setOptionCommand", "name", "value", "stopCommand", "uciCommand", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Parse a line of UCI output and return the appropriate response type.
         */
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.UCIParser.UCIResponse parseLine(@org.jetbrains.annotations.NotNull()
        java.lang.String line) {
            return null;
        }
        
        private final com.chesstrainer.engine.UCIParser.UCIResponse parseIdResponse(java.util.List<java.lang.String> parts) {
            return null;
        }
        
        private final com.chesstrainer.engine.UCIParser.UCIResponse parseBestMoveResponse(java.util.List<java.lang.String> parts) {
            return null;
        }
        
        private final com.chesstrainer.engine.UCIParser.UCIResponse parseInfoResponse(java.util.List<java.lang.String> parts) {
            return null;
        }
        
        private final com.chesstrainer.engine.UCIParser.EngineInfo parseInfoParameters(java.util.List<java.lang.String> params) {
            return null;
        }
        
        private final com.chesstrainer.engine.UCIParser.Score parseScore(java.util.List<java.lang.String> params, int startIndex) {
            return null;
        }
        
        private final com.chesstrainer.engine.UCIParser.UCIResponse parseOptionResponse(java.util.List<java.lang.String> parts) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String uciCommand() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String isReadyCommand() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String newGameCommand() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String positionCommand(@org.jetbrains.annotations.Nullable()
        java.lang.String fen, @org.jetbrains.annotations.NotNull()
        java.util.List<com.chesstrainer.chess.Move> moves) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String goCommand(@org.jetbrains.annotations.Nullable()
        java.util.List<com.chesstrainer.chess.Move> searchMoves, boolean ponder, @org.jetbrains.annotations.Nullable()
        java.lang.Long whiteTime, @org.jetbrains.annotations.Nullable()
        java.lang.Long blackTime, @org.jetbrains.annotations.Nullable()
        java.lang.Long whiteIncrement, @org.jetbrains.annotations.Nullable()
        java.lang.Long blackIncrement, @org.jetbrains.annotations.Nullable()
        java.lang.Integer movesToGo, @org.jetbrains.annotations.Nullable()
        java.lang.Integer depth, @org.jetbrains.annotations.Nullable()
        java.lang.Long nodes, @org.jetbrains.annotations.Nullable()
        java.lang.Integer mate, @org.jetbrains.annotations.Nullable()
        java.lang.Long moveTime, boolean infinite) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String stopCommand() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String ponderHitCommand() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String quitCommand() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String setOptionCommand(@org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.NotNull()
        java.lang.Object value) {
            return null;
        }
        
        /**
         * Check if a line indicates an error or unknown response
         */
        public final boolean isErrorLine(@org.jetbrains.annotations.NotNull()
        java.lang.String line) {
            return false;
        }
        
        /**
         * Extract engine name from ID responses
         */
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String extractEngineName(@org.jetbrains.annotations.NotNull()
        java.util.List<? extends com.chesstrainer.engine.UCIParser.UCIResponse> responses) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b.\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u00d1\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u0012\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0002\u0010\u0018J\u0010\u00101\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\u0010\u00102\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\u0010\u00103\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u0010\u00104\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u0010\u00105\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\u000b\u00106\u001a\u0004\u0018\u00010\u0015H\u00c6\u0003J\u000f\u00107\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u000f\u00108\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u0010\u00109\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\u0010\u0010:\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u0010\u0010;\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u000f\u0010<\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u0010\u0010=\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\u000b\u0010>\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u000b\u0010?\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u0010\u0010@\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\u00da\u0001\u0010A\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u000e\b\u0002\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0001\u00a2\u0006\u0002\u0010BJ\u0013\u0010C\u001a\u00020D2\b\u0010E\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010F\u001a\u00020\u0003H\u00d6\u0001J\t\u0010G\u001a\u00020\u0015H\u00d6\u0001R\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0015\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b \u0010\u001aR\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b!\u0010\u001aR\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\"\u0010\u001aR\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b#\u0010\u001aR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b$\u0010%R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b\'\u0010%R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001dR\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001dR\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b,\u0010\u001aR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b/\u0010%R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b0\u0010%\u00a8\u0006H"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$EngineInfo;", "", "depth", "", "selectiveDepth", "time", "", "nodes", "principalVariation", "", "Lcom/chesstrainer/chess/Move;", "multiPrincipalVariation", "score", "Lcom/chesstrainer/engine/UCIParser$Score;", "currentMove", "currentMoveNumber", "hashFull", "nodesPerSecond", "tableBaseHits", "cpuLoad", "string", "", "refutation", "currentLine", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Long;Ljava/lang/Long;Ljava/util/List;Ljava/lang/Integer;Lcom/chesstrainer/engine/UCIParser$Score;Lcom/chesstrainer/chess/Move;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Integer;Ljava/lang/String;Ljava/util/List;Ljava/util/List;)V", "getCpuLoad", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCurrentLine", "()Ljava/util/List;", "getCurrentMove", "()Lcom/chesstrainer/chess/Move;", "getCurrentMoveNumber", "getDepth", "getHashFull", "getMultiPrincipalVariation", "getNodes", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getNodesPerSecond", "getPrincipalVariation", "getRefutation", "getScore", "()Lcom/chesstrainer/engine/UCIParser$Score;", "getSelectiveDepth", "getString", "()Ljava/lang/String;", "getTableBaseHits", "getTime", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Long;Ljava/lang/Long;Ljava/util/List;Ljava/lang/Integer;Lcom/chesstrainer/engine/UCIParser$Score;Lcom/chesstrainer/chess/Move;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Integer;Ljava/lang/String;Ljava/util/List;Ljava/util/List;)Lcom/chesstrainer/engine/UCIParser$EngineInfo;", "equals", "", "other", "hashCode", "toString", "app_debug"})
    public static final class EngineInfo {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer depth = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer selectiveDepth = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long time = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long nodes = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.chesstrainer.chess.Move> principalVariation = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer multiPrincipalVariation = null;
        @org.jetbrains.annotations.Nullable()
        private final com.chesstrainer.engine.UCIParser.Score score = null;
        @org.jetbrains.annotations.Nullable()
        private final com.chesstrainer.chess.Move currentMove = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer currentMoveNumber = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer hashFull = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long nodesPerSecond = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Long tableBaseHits = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer cpuLoad = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String string = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.chesstrainer.chess.Move> refutation = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.chesstrainer.chess.Move> currentLine = null;
        
        public EngineInfo(@org.jetbrains.annotations.Nullable()
        java.lang.Integer depth, @org.jetbrains.annotations.Nullable()
        java.lang.Integer selectiveDepth, @org.jetbrains.annotations.Nullable()
        java.lang.Long time, @org.jetbrains.annotations.Nullable()
        java.lang.Long nodes, @org.jetbrains.annotations.NotNull()
        java.util.List<com.chesstrainer.chess.Move> principalVariation, @org.jetbrains.annotations.Nullable()
        java.lang.Integer multiPrincipalVariation, @org.jetbrains.annotations.Nullable()
        com.chesstrainer.engine.UCIParser.Score score, @org.jetbrains.annotations.Nullable()
        com.chesstrainer.chess.Move currentMove, @org.jetbrains.annotations.Nullable()
        java.lang.Integer currentMoveNumber, @org.jetbrains.annotations.Nullable()
        java.lang.Integer hashFull, @org.jetbrains.annotations.Nullable()
        java.lang.Long nodesPerSecond, @org.jetbrains.annotations.Nullable()
        java.lang.Long tableBaseHits, @org.jetbrains.annotations.Nullable()
        java.lang.Integer cpuLoad, @org.jetbrains.annotations.Nullable()
        java.lang.String string, @org.jetbrains.annotations.NotNull()
        java.util.List<com.chesstrainer.chess.Move> refutation, @org.jetbrains.annotations.NotNull()
        java.util.List<com.chesstrainer.chess.Move> currentLine) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getDepth() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getSelectiveDepth() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getTime() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getNodes() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.chesstrainer.chess.Move> getPrincipalVariation() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getMultiPrincipalVariation() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.chesstrainer.engine.UCIParser.Score getScore() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.chesstrainer.chess.Move getCurrentMove() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getCurrentMoveNumber() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getHashFull() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getNodesPerSecond() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long getTableBaseHits() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getCpuLoad() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getString() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.chesstrainer.chess.Move> getRefutation() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.chesstrainer.chess.Move> getCurrentLine() {
            return null;
        }
        
        public EngineInfo() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component10() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component11() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component12() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component13() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component14() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.chesstrainer.chess.Move> component15() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.chesstrainer.chess.Move> component16() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Long component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.chesstrainer.chess.Move> component5() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component6() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.chesstrainer.engine.UCIParser.Score component7() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.chesstrainer.chess.Move component8() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.UCIParser.EngineInfo copy(@org.jetbrains.annotations.Nullable()
        java.lang.Integer depth, @org.jetbrains.annotations.Nullable()
        java.lang.Integer selectiveDepth, @org.jetbrains.annotations.Nullable()
        java.lang.Long time, @org.jetbrains.annotations.Nullable()
        java.lang.Long nodes, @org.jetbrains.annotations.NotNull()
        java.util.List<com.chesstrainer.chess.Move> principalVariation, @org.jetbrains.annotations.Nullable()
        java.lang.Integer multiPrincipalVariation, @org.jetbrains.annotations.Nullable()
        com.chesstrainer.engine.UCIParser.Score score, @org.jetbrains.annotations.Nullable()
        com.chesstrainer.chess.Move currentMove, @org.jetbrains.annotations.Nullable()
        java.lang.Integer currentMoveNumber, @org.jetbrains.annotations.Nullable()
        java.lang.Integer hashFull, @org.jetbrains.annotations.Nullable()
        java.lang.Long nodesPerSecond, @org.jetbrains.annotations.Nullable()
        java.lang.Long tableBaseHits, @org.jetbrains.annotations.Nullable()
        java.lang.Integer cpuLoad, @org.jetbrains.annotations.Nullable()
        java.lang.String string, @org.jetbrains.annotations.NotNull()
        java.util.List<com.chesstrainer.chess.Move> refutation, @org.jetbrains.annotations.NotNull()
        java.util.List<com.chesstrainer.chess.Move> currentLine) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$OptionType;", "", "(Ljava/lang/String;I)V", "CHECK", "SPIN", "COMBO", "BUTTON", "STRING", "app_debug"})
    public static enum OptionType {
        /*public static final*/ CHECK /* = new CHECK() */,
        /*public static final*/ SPIN /* = new SPIN() */,
        /*public static final*/ COMBO /* = new COMBO() */,
        /*public static final*/ BUTTON /* = new BUTTON() */,
        /*public static final*/ STRING /* = new STRING() */;
        
        OptionType() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.chesstrainer.engine.UCIParser.OptionType> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$ResponseType;", "", "(Ljava/lang/String;I)V", "ID", "UCIOK", "READYOK", "BESTMOVE", "INFO", "OPTION", "ERROR", "UNKNOWN", "app_debug"})
    public static enum ResponseType {
        /*public static final*/ ID /* = new ID() */,
        /*public static final*/ UCIOK /* = new UCIOK() */,
        /*public static final*/ READYOK /* = new READYOK() */,
        /*public static final*/ BESTMOVE /* = new BESTMOVE() */,
        /*public static final*/ INFO /* = new INFO() */,
        /*public static final*/ OPTION /* = new OPTION() */,
        /*public static final*/ ERROR /* = new ERROR() */,
        /*public static final*/ UNKNOWN /* = new UNKNOWN() */;
        
        ResponseType() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.chesstrainer.engine.UCIParser.ResponseType> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0006H\u00c6\u0003J:\u0010\u0014\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0015J\u0013\u0010\u0016\u001a\u00020\u00062\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\r\u00a8\u0006\u001b"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$Score;", "", "centipawns", "", "mate", "lowerBound", "", "upperBound", "(Ljava/lang/Integer;Ljava/lang/Integer;ZZ)V", "getCentipawns", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getLowerBound", "()Z", "getMate", "getUpperBound", "component1", "component2", "component3", "component4", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;ZZ)Lcom/chesstrainer/engine/UCIParser$Score;", "equals", "other", "hashCode", "toString", "", "app_debug"})
    public static final class Score {
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer centipawns = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Integer mate = null;
        private final boolean lowerBound = false;
        private final boolean upperBound = false;
        
        public Score(@org.jetbrains.annotations.Nullable()
        java.lang.Integer centipawns, @org.jetbrains.annotations.Nullable()
        java.lang.Integer mate, boolean lowerBound, boolean upperBound) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getCentipawns() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer getMate() {
            return null;
        }
        
        public final boolean getLowerBound() {
            return false;
        }
        
        public final boolean getUpperBound() {
            return false;
        }
        
        public Score() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Integer component2() {
            return null;
        }
        
        public final boolean component3() {
            return false;
        }
        
        public final boolean component4() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.UCIParser.Score copy(@org.jetbrains.annotations.Nullable()
        java.lang.Integer centipawns, @org.jetbrains.annotations.Nullable()
        java.lang.Integer mate, boolean lowerBound, boolean upperBound) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0001\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u00c6\u0003JQ\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\nH\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006#"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$UCIOption;", "", "name", "", "type", "Lcom/chesstrainer/engine/UCIParser$OptionType;", "default", "min", "max", "options", "", "(Ljava/lang/String;Lcom/chesstrainer/engine/UCIParser$OptionType;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/util/List;)V", "getDefault", "()Ljava/lang/Object;", "getMax", "getMin", "getName", "()Ljava/lang/String;", "getOptions", "()Ljava/util/List;", "getType", "()Lcom/chesstrainer/engine/UCIParser$OptionType;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class UCIOption {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String name = null;
        @org.jetbrains.annotations.NotNull()
        private final com.chesstrainer.engine.UCIParser.OptionType type = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Object min = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Object max = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<java.lang.String> options = null;
        
        public UCIOption(@org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.engine.UCIParser.OptionType type, @org.jetbrains.annotations.Nullable()
        java.lang.Object p2_772401952, @org.jetbrains.annotations.Nullable()
        java.lang.Object min, @org.jetbrains.annotations.Nullable()
        java.lang.Object max, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> options) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.UCIParser.OptionType getType() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Object getDefault() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Object getMin() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Object getMax() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> getOptions() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.UCIParser.OptionType component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Object component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Object component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Object component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.String> component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.UCIParser.UCIOption copy(@org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.engine.UCIParser.OptionType type, @org.jetbrains.annotations.Nullable()
        java.lang.Object p2_772401952, @org.jetbrains.annotations.Nullable()
        java.lang.Object min, @org.jetbrains.annotations.Nullable()
        java.lang.Object max, @org.jetbrains.annotations.NotNull()
        java.util.List<java.lang.String> options) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\b\u0007\b\t\n\u000b\f\r\u000eB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\b\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$UCIResponse;", "", "type", "Lcom/chesstrainer/engine/UCIParser$ResponseType;", "(Lcom/chesstrainer/engine/UCIParser$ResponseType;)V", "getType", "()Lcom/chesstrainer/engine/UCIParser$ResponseType;", "BestMoveResponse", "ErrorResponse", "IdResponse", "InfoResponse", "OptionResponse", "ReadyOkResponse", "UciOkResponse", "UnknownResponse", "Lcom/chesstrainer/engine/UCIParser$UCIResponse$BestMoveResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse$ErrorResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse$IdResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse$InfoResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse$OptionResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse$ReadyOkResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse$UciOkResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse$UnknownResponse;", "app_debug"})
    public static abstract class UCIResponse {
        @org.jetbrains.annotations.NotNull()
        private final com.chesstrainer.engine.UCIParser.ResponseType type = null;
        
        private UCIResponse(com.chesstrainer.engine.UCIParser.ResponseType type) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.chesstrainer.engine.UCIParser.ResponseType getType() {
            return null;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$UCIResponse$BestMoveResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse;", "move", "Lcom/chesstrainer/chess/Move;", "ponder", "(Lcom/chesstrainer/chess/Move;Lcom/chesstrainer/chess/Move;)V", "getMove", "()Lcom/chesstrainer/chess/Move;", "getPonder", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class BestMoveResponse extends com.chesstrainer.engine.UCIParser.UCIResponse {
            @org.jetbrains.annotations.NotNull()
            private final com.chesstrainer.chess.Move move = null;
            @org.jetbrains.annotations.Nullable()
            private final com.chesstrainer.chess.Move ponder = null;
            
            public BestMoveResponse(@org.jetbrains.annotations.NotNull()
            com.chesstrainer.chess.Move move, @org.jetbrains.annotations.Nullable()
            com.chesstrainer.chess.Move ponder) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.chess.Move getMove() {
                return null;
            }
            
            @org.jetbrains.annotations.Nullable()
            public final com.chesstrainer.chess.Move getPonder() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.chess.Move component1() {
                return null;
            }
            
            @org.jetbrains.annotations.Nullable()
            public final com.chesstrainer.chess.Move component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.UCIResponse.BestMoveResponse copy(@org.jetbrains.annotations.NotNull()
            com.chesstrainer.chess.Move move, @org.jetbrains.annotations.Nullable()
            com.chesstrainer.chess.Move ponder) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$UCIResponse$ErrorResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class ErrorResponse extends com.chesstrainer.engine.UCIParser.UCIResponse {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String message = null;
            
            public ErrorResponse(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getMessage() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.UCIResponse.ErrorResponse copy(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$UCIResponse$IdResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse;", "name", "", "author", "(Ljava/lang/String;Ljava/lang/String;)V", "getAuthor", "()Ljava/lang/String;", "getName", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class IdResponse extends com.chesstrainer.engine.UCIParser.UCIResponse {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String name = null;
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String author = null;
            
            public IdResponse(@org.jetbrains.annotations.NotNull()
            java.lang.String name, @org.jetbrains.annotations.NotNull()
            java.lang.String author) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getName() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getAuthor() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.UCIResponse.IdResponse copy(@org.jetbrains.annotations.NotNull()
            java.lang.String name, @org.jetbrains.annotations.NotNull()
            java.lang.String author) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$UCIResponse$InfoResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse;", "info", "Lcom/chesstrainer/engine/UCIParser$EngineInfo;", "(Lcom/chesstrainer/engine/UCIParser$EngineInfo;)V", "getInfo", "()Lcom/chesstrainer/engine/UCIParser$EngineInfo;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class InfoResponse extends com.chesstrainer.engine.UCIParser.UCIResponse {
            @org.jetbrains.annotations.NotNull()
            private final com.chesstrainer.engine.UCIParser.EngineInfo info = null;
            
            public InfoResponse(@org.jetbrains.annotations.NotNull()
            com.chesstrainer.engine.UCIParser.EngineInfo info) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.EngineInfo getInfo() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.EngineInfo component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.UCIResponse.InfoResponse copy(@org.jetbrains.annotations.NotNull()
            com.chesstrainer.engine.UCIParser.EngineInfo info) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$UCIResponse$OptionResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse;", "option", "Lcom/chesstrainer/engine/UCIParser$UCIOption;", "(Lcom/chesstrainer/engine/UCIParser$UCIOption;)V", "getOption", "()Lcom/chesstrainer/engine/UCIParser$UCIOption;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class OptionResponse extends com.chesstrainer.engine.UCIParser.UCIResponse {
            @org.jetbrains.annotations.NotNull()
            private final com.chesstrainer.engine.UCIParser.UCIOption option = null;
            
            public OptionResponse(@org.jetbrains.annotations.NotNull()
            com.chesstrainer.engine.UCIParser.UCIOption option) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.UCIOption getOption() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.UCIOption component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.UCIResponse.OptionResponse copy(@org.jetbrains.annotations.NotNull()
            com.chesstrainer.engine.UCIParser.UCIOption option) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$UCIResponse$ReadyOkResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse;", "dummy", "", "(I)V", "getDummy", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "app_debug"})
        public static final class ReadyOkResponse extends com.chesstrainer.engine.UCIParser.UCIResponse {
            private final int dummy = 0;
            
            public ReadyOkResponse(int dummy) {
            }
            
            public final int getDummy() {
                return 0;
            }
            
            public ReadyOkResponse() {
            }
            
            public final int component1() {
                return 0;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.UCIResponse.ReadyOkResponse copy(int dummy) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u00c6\u0003J\u001f\u0010\n\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u00c6\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0004H\u00d6\u0001R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0012"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$UCIResponse$UciOkResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse;", "options", "", "", "Lcom/chesstrainer/engine/UCIParser$UCIOption;", "(Ljava/util/Map;)V", "getOptions", "()Ljava/util/Map;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class UciOkResponse extends com.chesstrainer.engine.UCIParser.UCIResponse {
            @org.jetbrains.annotations.NotNull()
            private final java.util.Map<java.lang.String, com.chesstrainer.engine.UCIParser.UCIOption> options = null;
            
            public UciOkResponse(@org.jetbrains.annotations.NotNull()
            java.util.Map<java.lang.String, com.chesstrainer.engine.UCIParser.UCIOption> options) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Map<java.lang.String, com.chesstrainer.engine.UCIParser.UCIOption> getOptions() {
                return null;
            }
            
            public UciOkResponse() {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.util.Map<java.lang.String, com.chesstrainer.engine.UCIParser.UCIOption> component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.UCIResponse.UciOkResponse copy(@org.jetbrains.annotations.NotNull()
            java.util.Map<java.lang.String, com.chesstrainer.engine.UCIParser.UCIOption> options) {
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
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/chesstrainer/engine/UCIParser$UCIResponse$UnknownResponse;", "Lcom/chesstrainer/engine/UCIParser$UCIResponse;", "line", "", "(Ljava/lang/String;)V", "getLine", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class UnknownResponse extends com.chesstrainer.engine.UCIParser.UCIResponse {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String line = null;
            
            public UnknownResponse(@org.jetbrains.annotations.NotNull()
            java.lang.String line) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getLine() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.chesstrainer.engine.UCIParser.UCIResponse.UnknownResponse copy(@org.jetbrains.annotations.NotNull()
            java.lang.String line) {
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
}