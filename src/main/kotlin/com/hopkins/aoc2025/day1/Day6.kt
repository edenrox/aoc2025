package com.hopkins.aoc2025.day1

import java.io.File

fun main() {
    val lines: List<String> = File("input/input06.txt").readLines().filterNot { it.isBlank() }

    // Part 1
    val allArgs: List<List<Long>> =
        lines.dropLast(1).map { line ->
            line.trim().split(" +".toRegex()).map { it.toLong() } }

    val numProblems = allArgs[0].size
    for (args in allArgs) {
        require(args.size == numProblems) { "Unexpected num args: ${args.size}"}
    }
    println("Num problems: $numProblems")

    val operations = lines.last().trim().split(" +".toRegex()).map { toOperation(it) }
    println("Operations: $operations")

    val problems: List<Problem> =
        allArgs[0].mapIndexed { index, it ->
            val argList: List<Long> = allArgs.map { it[index] }
            Problem(argList, operations[index])
        }

    println("Problems:")
    for (problem in problems) {
        println("$problem = ${problem.calculate()}")
    }
    val total = problems.sumOf { it.calculate() }
    println("Part 1. Total: $total")


    // Part 2
    val maxLen = lines.maxOf { it.length }
    val transposedLines: List<String> =
        (0 until maxLen).map { index -> lines.map { if (index < it.length) {it[index]} else {""} }.joinToString("") }
    println("Transposed lines")
    for (line in transposedLines) {
        println(line)
    }

    val part2Problems: MutableList<Problem> = mutableListOf()
    val args: MutableList<Long> = mutableListOf()
    var operation: Operation? = null
    for (line in transposedLines) {
        if (line.isBlank()) {
            part2Problems.add(Problem(args.toList(), operation!!))
            args.clear()
            continue
        }
        var parseLine = line
        if (line.endsWith("+")) {
            operation = Operation.PLUS
            parseLine = parseLine.dropLast(1)
        } else if (line.endsWith("*")) {
            operation = Operation.MULTIPLY
            parseLine = parseLine.dropLast(1)
        }
        args.add(parseLine.trim().toLong())
    }
    if (args.isNotEmpty()) {
        part2Problems.add(Problem(args.toList(), operation!!))
    }

    println("Part 2 problems:")
    for (problem in part2Problems) {
        println("$problem = ${problem.calculate()}")
    }
    val part2Sum = part2Problems.sumOf {it.calculate()}
    println("Part 2 Sum: $part2Sum")
}

private fun toOperation(value: String): Operation {
    return when (value) {
        "+" -> Operation.PLUS
        "*" -> Operation.MULTIPLY
        else -> throw IllegalArgumentException("Unexpected operation: $value")
    }
}

enum class Operation {
    PLUS,
    MULTIPLY
}

data class Problem(val args: List<Long>, val operation: Operation) {

    fun calculate(): Long {
        return when (operation) {
            Operation.PLUS -> args.sum()
            Operation.MULTIPLY -> args.reduce { acc, it -> acc * it }
        }
    }
}