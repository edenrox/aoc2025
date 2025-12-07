package com.hopkins.aoc2025.day1

import java.io.File

fun main() {
    val lines : List<String> = File("input/input01.txt").readLines()
    println("Num lines: ${lines.size}")

    var current = 50

    var numRestZero = 0
    var numPastZero = 0
    println("Current | Value")
    for (line in lines) {
        println("$current | $line")
        val wasZero = current == 0
        val isNegative = line.startsWith("L")
        val ticks = line.drop(1).toInt()

        val increment = ticks % 100
        val wraps = ticks / 100
        numPastZero += wraps

        if (isNegative) {
            current -= increment
        } else {
            current += increment
        }
        if (current >= 100) {
            current -= 100
            if (current != 0) {
                numPastZero++
            }
        }
        if (current < 0 && wasZero) {
            current += 100
        }
        if (current < 0) {
            current += 100
            numPastZero++
        }

        if (current == 0) {
            println("rest")
            numRestZero += 1
        }
    }

    val numZero = numRestZero + numPastZero
    println("Part 1: $numRestZero")
    println("Part 2: $numZero")
}