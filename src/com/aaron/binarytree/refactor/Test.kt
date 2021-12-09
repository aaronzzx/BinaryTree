package com.aaron.binarytree.refactor

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/3
 */
//fun main() {
//    val tree = getTree()
//    println(
//        """
//        size: ${tree.size}
//        height: ${tree.height()}
//        toList: ${tree.toList()}
//    """.trimIndent())
//    BinaryPrintTree(tree as BinaryTree<*>).println()
//    println("$tree")
//}
//
//private fun getTree(): Tree<Int> {
//    val tree = AVLTree<Int>()
//    val array = (1..31).toList()
//    tree.addAll(array)
//    return tree
//}

fun main() {
    val list = (1..10).toList()
    val tree = BTree<Int>(4)
    list.forEach {
        tree.add(it)
    }
    println("${tree.root}")
}