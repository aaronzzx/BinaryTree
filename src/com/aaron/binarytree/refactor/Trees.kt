package com.aaron.binarytree.refactor

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/3
 */

interface Tree<E> : Collection<E> {

    override val size: Int

    override fun contains(element: E): Boolean

    override fun containsAll(elements: Collection<E>): Boolean

    override fun isEmpty(): Boolean

    override fun iterator(): Iterator<E>

    /**
     * 前序遍历
     */
    fun preorder(): Iterator<E>

    /**
     * 中序遍历
     */
    fun inorder(): Iterator<E>

    /**
     * 后序遍历
     */
    fun postorder(): Iterator<E>

    /**
     * 层序遍历
     */
    fun levelOrder(): Iterator<E>

    /**
     * 获取树的高度
     */
    fun height(): Int
}

interface MutableTree<E> : Tree<E>, MutableCollection<E> {

    override fun add(element: E): Boolean

    override fun addAll(elements: Collection<E>): Boolean

    override fun clear()

    override fun remove(element: E): Boolean

    override fun removeAll(elements: Collection<E>): Boolean

    override fun retainAll(elements: Collection<E>): Boolean

    override fun iterator(): MutableIterator<E>

    override fun preorder(): MutableIterator<E>

    override fun inorder(): MutableIterator<E>

    override fun postorder(): MutableIterator<E>

    override fun levelOrder(): MutableIterator<E>
}