package com.aaron.binarytree.refactor

import java.util.*

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/3
 */
abstract class BinaryTree<E> : AbstractTree<E>() {

    protected open var root: TreeNode<E>? = null

    override fun iterator(): Iterator<E> {
        return levelOrder()
    }

    override fun preorder(): Iterator<E> {
        return PreorderIterator(root)
    }

    override fun inorder(): Iterator<E> {
        return InorderIterator(root)
    }

    override fun postorder(): Iterator<E> {
        return PostorderIterator(root)
    }

    override fun levelOrder(): Iterator<E> {
        return LevelOrderIterator(root)
    }

    override fun height(): Int {
        val node = root ?: return 0
        val queue = LinkedList<TreeNode<E>>().also {
            it.offer(node)
        }
        var height = 0
        var levelSize = 1
        while (queue.isNotEmpty()) {
            val poll = queue.poll()
            levelSize--
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
        }
        return height
    }

    override fun isFull(): Boolean {
        var isFull = true
        levelOrderTraversal {
            if (!(it.isLeaf || it.hasTwoChildren)) {
                isFull = false
                return@levelOrderTraversal true
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
        var isComplete = true
        var isLeafFound = false
        levelOrderTraversal {
            if (isLeafFound && !it.isLeaf) {
                isComplete = false
                return@levelOrderTraversal true
            }
            if (it.left == null && it.right != null) {
                isComplete = false
                return@levelOrderTraversal true
            }
            if (it.right == null) {
                isLeafFound = true
            }
            false
        }
        return isComplete
    }

    private fun levelOrderTraversal(onPoll: (TreeNode<E>) -> Boolean) {
        val node = root ?: return
        val queue = LinkedList<TreeNode<E>>().also {
            it.offer(node)
        }
        while (queue.isNotEmpty()) {
            val poll = queue.poll()
            if (onPoll(poll)) break
            if (poll.left != null) {
                queue.offer(poll.left)
            }
            if (poll.right != null) {
                queue.offer(poll.right)
            }
        }
    }

    protected open class TreeNode<E>(var item: E, var parent: TreeNode<E>?) {

        var left: TreeNode<E>? = null

        var right: TreeNode<E>? = null

        val isLeaf: Boolean
            get() = left == null && right == null

        val hasTwoChildren: Boolean
            get() = left != null && right != null

        val isLeftChild: Boolean
            get() = this == parent?.left

        val isRightChild: Boolean
            get() = this == parent?.right
    }

    protected abstract inner class BaseIterator(protected var node: TreeNode<E>?) : Iterator<E> {

        private var index = 0

        override fun hasNext(): Boolean {
            return index < size
        }

        override fun next(): E {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            index++
            return nextNode().item
        }

        internal abstract fun nextNode(): TreeNode<E>
    }

    protected open inner class PreorderIterator(node: TreeNode<E>?) : BaseIterator(node) {

        private val stack by lazy {
            LinkedList<TreeNode<E>>().also {
                it.push(node!!)
            }
        }

        override fun nextNode(): TreeNode<E> {
            val stack = stack
            val pop = stack.pop()
            if (pop.right != null) {
                stack.push(pop.right)
            }
            if (pop.left != null) {
                stack.push(pop.left)
            }
            return pop
        }
    }

    protected open inner class InorderIterator(node: TreeNode<E>?) : BaseIterator(node) {

        private val stack by lazy {
            LinkedList<TreeNode<E>>()
        }

        override fun nextNode(): TreeNode<E> {
            val stack = stack
            var node = node
            while (node != null || stack.isNotEmpty()) {
                if (node != null) {
                    stack.push(node)
                    node = node.left
                } else {
                    node = stack.pop()
                    this.node = node.right
                    return node
                }
            }
            throw NoSuchElementException()
        }
    }

    protected open inner class PostorderIterator(node: TreeNode<E>?) : BaseIterator(node) {

        private val inStack by lazy {
            LinkedList<TreeNode<E>>().also {
                it.push(node!!)
            }
        }

        private val outStack by lazy {
            LinkedList<TreeNode<E>>()
        }

        override fun nextNode(): TreeNode<E> {
            val inStack = inStack
            val outStack = outStack
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
                return outStack.pop()
            }
            throw NoSuchElementException()
        }
    }

    protected open inner class LevelOrderIterator(node: TreeNode<E>?) : BaseIterator(node) {

        private val queue by lazy {
            LinkedList<TreeNode<E>>().also {
                it.offer(node!!)
            }
        }

        override fun nextNode(): TreeNode<E> {
            val queue = queue
            val poll = queue.poll()
            if (poll.left != null) {
                queue.offer(poll.left)
            }
            if (poll.right != null) {
                queue.offer(poll.right)
            }
            return poll
        }
    }
}