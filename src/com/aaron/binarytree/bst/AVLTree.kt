package com.aaron.binarytree.bst

import kotlin.math.abs
import kotlin.math.max

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/3
 */
class AVLTree<E>(comparator: Comparator<E>? = null) : BST<E>(comparator) {

    override fun afterAdd(node: BaseNode<E>) {
        checkBalanceAndFix(node, true)
    }

    override fun afterRemove(node: BaseNode<E>) {
        checkBalanceAndFix(node, false)
    }

    private fun checkBalanceAndFix(node: BaseNode<E>, forAdd: Boolean) {
        var _node: AVLNode<E>? = node as AVLNode<E>
        while (_node != null) {
            if (_node.isBalanced) {
                _node.updateHeight()
            } else {
                rebalance(_node)
                if (forAdd) break
            }
            _node = _node.parent()
        }
    }

    private fun rebalance(grand: AVLNode<E>) {
        val son = grand.tallerChild!!
        val grandson = son.tallerChild!!
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

    private fun rotateLeft(grand: AVLNode<E>) {
        val son = grand.right()!!
        val grandson = son.left()
        grand.right = grandson
        son.left = grand
        afterRotate(grand, son, grandson)
    }

    private fun rotateRight(grand: AVLNode<E>) {
        val son = grand.left()!!
        val grandson = son.right()
        grand.left = grandson
        son.right = grand
        afterRotate(grand, son, grandson)
    }

    private fun afterRotate(grand: AVLNode<E>, son: AVLNode<E>, grandson: AVLNode<E>?) {
        son.parent = grand.parent()
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

    override fun createNode(item: E, parent: BaseNode<E>?): BaseNode<E> {
        return AVLNode(item, parent)
    }

    private class AVLNode<E>(item: E, parent: BaseNode<E>?) : BaseNode<E>(item, parent) {

        var height = 1
            private set

        val balanceFactor: Int
            get() = leftHeight - rightHeight

        val isBalanced: Boolean
            get() = abs(balanceFactor) <= 1

        val tallerChild: AVLNode<E>?
            get() {
                if (leftHeight > rightHeight) return cast(left)
                if (leftHeight < rightHeight) return cast(right)
                return if (isLeftChild) cast(left) else cast(right)
            }

        private val leftHeight: Int
            get() = cast(left)?.height ?: 0

        private val rightHeight: Int
            get() = cast(right)?.height ?: 0

        fun updateHeight() {
            height = 1 + max(leftHeight, rightHeight)
        }

        fun parent(): AVLNode<E>? {
            return cast(parent)
        }

        fun left(): AVLNode<E>? {
            return cast(left)
        }

        fun right(): AVLNode<E>? {
            return cast(right)
        }

        private fun cast(node: BaseNode<E>?): AVLNode<E>? {
            return node as? AVLNode<E>
        }
    }
}