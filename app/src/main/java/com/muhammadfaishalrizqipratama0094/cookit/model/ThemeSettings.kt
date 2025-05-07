package com.muhammadfaishalrizqipratama0094.cookit.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.muhammadfaishalrizqipratama0094.cookit.ui.theme.*

enum class AppTheme(val id: Int, val themeName: String) {
    ORANGE(0, "Orange"),
    GREEN(1, "Green"),
    BLUE(2, "Blue"),
    RED(3, "Red"),
    PURPLE(4, "Purple");

    companion object {
        fun fromId(id: Int): AppTheme {
            return values().find { it.id == id } ?: ORANGE
        }
    }
}

data class ThemeColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color
)

@Composable
fun getThemeColors(isDarkTheme: Boolean, theme: AppTheme): ThemeColors {
    return when (theme) {
        AppTheme.ORANGE -> if (isDarkTheme) {
            ThemeColors(Orange80, OrangeGrey80, OrangeDark80)
        } else {
            ThemeColors(Orange40, OrangeGrey40, OrangeDark40)
        }
        AppTheme.GREEN -> if (isDarkTheme) {
            ThemeColors(Green80, GreenGrey80, GreenDark80)
        } else {
            ThemeColors(Green40, GreenGrey40, GreenDark40)
        }
        AppTheme.BLUE -> if (isDarkTheme) {
            ThemeColors(Blue80, BlueGrey80, BlueDark80)
        } else {
            ThemeColors(Blue40, BlueGrey40, BlueDark40)
        }
        AppTheme.RED -> if (isDarkTheme) {
            ThemeColors(Red80, RedGrey80, RedDark80)
        } else {
            ThemeColors(Red40, RedGrey40, RedDark40)
        }
        AppTheme.PURPLE -> if (isDarkTheme) {
            ThemeColors(Purple80, PurpleGrey80, Pink80)
        } else {
            ThemeColors(Purple40, PurpleGrey40, Pink40)
        }
    }
}