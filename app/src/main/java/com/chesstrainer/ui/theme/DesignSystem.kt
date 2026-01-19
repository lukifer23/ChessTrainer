package com.chesstrainer.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Modern Color Palette
val Slate900 = Color(0xFF0F172A)
val Slate800 = Color(0xFF1E293B)
val Slate700 = Color(0xFF334155)
val Slate200 = Color(0xFFE2E8F0)
val Slate50 = Color(0xFFF8FAFC)

val AccentBlue = Color(0xFF3B82F6)
val AccentViola = Color(0xFF8B5CF6)
val AccentTeal = Color(0xFF14B8A6)
val ErrorRed = Color(0xFFEF4444)
val WarningGold = Color(0xFFF59E0B)

private val DarkColorPalette = darkColors(
    primary = AccentBlue,
    primaryVariant = AccentViola,
    secondary = AccentTeal,
    background = Slate900,
    surface = Slate800,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Slate50,
    onSurface = Slate50,
    error = ErrorRed
)

// Typography
val ModernTypography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    )
)

@Composable
fun ChessTrainerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = ModernTypography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
