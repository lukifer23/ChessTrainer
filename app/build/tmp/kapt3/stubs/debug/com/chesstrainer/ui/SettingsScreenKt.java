package com.chesstrainer.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a.\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a\"\u0010\t\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u00032\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003H\u0003\u001a8\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0003\u001a\u0016\u0010\u000f\u001a\u00020\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a(\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0003\u00a8\u0006\u0019"}, d2 = {"EngineOption", "", "title", "", "description", "selected", "", "onSelect", "Lkotlin/Function0;", "EngineStatusRow", "status", "details", "OrientationOption", "modifier", "Landroidx/compose/ui/Modifier;", "SettingsScreen", "onNavigateBack", "SettingsSummary", "engine", "Lcom/chesstrainer/utils/EngineType;", "boardOrientation", "Lcom/chesstrainer/chess/Color;", "leelaNodes", "", "stockfishDepth", "app_debug"})
public final class SettingsScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EngineOption(java.lang.String title, java.lang.String description, boolean selected, kotlin.jvm.functions.Function0<kotlin.Unit> onSelect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void OrientationOption(java.lang.String title, java.lang.String description, boolean selected, kotlin.jvm.functions.Function0<kotlin.Unit> onSelect, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EngineStatusRow(java.lang.String title, java.lang.String status, java.lang.String details) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsSummary(com.chesstrainer.utils.EngineType engine, com.chesstrainer.chess.Color boardOrientation, int leelaNodes, int stockfishDepth) {
    }
}