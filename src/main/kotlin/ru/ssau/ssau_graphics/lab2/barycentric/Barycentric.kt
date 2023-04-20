package ru.ssau.ssau_graphics.lab2.barycentric

import ru.ssau.ssau_graphics.lab1.model.Coordinate
import ru.ssau.ssau_graphics.lab1.model.Point2d
import ru.ssau.ssau_graphics.lab1.model.Polygon

fun toBarycentric(polygon: Polygon, point: Point2d): Coordinate =
    polygon.run {
        val lambda0 = ((v2.x - v3.x) * (point.y - v3.y) - (v2.y - v3.y) * (point.x - v3.x)) / ((v2.x - v3.x)*(v1.y - v3.y) - (v2.y - v3.y) * (v1.x - v3.x))
        val lambda1 = ((v3.x - v1.x) * (point.y - v1.y) - (v3.y - v1.y) * (point.x - v1.x)) / ((v3.x - v1.x)*(v2.y - v1.y) - (v3.y - v1.y) * (v2.x - v1.x))
        val lambda2 = ((v1.x - v2.x) * (point.y - v2.y) - (v1.y - v2.y) * (point.x - v2.x)) / ((v1.x - v2.x)*(v3.y - v2.y) - (v1.y - v2.y) * (v3.x - v2.x))
        Coordinate(lambda0, lambda1, lambda2)
    }
