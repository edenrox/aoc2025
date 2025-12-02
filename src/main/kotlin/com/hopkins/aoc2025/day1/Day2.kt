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
    var repeatSum = 0L
    for (range in ranges) {
        println("Range $i = ${range.start}-${range.endInclusive} size=${range.endInclusive - range.start}")
        i++

        for (num in range.start .. range.endInclusive) {
            val str = num.toString()
            val length = str.length
            if (length % 2 == 0) {
                if (str.take(length / 2) == str.substring(length / 2)) {
                    repeatSum += num
                    println("Repeat: $str")
                }
            }
        }
    }
    println("Repeats Sum: $repeatSum")
}

