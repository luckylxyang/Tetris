package com.lxy.tetris.entity

import kotlin.random.Random

class J() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = rotateBlock()
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        println("J's rotateBlock()")
        rotations++;
        if (rotations > 3) {
            rotations = 0
        }
        when (rotations) {
            1 -> {
                return arrayOf(
                    booleanArrayOf(true, false, false),
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(false, false, false)
                )
            }

            2 -> {
                return arrayOf(
                    booleanArrayOf(false, true, true),
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(false, true, false)
                )
            }

            3 -> {
                return arrayOf(
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(false, false, true),
                    booleanArrayOf(false, false, false)
                )
            }

            else -> {
                return arrayOf(
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(true, true, false)
                )
            }
        }

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
        println("O's rotateBlock()")
        return shape
    }
}

class I() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = rotateBlock()
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        println("I's rotateBlock()")
        rotations++
        if (rotations > 1) {
            rotations = 0
        }
        when (rotations) {
            1 -> {
                return arrayOf(
                    booleanArrayOf(false, false, false, false),
                    booleanArrayOf(true, true, true, true),
                    booleanArrayOf(false, false, false, false),
                    booleanArrayOf(false, false, false, false)
                )
            }

            else -> {
                return arrayOf(
                    booleanArrayOf(false, true, false, false),
                    booleanArrayOf(false, true, false, false),
                    booleanArrayOf(false, true, false, false),
                    booleanArrayOf(false, true, false, false)
                )
            }
        }
    }
}

class L() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = rotateBlock()
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        println("L's rotateBlock()")
        rotations++
        if (rotations > 2) {
            rotations = 0
        }
        when (rotations) {
            1 -> {
                return arrayOf(
                    booleanArrayOf(false, false, false),
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(true, false, false),
                )
            }
            2 -> {
                return arrayOf(
                    booleanArrayOf(true, true, false),
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(false, true, false),
                )
            }

            else -> {
                return arrayOf(
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(false, true, true),
                )
            }
        }
    }
}

class S() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = rotateBlock()
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        println("S's rotateBlock()")
        rotations++;
        if (rotations > 1) {
            rotations = 0
        }
        when (rotations) {
            1 -> {
                return arrayOf(
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(false, true, true),
                    booleanArrayOf(false, false, true),
                )
            }

            else -> {
                return arrayOf(
                    booleanArrayOf(false, true, true),
                    booleanArrayOf(true, true, false),
                    booleanArrayOf(false, false, false),
                )
            }
        }

    }
}

class Z() : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = rotateBlock()
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        println("Z's rotateBlock()")
        rotations++;
        if (rotations > 1) {
            rotations = 0
        }
        when (rotations) {
            0 -> {
                return arrayOf(
                    booleanArrayOf(true, true, false),
                    booleanArrayOf(false, true, true),
                    booleanArrayOf(false, false, false),
                )
            }

            else -> {
                return arrayOf(
                    booleanArrayOf(false, false, true),
                    booleanArrayOf(false, true, true),
                    booleanArrayOf(false, true, false),
                )
            }
        }

    }
}

class T : TetrisBlock() {
    override var shape: Array<BooleanArray>
        get() = rotateBlock()
        set(value) {}

    override fun rotateBlock(): Array<BooleanArray> {
        println("T's rotateBlock()")
        rotations++;
        if (rotations > 3) {
            rotations = 0
        }
        when (rotations) {
            1 -> {
                return arrayOf(
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(false, false, false),
                )
            }
            2 -> {
                return arrayOf(
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(false, true, true),
                    booleanArrayOf(false, true, false),
                )
            }
            3 -> {
                return arrayOf(
                    booleanArrayOf(false, false, false),
                    booleanArrayOf(true, true, true),
                    booleanArrayOf(false, true, false),
                )
            }

            else -> {
                return arrayOf(
                    booleanArrayOf(false, true, false),
                    booleanArrayOf(true, true, false),
                    booleanArrayOf(false, true, false),
                )
            }
        }

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
        O()
    )

    // 随机生成一个方块
    fun getRandomTetrisBlocks(): TetrisBlock {
        val randomIndex = Random.nextInt(allTetrisBlocks.size)
        val tetrisBlock = allTetrisBlocks[randomIndex]
        println("It's getRandomTetrisBlocks is ${tetrisBlock.javaClass.name}")
        return tetrisBlock
    }

}