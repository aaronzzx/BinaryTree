package com.aaron.binarytree.bst

import com.aaron.binarytree.base.BinaryTree

/**
 * 二叉搜索树实现类
 *
 * @author aaronzzxup@gmail.com
 * @since 2021/12/1
 */
open class BST<E> : BinaryTree<E>, IBinarySearchTree<E> {

    private var comparator: Comparator<E>? = null

    override var root: ITreeNode<E>? = null

    constructor() : this(null)

    constructor(comparator: Comparator<E>?) {
        this.comparator = comparator
    }

    /**
     * 添加逻辑就几点需要注意的：
     * 1. 根节点未创建
     * 2. 查找元素插入的方向
     * 3. 遇到相同元素的处理
     */
    override fun add(item: E): Boolean {
        // 不允许元素为空
        require(item != null) {
            "The item must not be null."
        }
        var node: ITreeNode<E>? = root
        if (node == null) {
            // 根节点为空需要先创建根节点
            node = createNode(item, null)
            root = node
            size++
            afterAdd(node)
            return true
        }
        var cmp = 0
        var parent = node
        while (node != null) {
            parent = node
            cmp = compare(item, node.item)
            if (cmp < 0) {
                // 往左子树找
                node = node.left
            } else if (cmp > 0) {
                // 往右子树找
                node = node.right
            } else {
                // 存在相同元素，替换
                node.item = item
                return true
            }
        }
        val newNode = createNode(item, parent)
        // 根据比较结果插入元素
        if (cmp < 0) {
            parent?.left = newNode
        } else {
            parent?.right = newNode
        }
        size++
        afterAdd(newNode)
        return true
    }

    protected open fun afterAdd(node: ITreeNode<E>) = Unit

    override fun remove(item: E): Boolean {
        return remove(node(item))
    }

    /**
     * 移除元素注意事项：
     * 1. 节点度为 2 的情况
     * 2. 节点度为 1 的情况
     * 3. 节点是根节点
     * 4. 节点是叶子节点
     */
    private fun remove(node: ITreeNode<E>?): Boolean {
        var _node = node ?: return false
        // 处理度为 2 的情况
        if (_node.hasTwoChildren) {
            // 找到后继节点，前驱当然也可以，选其一
            val succ = successor(_node)!!
            // 将后继的元素赋值给要删除的节点
            _node.item = succ.item
            // 使要删除的节点指向后继，这时候 _node 其实可以算删除了
            // 因为我们做的就是找到后继替代它，那么到这里就变成了需要删除
            // 后继节点
            _node = succ
        }
        // 来到这里就只有两种情况，度为 1 或度为 0 ，
        // 因此直接拿待删除节点的左节点（如果为空就拿右节点）
        val replacement = _node.left ?: _node.right
        if (replacement != null) {
            // 能来到这里表示度为 1
            val _nodeParent = _node.parent
            // 第一步先拿待删除节点的父节点赋值给替代节点
            replacement.parent = _nodeParent
            if (_nodeParent == null) {
                // 待删除节点的父节点是可能为空的，如下：
                // root
                //     \
                //    child
                // 这种情况直接使根节点指向替代节点即可
                root = replacement
            } else if (_node == _nodeParent.left) {
                // 待删除节点在父节点的左子树
                _node.parent?.left = replacement
            } else {
                // 待删除节点在父节点的右子树
                _node.parent?.right = replacement
            }
        } else if (_node.parent == null) {
            // 来到这里其实就是这棵树只有一个节点，也就是根节点
            // 因此删除节点其实就是删除根节点
            root = null
        } else {
            // 来到这里表示待删除节点是叶子节点
            // 直接判断待删除节点是它爸爸的左子树还是右子树然后置空
            if (_node == _node.parent?.left) {
                _node.parent?.left = null
            } else {
                _node.parent?.right = null
            }
        }
        size--
        return true
    }

    override fun contains(item: E): Boolean {
        return node(item) != null
    }

    override fun predecessorOf(item: E): E? {
        return predecessor(node(item))?.item
    }

    /**
     * 寻找前驱，node.left 为空则没有前驱，否则从 left 开始一直往 left
     * 的 right 往下找，最 right 的那个就是前驱，如果 left 没有子节点
     * 那么 left 就是前驱。
     */
    private fun predecessor(node: ITreeNode<E>?): ITreeNode<E>? {
        var _node: ITreeNode<E>? = node ?: return null
        if (_node?.left != null) {
            _node = _node.left
            while (_node?.right != null) {
                _node = _node.right
            }
            return _node
        }
        while (_node?.parent != null && _node != _node.parent?.right) {
            _node = _node.parent
        }
        return _node?.parent
    }

    override fun successorOf(item: E): E? {
        return successor(node(item))?.item
    }

    /**
     * 寻找后继，逻辑和寻找前驱相反。
     */
    private fun successor(node: ITreeNode<E>?): ITreeNode<E>? {
        var _node: ITreeNode<E>? = node ?: return null
        if (_node?.right != null) {
            _node = _node.right
            while (_node?.left != null) {
                _node = _node.left
            }
            return _node
        }
        while (_node?.parent != null && _node != _node.parent?.left) {
            _node = _node.parent
        }
        return _node?.parent
    }

    /**
     * 根据元素寻找节点
     */
    private fun node(item: E): ITreeNode<E>? {
        var node = root
        while (node != null) {
            val cmp = compare(item, node.item)
            if (cmp < 0) {
                node = node.left
            } else if (cmp > 0) {
                node = node.right
            } else {
                return node
            }
        }
        return null
    }

    private fun compare(e1: E, e2: E): Int {
        val cmp = comparator
        if (cmp != null) {
            return cmp.compare(e1, e2)
        }
        return (e1 as Comparable<E>).compareTo(e2)
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
}