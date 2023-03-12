package ru.ssau.ssau_graphics.lab2.barycentric

import ru.ssau.ssau_graphics.lab1.model.Coordinate
import ru.ssau.ssau_graphics.lab1.model.Point2d
import ru.ssau.ssau_graphics.lab1.model.Polygon

fun toBarycentric(polygon: Polygon, point: Point2d): Coordinate {
    val xUp = ((polygon.v2.x - polygon.v3.x) * (point.y - polygon.v3.y) - (polygon.v2.y - polygon.v3.y) * (point.x - polygon.v3.x))
    val xDiv =  ((polygon.v2.x - polygon.v3.x) * (polygon.v1.y - polygon.v3.y) - (polygon.v2.y - polygon.v3.y) * (polygon.v1.x - polygon.v3.x))
    val yUp =((polygon.v3.x - polygon.v1.x) * (point.y - polygon.v1.y) - (polygon.v3.y - polygon.v1.y) * (point.x - polygon.v1.x))
    val yDiv = ((polygon.v3.x - polygon.v1.x) * (polygon.v2.y - polygon.v1.y) - (polygon.v3.y - polygon.v1.y) * (polygon.v2.x - polygon.v1.x))
    val zUp = ((polygon.v1.x - polygon.v2.x) * (point.y - polygon.v2.y) - (polygon.v1.y - polygon.v2.y) * (point.x - polygon.v2.x))
    val zDiv = ((polygon.v1.x - polygon.v2.x) * (polygon.v3.y - polygon.v2.y) - (polygon.v1.y - polygon.v2.y) * (polygon.v3.x - polygon.v2.x))
    return Coordinate(
        x = if (xDiv != 0.0) xUp / xDiv else 0.0,
        y = if (yDiv != 0.0) yUp / yDiv else 0.0,
        z = if (zDiv != 0.0) zUp / zDiv else 0.0,
    )
}
