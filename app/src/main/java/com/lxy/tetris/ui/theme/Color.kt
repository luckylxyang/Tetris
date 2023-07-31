package com.lxy.tetris.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val BackgroundColorLight = Color(0xF2FFD600)
val OnBackgroundColorLight = Color(0xff9ead86)
val BrickColorAlpha = Color.Black.copy(alpha = 0.2f)
val BrickColorFill = Color.Black.copy(alpha = 0.9f)

val ButtonEnabledColor = Brush.linearGradient(
    colors = listOf(
        Color(0xFFE53935),
        Color(0xFFF02A26),
        Color(0xFFF3413D)
    )
)

val ButtonDisableColor = Brush.linearGradient(
    colors = listOf(
        Color(0xFF42A5F5),
        Color(0xFF1F8AE0),
        Color(0xFF1A7ECF)
    )
)