package com.example.caregiver.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF6200EE),
    secondary = androidx.compose.ui.graphics.Color(0xFF03DAC5),
    tertiary = androidx.compose.ui.graphics.Color(0xFF3700B3)
)

private val LightColorScheme = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF6200EE),
    secondary = androidx.compose.ui.graphics.Color(0xFF03DAC5),
    tertiary = androidx.compose.ui.graphics.Color(0xFF3700B3),
    background = androidx.compose.ui.graphics.Color.White,
    surface = androidx.compose.ui.graphics.Color.White
)

@Composable
fun CaregiverTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

