package ru.ssau.ssau_graphics.lab1.draw.polygon

import ru.ssau.ssau_graphics.lab1.draw.line.DrawLine
import ru.ssau.ssau_graphics.lab1.model.Image
import ru.ssau.ssau_graphics.lab1.model.Point2d
import ru.ssau.ssau_graphics.lab1.model.Polygon
import kotlin.math.round

fun <ColorType> drawPolygonXY(
    image: Image<ColorType>,
    polygon: Polygon,
    drawer: DrawLine<ColorType>,
    color: ColorType,
    xMult: Int,
    xDelta: Int,
    yMult: Int,
    yDelta: Int,
) {
    val vertex1 = Point2d(round(xMult * polygon.v1.x + xDelta).toInt(), round(yMult * polygon.v2.y + yDelta).toInt())
    val vertex2 = Point2d(round(xMult * polygon.v2.x + xDelta).toInt(), round(yMult * polygon.v3.y + yDelta).toInt())
    val vertex3 = Point2d(round(xMult * polygon.v3.x + xDelta).toInt(), round(yMult * polygon.v1.y + yDelta).toInt())
    drawer.drawLine(image, vertex1, vertex2, color)
    drawer.drawLine(image, vertex2, vertex3, color)
    drawer.drawLine(image, vertex3, vertex1, color)
}
