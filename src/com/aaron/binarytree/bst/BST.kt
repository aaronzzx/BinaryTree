package com.aaron.binarytree.bst

import com.aaron.binarytree.base.BinaryTree

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/1
 */
open class BST<E> : BinaryTree<E>, IBinarySearchTree<E> {

    private var comparator: Comparator<E>? = null

    override var root: ITreeNode<E>? = null

    constructor() : this(null)

    constructor(comparator: Comparator<E>?) {
        this.comparator = comparator
    }

    override fun add(item: E): Boolean {
        var node: ITreeNode<E>? = root
        if (node == null) {
            node = createNode(item, null)
            root = node
            size++
            return true
        }
        var cmp = 0
        var parent = node
        while (node != null) {
            parent = node
            cmp = compare(item, node.item)
            if (cmp < 0) {
                node = node.left
            } else if (cmp > 0) {
                node = node.right
            } else {
                node.item = item
                return true
            }
        }
        val newNode = createNode(item, parent)
        if (cmp < 0) {
            parent?.left = newNode
        } else {
            parent?.right = newNode
        }
        size++
        return true
    }

    override fun remove(item: E): Boolean {
        return remove(node(item))
    }

    private fun remove(node: ITreeNode<E>?): Boolean {
        var _node = node ?: return false
        if (_node.hasTwoChildren) {
            val succ = successor(_node)!!
            _node.item = succ.item
            _node = succ
        }
        val replacement = _node.left ?: _node.right
        if (replacement != null) {
            val _nodeParent = _node.parent
            replacement.parent = _nodeParent
            if (_nodeParent == null) {
                root = replacement
            } else if (_node == _nodeParent.left) {
                _node.parent?.left = replacement
            } else {
                _node.parent?.right = replacement
            }
        } else if (_node.parent == null) {
            root = null
        } else {
            if (_node == _node.parent?.left) {
                _node.parent?.left = null
            } else {
                _node.parent?.right = null
            }
        }
        size--
        return true
    }

    override fun contains(item: E): Boolean {
        return node(item) != null
    }

    override fun predecessorOf(item: E): E? {
        return predecessor(node(item))?.item
    }

    private fun predecessor(node: ITreeNode<E>?): ITreeNode<E>? {
        var _node: ITreeNode<E>? = node ?: return null
        if (_node?.left != null) {
            _node = _node.left
            while (_node?.right != null) {
                _node = _node.right
            }
            return _node
        }
        while (_node?.parent != null && _node != _node.parent?.right) {
            _node = _node.parent
        }
        return _node?.parent
    }

    override fun successorOf(item: E): E? {
        return successor(node(item))?.item
    }

    private fun successor(node: ITreeNode<E>?): ITreeNode<E>? {
        var _node: ITreeNode<E>? = node ?: return null
        if (_node?.right != null) {
            _node = _node.right
            while (_node?.left != null) {
                _node = _node.left
            }
            return _node
        }
        while (_node?.parent != null && _node != _node.parent?.left) {
            _node = _node.parent
        }
        return _node?.parent
    }

    private fun node(item: E): ITreeNode<E>? {
        var node = root
        while (node != null) {
            val cmp = compare(item, node.item)
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

    private fun compare(e1: E, e2: E): Int {
        val cmp = comparator
        if (cmp != null) {
            return cmp.compare(e1, e2)
        }
        return (e1 as Comparable<E>).compareTo(e2)
    }
}