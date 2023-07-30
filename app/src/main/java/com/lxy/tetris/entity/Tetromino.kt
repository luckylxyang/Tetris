package com.lxy.tetris.entity

import androidx.compose.ui.graphics.Color
import java.util.concurrent.TimeUnit

data class Tetromino(
    val shape: Array<BooleanArray>,
    val color: Color,
    var rotations: Int = 0,
)