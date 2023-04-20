package ru.ssau.ssau_graphics.lab2.math

import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.get
import ru.ssau.ssau_graphics.lab1.model.Coordinate
import ru.ssau.ssau_graphics.lab1.model.Polygon
import kotlin.math.sqrt

typealias Matrix = List<List<Double>>

fun findNormal(polygon: Polygon): Coordinate {
    val a = Coordinate(
        polygon.v2.x - polygon.v1.x,
        polygon.v2.y - polygon.v1.y,
        polygon.v2.z - polygon.v1.z,
    )
    val b = Coordinate(
        polygon.v2.x - polygon.v3.x,
        polygon.v2.y - polygon.v3.y,
        polygon.v2.z - polygon.v3.z,
    )
    return vectorMult(a, b)
}

fun vectorMult(a: Coordinate, b: Coordinate): Coordinate =
    Coordinate(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x)

val Coordinate.length
    get() = sqrt(x * x + y * y + z * z)

fun normalizedScalarMult(left: Coordinate, right: Coordinate): Double {
    val a = mk.ndarray(mk[left.x, left.y, left.z])
    val b = mk.ndarray(mk[right.x, right.y, right.z])
    return a.dot(b) / (left.length * right.length)
}

operator fun Matrix.times(right: Matrix): Matrix {
    val result = mk.ndarray(this) dot mk.ndarray(right)
    return List(result.shape[0]) { i ->
        List(result.shape[1]) { j ->
            result[i, j]
        }
    }
}
