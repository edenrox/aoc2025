package com.hopkins.aoc2025.day1

import java.io.File
import kotlin.math.pow

fun main() {
    val lines: List<String> = File("input/input03.txt").readLines()

    val joltageSum: Long =
        lines.sumOf { line -> findMaxJoltageP2(convertBankToBatteries(line)) }

    println("Joltage Sum: $joltageSum")
}

private fun convertBankToBatteries(bank: String): List<Int> =
    bank.toCharArray().map { char -> char.digitToInt() }

private fun findMaxJoltageP1(batteries: List<Int>): Long {
    return findMaxJoltage(batteries, 2)
}

private fun findMaxJoltageP2(batteries: List<Int>): Long {
    return findMaxJoltage(batteries, 12)
}

private fun findMaxJoltage(batteries: List<Int>, toEnable: Int): Long {
    if (toEnable == 0) {
        return 0
    }
    val candidates = batteries.dropLast(toEnable - 1)
    val max = candidates.max()
    val maxFirstIndex = batteries.indexOf(max)

    val left = max * (10.0).pow(toEnable - 1).toLong()
    val right = findMaxJoltage(batteries.drop(maxFirstIndex + 1), toEnable - 1)

    return left + right
}

