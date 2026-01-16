package com.chesstrainer.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = androidx.compose.ui.graphics.Color(0xFF1976D2),
    primaryVariant = androidx.compose.ui.graphics.Color(0xFF1565C0),
    secondary = androidx.compose.ui.graphics.Color(0xFFDCEDC8),
    background = androidx.compose.ui.graphics.Color.White,
    surface = androidx.compose.ui.graphics.Color.White,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    onSecondary = androidx.compose.ui.graphics.Color.Black,
    onBackground = androidx.compose.ui.graphics.Color.Black,
    onSurface = androidx.compose.ui.graphics.Color.Black
)

@Composable
fun ChessTrainerTheme(
    darkTheme: Boolean = false, // Default to light theme for simplicity
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorPalette,
        content = content
    )
}