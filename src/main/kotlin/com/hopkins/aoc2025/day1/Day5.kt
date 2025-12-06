package com.hopkins.aoc2025.day1

import java.io.File
import kotlin.math.max

fun main() {
    val lines: List<String> = File("input/input05.txt").readLines()

    val ranges: List<ClosedRange<Long>> =
        lines.filter { isRange(it) }.map { parseRange(it) }
    val ingredients: List<Long> =
        lines.filterNot { isRange(it) || it.isBlank() }.map { it.toLong() }

    println("Num ranges: ${ranges.size}")
    println("Num ingredients: ${ingredients.size}")

    // Part 1: num fresh
    val countFresh = ingredients.count { ingredient -> ranges.any { it.contains(ingredient) }}
    println("Part 1. Num fresh: $countFresh")

    // Part 2: total fresh
    val sortedRanges = ranges.sortedBy { it.start }
    val nonOverLappingRanges: MutableList<ClosedRange<Long>> = mutableListOf()
    var currentRange: ClosedRange<Long> = sortedRanges.first()
    for (range in sortedRanges.drop(1)) {
        if (currentRange.contains(range.start)) {
            currentRange = currentRange.start.rangeTo(max(currentRange.endInclusive, range.endInclusive))
        } else {
            nonOverLappingRanges.add(currentRange)
            currentRange = range
        }
    }
    nonOverLappingRanges.add(currentRange)
    println("Non overlapping ranges")
    nonOverLappingRanges.forEach { println(it) }
    val totalFresh = nonOverLappingRanges.sumOf { it.endInclusive + 1 - it.start }
    println("Part 2. Total fresh: $totalFresh")
}

private fun isRange(line: String): Boolean = line.contains("-")

private fun parseRange(line: String): ClosedRange<Long> {
    val (left, right) = line.split("-")
    return left.toLong().rangeTo(right.toLong())
}