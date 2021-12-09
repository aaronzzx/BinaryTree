package com.aaron.binarytree.refactor

import kotlin.math.ceil

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/8
 */
class BTree<E>(val m: Int, private val comparator: Comparator<E>? = null) : MutableTree<E> {

    internal var root: BNode<E>? = null

    override var size: Int = 0
        private set

    init {
        require(m >= 2) {
            "m < 2, m: $m"
        }
    }

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
        var root = root
        if (root == null) {
            root = BNode(element, null, null, null)
            this.root = root
            size++
            return true
        }
        var node = root
        var leaf = root

        while (node != null) {
            leaf = node
            val cmp = compare(element, node.item)
            if (cmp < 0) {
                node = node.left
            } else if (cmp > 0) {
                node = node.next ?: node.right
            } else {
                node.item = element
                return true
            }
        }
        linkNode(element, leaf!!)
        size++
        checkOverflow(leaf)
        return true
    }

    private fun linkNode(element: E, first: BNode<E>): E? {
        var node: BNode<E>? = first
        var cmp = 0
        while (node != null) {
            cmp = compare(element, node.item)
            if (cmp < 0) {
                break
            } else if (cmp > 0) {
                node = node.next ?: break
            } else {
                val oldVal = node.item
                node.item = element
                return oldVal
            }
        }
        node!!
        val next = node.next
        val newNode = BNode(element, node.parent, node, next)
        next?.prev = newNode
        node.next = newNode
        if (cmp < 0) {
            val oldVal = node.item
            newNode.item = oldVal
            node.item = element
        }
        return null
    }

    private fun checkOverflow(node: BNode<E>) {
        var _node: BNode<E>? = node.first
        val maxElement = m - 1
        var count = 0
        var medium: BNode<E>? = null
        val mediumIndex = ceil(m / 2f).toInt() - 1
        while (_node != null) {
            while (_node != null) {
                if (count == mediumIndex) {
                    medium = _node
                }
                _node = _node.next
                count++
            }
            if (count <= maxElement) {
                return
            }
            split(medium!!)
            _node = medium.first
            count = 0
        }
    }

    private fun split(node: BNode<E>) {
        val prev = node.prev
        val next = node.next
        val first = node.first
        if (first == node.parent?.left) {

        } else if (first == node.parent?.right) {
            node.prev = node.parent
            node.next = null
            node.parent?.next = node
            node.parent = node.parent?.parent

            if (node.left != null) {
                prev?.right = node.left
                node.left = null
                var _prev = node.left
                while (_prev != null) {
                    _prev.parent = prev
                    _prev = _prev.prev
                }
            }

            prev?.next = null
            next?.prev = null

            next?.parent = node
            node.right = next
            var _next = next
            while (_next != null) {
                _next.parent = node
                _next = _next.next
            }
        } else {
            root = node
            node.prev = null
            node.next = null
            prev?.right = node.left
            next?.left = node.right
            node.left = prev?.first
            node.right = next

            prev?.next = null
            next?.prev = null
            var _prev = prev
            while (_prev != null) {
                _prev.parent = node
                _prev = _prev.prev
            }
            var _next = next
            while (_next != null) {
                _next.parent = node
                _next = _next.next
            }
        }
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

    internal class BNode<E>(
        var item: E,
        var parent: BNode<E>?,
        var prev: BNode<E>?,
        var next: BNode<E>?
    ) {

        var left: BNode<E>? = null

        var right: BNode<E>? = null

        val isLeaf: Boolean
            get() = left == null && right == null

        val hasTwoChildren: Boolean
            get() = left != null && right != null

        val isLeftChild: Boolean
            get() = first == parent?.left

        val isRightChild: Boolean
            get() = first == parent?.right

        val first: BNode<E>
            get() {
                val prev = prev ?: return this
                var node = prev
                while (node.prev != null) {
                    node = node.prev!!
                }
                return node
            }

        val last: BNode<E>
            get() {
                val next = next ?: return this
                var node = next
                while (node.next != null) {
                    node = node.next!!
                }
                return node
            }
    }
}