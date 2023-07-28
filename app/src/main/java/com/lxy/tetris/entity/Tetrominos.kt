package com.lxy.tetris.entity

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

object Tetrominoes {
    val I = Tetromino(
        shape = arrayOf(
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, true, false, false)
        ),
        color = Color.Blue
    )

    val J = Tetromino(
        shape = arrayOf(
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, true, false),
            booleanArrayOf(true, true, false)
        ),
        color = Color.Red
    )

    val L = Tetromino(
        shape = arrayOf(
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, true, true)
        ),
        color = Color.Green
    )

    // 添加更多的方块类型...

    // 所有方块类型的列表
    private val allTetrominoes = listOf(I, J, L /*...*/)

    // 随机生成一个方块
    fun getRandomTetromino(): Tetromino {
        val randomIndex = Random.nextInt(allTetrominoes.size)
        return allTetrominoes[randomIndex]
    }
}