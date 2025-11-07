package edu.pdm.proyectounomacsosa.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color


private val DarkColors = darkColorScheme(
    primary = Color(0xFF1E88E5),       // buttons, checkboxes, etc.
    onPrimary = Color.White,           // text on buttons
    secondary = Color(0xFF03DAC5),
    onSecondary = Color.Black,
    background = Color(0xFF121212),   // screen background
    surface = Color(0xFF1E1E1E),
    onBackground = Color.White,
    onSurface = Color.White,
)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1F1B24),
    onPrimary = Color.White,
    secondary = Color(0xFF424242),
    onSecondary = Color.Black,
    background = Color(0xFF101010),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    error = Color(0xFFFFFFFF),
    onError = Color.Black
)

@Composable
fun ProyectoUnoMacSosaTheme(
    darkTheme: Boolean = true, // always dark
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else lightColorScheme()

    MaterialTheme(
        colorScheme = DarkColors,
        typography = Typography,
        content = content
    )
}
