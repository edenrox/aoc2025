package com.hopkins.aoc2025.day1

import java.io.File


fun main() {
    val lock = Lock(50, 100)

    val lines : List<String> = File("input/input01.txt").readLines()
    println("Num lines: ${lines.size}")

    val trimmedLines = lines.map { it.trim() }.filterNot { it.isEmpty() }
    var numZeros = 0
    for (line in trimmedLines) {
        val value = line.substring(1).toInt()
        if (line.startsWith("L")) {
            lock.turnLeft(value)
        } else if (line.startsWith("R")) {
            lock.turnRight(value)
        } else {
            throw IllegalArgumentException("Unexpected line: ${line}")
        }
        println("Current: ${lock.current} isZero: ${lock.isZero()}")
        if (lock.isZero()) {
            numZeros++
        }
    }
    println("Num Zeros: $numZeros")

}

class Lock(start: Int, private val max: Int) {
    var current: Int = start

    fun turnRight(value: Int) {
        current = (current + value) % max
    }

    fun turnLeft(value: Int) {
        turnRight(max - value)
    }

    fun isZero(): Boolean =
        current == 0
}