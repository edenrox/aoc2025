package com.hopkins.aoc2025.common

import kotlin.math.pow
import kotlin.math.sqrt

class Point3i(val x: Int, val y: Int, val z: Int) {

    fun distanceTo(other: Point3i): Double =
        sqrt(
            (x - other.x).toDouble().pow(2) +
                    (y - other.y).toDouble().pow(2) +
                    (z - other.z).toDouble().pow(2))

    override fun toString() = "[$x, $y, $z]"
}