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
            return this.color(RED)
        }

        private fun <E> TreeNode<E>?.black(): TreeNode<E>? {
            return this.color(BLACK)
        }

        private fun <E> TreeNode<E>?.color(color: Boolean): TreeNode<E>? {
            return toRBNode()?.also { it.color = color }
        }

        private fun <E> TreeNode<E>?.color(): Boolean {
            return toRBNode()?.color ?: BLACK
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

    override fun afterRemove(node: TreeNode<E>) {
        var _node = node
        while (_node != root && _node.isBlack()) {
            if (_node.isLeftChild || _node.parent?.left == null) {
                var sibling = _node.parent?.right
                if (sibling.isRed()) {
                    sibling.black()
                    _node.parent.red()
                    rotateLeft(_node.parent)
                    sibling = _node.parent?.right
                }
                if (sibling?.left.isBlack() && sibling?.right.isBlack()) {
                    sibling.red()
                    _node = _node.parent ?: break
                } else {
                    if (sibling?.right.isBlack()) {
                        sibling?.left.black()
                        sibling.red()
                        rotateRight(sibling)
                        sibling = _node.parent?.right
                    }
                    sibling.color(_node.parent.color())
                    _node.parent.black()
                    sibling?.right.black()
                    rotateLeft(_node.parent)
                    _node = root!!
                }
            } else {
                var sibling = _node.parent?.left
                if (sibling.isRed()) {
                    sibling.black()
                    _node.parent.red()
                    rotateRight(_node.parent)
                    sibling = _node.parent?.left
                }
                if (sibling?.left.isBlack() && sibling?.right.isBlack()) {
                    sibling.red()
                    _node = _node.parent ?: break
                } else {
                    if (sibling?.left.isBlack()) {
                        sibling?.right.black()
                        sibling.red()
                        rotateLeft(sibling)
                        sibling = _node.parent?.left
                    }
                    sibling.color(_node.parent.color())
                    _node.parent.black()
                    sibling?.left.black()
                    rotateRight(_node.parent)
                    _node = root!!
                }
            }
        }
        _node.black()
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