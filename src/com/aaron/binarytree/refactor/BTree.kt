package com.aaron.binarytree.refactor

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/8
 */
class BTree<E>(private val comparator: Comparator<E>?) : MutableTree<E> {

    private var root: BNode<E>? = null

    override var size: Int = 0
        private set

    override fun contains(element: E): Boolean {
        return node(element) != null
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return elements.all { contains(it) }
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun height(): Int {
        TODO("Not implemented!")
    }

    override fun add(element: E): Boolean {
        TODO("Not implemented!")
    }

    override fun addAll(elements: Collection<E>): Boolean {
        TODO("Not implemented!")
    }

    override fun clear() {
        root = null
        size = 0
    }

    override fun remove(element: E): Boolean {
        TODO("Not implemented!")
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        TODO("Not implemented!")
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        TODO("Not implemented!")
    }

    private fun node(item: E): BNode<E>? {
        var node = root
        while (node != null) {
            val cmp = compare(item, node.item)
            if (cmp < 0) {
                node = node.left
            } else if (cmp > 0) {
                if (node.next != null && compare(item, node.next!!.item) >= 0) {
                    node = node.next
                } else {
                    node = node.right
                }
            } else {
                return node
            }
        }
        return null
    }

    private fun compare(e1: E, e2: E): Int {
        val cmp = comparator
        if (cmp != null) {
            return cmp.compare(e1, e2)
        }
        return (e1 as Comparable<E>).compareTo(e2)
    }

    override fun iterator(): MutableIterator<E> {
        TODO("Not implemented!")
    }

    override fun preorder(): MutableIterator<E> {
        TODO("Not implemented!")
    }

    override fun inorder(): MutableIterator<E> {
        TODO("Not implemented!")
    }

    override fun postorder(): MutableIterator<E> {
        TODO("Not implemented!")
    }

    override fun levelOrder(): MutableIterator<E> {
        TODO("Not implemented!")
    }

    private class BNode<E>(var item: E, var parent: BNode<E>?) {

        var next: BNode<E>? = null

        var left: BNode<E>? = null

        var right: BNode<E>? = null

        val isLeaf: Boolean
            get() = left == null && right == null

        val hasTwoChildren: Boolean
            get() = left != null && right != null

        val isLeftChild: Boolean
            get() = this == parent?.left

        val isRightChild: Boolean
            get() = this == parent?.right
    }
}