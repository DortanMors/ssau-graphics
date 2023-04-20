package ru.ssau.ssau_graphics.lab2.math

import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.get
import ru.ssau.ssau_graphics.lab1.model.Coordinate
import ru.ssau.ssau_graphics.lab1.model.Polygon
import kotlin.math.sqrt

typealias Matrix = List<List<Double>>

fun findNormal(polygon: Polygon): Coordinate =
    polygon.run {
        val vector1 = Coordinate(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z)
        val vector2 = Coordinate(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z)
        Coordinate(
            vector1.y * vector2.z - vector1.z * vector2.y,
            vector1.z * vector2.x - vector1.x * vector2.z,
            vector1.x * vector2.y - vector1.y * vector2.x,
        )
    }

fun vectorMult(a: Coordinate, b: Coordinate): Coordinate =
    Coordinate(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x)

val Coordinate.length
    get() = sqrt(x * x + y * y + z * z)

fun normalizedScalarMult(left: Coordinate, right: Coordinate): Double =
    (left.x * right.x + left.y * right.y + left.z * right.z) / (left.length * right.length)

operator fun Matrix.times(right: Matrix): Matrix {
    val result = mk.ndarray(this) dot mk.ndarray(right)
    return List(result.shape[0]) { i ->
        List(result.shape[1]) { j ->
            result[i, j]
        }
    }
}
