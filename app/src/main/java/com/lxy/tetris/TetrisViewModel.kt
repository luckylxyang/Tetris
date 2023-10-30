package com.lxy.tetris

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lxy.tetris.entity.TetrisBlock
import com.lxy.tetris.entity.TetrisBlocks
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TetrisViewModel : ViewModel() {

    private val gameAreaWidth = 10
    private val gameAreaHeight = 20

    val uiState = mutableStateOf(false)

    // 当前方块
    val currentTetromino = MutableStateFlow(TetrisBlocks.getRandomTetrisBlocks())
    val currentTetrisArea = MutableStateFlow(emptyArray<BooleanArray>())

    val currentCol = mutableStateOf(0)
    val currentRow = mutableStateOf(0)

    // 当前方块

    // 游戏区域
    val gameArea = MutableStateFlow(emptyArray<BooleanArray>())

    // 分数
    val score = mutableStateOf(0)
    // 消除的行数
    private val lines = mutableStateOf(0)

    // 方块下落的时间间隔（毫秒）
    private val dropInterval = 500L

    // 暂停游戏
    private var isPause = false

    private lateinit var dropJob : Job

    // 音效控制
    val soundState = mutableStateOf(true)

    // 开始游戏
    fun startGame() {
        // 初始化游戏状态，生成新的方块等
        gameArea.value = createEmptyGameArea()
        currentCol.value = gameAreaWidth / 2
        currentRow.value = 0
        // 生成新的方块
        currentTetromino.value = generateRandomBlock()
        // 初始化分数
        score.value = 0

        uiState.value = true
        this.isPause = false
        startDropTimer()
    }


    // 旋转方块
    fun rotateBlock() {
        // 处理方块的旋转逻辑
        printArrays( currentTetrisArea.value)
        currentTetrisArea.value = currentTetromino.value.rotateBlock()
        printArrays( currentTetrisArea.value)
        updatePosition()
        this.isPause = false
    }

    fun printArrays(arrays: Array<BooleanArray>){
        arrays.forEach {
            it.forEach {
                print("$it，")
            }
            println()
        }
    }

    fun leftMoveBlock() {
        this.isPause = false
        moveBlock(Direction.Left)
    }

    fun rightMoveBlock() {
        this.isPause = false
        moveBlock(Direction.Right)
    }

    fun quickDropBlock() {
        this.isPause = false
        moveBlock(Direction.Down)
    }

    fun pauseGame(){
        this.isPause = !isPause
    }

    fun restartGame(){
        dropJob.cancel()
        startGame()
    }

    fun soundControl(){
        soundState.value = !soundState.value
    }


    private fun createEmptyGameArea(): Array<BooleanArray> {
        return Array(gameAreaHeight) {
            BooleanArray(gameAreaWidth) { false }
        }
    }

    // 生成随机方块
    private fun generateRandomBlock(): TetrisBlock {

        currentCol.value = gameAreaWidth / 2
        currentRow.value = 0

        val block = TetrisBlocks.getRandomTetrisBlocks()
        this.currentTetrisArea.value = block.shape
//        block.row = 0
//        block.col = gameAreaWidth / 2
        return block
    }

    // 启动方块下落的定时器
    private fun startDropTimer() {
        dropJob = viewModelScope.launch {
            while (isActive) {
                delay(dropInterval)
                if (!isPause) {
//                    moveBlock(Direction.Down)
                    if (!isMoveValid(Direction.Down)) {
                        // 方块无法再向下移动，固定方块在游戏区域中
                        fixBlock()

                        // 检查并消除已满的行
                        checkAndRemoveLines()
                    } else {
                        currentRow.value += 1
                        updatePosition()
                    }
                }
            }
        }
    }

    // 移动方块
    private fun moveBlock(direction: Direction) {
        // 根据方向更新方块的位置
        when (direction) {
            Direction.Left -> {
                if (isMoveValid(direction)) {
                    currentCol.value -= 1
                }
            }

            Direction.Right -> {
                if (isMoveValid(direction)) {
                    currentCol.value += 1
                }
            }

            Direction.Up -> {

            }

            Direction.Down -> {
                if (isMoveValid(direction)) {
                    currentRow.value += 1
                }
            }
        }

        updatePosition()

    }

    private fun updatePosition() {

//        currentCol.value = currentTetromino.value.col
//        currentRow.value = currentTetromino.value.row
    }


    // 获取当前方块
    private fun getCurrentBlock(): Array<BooleanArray> {
        // 返回当前方块的数据，根据实际游戏逻辑获取
        return currentTetromino.value.shape
    }

    // 检查方块是否可以继续移动
    private fun isMoveValid(direction: Direction): Boolean {
        val currentBlock = getCurrentBlock()

        for (row in currentBlock.indices) {
            for (col in 0 until currentBlock[0].size) {
                if (currentBlock[row][col]) {
                    var newRow = currentRow.value + row
                    var newCol = currentCol.value + col
                    when(direction){
                        Direction.Left -> {
                            newCol -= 1
                        }
                        Direction.Right ->{
                            newCol += 1
                        }
                        Direction.Down ->{
                            newRow += 1
                        }
                        else -> {

                        }
                    }


                    // 检查是否到顶。游戏结束

                    // 检查是否超出游戏区域范围
                    if (newRow >= gameAreaHeight || newCol < 0 || newCol >= gameAreaWidth) {
                        Log.d("TAG1", "isMoveValid: " + newCol)
                        return false
                    }

                    // 检查是否与其他方块重叠
                    if (gameArea.value[newRow][newCol]) {
                        Log.d("TAG2", "isMoveValid: " + newCol)
                        if (newRow <= 1) {
                            isPause = true
                        }
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
                    val newRow = currentRow.value + row
                    val newCol = currentCol.value + col

                    // 将方块的位置标记为已固定
                    gameArea.value[newRow][newCol] = true
                }
            }
        }

        // 随机生成新的方块
        this.currentTetromino.value = generateRandomBlock()

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

            lines.value += rowsToRemove.size
            score.value += rowsToRemove.size
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
