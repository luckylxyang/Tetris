package com.lxy.tetris.entity

import androidx.compose.ui.graphics.Color

abstract class TetrisBlock {

    abstract var shape: Array<BooleanArray>

    val color: Color = Color.Blue

    protected var rotations : Int = 0
    var row : Int = 0
    var col : Int = 0

    abstract fun rotateBlock() : Array<BooleanArray>

}