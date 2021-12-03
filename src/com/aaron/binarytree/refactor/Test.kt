package com.aaron.binarytree.refactor

import com.aaron.binarytree.ktx.println

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/3
 */
fun main() {
    val tree = BST<Int>()
    val wrapper = PrintTree(tree)
    val array = listOf(7, 4, 2, 1, 3, 5, 9, 8, 11, 10, 12, 6)
    tree.addAll(array)
    wrapper.println()
    val list = listOf(1, 2, 3, 4, 5, 6)
    // retainAll 有问题
    tree.retainAll(list)
    wrapper.println()
}