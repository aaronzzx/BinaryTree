package com.aaron.binarytree.refactor

import com.aaron.binarytree.printer.BinaryTreeInfo

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/4
 */
class BinaryPrintTree<E>(private val tree: BinaryTree<E>) : Tree<E> by tree, BinaryTreeInfo {

    override fun root(): Any? {
        return getRoot(tree)
    }

    override fun left(node: Any?): Any? {
        return getNode(node, true)
    }

    override fun right(node: Any?): Any? {
        return getNode(node, false)
    }

    override fun string(node: Any?): Any? {
        return node
    }

    private fun getRoot(obj: Any?): Any? {
        return try {
            val field = BinaryTree::class.java.getDeclaredField("root").also {
                it.isAccessible = true
            }
            field.get(obj)
        } catch (ignored: Exception) {
            null
        }
    }

    private fun getNode(obj: Any?, left: Boolean): Any? {
        return try {
            val bt = BinaryTree::class.java
            val subClasses = bt.declaredClasses
            val clazz = subClasses.find {
                it.name == "${bt.name}\$TreeNode"
            } ?: return null
            val child = when (left) {
                true -> clazz.getDeclaredField("left")
                else -> clazz.getDeclaredField("right")
            }.also {
                it.isAccessible = true
            }
            child.get(obj)
        } catch (ignored: Exception) {
            null
        }
    }
}