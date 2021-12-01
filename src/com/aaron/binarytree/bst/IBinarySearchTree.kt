package com.aaron.binarytree.bst

import com.aaron.binarytree.base.IBinaryTree

/**
 * 二叉搜索树基础接口
 *
 * @author aaronzzxup@gmail.com
 * @since 2021/11/29
 */
interface IBinarySearchTree<E> : IBinaryTree<E> {

    /**
     * 往树添加元素
     *
     * @return true 如果添加成功，false 则失败
     */
    fun add(item: E): Boolean

    /**
     * 从树中移除元素
     *
     * @return true 如果移除成功，false 则失败
     */
    fun remove(item: E): Boolean

    /**
     * 是否包含元素
     *
     * @return true 如果包含，false 不包含
     */
    fun contains(item: E): Boolean

    /**
     * 根据指定元素寻找前驱
     */
    fun predecessorOf(item: E): E?

    /**
     * 根据指定元素寻找后继
     */
    fun successorOf(item: E): E?
}