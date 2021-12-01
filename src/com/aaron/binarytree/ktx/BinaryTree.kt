@file:Suppress("UNUSED")

package com.aaron.binarytree.ktx

import com.aaron.binarytree.base.IBinaryTree
import com.aaron.binarytree.base.IBinaryTreeTraversal
import com.aaron.binarytree.base.Visitor

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/1
 */

private const val Z = 0

/**
 * 按前序输入 List
 */
fun <E> IBinaryTreeTraversal<E>.toListByPreorder(): List<E> {
    val list = createList()
    preorderTraversal(inflateInto(list))
    return list
}

/**
 * 按中序输出 List
 */
fun <E> IBinaryTreeTraversal<E>.toListByInorder(): List<E> {
    val list = createList()
    inorderTraversal(inflateInto(list))
    return list
}

/**
 * 按后序输出 List
 */
fun <E> IBinaryTreeTraversal<E>.toListByPostorder(): List<E> {
    val list = createList()
    postorderTraversal(inflateInto(list))
    return list
}

/**
 * 按层序输出 List
 */
fun <E> IBinaryTreeTraversal<E>.toListByLevelOrder(): List<E> {
    val list = createList()
    levelOrderTraversal(inflateInto(list))
    return list
}

private fun <E> inflateInto(collection: MutableCollection<E>): Visitor<E> {
    return object : Visitor<E>() {
        override fun visit(item: E): Boolean {
            collection.add(item)
            return false
        }
    }
}

private fun <E> IBinaryTreeTraversal<E>.createList(): MutableList<E> {
    return when (this) {
        is IBinaryTree<E> -> ArrayList(size)
        else -> ArrayList()
    }
}