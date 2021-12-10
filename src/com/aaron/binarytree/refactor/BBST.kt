package com.aaron.binarytree.refactor

/**
 * @author DS-Z
 * @since 2021/12/10
 */
abstract class BBST<E>(comparator: Comparator<E>? = null) : BST<E>(comparator) {

    protected open fun rotateLeft(grandparent: TreeNode<E>?) {
        grandparent ?: return
        val parent = grandparent.right!!
        val child = parent.left
        grandparent.right = child
        parent.left = grandparent
        afterRotate(grandparent, parent, child)
    }

    protected open fun rotateRight(grandparent: TreeNode<E>?) {
        grandparent ?: return
        val parent = grandparent.left!!
        val child = parent.right
        grandparent.left = child
        parent.right = grandparent
        afterRotate(grandparent, parent, child)
    }

    protected open fun afterRotate(grandparent: TreeNode<E>?, parent: TreeNode<E>?, child: TreeNode<E>?) {
        grandparent ?: return
        parent ?: return
        parent.parent = grandparent.parent
        if (grandparent.isLeftChild) {
            grandparent.parent?.left = parent
        } else if (grandparent.isRightChild) {
            grandparent.parent?.right = parent
        } else {
            root = parent
        }

        child?.parent = grandparent
        grandparent.parent = parent
    }
}