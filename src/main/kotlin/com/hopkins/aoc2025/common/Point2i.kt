package com.hopkins.aoc2025.common

data class Point2i(val x: Int, val y: Int) {
    override fun toString() = "[$x, $y]"

    fun add(dx: Int, dy: Int): Point2i = Point2i(x + dx, y + dy)

    fun add(dp: Point2i): Point2i = Point2i(x + dp.x, y + dp.y)
}