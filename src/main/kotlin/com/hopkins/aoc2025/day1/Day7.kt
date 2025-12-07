package com.hopkins.aoc2025.day1

import com.hopkins.aoc2025.common.Point2i
import java.io.File

fun main() {
    val lines: List<String> = File("input/input07.txt").readLines()
    val invalidPoint = Point2i(-1, -1)

    var start: Point2i = invalidPoint
    val splitters: Set<Point2i> =
        lines.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                if (c == 'S') {
                    start = Point2i(x, y)
                }
                if (c == '^') {
                    Point2i(x, y)
                } else {
                    null
                }
            }
        }.toSet()
    require(start != invalidPoint) { "Start not found in map" }

    println("Start: $start")
    println("Num Splitters: ${splitters.size}")


    val width = lines[0].length
    val height = lines.size
    println("Map Details:")
    println(" width: $width")
    println(" height: $height")
    println()

    val tachyons: MutableSet<Point2i> = mutableSetOf()
    val nextTachyons: MutableSet<Point2i> = mutableSetOf()
    nextTachyons.add(start.add(0, 1))
    while (nextTachyons.isNotEmpty()) {
        val newTachyons: MutableSet<Point2i> = mutableSetOf()
        for (tachyon in nextTachyons) {
            if (tachyons.contains(tachyon)) {
                // Noop, we've already simulated this tachyon
            } else if (splitters.contains(tachyon)) {
                newTachyons.add(tachyon.add(-1, 1))
                newTachyons.add(tachyon.add(1, 1))
            } else {
                newTachyons.add(tachyon.add(0, 1))
            }
        }
        tachyons.addAll(nextTachyons)
        nextTachyons.clear()
        nextTachyons.addAll(newTachyons.filter { isInBounds(it, width, height)})
    }


    for (y in 0 until height) {
        for (x in 0 until width) {
            val current = Point2i(x, y)
            if (current == start) {
                print("S")
            } else if (splitters.contains(current)) {
                if (tachyons.contains(current)) {
                    // Activated splitter
                    print("A")
                } else {
                    print("^")
                }
            } else if (tachyons.contains(current)) {
                print("|")
            } else {
                print(".")
            }
        }
        println()
    }

    val activeSplitters = splitters.filter { tachyons.contains(it) }
    println("Part 1: Active splitters: ${activeSplitters.size}")
}

private fun isInBounds(point: Point2i, width: Int, height: Int): Boolean =
    point.x >= 0 && point.y >= 0 && point.x < width && point.y < height