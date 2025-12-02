package com.hopkins.aoc2025.day1

import java.io.File

fun main() {
    val line: String = File("input/input02.txt").readLines().first()

    val ranges: List<ClosedRange<Long>> =
        line.split(",").map { range ->
            val (min, max) = range.split("-").map { it.toLong() }
            min.rangeTo(max)
        }
    println("Num ranges: ${ranges.size}")

    var i = 0
    var repeatSumP1 = 0L
    var repeatSumP2 = 0L
    for (range in ranges) {
        println("Range $i = ${range.start}-${range.endInclusive} size=${range.endInclusive - range.start}")
        i++

        for (num in range.start .. range.endInclusive) {
            if (isRepeatP1(num)) {
                //println("Repeat P1: $num")
                repeatSumP1 += num
            }
            if (isRepeatP2(num)) {
                println("Repeat P2: $num")
                repeatSumP2 += num
            }
        }
    }
    println("Repeats Sum Part 1: $repeatSumP1")
    println("Repeats Sum Part 2: $repeatSumP2")
}

private fun isRepeatP1(value: Long): Boolean {
    val str = value.toString()
    val length = str.length
    if (length % 2 == 0) {
        if (str.take(length / 2) == str.substring(length / 2)) {
            return true
        }
    }
    return false
}

private fun isRepeatP2(value: Long): Boolean {
    val str = value.toString()
    val length = str.length
    for (i in 2.. length) {
        if (length % i == 0) {
            val substrlen = length / i
            val chunks = str.chunked(substrlen)
            if (chunks.all { it == chunks[0]}) {
                return true
            }
        }
    }
    return false
}

