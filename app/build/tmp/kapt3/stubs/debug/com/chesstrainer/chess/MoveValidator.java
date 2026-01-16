package com.chesstrainer.chess;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/chesstrainer/chess/MoveValidator;", "", "()V", "Companion", "app_debug"})
public final class MoveValidator {
    @org.jetbrains.annotations.NotNull()
    public static final com.chesstrainer.chess.MoveValidator.Companion Companion = null;
    
    public MoveValidator() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J(\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J \u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\u001a\u0010\u0011\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0002J&\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J.\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J&\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u001c\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0019J.\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J.\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J&\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J&\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J@\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0018\u0010!\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020#0\"0\u0013H\u0002J\u0016\u0010$\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eJ \u0010%\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J \u0010&\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\u000eH\u0002J \u0010)\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J \u0010+\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J \u0010,\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001e\u0010-\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0019J\u0016\u0010.\u001a\u00020/2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0014J\u001e\u00100\u001a\u00020/2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0019J\f\u00101\u001a\u00020\u000e*\u00020\u000eH\u0002\u00a8\u00062"}, d2 = {"Lcom/chesstrainer/chess/MoveValidator$Companion;", "", "()V", "canBishopAttack", "", "board", "Lcom/chesstrainer/chess/Board;", "from", "Lcom/chesstrainer/chess/Square;", "to", "canKingAttack", "canKnightAttack", "canPawnAttack", "color", "Lcom/chesstrainer/chess/Color;", "canQueenAttack", "canRookAttack", "findKing", "generateBishopMoves", "", "Lcom/chesstrainer/chess/Move;", "piece", "Lcom/chesstrainer/chess/Piece;", "generateKingMoves", "gameState", "Lcom/chesstrainer/chess/GameState;", "generateKnightMoves", "generateLegalMoves", "generatePawnMoves", "generatePieceMoves", "generateQueenMoves", "generateRookMoves", "generateSlidingMoves", "directions", "Lkotlin/Pair;", "", "isKingInCheck", "isPathClear", "isSquareAttacked", "square", "byColor", "isValidCastling", "move", "isValidDestination", "isValidEnPassant", "isValidMove", "performCastling", "", "performEnPassant", "opposite", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean isValidMove(@org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.Board board, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.Move move, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.GameState gameState) {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.chesstrainer.chess.Move> generateLegalMoves(@org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.Board board, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.GameState gameState) {
            return null;
        }
        
        private final java.util.List<com.chesstrainer.chess.Move> generatePieceMoves(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Piece piece, com.chesstrainer.chess.GameState gameState) {
            return null;
        }
        
        private final java.util.List<com.chesstrainer.chess.Move> generatePawnMoves(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Piece piece, com.chesstrainer.chess.GameState gameState) {
            return null;
        }
        
        private final java.util.List<com.chesstrainer.chess.Move> generateRookMoves(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Piece piece) {
            return null;
        }
        
        private final java.util.List<com.chesstrainer.chess.Move> generateBishopMoves(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Piece piece) {
            return null;
        }
        
        private final java.util.List<com.chesstrainer.chess.Move> generateQueenMoves(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Piece piece) {
            return null;
        }
        
        private final java.util.List<com.chesstrainer.chess.Move> generateSlidingMoves(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Piece piece, java.util.List<kotlin.Pair<java.lang.Integer, java.lang.Integer>> directions) {
            return null;
        }
        
        private final java.util.List<com.chesstrainer.chess.Move> generateKnightMoves(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Piece piece) {
            return null;
        }
        
        private final java.util.List<com.chesstrainer.chess.Move> generateKingMoves(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Piece piece, com.chesstrainer.chess.GameState gameState) {
            return null;
        }
        
        private final boolean isValidDestination(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Move move, com.chesstrainer.chess.Piece piece) {
            return false;
        }
        
        private final boolean isValidCastling(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Move move, com.chesstrainer.chess.GameState gameState) {
            return false;
        }
        
        private final boolean isValidEnPassant(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Move move, com.chesstrainer.chess.GameState gameState) {
            return false;
        }
        
        public final void performCastling(@org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.Board board, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.Move move) {
        }
        
        public final void performEnPassant(@org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.Board board, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.Move move, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.GameState gameState) {
        }
        
        public final boolean isKingInCheck(@org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.Board board, @org.jetbrains.annotations.NotNull()
        com.chesstrainer.chess.Color color) {
            return false;
        }
        
        private final com.chesstrainer.chess.Square findKing(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Color color) {
            return null;
        }
        
        private final boolean isSquareAttacked(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square square, com.chesstrainer.chess.Color byColor) {
            return false;
        }
        
        private final boolean canPawnAttack(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Square to, com.chesstrainer.chess.Color color) {
            return false;
        }
        
        private final boolean canRookAttack(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Square to) {
            return false;
        }
        
        private final boolean canBishopAttack(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Square to) {
            return false;
        }
        
        private final boolean canQueenAttack(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Square to) {
            return false;
        }
        
        private final boolean canKnightAttack(com.chesstrainer.chess.Square from, com.chesstrainer.chess.Square to) {
            return false;
        }
        
        private final boolean canKingAttack(com.chesstrainer.chess.Square from, com.chesstrainer.chess.Square to) {
            return false;
        }
        
        private final boolean isPathClear(com.chesstrainer.chess.Board board, com.chesstrainer.chess.Square from, com.chesstrainer.chess.Square to) {
            return false;
        }
        
        private final com.chesstrainer.chess.Color opposite(com.chesstrainer.chess.Color $this$opposite) {
            return null;
        }
    }
}