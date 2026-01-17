package com.chesstrainer.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000T\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\u001aw\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\b\u0010\t\u001a\u0004\u0018\u00010\n2$\u0010\u000b\u001a \b\u0001\u0012\u0004\u0012\u00020\r\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u00100\f2\u0018\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\fH\u0003\u00a2\u0006\u0002\u0010\u0014\u001a\u0016\u0010\u0015\u001a\u00020\u00012\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0017H\u0007\u001a5\u0010\u0018\u001a\u00020\u00122\b\u0010\u0019\u001a\u0004\u0018\u00010\u00132\b\u0010\u001a\u001a\u0004\u0018\u00010\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u0013H\u0002\u00a2\u0006\u0002\u0010\u001d\u001a5\u0010\u001e\u001a\u00020\u00072\b\u0010\u0019\u001a\u0004\u0018\u00010\u00132\b\u0010\u001a\u001a\u0004\u0018\u00010\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u0013H\u0002\u00a2\u0006\u0002\u0010\u001f\u001a)\u0010 \u001a\u0004\u0018\u00010\u00132\b\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0002\u00a2\u0006\u0002\u0010%\u00a8\u0006&"}, d2 = {"LessonExerciseCard", "", "exercise", "Lcom/chesstrainer/data/LessonExercise;", "boardOrientation", "Lcom/chesstrainer/chess/Color;", "isCompleted", "", "isLocked", "exerciseProgress", "Lcom/chesstrainer/data/LessonExerciseProgress;", "analysisProvider", "Lkotlin/Function2;", "Lcom/chesstrainer/chess/GameState;", "Lkotlin/coroutines/Continuation;", "Lcom/chesstrainer/ui/LessonAnalysis;", "", "onExerciseCompleted", "", "", "(Lcom/chesstrainer/data/LessonExercise;Lcom/chesstrainer/chess/Color;ZZLcom/chesstrainer/data/LessonExerciseProgress;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;)V", "LessonsScreen", "onNavigateBack", "Lkotlin/Function0;", "formatEvalHint", "moveScore", "bestScore", "minEval", "maxMistake", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/String;", "isEvaluationAcceptable", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)Z", "toCentipawns", "score", "Lcom/chesstrainer/engine/UCIParser$Score;", "perspective", "sideToMove", "(Lcom/chesstrainer/engine/UCIParser$Score;Lcom/chesstrainer/chess/Color;Lcom/chesstrainer/chess/Color;)Ljava/lang/Integer;", "app_debug"})
public final class LessonsScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void LessonsScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LessonExerciseCard(com.chesstrainer.data.LessonExercise exercise, com.chesstrainer.chess.Color boardOrientation, boolean isCompleted, boolean isLocked, com.chesstrainer.data.LessonExerciseProgress exerciseProgress, kotlin.jvm.functions.Function2<? super com.chesstrainer.chess.GameState, ? super kotlin.coroutines.Continuation<? super com.chesstrainer.ui.LessonAnalysis>, ? extends java.lang.Object> analysisProvider, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Integer, kotlin.Unit> onExerciseCompleted) {
    }
    
    private static final java.lang.Integer toCentipawns(com.chesstrainer.engine.UCIParser.Score score, com.chesstrainer.chess.Color perspective, com.chesstrainer.chess.Color sideToMove) {
        return null;
    }
    
    private static final boolean isEvaluationAcceptable(java.lang.Integer moveScore, java.lang.Integer bestScore, java.lang.Integer minEval, java.lang.Integer maxMistake) {
        return false;
    }
    
    private static final java.lang.String formatEvalHint(java.lang.Integer moveScore, java.lang.Integer bestScore, java.lang.Integer minEval, java.lang.Integer maxMistake) {
        return null;
    }
}