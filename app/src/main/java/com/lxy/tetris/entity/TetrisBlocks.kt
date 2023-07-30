package com.lxy.tetris.entity

import kotlin.random.Random

class J() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = arrayOf(
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, true, false),
            booleanArrayOf(true, true, false)
        )
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        rotations++;
        if (rotations > 4) {
            rotations = 1;
        }
        when (rotations) {
            1 -> {
                shape = arrayOf(
                    booleanArrayOf(true, false, false),
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(false, false, false)
                )
            }

            2 -> {
                shape = arrayOf(
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(false, false, true),
                    booleanArrayOf(false, false, false)
                )
            }

            3 -> {
                shape = arrayOf(
                    booleanArrayOf(false, true, true),
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(false, true, false)
                )
            }

            4 -> {
                shape = arrayOf(
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(false, false, true),
                    booleanArrayOf(false, false, false)
                )
            }
        }
        return shape
    }
}

class O() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = arrayOf(
            booleanArrayOf(true, true, false),
            booleanArrayOf(true, true, false),
            booleanArrayOf(false, false, false)
        )
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        return shape
    }
}

class I() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = arrayOf(
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, true, false, false),
            booleanArrayOf(false, true, false, false)
        )
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        rotations++;
        if (rotations > 2) {
            rotations = 1;
        }
        when (rotations) {
            1 -> {
                shape = arrayOf(
                    booleanArrayOf(false, true, false, false),
                    booleanArrayOf(false, true, false, false),
                    booleanArrayOf(false, true, false, false),
                    booleanArrayOf(false, true, false, false)
                )
                row -= 1
                col += 1
            }

            else -> {
                shape = arrayOf(
                    booleanArrayOf(false, false, false, false),
                    booleanArrayOf(true, true, true, true),
                    booleanArrayOf(false, false, false, false),
                    booleanArrayOf(false, false, false, false)
                )
                row += 1
                col -= 1
            }
        }
        return shape
    }
}

class L() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = arrayOf(
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, true, true),
        )
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        rotations++;
        if (rotations > 2) {
            rotations = 1;
        }
        when (rotations) {
            1 -> {
                shape = arrayOf(
                    booleanArrayOf(false, false, false),
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(true, false, false),
                )
                row += 1
                col -= 1
            }
            2 -> {
                shape = arrayOf(
                    booleanArrayOf(false, false, false),
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(true, false, false),
                )
                row += 1
                col -= 1
            }

            3 -> {
                shape = arrayOf(
                    booleanArrayOf(true, true, false),
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(false, true, false),
                )
                row += 1
                col -= 1
            }
        }
        return shape
    }
}

class S() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = arrayOf(
            booleanArrayOf(false, true, true),
            booleanArrayOf(true, true, false),
            booleanArrayOf(false, false, false),
        )
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        rotations++;
        if (rotations > 2) {
            rotations = 1;
        }
        when (rotations) {
            1 -> {
                shape = arrayOf(
                    booleanArrayOf(false, true, true),
                    booleanArrayOf(true, true, false),
                    booleanArrayOf(false, false, false),
                )
                row += 1
                col -= 1
            }

            else -> {
                shape = arrayOf(
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(false, true, true),
                    booleanArrayOf(false, false, true),
                )
                row -= 1
                col += 1
            }
        }
        return shape
    }
}

class Z() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = arrayOf(
            booleanArrayOf(true, true, false),
            booleanArrayOf(false, true, true),
            booleanArrayOf(false, false, false),
        )
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        rotations++;
        if (rotations > 2) {
            rotations = 1;
        }
        when (rotations) {
            1 -> {
                shape = arrayOf(
                    booleanArrayOf(true, true, false),
                    booleanArrayOf(false, true, true),
                    booleanArrayOf(false, false, false),
                )
                col -= 1
            }

            else -> {
                shape = arrayOf(
                    booleanArrayOf(false, false, true),
                    booleanArrayOf(false, true, true),
                    booleanArrayOf(false, true, false),
                )
                col += 1
            }
        }
        return shape
    }
}

class T() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = arrayOf(
            booleanArrayOf(false, true, false),
            booleanArrayOf(true, true, false),
            booleanArrayOf(false, true, false),
        )
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        rotations++;
        if (rotations > 2) {
            rotations = 1;
        }
        when (rotations) {
            1 -> {
                shape = arrayOf(
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(true, true, false),
                    booleanArrayOf(false, true, false),
                )
            }

            else -> {
                shape = arrayOf(
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(true, true, false),
                    booleanArrayOf(false, true, false),
                )
            }
        }
        return shape
    }
}

// 添加更多的方块类型...
object TetrisBlocks {
    private val allTetrisBlocks = listOf(
        I(),
        J(),
        L(),
        S(),
        Z(),
        T(),
        O() /*...*/
    )

    // 随机生成一个方块
    fun getRandomTetrisBlocks(): TetrisBlock {
        val randomIndex = Random.nextInt(allTetrisBlocks.size)
        return allTetrisBlocks[randomIndex]
    }

}