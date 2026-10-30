package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = FunoraLimePrimary,
    onPrimary = FunoraLimeOnPrimary,
    primaryContainer = FunoraDarkSurfaceHigh,
    onPrimaryContainer = FunoraLimePrimary,
    secondary = FunoraLimeSecondary,
    onSecondary = FunoraLimeOnPrimary,
    tertiary = FunoraNeonCyan,
    onTertiary = FunoraLimeOnPrimary,
    background = FunoraDarkBackground,
    onBackground = FunoraTextHigh,
    surface = FunoraDarkSurface,
    onSurface = FunoraTextHigh,
    surfaceVariant = FunoraDarkSurfaceHigh,
    onSurfaceVariant = FunoraTextMedium,
    outline = FunoraDarkBorder,
    error = FunoraError,
    onError = FunoraLimeOnPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = FunoraLimePrimary,
    onPrimary = FunoraLimeOnPrimary,
    primaryContainer = FunoraDarkSurfaceHigh,
    onPrimaryContainer = FunoraLimePrimary,
    secondary = FunoraLimeSecondary,
    onSecondary = FunoraLimeOnPrimary,
    tertiary = FunoraNeonCyan,
    onTertiary = FunoraLimeOnPrimary,
    background = FunoraDarkBackground,
    onBackground = FunoraTextHigh,
    surface = FunoraDarkSurface,
    onSurface = FunoraTextHigh,
    surfaceVariant = FunoraDarkSurfaceHigh,
    onSurfaceVariant = FunoraTextMedium,
    outline = FunoraDarkBorder,
    error = FunoraError,
    onError = FunoraLimeOnPrimary
)

@Composable
fun FunoraTheme(
    darkTheme: Boolean = true, // Funora is built primarily around its signature dark & neon vibe
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
