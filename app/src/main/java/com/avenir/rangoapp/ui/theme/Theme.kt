package com.avenir.rangoapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SecondaryColor,
    secondary = PrimaryColor,
    tertiary = Color.Yellow,
    background = SecondaryColor,
    surface = SecondaryColor,
    onPrimary = PrimaryColor,
    onSecondary = SecondaryColor,
    onBackground = PrimaryColor,
    onSurface = PrimaryColor,
    onTertiary = SecondaryColor
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    tertiary = SecondaryColor,
    background = PrimaryColor,
    surface = PrimaryColor,
    onPrimary = SecondaryColor,
    onSecondary = PrimaryColor,
    onBackground = SecondaryColor,
    onSurface = SecondaryColor,
    onTertiary = Color.Yellow

)

@Composable
fun RangoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}