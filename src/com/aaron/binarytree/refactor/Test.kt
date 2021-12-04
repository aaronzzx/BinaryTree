package com.aaron.binarytree.refactor

import com.aaron.binarytree.ktx.println

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/3
 */
fun main() {
    val tree = AVLTree<Int>()
    val wrapper = PrintTree(tree)
    val array = (1..63).toList()
    tree.addAll(array)
    wrapper.println()
    tree.retainAll((1..32).toSet())
    wrapper.println()
}