package com.aaron.binarytree.base

/**
 * 二叉树遍历接口
 *
 * @author aaronzzxup@gmail.com
 * @since 2021/11/18
 */
interface IBinaryTreeTraversal<E> {

    /**
     * 前序遍历
     */
    fun preorderTraversal(visitor: Visitor<E>)

    /**
     * 中序遍历
     */
    fun inorderTraversal(visitor: Visitor<E>)

    /**
     * 后序遍历
     */
    fun postorderTraversal(visitor: Visitor<E>)

    /**
     * 层序遍历
     */
    fun levelOrderTraversal(visitor: Visitor<E>)
}