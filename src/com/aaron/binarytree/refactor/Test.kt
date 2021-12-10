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
        toList: ${tree.toList()}
    """.trimIndent())
    BinaryPrintTree(tree as BinaryTree<*>).println()
    println("$tree")
}

private fun getTree(): Tree<Int> {
    val tree = RBTree<Int>()
    val array = listOf(67, 52, 92, 96, 53, 95, 13, 63, 34, 82, 76, 54, 9, 68, 39)
    tree.addAll(array)
    return tree
}