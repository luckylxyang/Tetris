package com.lxy.tetris

import android.graphics.Path
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lxy.tetris.entity.Tetromino
import com.lxy.tetris.entity.Tetrominoes
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TetrisViewModel : ViewModel() {

    private val gameAreaWidth = 10
    private val gameAreaHeight = 20

    val uiState = mutableStateOf(false)

    // 当前方块
    val currentBlock = mutableStateOf<Tetromino>(Tetrominoes.getRandomTetromino())


    // 游戏区域
    val gameArea = mutableStateOf(emptyArray<BooleanArray>())

    // 分数
    val score = mutableStateOf(0)

    // 方块下落的时间间隔（毫秒）
    private val dropInterval = 500L

    // 是否方块已经固定在游戏区域中
    private var isBlockFixed = false
    // 开始游戏
    fun startGame() {
        // 初始化游戏状态，生成新的方块等

        uiState.value = true

        gameArea.value = createEmptyGameArea()

        // 生成新的方块
        currentBlock.value = generateRandomBlock()

        // 初始化分数
        score.value = 0

        startDropTimer()
    }


    // 旋转方块
    fun rotateBlock() {
        // 处理方块的旋转逻辑
        // ...
    }

    // 快速下落方块
    fun dropBlock() {
        // 处理方块的快速下落逻辑
        // ...
    }


    private fun createEmptyGameArea(): Array<BooleanArray> {
        return Array(gameAreaHeight) {
            BooleanArray(gameAreaWidth) { false }
        }
    }

    // 生成随机方块
    private fun generateRandomBlock(): Tetromino {
        // 简单起见，这里只生成一个简单的 L 形方块
        var randomTetromino = Tetrominoes.getRandomTetromino()
        randomTetromino.currentCol = gameAreaWidth / 2
        return randomTetromino
    }

    // 启动方块下落的定时器
    private fun startDropTimer() {
        viewModelScope.launch {
            while (isActive) {
                delay(dropInterval)
                if (!isBlockFixed) {
                    moveBlock(Direction.Down)
                }
            }
        }
    }

    // 移动方块
    fun moveBlock(direction: Direction) {
        // 根据方向更新方块的位置
        when (direction) {
            Direction.Left -> {
                if (isMoveValid()) {
                    currentBlock.value.currentCol -= 1
                }
            }
            Direction.Right -> {
                if (isMoveValid()) {
                    currentBlock.value.currentCol += 1
                }
            }
            Direction.Up -> {

            }
            Direction.Down -> {
                if (!isMoveValid()) {
                    // 方块无法再向下移动，固定方块在游戏区域中
                    fixBlock()

                    // 检查并消除已满的行
                    checkAndRemoveLines()
                } else{
                    currentBlock.value.currentRow += 1
                }
            }
        }

        // 更新游戏区域
//        updateGameArea()
    }

    // 更新游戏区域状态
    private fun updateGameArea() {
        val newGameArea = gameArea.value
        val currentBlock = getCurrentBlock()

        // 将当前方块的位置更新到游戏区域
        for (row in 0 until currentBlock.size) {
            for (col in 0 until currentBlock[0].size) {
                if (currentBlock[row][col]) {
                    val newRow = this.currentBlock.value.currentRow + row
                    val newCol = this.currentBlock.value.currentCol + col

                    // 检查是否超出游戏区域范围，如果超出则忽略
                    if (newRow in 0 until gameAreaHeight && newCol in 0 until gameAreaWidth) {
                        newGameArea[newRow][newCol] = true
                    }
                }
            }
        }

        // 更新游戏区域数据
        gameArea.value = newGameArea
    }

    // 获取当前方块
    fun getCurrentBlock(): Array<BooleanArray> {
        // 返回当前方块的数据，根据实际游戏逻辑获取
        return currentBlock.value.shape
    }

    // 检查方块是否可以继续移动
    private fun isMoveValid(): Boolean {
        val currentBlock = getCurrentBlock()

        for (row in currentBlock.indices) {
            for (col in 0 until currentBlock[0].size) {
                if (currentBlock[row][col]) {
                    val newRow = this.currentBlock.value.currentRow + row
                    val newCol = this.currentBlock.value.currentCol + col

                    // 检查是否超出游戏区域范围
                    if (newRow >= gameAreaHeight || newCol < 0 || newCol >= gameAreaWidth) {
                        return false
                    }

                    // 检查是否与其他方块重叠
                    if (gameArea.value[newRow][newCol]) {
                        return false
                    }
                }
            }
        }

        return true
    }

    // 将方块固定在游戏区域中
    private fun fixBlock() {
        val currentBlock = getCurrentBlock()

        for (row in currentBlock.indices) {
            for (col in 0 until currentBlock[0].size) {
                if (currentBlock[row][col]) {
                    val newRow = this.currentBlock.value.currentRow + row
                    val newCol = this.currentBlock.value.currentCol + col

                    // 将方块的位置标记为已固定
                    gameArea.value[newRow][newCol] = true
                }
            }
        }

        // 方块已经固定，重置当前方块的位置
        isBlockFixed = true

        // 随机生成新的方块
        this.currentBlock.value = generateRandomBlock()

    }

    // 检查并消除行
    private fun checkAndRemoveLines() {
        val rowsToRemove = mutableListOf<Int>()

        // 遍历游戏区域的每一行
        for (row in 0 until gameAreaHeight) {
            var isFullRow = true

            // 检查当前行是否满格
            for (col in 0 until gameAreaWidth) {
                if (!gameArea.value[row][col]) {
                    isFullRow = false
                    break
                }
            }

            // 如果当前行是满格，加入待删除行的列表
            if (isFullRow) {
                rowsToRemove.add(row)
            }
        }

        // 删除满格的行并在顶部插入新的空行
        if (rowsToRemove.isNotEmpty()) {
            val newGameArea = createEmptyGameArea()

            // 将不需要删除的行复制到新的游戏区域
            var newRow = gameAreaHeight - 1
            for (row in gameAreaHeight - 1 downTo 0) {
                if (row !in rowsToRemove) {
                    newGameArea[newRow] = gameArea.value[row]
                    newRow -= 1
                }
            }

            // 在顶部插入新的空行
            for (i in 0 until rowsToRemove.size) {
                newGameArea[i] = BooleanArray(gameAreaWidth) { false }
            }

            // 更新游戏区域数据
            gameArea.value = newGameArea
        }
    }
}
