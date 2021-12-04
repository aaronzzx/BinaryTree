package com.aaron.binarytree.refactor

import com.aaron.binarytree.ktx.println

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/3
 */
fun main() {
    val tree = getTree()
    println(
        """
        size: ${tree.size}
        height: ${tree.height()}
        isFull: ${tree.isFull()}
        isPerfect: ${tree.isPerfect()}
        isComplete: ${tree.isComplete()}
        toList: ${tree.toList()}
    """.trimIndent())
    PrintTree(tree as BinaryTree<*>).println()
    println("$tree")
}

private fun getTree(): Tree<Int> {
    val tree = AVLTree<Int>()
    val array = (1..31).toList()
    tree.addAll(array)
    return tree
}