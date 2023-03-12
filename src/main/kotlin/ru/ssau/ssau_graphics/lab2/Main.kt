package ru.ssau.ssau_graphics.lab2

import ru.ssau.ssau_graphics.lab1.model.Color3
import ru.ssau.ssau_graphics.lab1.model.Coordinate
import ru.ssau.ssau_graphics.lab1.model.Point2d
import ru.ssau.ssau_graphics.lab1.model.Polygon
import ru.ssau.ssau_graphics.lab2.barycentric.toBarycentric
import ru.ssau.ssau_graphics.lab2.draw.drawPolygonTriangle
import ru.ssau.ssau_graphics.utils.createImage3
import ru.ssau.ssau_graphics.utils.saveImage

private fun task8n1() {
    val point = Point2d(1, 5)
    val polygon = Polygon(
        v1 = Coordinate(0.0, 0.0, 0.0),
        v2 = Coordinate(10.0, 10.0, 10.0),
        v3 = Coordinate(0.0, 10.0, 1.0),
    )
    toBarycentric(polygon, point).run {
        println("($x) + ($y) + ($z) = ${x + y + z}")
    }
}

private fun task9n1(h: Int = 100, w: Int = 100) {
    val image = createImage3(h, w)
    val polygon = Polygon(
        v1 = Coordinate(0.0, 0.0, 0.0),
        v2 = Coordinate(47.0, 10.0, 99.0),
        v3 = Coordinate(22.0, 22.0, 22.0),
    )
    drawPolygonTriangle(
        image,
        polygon,
        Color3(255, 255, 255),
    )
    saveImage(image, "task9n1")
}

fun main() {
    task8n1()
    task9n1()
}
