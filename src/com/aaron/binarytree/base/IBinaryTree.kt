package com.aaron.binarytree.base

/**
 * 二叉树基础接口
 *
 * @author aaronzzxup@gmail.com
 * @since 2021/12/1
 */
interface IBinaryTree<E> : IBinaryTreeTraversal<E> {

    /**
     * 总节点数量
     */
    val size: Int

    /**
     * 是否空树
     */
    fun isEmpty(): Boolean

    /**
     * 是否包含元素
     *
     * @return true 如果包含，false 不包含
     */
    fun contains(item: E): Boolean

    /**
     * 清空整棵树
     */
    fun clear()

    /**
     * 获取树的高度
     */
    fun height(): Int

    /**
     * 是否满二叉树（国内叫真二叉树）
     */
    fun isFull(): Boolean

    /**
     * 是否完美二叉树（国内叫满二叉树）
     */
    fun isPerfect(): Boolean

    /**
     * 是否完全二叉树
     */
    fun isComplete(): Boolean
}