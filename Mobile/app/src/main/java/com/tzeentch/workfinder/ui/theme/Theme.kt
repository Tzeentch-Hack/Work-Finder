package com.tzeentch.workfinder.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val ColorScheme = darkColorScheme(
    primary = Color(0xFF284779),
    secondary = Color(0xFFFFDB58),
    tertiary = Color(0xFFEFB8C8),
    background = Color(0xFFFFFBFE),
    surface = Color(0xFF9CADC8),
    onPrimary = Color.White,
    onSecondary = Color(0xFF332D41),
    onTertiary = Color(0xFF492532),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun WorkfinderTheme(
    content: @Composable () -> Unit
) {

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = ColorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}