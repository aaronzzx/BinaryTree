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
        _2_3_tree(node)
//        _2_3_4_tree(node)
    }

    private fun _2_3_4_tree(node: TreeNode<E>) {
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
                    _node.parent.black()
                    rotateRight(_node.grandparent.red())
                } else {
                    if (_node.isLeftChild) {
                        _node = _node.parent!!
                        rotateRight(_node)
                    }
                    _node.parent.black()
                    rotateLeft(_node.grandparent.red())
                }
            }
        }
        root.black()
    }

    private fun _2_3_tree(node: TreeNode<E>) {
        node.red()
        var _node = node
        while (_node != root && _node.isRed() && (_node.parent.isRed() || _node.isLeftChild)) {
            if (_node.parent.isRed()) {
                // 双红
                if (_node.isLeftChild) {
                    // RL
                    _node = _node.parent!!
                    rotateRight(_node)
                }
                // RR
                _node.black()
                rotateLeft(_node.grandparent.black())
                _node = _node.parent!!
            } else {
                if (_node.sibling.isRed()) {
                    // 两个红节点发生上溢
                    _node.black()
                    _node.sibling.black()
                    _node = _node.parent.red()!!
                } else if (_node.isLeftChild) {
                    // 红节点是左子节点
                    _node.black()
                    rotateRight(_node.parent.red()!!)
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