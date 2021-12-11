package com.aaron.binarytree.refactor

import com.aaron.binarytree.ktx.println
import kotlin.random.Random

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/3
 */
fun main() {
    val list = 1..31
    val avlTree = AVLTree<Int>().also { it.addAll(list) }
    val rbTree = RBTree<Int>().also { it.addAll(list) }
    BinaryPrintTree(avlTree as BinaryTree<*>).println()
    BinaryPrintTree(rbTree as BinaryTree<*>).println()
}

private fun getTree(tree: BST<Int>): Tree<Int> {
    val random = Random(System.currentTimeMillis())
    val list = arrayListOf<Int>()
    repeat(random.nextInt(7, 24)) {
        list.add(random.nextInt(1, 100))
    }
    tree.addAll(list)
    return tree
}