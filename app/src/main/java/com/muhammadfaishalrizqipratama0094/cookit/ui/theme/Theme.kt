package com.muhammadfaishalrizqipratama0094.cookit.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.muhammadfaishalrizqipratama0094.cookit.model.AppTheme
import com.muhammadfaishalrizqipratama0094.cookit.model.getThemeColors
import com.muhammadfaishalrizqipratama0094.cookit.viewmodel.ResepViewModel

@Composable
fun CookITTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    viewModel: ResepViewModel? = null,
    content: @Composable () -> Unit
) {
    val currentTheme by viewModel?.themePreference?.collectAsState() ?: remember { androidx.compose.runtime.mutableStateOf(AppTheme.ORANGE) }

    val themeColors = getThemeColors(darkTheme, currentTheme)

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context).copy(
                    primary = themeColors.primary,
                    secondary = themeColors.secondary,
                    tertiary = themeColors.tertiary
                )
            } else {
                dynamicLightColorScheme(context).copy(
                    primary = themeColors.primary,
                    secondary = themeColors.secondary,
                    tertiary = themeColors.tertiary
                )
            }
        }
        darkTheme -> darkColorScheme(
            primary = themeColors.primary,
            secondary = themeColors.secondary,
            tertiary = themeColors.tertiary
        )
        else -> lightColorScheme(
            primary = themeColors.primary,
            secondary = themeColors.secondary,
            tertiary = themeColors.tertiary
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}