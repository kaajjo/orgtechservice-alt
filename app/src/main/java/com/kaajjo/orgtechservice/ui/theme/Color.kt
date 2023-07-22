package com.kaajjo.orgtechservice.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

@Immutable
data class CustomColorsPalette(
    val positiveText: Color = Color.Unspecified,
    val negative: Color = Color.Unspecified,
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }

val OnLightCustomColorsPalette = CustomColorsPalette(
    positiveText = Color(0xFF45A221),
    negative = Color(0xFFB62843)
)

val OnDarkCustomColorsPalette = CustomColorsPalette(
    positiveText = Color(0xFF6EDC71),
    negative = Color(0xFFF14868)
)