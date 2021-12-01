package com.aaron.binarytree.base

/**
 * @author aaronzzxup@gmail.com
 * @since 2021/11/18
 */
abstract class Visitor<E> {

    internal var stop = false

    abstract fun visit(item: E): Boolean
}