package com.hopkins.aoc2025.day1

import com.hopkins.aoc2025.common.Point2i
import java.io.File

fun main() {
    val lines: List<String> =
        File("input/input04.txt")
            .readLines()
            .map { it.trim() }
            .filterNot { it.isEmpty() }

    val width = lines[0].length
    val height = lines.size
    println("Size: $width x $height")

    val map: MutableSet<Point2i> = mutableSetOf()
    lines.forEachIndexed { y, line -> line.forEachIndexed { x, c ->
        if (c == '@') {
            map.add(Point2i(x, y))
        }
    }}

    val isPart1 = false
    val accessible: MutableSet<Point2i> = mutableSetOf()
    while (true) {
        val toAdd: MutableSet<Point2i> = mutableSetOf()
        for (y in 0 until height) {
            for (x in 0 until width) {
                val current = Point2i(x, y)
                if (map.contains(current) && !accessible.contains(current)) {
                    val countAdjacent =
                        findAdjacentPositions(current)
                            .count { map.contains(it) && !accessible.contains(it) }
                    if (countAdjacent < 4) {
                        toAdd.add(current)
                    }
                }
            }
        }
        accessible.addAll(toAdd)
        if (isPart1 || toAdd.isEmpty()) {
            break
        }
    }

    println("Debug Layout:")
    for (y in 0 until height) {
        for (x in 0 until width) {
            val current = Point2i(x, y)
            if (accessible.contains(current)) {
                print("x")
            } else if (map.contains(current)) {
                print("@")
            } else {
                print(".")
            }
        }
        println()
    }

    println("Num accessible: ${accessible.size}")
}

private val directions: List<Point2i> = listOf(
    Point2i(-1, -1),
    Point2i(0, -1),
    Point2i(1, -1),
    Point2i(-1, 0),
    Point2i(1, 0),
    Point2i(-1, 1),
    Point2i(0, 1),
    Point2i(1, 1),
)

private fun findAdjacentPositions(point: Point2i): List<Point2i> {
    return directions.map { point.add(it) }
}

