package com.lxy.tetris

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lxy.tetris.entity.TetrisBlock
import com.lxy.tetris.entity.Tetromino
import com.lxy.tetris.ui.theme.BrickColorAlpha
import com.lxy.tetris.ui.theme.BrickColorFill

@Composable
fun GameScreen(viewModel: TetrisViewModel) {
    // 游戏区域的宽高和格子大小
    val gameAreaWidth = 10
    val gameAreaHeight = 20
    val cellSize = 14.dp
    val gameArea = remember { viewModel.gameArea }
    val currentTetromino = remember { viewModel.currentTetromino }
    val bgColor = MaterialTheme.colorScheme.onBackground
    // 绘制游戏界面
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Yellow // 绘制边缘区域（金黄色），用于区分非游戏区域
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            // 绘制游戏区域
            Canvas(
                modifier = Modifier.size(
                    (gameAreaWidth * cellSize.value).dp,
                    (gameAreaHeight * cellSize.value).dp
                ),
            ) {
                // 绘制边缘区域（金黄色）
                drawRect(color = Color.Yellow, size = size)

                // 绘制游戏区域（深灰色）
                drawRect(
                    color = Color.Gray,
                    size = Size(gameAreaWidth * cellSize.toPx(), gameAreaHeight * cellSize.toPx())
                )

                // 绘制游戏区域内的方块
                drawGameArea(bgColor, gameAreaWidth, gameAreaHeight, cellSize, gameArea.value)
                drawCurrentBlock(
                    bgColor,
                    currentTetromino.value,
                    viewModel.currentRow.value,
                    viewModel.currentCol.value,
                    cellSize
                )
            }

            // 下方控制按钮区域
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                // 分为左右两块，
                // 左侧有 暂停、音效、重玩、掉落

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = { viewModel.pauseGame() }) {
                            Text(text = "暂停")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { /* 下方按钮点击事件 */ }) {
                            Text(text = "音效")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { /* 右方按钮点击事件 */ }) {
                            Text(text = "重玩")
                        }
                    }

                    Button(onClick = { /* 左方按钮点击事件 */ }) {
                        Text(text = "掉落")
                    }
                }


                // 右侧有左、右、下、和旋转
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                        .graphicsLayer(rotationZ = 45f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = { viewModel.rotateBlock() }) {
                            Text(text = "旋转")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { viewModel.rightMoveBlock() }) {
                            Text(text = "右")
                        }

                    }
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Button(onClick = { viewModel.leftMoveBlock() }) {
                            Text(text = "左")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { viewModel.quickDropBlock() }) {
                            Text(text = "下")
                        }

                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            // 显示分数
            Text(text = "Score: ${viewModel.score.value}")
        }
    }
}

private fun DrawScope.drawGameArea(
    bgColor: Color,
    width: Int,
    height: Int,
    cellSize: Dp,
    gameArea: Array<BooleanArray>
) {
    for (row in 0 until height) {
        for (col in 0 until width) {
            if (gameArea[row][col]) {
//                drawRect(
//                    color = Color.Blue,
//                    size = Size(cellSize.toPx(), cellSize.toPx()),
//                    topLeft = Offset(col * cellSize.toPx(), row * cellSize.toPx())
//                )

                drawBrick(
                    bgColor = bgColor,
                    brickSize = cellSize.toPx(),
                    BrickColorAlpha,
                    topLeft = Offset(col * cellSize.toPx(), row * cellSize.toPx())
                )
            }
        }
    }
}

private fun DrawScope.drawCurrentBlock(
    bgColor : Color,
    tetris: TetrisBlock,
    currentRow : Int,
    currentcol : Int,
    cellSize: Dp
) {
    val currentBlock = tetris.shape
    val numRows = currentBlock.size
    val numCols = currentBlock[0].size

    for (row in 0 until numRows) {
        for (col in 0 until numCols) {
            // 这里不做是否出界的验证，因为更新方块位置之前，已经验证过一次了
            if (currentBlock[row][col]) {
                val newRow = currentRow + row
                val newCol = currentcol + col
                drawBrick(
                    bgColor = bgColor,
                    brickSize = cellSize.toPx(),
                    BrickColorFill,
                    topLeft = Offset(newCol * cellSize.toPx(), newRow * cellSize.toPx())
                )
            }
        }
    }
}

private fun DrawScope.drawBrick(bgColor: Color, brickSize: Float, brickColor: Color, topLeft : Offset) {
    drawRect(color = brickColor, size = Size(width = brickSize, height = brickSize))
    val strokeWidth = brickSize / 9f
    translate(left = strokeWidth, top = strokeWidth) {
        drawRect(
            color = bgColor,
            size = Size(
                width = brickSize - 2 * strokeWidth,
                height = brickSize - 2 * strokeWidth
            ),
            topLeft = topLeft
        )
    }
    val brickInnerSize = brickSize / 2.0f
    val translateLeft = (brickSize - brickInnerSize) / 2
    translate(left = translateLeft, top = translateLeft) {
        drawRect(
            color = brickColor,
            size = Size(width = brickInnerSize, height = brickInnerSize),
            topLeft = topLeft
        )
    }
}
