package com.aaron.binarytree.refactor

/**
 * @author DS-Z
 * @since 2021/12/10
 */
class RBTree<E>(comparator: Comparator<E>? = null) : BBST<E>(comparator) {

    companion object {
        private const val BLACK = false
        private const val RED = true

        private fun <E> TreeNode<E>?.isRed(): Boolean {
            val node = toRBNode() ?: return false
            return node.color == RED
        }

        private fun <E> TreeNode<E>?.isBlack(): Boolean {
            val node = toRBNode() ?: return true
            return node.color == BLACK
        }

        private fun <E> TreeNode<E>?.red(): TreeNode<E>? {
            toRBNode()?.color = RED
            return this
        }

        private fun <E> TreeNode<E>?.black(): TreeNode<E>? {
            toRBNode()?.color = BLACK
            return this
        }

        private fun <E> TreeNode<E>?.toRBNode(): RBNode<E>? {
            return this as? RBNode<E>
        }
    }

    override fun afterAdd(node: TreeNode<E>) {
        val parent = node.parent

        if (parent == null) {
            node.black()
            return
        }

        if (parent.isBlack()) return

        val grandparent = node.grandparent.red()
        val uncle = node.uncle

        if (uncle.isRed()) {
            parent.black()
            uncle.black()
            afterAdd(grandparent!!)
            return
        }

        if (parent.isLeftChild) {
            if (node.isLeftChild) {
                parent.black()
            } else {
                node.black()
                rotateLeft(parent)
            }
            rotateRight(grandparent)
        } else {
            if (node.isLeftChild) {
                node.black()
                rotateRight(parent)
            } else {
                parent.black()
            }
            rotateLeft(grandparent)
        }
    }

    override fun createNode(item: E, parent: TreeNode<E>?): TreeNode<E> {
        return RBNode(item, parent)
    }

    private class RBNode<E>(item: E, parent: TreeNode<E>?, var color: Boolean = RED) : TreeNode<E>(item, parent) {

        override fun toString(): String {
            if (isRed()) {
                return "RED-$item"
            }
            return item.toString()
        }
    }
}