package ru.ssau.ssau_graphics.lab2.draw

import ru.ssau.ssau_graphics.lab1.model.Image
import ru.ssau.ssau_graphics.lab1.model.Point2d
import ru.ssau.ssau_graphics.lab1.model.Polygon
import ru.ssau.ssau_graphics.lab2.barycentric.toBarycentric
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

fun <ColorType> drawPolygonTriangle(
    image: Image<ColorType>,
    polygon: Polygon,
    color: ColorType,
) {
    // 1-2 Ограничивающий прямоугольник:
    val xMin = max(
        0,
        round(min(min(polygon.v1.x, polygon.v2.x), polygon.v3.x)).toInt(),
    )
    val yMin = max(
        0,
        round(min(min(polygon.v1.y, polygon.v2.y), polygon.v3.y)).toInt(),
    )

    val xMax = min(
        image.width - 1,
        round(max(max(polygon.v1.x, polygon.v2.x), polygon.v3.x)).toInt(),
    )
    val yMax = min(
        image.height - 1,
        round(max(max(polygon.v1.y, polygon.v2.y), polygon.v3.y)).toInt(),
    )

    // 3
    for (x in xMin..xMax) {
        for (y in yMin..yMax) {
            val barCords = toBarycentric(polygon, Point2d(x, y))
            if (barCords.x > 0 && barCords.y > 0 && barCords.z > 0) {
                image[x, y] = color
            }
        }
    }
}
