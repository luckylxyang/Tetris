package com.lxy.tetris.entity

abstract class TetrisBlock {


    // 返回当前方块的数据，根据实际游戏逻辑获取
    abstract fun getBlock() : Array<BooleanArray>

    public fun rotateBlock(){

    }
}