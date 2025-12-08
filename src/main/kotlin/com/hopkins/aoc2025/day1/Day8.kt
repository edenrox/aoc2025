package com.hopkins.aoc2025.day1

import com.hopkins.aoc2025.common.Point3i
import java.io.File

fun main() {
    val lines: List<String> = File("input/input08.txt").readLines()

    // Parse the lines into junction boxes
    val junctionBoxes: List<Point3i> =
        lines.map { parsePoint3i(it) }

    // Debug: output the junction boxes
    println("Junction Boxes")
    println(" num: ${junctionBoxes.size}")
    for (junctionBox in junctionBoxes) {
        //println(" $junctionBox")
    }

    // Build a list of junction box pairs
    val junctionBoxPairs: List<Pair<Point3i, Point3i>> =
        junctionBoxes.flatMapIndexed { index, left -> junctionBoxes.drop(index + 1).map { right ->
            left to right
        }}

    // Sort the junction box pairs by distance and take the top N
    val numJunctionBoxesToKeep = 1000
    val junctionBoxPairsSortedTopN =
        junctionBoxPairs.sortedBy { it.first.distanceTo(it.second)}.take(numJunctionBoxesToKeep)

    // Debug: output the sorted junction box pairs
    println("Sorted Junction Boxes")
    println(" num: ${junctionBoxPairsSortedTopN.size}")
    for (junctionBoxPair in junctionBoxPairsSortedTopN) {
        //println(" $junctionBoxPair")
    }

    // Build an initial set of circuits (all size 1)
    val circuits: MutableList<MutableSet<Point3i>> = mutableListOf()
    for (junctionBox in junctionBoxes) {
        circuits.add(mutableSetOf(junctionBox))
    }

    // Merge circuits
    for (pair in junctionBoxPairsSortedTopN) {
        val circuit1 = circuits.first { it.contains(pair.first) }
        val circuit2 = circuits.first { it.contains(pair.second) }
        if (circuit1 != circuit2) {
            // Merge the circuits
            circuit1.addAll(circuit2)
            circuit2.clear()
        }
    }


    // Sort the circuits by size
    val orderedCircuits = circuits.sortedByDescending { it.size }

    // Debug: print the circuits
    println("Ordered Circuits: ")
    val nonEmptyCircuits = orderedCircuits.filter { it.isNotEmpty() }
    println(" num non-empty circuits: ${nonEmptyCircuits.size}")
    for (circuit in orderedCircuits) {
        //println("${circuit.size} $circuit")
    }

    // Take the top 3
    val (x, y, z) = orderedCircuits.take(3).map { it.size}
    val result = x * y * z
    println("Part 1.  Result: $result")


    // Part 2
    circuits.clear()
    for (junctionBox in junctionBoxes) {
        circuits.add(mutableSetOf(junctionBox))
    }

    // Merge circuits,
    val junctionBoxPairsSorted =
        junctionBoxPairs.sortedBy { it.first.distanceTo(it.second)}
    for (pair in junctionBoxPairsSorted) {
        val circuit1 = circuits.first { it.contains(pair.first) }
        val circuit2 = circuits.first { it.contains(pair.second) }
        if (circuit1 != circuit2) {
            // Merge the circuits
            circuit1.addAll(circuit2)
            circuit2.clear()
        }
        val numNonEmptyCircuits = circuits.count { it.isNotEmpty() }
        if (numNonEmptyCircuits == 1) {
            println("Just made 1 circuit")
            println(" first: ${pair.first}")
            println(" second: ${pair.second}")
            val part2Result = pair.first.x.toLong() * pair.second.x.toLong()
            println(" result: $part2Result")
            break
        }
    }
}

private fun parsePoint3i(line: String): Point3i {
    val (x, y, z) = line.split(",")
    return Point3i(x.toInt(), y.toInt(), z.toInt())
}

