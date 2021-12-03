package com.aaron.binarytree.refactor

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/3
 */
abstract class AbstractTree<E> : Tree<E> {

    override fun contains(element: E): Boolean {
        return any { it == element }
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return elements.all { contains(it) }
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }
}