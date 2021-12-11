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
            if (toRBNode() == null) {
                return false
            }
            return toRBNode()!!.color == RED
        }

        private fun <E> TreeNode<E>?.isBlack(): Boolean {
            if (toRBNode() == null) {
                return true
            }
            return toRBNode()!!.color == BLACK
        }

        private fun <E> TreeNode<E>?.red(): TreeNode<E>? {
            return toRBNode()?.also { it.color = RED }
        }

        private fun <E> TreeNode<E>?.black(): TreeNode<E>? {
            return toRBNode()?.also { it.color = BLACK }
        }

        private fun <E> TreeNode<E>?.toRBNode(): RBNode<E>? {
            return this as? RBNode<E>
        }
    }

    override fun afterAdd(node: TreeNode<E>) {
        node.red()
        var _node = node
        while (_node != root && _node.parent.isRed()) {
            if (_node.uncle.isRed()) {
                _node.parent.black()
                _node.uncle.black()
                _node = _node.grandparent.red() ?: break
            } else {
                if (_node.parent!!.isLeftChild) {
                    if (_node.isRightChild) {
                        _node = _node.parent!!
                        rotateLeft(_node)
                    }
                    _node.grandparent.red()
                    _node.parent.black()
                    rotateRight(_node.grandparent)
                } else {
                    if (_node.isLeftChild) {
                        _node = _node.parent!!
                        rotateRight(_node)
                    }
                    _node.grandparent.red()
                    _node.parent.black()
                    rotateLeft(_node.grandparent)
                }
            }
        }
        root.black()
    }

    override fun createNode(item: E, parent: TreeNode<E>?): TreeNode<E> {
        return RBNode(item, parent)
    }

    private class RBNode<E>(item: E, parent: TreeNode<E>?) : TreeNode<E>(item, parent) {

        var color: Boolean = RED

        override fun toString(): String {
            if (isRed()) {
                return "RED-$item"
            }
            return item.toString()
        }
    }
}