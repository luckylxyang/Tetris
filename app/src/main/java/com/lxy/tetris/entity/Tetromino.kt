package com.lxy.tetris.entity

import androidx.compose.ui.graphics.Color

data class Tetromino(
    val shape: Array<BooleanArray>,
    val color: Color,
    var currentRow: Int = 0,
    var currentCol: Int = 0
)