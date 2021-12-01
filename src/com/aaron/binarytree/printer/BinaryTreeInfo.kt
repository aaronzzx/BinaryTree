package com.aaron.binarytree.printer

/**
 * @author https://github.com/CoderMJLee/BinaryTrees
 */
interface BinaryTreeInfo {
    /**
     * who is the root node
     */
    fun root(): Any?

    /**
     * how to get the left child of the node
     */
    fun left(node: Any?): Any?

    /**
     * how to get the right child of the node
     */
    fun right(node: Any?): Any?

    /**
     * how to print the node
     */
    fun string(node: Any?): Any?
}