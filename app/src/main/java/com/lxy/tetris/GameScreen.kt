package com.lxy.tetris

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lxy.tetris.ui.theme.BrickColorAlpha
import com.lxy.tetris.ui.theme.BrickColorFill

@Composable
fun GameScreen(viewModel: TetrisViewModel) {
    // 游戏区域的宽高和格子大小
    val gameAreaWidth = 10
    val gameAreaHeight = 20
    val cellSize = 14.dp
    val gameArea by viewModel.gameArea.collectAsState()
    val currentTetrisArea by viewModel.currentTetrisArea.collectAsState()
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
                drawGameArea(bgColor, gameAreaWidth, gameAreaHeight, cellSize, gameArea)
                drawCurrentBlock(
                    bgColor,
                    currentTetrisArea,
                    viewModel.currentRow.value,
                    viewModel.currentCol.value,
                    cellSize
                )
            }

            // 下方控制按钮区域
            Row(
                modifier = Modifier.padding(4.dp, 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                // 分为左右两块，
                // 左侧有 暂停、音效、重玩、掉落

                val source = if(viewModel.soundState.value) R.drawable.ic_sound else R.drawable.ic_sound_off
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.padding(4.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        FloatingActionButton(
                            shape = Shapes(small = RoundedCornerShape(50.dp)).small,
                            onClick = { viewModel.pauseGame() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_pause),
                                contentDescription = "暂停"
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        FloatingActionButton(
                            shape = Shapes(small = RoundedCornerShape(50.dp)).small,
                            onClick = { viewModel.soundControl() }) {
                            Icon(
                                painter = painterResource(source),
                                contentDescription = "音效"
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { viewModel.restartGame() }) {
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
                        .padding(8.dp)
                        .weight(1f)
                        .graphicsLayer(rotationZ = 45f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        FloatingActionButton(
                            modifier = Modifier.graphicsLayer(rotationZ = -45f),
                            shape = Shapes(small = RoundedCornerShape(50.dp)).small,
                            onClick = { viewModel.rotateBlock() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_rotate),
                                contentDescription = "旋转"
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        FloatingActionButton(
                            modifier = Modifier.graphicsLayer(rotationZ = -45f),
                            shape = Shapes(small = RoundedCornerShape(50.dp)).small,
                            onClick = { viewModel.rightMoveBlock() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_right),
                                contentDescription = "向右"
                            )
                        }

                    }
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        FloatingActionButton(
                            modifier = Modifier.graphicsLayer(rotationZ = -45f),
                            shape = Shapes(small = RoundedCornerShape(50.dp)).small,
                            onClick = { viewModel.leftMoveBlock() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_left),
                                contentDescription = "向左"
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        FloatingActionButton(
                            modifier = Modifier.graphicsLayer(rotationZ = -45f),
                            shape = Shapes(small = RoundedCornerShape(50.dp)).small,
                            onClick = { viewModel.quickDropBlock() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_down),
                                contentDescription = "向下"
                            )
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
    bgColor: Color,
    tetris: Array<BooleanArray>,
    currentRow: Int,
    currentcol: Int,
    cellSize: Dp
) {
    val numRows = tetris.size
    val numCols = tetris[0].size

    for (row in 0 until numRows) {
        for (col in 0 until numCols) {
            // 这里不做是否出界的验证，因为更新方块位置之前，已经验证过一次了
            if (tetris[row][col]) {
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

private fun DrawScope.drawBrick(
    bgColor: Color,
    brickSize: Float,
    brickColor: Color,
    topLeft: Offset
) {
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
