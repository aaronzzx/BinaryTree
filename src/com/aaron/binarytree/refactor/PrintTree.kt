package com.aaron.binarytree.refactor

import com.aaron.binarytree.printer.BinaryTreeInfo

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/4
 */
class PrintTree<E>(private val tree: BinaryTree<E>) : Tree<E> by tree, BinaryTreeInfo {

    override fun root(): Any? {
        return tree.rootNode()
    }

    override fun left(node: Any?): Any? {
        return tree.leftNode(node)
    }

    override fun right(node: Any?): Any? {
        return tree.rightNode(node)
    }

    override fun string(node: Any?): Any? {
        return tree.nodeElement(node)
    }
}