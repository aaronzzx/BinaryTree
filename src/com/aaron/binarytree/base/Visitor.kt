package com.aaron.binarytree.base

/**
 * 二叉树遍历辅助类
 *
 * @author aaronzzxup@gmail.com
 * @since 2021/11/18
 */
abstract class Visitor<E> {

    /**
     * 停止遍历标记位，由二叉树内部控制，应对外部隐藏
     */
    internal var stop = false

    /**
     * 遍历回调
     *
     * @param item 元素
     * @return true 表示中止遍历，false 则继续
     */
    abstract fun visit(item: E): Boolean
}