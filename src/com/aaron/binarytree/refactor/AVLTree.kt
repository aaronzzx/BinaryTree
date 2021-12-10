package com.aaron.binarytree.refactor

import kotlin.math.abs
import kotlin.math.max

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/4
 */
class AVLTree<E>(comparator: Comparator<E>? = null) : BBST<E>(comparator) {

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

    private fun rebalance2(grandparent: AVLNode<E>) {
        val parent = grandparent.tallerChild!!
        val child = parent.tallerChild!!
        if (parent.isLeftChild) {
            if (child.isLeftChild) {
                rotate(grandparent, child.left(), child, child.right(), parent, parent.right(), grandparent, grandparent.right())
            } else {
                rotate(grandparent, parent.left(), parent, child.left(), child, child.right(), grandparent, grandparent.right())
            }
        } else {
            if (child.isLeftChild) {
                rotate(grandparent, grandparent.left(), grandparent, child.left(), child, child.right(), parent, parent.right())
            } else {
                rotate(grandparent, grandparent.left(), grandparent, parent.left(), parent, child.left(), child, child.right())
            }
        }
    }

    private fun rotate(
        root: AVLNode<E>,
        a: AVLNode<E>?, b: AVLNode<E>, c: AVLNode<E>?,
        d: AVLNode<E>,
        e: AVLNode<E>?, f: AVLNode<E>, g: AVLNode<E>?
    ) {
        d.parent = root.parent
        if (root.isLeftChild) {
            root.parent?.left = d
        } else if (root.isRightChild) {
            root.parent?.right = d
        } else {
            this.root = d
        }

        a?.parent = b
        c?.parent = b
        b.left = a
        b.right = c
        b.updateHeight()

        e?.parent = f
        g?.parent = f
        f.left = e
        f.right = g
        f.updateHeight()

        b.parent = d
        f.parent = d
        d.left = b
        d.right = f
        d.updateHeight()
    }

    private fun rebalance(grandparent: AVLNode<E>) {
        val parent = grandparent.tallerChild!!
        val child = parent.tallerChild!!
        if (parent.isLeftChild) {
            if (child.isLeftChild) {
                rotateRight(grandparent)
            } else {
                rotateLeft(parent)
                rotateRight(grandparent)
            }
        } else {
            if (child.isLeftChild) {
                rotateRight(parent)
                rotateLeft(grandparent)
            } else {
                rotateLeft(grandparent)
            }
        }
    }

    override fun afterRotate(grandparent: TreeNode<E>?, parent: TreeNode<E>?, child: TreeNode<E>?) {
        super.afterRotate(grandparent, parent, child)
        (grandparent as AVLNode<E>).updateHeight()
        (parent as AVLNode<E>).updateHeight()
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