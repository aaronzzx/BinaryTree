package com.aaron.binarytree.base

import com.aaron.binarytree.ktx.toListByLevelOrder
import com.aaron.binarytree.printer.BinaryTreeInfo
import java.util.*
import kotlin.math.max

/**
 * 二叉树基类
 *
 * @author aaronzzxup@gmail.com
 * @since 2021/12/1
 */
abstract class BinaryTree<E> : IBinaryTree<E>, BinaryTreeInfo {

    /**
     * 根节点
     */
    protected open var root: ITreeNode<E>? = null

    /**
     * 总节点数量，仅内部可修改
     */
    override var size: Int = 0
        protected set

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun contains(item: E): Boolean {
        return toListByLevelOrder().contains(item)
    }

    override fun clear() {
        root = null
        size = 0
    }

    override fun height(): Int {
        return getHeightByTraversal(root)
    }

    /**
     * 递归方式获取节点高度
     */
    protected fun getHeightByRecursive(node: ITreeNode<E>?): Int {
        node ?: return 0
        return 1 + max(getHeightByRecursive(node.left), getHeightByRecursive(node.right))
    }

    /**
     * 遍历方式获取节点高度
     */
    protected fun getHeightByTraversal(node: ITreeNode<E>?): Int {
        node ?: return 0
        var height = 0
        var levelSize = 1 // 每一层节点数量
        simpleLevelOrderTraversal(node) { queue, poll ->
            // 每一轮遍历层节点数量都需要减一
            levelSize--
            // 当层节点数量为零时代表遍历完一层，于是高度加一
            if (levelSize == 0) {
                levelSize = queue.size
                height++
            }
            false
        }
        return height
    }

    /**
     * 满二叉树定义：所有节点的度要么为 0 要么为 2
     */
    override fun isFull(): Boolean {
        val node = root ?: return false
        var isFull = true
        simpleLevelOrderTraversal(node) { queue, poll ->
            // 非叶子节点或度不为 2 则树不为满二叉树
            if (!(poll.isLeaf || poll.hasTwoChildren)) {
                isFull = false
                return@simpleLevelOrderTraversal true
            }
            false
        }
        return isFull
    }

    /**
     * 完美二叉树定义：除叶子节点外其他节点的度都为 2 ，并且所有叶子节点都在最后一层。
     * 可通过公式判断是否为完美二叉树：
     *     h = 树的高度
     *     Perfect's size == (2^h) - 1
     */
    override fun isPerfect(): Boolean {
        val height = height()
        if (height == 0) {
            return false
        }
        return (2 shl (height - 1)) - 1 == size
    }

    /**
     * 完全二叉树定义：所有节点靠左排列，并且叶子节点的高度差不能大于 1 ，
     * 可以看作完美二叉树的不完全版本。
     */
    override fun isComplete(): Boolean {
        val node = root ?: return false
        var isComplete = true
        var isLeafFound = false
        simpleLevelOrderTraversal(node) { queue, poll ->
            // 如果前面已经找到叶子节点，那么接下来的所有节点必须是叶子节点
            if (isLeafFound && !poll.isLeaf) {
                isComplete = false
                return@simpleLevelOrderTraversal true
            }
            if (poll.left == null && poll.right != null) {
                isComplete = false
                return@simpleLevelOrderTraversal true
            }
            if (poll.right == null) {
                // 一出现右子节点为空，那么接下来的所有节点都必须是叶子节点
                isLeafFound = true
            }
            false
        }
        return isComplete
    }

    override fun preorderTraversal(visitor: Visitor<E>) {
        preorderTraversal(root, visitor)
    }

    /**
     * 递归方式的前序遍历
     */
    protected fun preorderRecursive(node: ITreeNode<E>?, visitor: Visitor<E>) {
        node ?: return
        if (visitor.stop) return
        visitor.stop = visitor.visit(node.item)
        if (visitor.stop) return
        preorderRecursive(node.left, visitor)
        if (visitor.stop) return
        preorderRecursive(node.right, visitor)
    }

    /**
     * 非递归方式的前序遍历
     */
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

    /**
     * 递归方式的中序遍历
     */
    protected fun inorderRecursive(node: ITreeNode<E>?, visitor: Visitor<E>) {
        node ?: return
        if (visitor.stop) return
        inorderRecursive(node.left, visitor)
        if (visitor.stop) return
        visitor.stop = visitor.visit(node.item)
        if (visitor.stop) return
        inorderRecursive(node.right, visitor)
    }

    /**
     * 非递归方式的中序遍历
     */
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

    /**
     * 递归方式的后序遍历
     */
    protected fun postorderRecursive(node: ITreeNode<E>?, visitor: Visitor<E>) {
        node ?: return
        if (visitor.stop) return
        postorderRecursive(node.left, visitor)
        if (visitor.stop) return
        postorderRecursive(node.right, visitor)
        if (visitor.stop) return
        visitor.stop = visitor.visit(node.item)
    }

    /**
     * 非递归方式的后序遍历
     */
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

    /**
     * 层序遍历
     */
    override fun levelOrderTraversal(visitor: Visitor<E>) {
        val node = root ?: return
        simpleLevelOrderTraversal(node) { queue, poll ->
            if (visitor.visit(poll.item)) {
                return@simpleLevelOrderTraversal true
            }
            false
        }
    }

    protected inline fun simpleLevelOrderTraversal(
        node: ITreeNode<E>,
        crossinline traversal: (Queue<ITreeNode<E>>, ITreeNode<E>) -> Boolean
    ) {
        val queue = LinkedList<ITreeNode<E>>().also {
            it.offer(node)
        }
        while (queue.isNotEmpty()) {
            val poll = queue.poll()
            if (poll.left != null) {
                queue.offer(poll.left)
            }
            if (poll.right != null) {
                queue.offer(poll.right)
            }
            if (traversal(queue, poll)) break
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

    protected interface ITreeNode<E> {

        var item: E

        var parent: ITreeNode<E>?

        var left: ITreeNode<E>?

        var right: ITreeNode<E>?

        val isLeaf: Boolean
            get() = left == null && right == null

        val hasTwoChildren: Boolean
            get() = left != null && right != null

        val isLeftChild: Boolean
            get() = this == parent?.left

        val isRightChild: Boolean
            get() = this == parent?.right
    }
}