package com.aaron.binarytree

import com.aaron.binarytree.bst.AVLTree
import com.aaron.binarytree.bst.BST
import com.aaron.binarytree.ktx.*

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/1
 */
fun main() {
    testAVL()
}

private fun testBST() {
    val array = intArrayOf(7, 4, 2, 1, 3, 5, 9, 8, 11, 10, 12, 6)
    val tree = BST<Int>()
    array.forEach {
        tree.add(it)
    }
    tree.println()
    val predOrSucc = 7
    println(
        """
        height: ${tree.height()}
        isFull: ${tree.isFull()}
        isPerfect: ${tree.isPerfect()}
        isComplete: ${tree.isComplete()}
        preorder: ${tree.toListByPreorder()}
        inorder: ${tree.toListByInorder()}
        postorder: ${tree.toListByPostorder()}
        levelOrder: ${tree.toListByLevelOrder()}
        predecessorOf($predOrSucc): ${tree.predecessorOf(predOrSucc)}
        successorOf($predOrSucc): ${tree.successorOf(predOrSucc)}
    """.trimIndent()
    )
}

private fun testAVL() {
    val tree = AVLTree<Int>()
    val array = intArrayOf(13, 14, 15, 12, 11, 17, 16, 8, 9, 1)
    array.forEach {
        tree.add(it)
    }
    tree.println()
    tree.remove(15)
    tree.remove(17)
    tree.remove(16)
    tree.remove(1)
    tree.println()
}