package com.aaron.binarytree.bst

import kotlin.math.abs
import kotlin.math.max

/**
 * AVL树
 *
 * @author aaronzzxup@gmail.com
 * @since 2021/12/2
 */
class AVLTree<E> : BST<E> {

    constructor() : this(null)

    constructor(comparator: Comparator<E>?) : super(comparator)

    override fun afterAdd(node: BaseNode<E>) {
        var _node: BaseNode<E>? = node
        while (_node != null) {
            val avl = castNonNull(_node)
            if (avl.isBalanced) {
                avl.updateHeight()
            } else {
                rebalance(avl)
                break
            }
            _node = _node.parent
        }
    }

    private fun rebalance(grand: AVLNode<E>) {
        val son = grand.tallerChild()!!
        val grandson = son.tallerChild()!!
        if (son.isLeftChild) {
            if (grandson.isLeftChild) {
                // LL
                rotateRight(grand)
            } else {
                // LR
                rotateLeft(son)
                rotateRight(grand)
            }
        } else {
            if (grandson.isLeftChild) {
                // RL
                rotateRight(son)
                rotateLeft(grand)
            } else {
                // RR
                rotateLeft(grand)
            }
        }
    }

    private fun rotateLeft(node: AVLNode<E>) {
        val child = cast(node.right)!!
        val childLeft = cast(child.left)

        node.right = childLeft
        child.left = node

        afterRotate(node, child, childLeft)
    }

    private fun rotateRight(node: AVLNode<E>) {
        val child = cast(node.left)!!
        val childRight = cast(child.right)

        node.left = childRight
        child.right = node

        afterRotate(node, child, childRight)
    }

    private fun afterRotate(grand: AVLNode<E>, son: AVLNode<E>, grandson: AVLNode<E>?) {
        son.parent = grand.parent
        if (grand.isLeftChild) {
            grand.parent?.left = son
        } else if (grand.isRightChild) {
            grand.parent?.right = son
        } else {
            root = son
        }

        grandson?.parent = grand
        grand.parent = son

        grand.updateHeight()
        son.updateHeight()
    }

    override fun afterRemove(node: BaseNode<E>) {
        var _node: BaseNode<E>? = node
        while (_node != null) {
            val avl = castNonNull(_node)
            if (avl.isBalanced) {
                avl.updateHeight()
            } else {
                rebalance(avl)
            }
            _node = _node.parent
        }
    }

    override fun createNode(item: E, parent: BaseNode<E>?): BaseNode<E> {
        return AVLNode(item, parent)
    }

    private fun castNonNull(node: BaseNode<E>): AVLNode<E> {
        return cast(node)!!
    }

    private fun cast(node: BaseNode<E>?): AVLNode<E>? {
        return node as? AVLNode<E>
    }

    private class AVLNode<E>(item: E, parent: BaseNode<E>?) : BaseNode<E>(item, parent) {

        var height = 1

        val isBalanced: Boolean
            get() = abs(balanceFactor) <= 1

        val balanceFactor: Int
            get() = (cast(left)?.height ?: 0) - (cast(right)?.height ?: 0)

        private val leftHeight: Int
            get() = cast(left)?.height ?: 0

        private val rightHeight: Int
            get() = cast(right)?.height ?: 0

        fun updateHeight() {
            height = 1 + max(leftHeight, rightHeight)
        }

        fun tallerChild(): AVLNode<E>? {
            if (leftHeight > rightHeight) return cast(left)
            if (leftHeight < rightHeight) return cast(right)
            return if (isLeftChild) cast(left) else cast(right)
        }

        private fun cast(node: BaseNode<E>?): AVLNode<E>? {
            return node as? AVLNode<E>
        }
    }
}