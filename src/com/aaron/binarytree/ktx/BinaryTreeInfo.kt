package com.aaron.binarytree.ktx

import com.aaron.binarytree.printer.BinaryTreeInfo
import com.aaron.binarytree.printer.BinaryTrees
import com.aaron.binarytree.printer.InorderPrinter
import com.aaron.binarytree.printer.LevelOrderPrinter

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/12/1
 */

fun BinaryTreeInfo.println(style: BinaryTrees.PrintStyle = BinaryTrees.PrintStyle.LEVEL_ORDER) {
    when (style) {
        BinaryTrees.PrintStyle.LEVEL_ORDER -> LevelOrderPrinter(this).println()
        BinaryTrees.PrintStyle.INORDER -> InorderPrinter(this).println()
    }
}

fun BinaryTreeInfo.print(style: BinaryTrees.PrintStyle = BinaryTrees.PrintStyle.LEVEL_ORDER) {
    when (style) {
        BinaryTrees.PrintStyle.LEVEL_ORDER -> LevelOrderPrinter(this).print()
        BinaryTrees.PrintStyle.INORDER -> InorderPrinter(this).print()
    }
}

fun BinaryTreeInfo.printString(style: BinaryTrees.PrintStyle = BinaryTrees.PrintStyle.LEVEL_ORDER): String {
    return when (style) {
        BinaryTrees.PrintStyle.LEVEL_ORDER -> LevelOrderPrinter(this).printString()
        BinaryTrees.PrintStyle.INORDER -> InorderPrinter(this).printString()
    }
}