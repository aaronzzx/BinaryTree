package com.aaron.binarytree.base

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/1
 */
interface IBinaryTree<E> : IBinaryTreeTraversal<E> {

    val size: Int

    fun isEmpty(): Boolean

    fun clear()

    fun height(): Int

    fun isFull(): Boolean

    fun isPerfect(): Boolean

    fun isComplete(): Boolean
}