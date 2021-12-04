package com.aaron.binarytree.refactor

import kotlin.math.abs
import kotlin.math.max

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/4
 */
class AVLTree<E>(comparator: Comparator<E>? = null) : BST<E>(comparator) {

    override fun afterAdd(node: TreeNode<E>) {
        checkBalanceAndFix(node, true)
    }

    override fun afterRemove(node: TreeNode<E>) {
        checkBalanceAndFix(node, false)
    }

    private fun checkBalanceAndFix(node: TreeNode<E>, forAdd: Boolean) {
        var avlNode = node as? AVLNode<E>
        while (avlNode != null) {
            if (avlNode.isBalanced) {
                avlNode.updateHeight()
            } else {
                rebalance(avlNode)
                if (forAdd) break
            }
            avlNode = avlNode.parent()
        }
    }

    private fun rebalance(grand: AVLNode<E>) {
        val papa = grand.tallerChild!!
        val son = papa.tallerChild!!
        if (papa.isLeftChild) {
            if (son.isLeftChild) {
                rotateRight(grand)
            } else {
                rotateLeft(papa)
                rotateRight(grand)
            }
        } else {
            if (son.isLeftChild) {
                rotateRight(papa)
                rotateLeft(grand)
            } else {
                rotateLeft(grand)
            }
        }
    }

    private fun rotateLeft(grand: AVLNode<E>) {
        val papa = grand.right()!!
        val son = papa.left()
        grand.right = son
        papa.left = grand
        afterRotate(grand, papa, son)
    }

    private fun rotateRight(grand: AVLNode<E>) {
        val papa = grand.left()!!
        val son = papa.right()
        grand.left = son
        papa.right = grand
        afterRotate(grand, papa, son)
    }

    private fun afterRotate(grand: AVLNode<E>, papa: AVLNode<E>, son: AVLNode<E>?) {
        papa.parent = grand.parent
        if (grand.isLeftChild) {
            grand.parent?.left = papa
        } else if (grand.isRightChild) {
            grand.parent?.right = papa
        } else {
            root = papa
        }

        son?.parent = grand
        grand.parent = papa

        grand.updateHeight()
        papa.updateHeight()
    }

    override fun createNode(item: E, parent: TreeNode<E>?): TreeNode<E> {
        return AVLNode(item, parent)
    }

    private class AVLNode<E>(item: E, parent: TreeNode<E>?) : TreeNode<E>(item, parent) {

        val balanceFactor: Int
            get() = leftHeight - rightHeight

        val isBalanced: Boolean
            get() = abs(balanceFactor) <= 1

        val tallerChild: AVLNode<E>?
            get() {
                if (leftHeight > rightHeight) return left()
                if (leftHeight < rightHeight) return right()
                return if (isLeftChild) left() else right()
            }

        var height = 1
            private set

        val leftHeight: Int
            get() = left()?.height ?: 0

        val rightHeight: Int
            get() = right()?.height ?: 0

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

        private fun cast(node: TreeNode<E>?): AVLNode<E>? {
            return node as? AVLNode<E>
        }
    }
}