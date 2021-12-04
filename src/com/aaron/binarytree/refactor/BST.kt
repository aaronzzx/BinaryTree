package com.aaron.binarytree.refactor

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/3
 */
open class BST<E>(private var comparator: Comparator<E>? = null) : BinaryTree<E>(), MutableTree<E> {

    override var size: Int = 0
        protected set

    override fun add(element: E): Boolean {
        require(element != null) {
            "The element must not be null."
        }
        var node = root
        if (node == null) {
            node = createNode(element, null)
            root = node
            size++
            afterAdd(node)
            return true
        }
        var compare = 0
        var parent = node
        while (node != null) {
            parent = node
            compare = compare(element, node.item)
            if (compare < 0) {
                node = node.left
            } else if (compare > 0) {
                node = node.right
            } else {
                node.item = element
                return true
            }
        }
        val newNode = createNode(element, parent)
        if (compare < 0) {
            parent!!.left = newNode
        } else {
            parent!!.right = newNode
        }
        size++
        afterAdd(newNode)
        return true
    }

    protected open fun afterAdd(node: TreeNode<E>) = Unit

    override fun addAll(elements: Collection<E>): Boolean {
        elements.forEach {
            add(it)
        }
        return true
    }

    override fun clear() {
        root = null
        size = 0
    }

    override fun remove(element: E): Boolean {
        return remove(node(element))
    }

    private fun remove(node: TreeNode<E>?): Boolean {
        var _node = node ?: return false
        if (_node.hasTwoChildren) {
            val succ = successor(_node)!!
            _node.item = succ.item
            _node = succ
        }
        val replacement = _node.left ?: _node.right
        if (replacement != null) {
            replacement.parent = _node.parent
            if (_node.parent == null) {
                root = replacement
            } else if (_node.isLeftChild) {
                _node.parent!!.left = replacement
            } else {
                _node.parent!!.right = replacement
            }
            afterRemove(_node)
        } else if (_node.parent == null) {
            root = null
            afterRemove(_node)
        } else {
            if (_node.isLeftChild) {
                _node.parent!!.left = null
            } else {
                _node.parent!!.right = null
            }
            afterRemove(_node)
        }
        size--
        return true
    }

    private fun predecessor(node: TreeNode<E>): TreeNode<E>? {
        var _node: TreeNode<E>? = node
        if (node.left != null) {
            _node = node.left
            while (_node?.right != null) {
                _node = _node.right
            }
            return _node
        }
        while (_node?.parent != null && _node.isLeftChild) {
            _node = _node.parent
        }
        return _node?.parent
    }

    private fun successor(node: TreeNode<E>): TreeNode<E>? {
        var _node: TreeNode<E>? = node
        if (node.right != null) {
            _node = node.right
            while (_node?.left != null) {
                _node = _node.left
            }
            return _node
        }
        while (_node?.parent != null && _node.isRightChild) {
            _node = _node.parent
        }
        return _node?.parent
    }

    protected open fun afterRemove(node: TreeNode<E>) = Unit

    override fun removeAll(elements: Collection<E>): Boolean {
        var modified = false
        elements.forEach {
            modified = modified or remove(it)
        }
        return modified
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        var modified = false
        val itr = iterator()
        val needRemoved = arrayListOf<E>()
        while (itr.hasNext()) {
            val next = itr.next()
            if (!elements.contains(next)) {
                needRemoved.add(next)
                modified = true
            }
        }
        removeAll(needRemoved)
        return modified
    }

    override fun contains(element: E): Boolean {
        return node(element) != null
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return elements.all { contains(it) }
    }

    protected open fun node(element: E): TreeNode<E>? {
        var node: TreeNode<E>? = root
        while (node != null) {
            val cmp = compare(element, node.item)
            if (cmp < 0) {
                node = node.left
            } else if (cmp > 0) {
                node = node.right
            } else {
                return node
            }
        }
        return null
    }

    protected open fun compare(e1: E, e2: E): Int {
        val cmp = comparator
        if (cmp != null) {
            return cmp.compare(e1, e2)
        }
        return (e1 as Comparable<E>).compareTo(e2)
    }

    override fun iterator(): MutableIterator<E> {
        return inorder()
    }

    override fun preorder(): MutableIterator<E> {
        return ItrProxy(PreorderIterator(root))
    }

    override fun inorder(): MutableIterator<E> {
        return ItrProxy(InorderIterator(root))
    }

    override fun postorder(): MutableIterator<E> {
        return ItrProxy(PostorderIterator(root))
    }

    override fun levelOrder(): MutableIterator<E> {
        return ItrProxy(LevelOrderIterator(root))
    }

    protected open fun createNode(item: E, parent: TreeNode<E>?): TreeNode<E> {
        return TreeNode(item, parent)
    }

    private inner class ItrProxy(
        private val target: BaseIterator
    ) : MutableIterator<E> {

        override fun hasNext(): Boolean {
            return target.hasNext()
        }

        override fun next(): E {
            return target.next()
        }

        override fun remove() {
            throw UnsupportedOperationException()
        }
    }
}