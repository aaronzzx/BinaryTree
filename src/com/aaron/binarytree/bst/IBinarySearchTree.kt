package com.aaron.binarytree.bst

import com.aaron.binarytree.base.IBinaryTree

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/11/29
 */
interface IBinarySearchTree<E> : IBinaryTree<E> {

    fun add(item: E): Boolean

    fun remove(item: E): Boolean

    fun contains(item: E): Boolean

    fun predecessorOf(item: E): E?

    fun successorOf(item: E): E?
}