package com.aaron.binarytree.base

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/11/18
 */
interface IBinaryTreeTraversal<E> {

    fun preorderTraversal(visitor: Visitor<E>)

    fun inorderTraversal(visitor: Visitor<E>)

    fun postorderTraversal(visitor: Visitor<E>)

    fun levelOrderTraversal(visitor: Visitor<E>)
}