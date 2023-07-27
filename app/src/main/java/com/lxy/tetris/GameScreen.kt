package com.lxy.tetris

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GameScreen(viewModel: TetrisViewModel) {
    // 绘制游戏区域和方块
    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            // 绘制游戏区域
//            drawRect(/* ... */)

            // 绘制当前方块
            val currentBlock = viewModel.currentBlock.value
            for (row in 0 until currentBlock.size) {
                for (col in 0 until currentBlock[row].size) {
                    if (currentBlock[row][col]) {
                        // 绘制方块格子
//                        drawRect(/* ... */)
                    }
                }
            }
        }
    )

    // 显示分数和游戏状态等其他元素
    // ...
}