package com.aaron.binarytree.base

import com.aaron.binarytree.printer.BinaryTreeInfo
import java.util.*
import kotlin.math.max

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/1
 */
open class BinaryTree<E> : IBinaryTree<E>, BinaryTreeInfo {

    protected open var root: ITreeNode<E>? = null

    override var size: Int = 0
        protected set

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun clear() {
        root = null
        size = 0
    }

    override fun height(): Int {
        return getHeightByTraversal(root)
    }

    protected fun getHeightByRecursive(node: ITreeNode<E>?): Int {
        node ?: return 0
        return 1 + max(getHeightByRecursive(node.left), getHeightByRecursive(node.right))
    }

    protected fun getHeightByTraversal(node: ITreeNode<E>?): Int {
        node ?: return 0
        var height = 0
        var levelSize = 1
        simpleLevelOrderTraversal(node) { queue ->
            levelSize--
            val poll = queue.poll()
            if (poll.left != null) {
                queue.offer(poll.left)
            }
            if (poll.right != null) {
                queue.offer(poll.right)
            }
            if (levelSize == 0) {
                levelSize = queue.size
                height++
            }
            false
        }
        return height
    }

    override fun isFull(): Boolean {
        val node = root ?: return false
        var isFull = true
        simpleLevelOrderTraversal(node) { queue ->
            val poll = queue.poll()
            if (!(poll.isLeaf || poll.hasTwoChildren)) {
                isFull = false
                return@simpleLevelOrderTraversal true
            }
            if (poll.left != null) {
                queue.offer(poll.left)
            }
            if (poll.right != null) {
                queue.offer(poll.right)
            }
            false
        }
        return isFull
    }

    override fun isPerfect(): Boolean {
        val height = height()
        if (height == 0) {
            return false
        }
        return (2 shl (height - 1)) - 1 == size
    }

    override fun isComplete(): Boolean {
        val node = root ?: return false
        var isComplete = true
        var isLeafFound = false
        simpleLevelOrderTraversal(node) { queue ->
            val poll = queue.poll()
            if (isLeafFound && !poll.isLeaf) {
                isComplete = false
                return@simpleLevelOrderTraversal true
            }
            if (poll.left != null) {
                queue.offer(poll.left)
            } else if (poll.right != null) {
                isComplete = false
                return@simpleLevelOrderTraversal true
            }
            if (poll.right != null) {
                queue.offer(poll.right)
            } else {
                isLeafFound = true
            }
            false
        }
        return isComplete
    }

    override fun preorderTraversal(visitor: Visitor<E>) {
        preorderTraversal(root, visitor)
    }

    protected fun preorderRecursive(node: ITreeNode<E>?, visitor: Visitor<E>) {
        node ?: return
        if (visitor.stop) return
        visitor.stop = visitor.visit(node.item)
        if (visitor.stop) return
        preorderRecursive(node.left, visitor)
        if (visitor.stop) return
        preorderRecursive(node.right, visitor)
    }

    protected fun preorderTraversal(node: ITreeNode<E>?, visitor: Visitor<E>) {
        node ?: return
        val stack = LinkedList<ITreeNode<E>>().also {
            it.push(node)
        }
        while (stack.isNotEmpty()) {
            val pop = stack.pop()
            if (visitor.visit(pop.item)) {
                break
            }
            if (pop.right != null) {
                stack.push(pop.right)
            }
            if (pop.left != null) {
                stack.push(pop.left)
            }
        }
    }

    override fun inorderTraversal(visitor: Visitor<E>) {
        inorderTraversal(root, visitor)
    }

    protected fun inorderRecursive(node: ITreeNode<E>?, visitor: Visitor<E>) {
        node ?: return
        if (visitor.stop) return
        inorderRecursive(node.left, visitor)
        if (visitor.stop) return
        visitor.stop = visitor.visit(node.item)
        if (visitor.stop) return
        inorderRecursive(node.right, visitor)
    }

    protected fun inorderTraversal(node: ITreeNode<E>?, visitor: Visitor<E>) {
        var _node: ITreeNode<E>? = node ?: return
        val stack = LinkedList<ITreeNode<E>>()
        while (_node != null || stack.isNotEmpty()) {
            if (_node != null) {
                stack.push(_node)
                _node = _node.left
            } else {
                _node = stack.pop()
                if (visitor.visit(_node.item)) {
                    break
                }
                _node = _node.right
            }
        }
    }

    override fun postorderTraversal(visitor: Visitor<E>) {
        postorderTraversal(root, visitor)
    }

    protected fun postorderRecursive(node: ITreeNode<E>?, visitor: Visitor<E>) {
        node ?: return
        if (visitor.stop) return
        postorderRecursive(node.left, visitor)
        if (visitor.stop) return
        postorderRecursive(node.right, visitor)
        if (visitor.stop) return
        visitor.stop = visitor.visit(node.item)
    }

    protected fun postorderTraversal(node: ITreeNode<E>?, visitor: Visitor<E>) {
        node ?: return
        val inStack = LinkedList<ITreeNode<E>>().also {
            it.push(node)
        }
        val outStack = LinkedList<ITreeNode<E>>()
        while (inStack.isNotEmpty()) {
            val pop = inStack.pop()
            outStack.push(pop)
            if (pop.left != null) {
                inStack.push(pop.left)
            }
            if (pop.right != null) {
                inStack.push(pop.right)
            }
        }
        while (outStack.isNotEmpty()) {
            val pop = outStack.pop()
            if (visitor.visit(pop.item)) {
                break
            }
        }
    }

    override fun levelOrderTraversal(visitor: Visitor<E>) {
        val node = root ?: return
        simpleLevelOrderTraversal(node) { queue ->
            val poll = queue.poll()
            if (visitor.visit(poll.item)) {
                return@simpleLevelOrderTraversal true
            }
            if (poll.left != null) {
                queue.offer(poll.left)
            }
            if (poll.right != null) {
                queue.offer(poll.right)
            }
            false
        }
    }

    protected inline fun simpleLevelOrderTraversal(
        node: ITreeNode<E>,
        crossinline traversal: (Queue<ITreeNode<E>>) -> Boolean
    ) {
        val queue = LinkedList<ITreeNode<E>>().also {
            it.offer(node)
        }
        while (queue.isNotEmpty()) {
            if (traversal(queue)) break
        }
    }

    override fun root(): Any? {
        return root
    }

    override fun left(node: Any?): Any? {
        return (node as? ITreeNode<E>)?.left
    }

    override fun right(node: Any?): Any? {
        return (node as? ITreeNode<E>)?.right
    }

    override fun string(node: Any?): Any? {
        return (node as? ITreeNode<E>)?.item
    }

    protected open fun createNode(item: E, parent: ITreeNode<E>?): ITreeNode<E> {
        return TreeNode(item, parent)
    }

    private class TreeNode<E>(
        override var item: E,
        override var parent: ITreeNode<E>?
    ) : ITreeNode<E> {
        override var left: ITreeNode<E>? = null
        override var right: ITreeNode<E>? = null
    }

    protected interface ITreeNode<E> {

        var item: E

        var parent: ITreeNode<E>?

        var left: ITreeNode<E>?

        var right: ITreeNode<E>?

        val isLeaf: Boolean
            get() = left == null && right == null

        val hasTwoChildren: Boolean
            get() = left != null && right != null
    }
}