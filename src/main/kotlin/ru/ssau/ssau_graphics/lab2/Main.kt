package ru.ssau.ssau_graphics.lab2

import ru.ssau.ssau_graphics.lab1.io.readPolygonalModelFromFile
import ru.ssau.ssau_graphics.lab1.model.Color3
import ru.ssau.ssau_graphics.lab1.model.Coordinate
import ru.ssau.ssau_graphics.lab1.model.Point2d
import ru.ssau.ssau_graphics.lab1.model.Polygon
import ru.ssau.ssau_graphics.lab2.barycentric.toBarycentric
import ru.ssau.ssau_graphics.lab2.draw.drawPolygonTriangle
import ru.ssau.ssau_graphics.utils.createImage3
import ru.ssau.ssau_graphics.utils.saveImage
import kotlin.random.Random

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

private fun task10n1(h: Int = 100, w: Int = 100) {
    listOf(
        Polygon(
            v1 = Coordinate(0.0, 0.0, 0.0),
            v2 = Coordinate(47.0, 10.0, 99.0),
            v3 = Coordinate(22.0, 22.0, 22.0),
        ),
        Polygon(
            v1 = Coordinate(-10.0, -10.0, 0.0),
            v2 = Coordinate(-47.0, 10.0, 99.0),
            v3 = Coordinate(99.0, 101.0, 22.0),
        ),
        Polygon(
            v1 = Coordinate(10.0, 10.0, 0.0),
            v2 = Coordinate(47.0, -10.0, -99.0),
            v3 = Coordinate(-99.0, -120.0, -22.0),
        ),
    ).forEachIndexed { i, polygon ->
        val image = createImage3(h, w)
        drawPolygonTriangle(
            image,
            polygon,
            Color3(255, 255, 255),
        )
        saveImage(image, "task10n$i")
    }
}

fun task11n1(filename: String, h: Int = 1000, w: Int = 1000, xMult: Int = 5000, xDelta: Int = 500, yMult: Int = 5000, yDelta: Int = 500) {
    val image = createImage3(1000, 1000)
    val model = readPolygonalModelFromFile(filename)
    model.polygons
        .map { p ->
            Polygon(
                Coordinate(xMult * p.v1.x + xDelta, yMult * p.v1.y + yDelta, 0.0),
                Coordinate(xMult * p.v2.x + xDelta, yMult * p.v2.y + yDelta, 0.0),
                Coordinate(xMult * p.v3.x + xDelta, yMult * p.v3.y + yDelta, 0.0),
            )
        }.forEach { polygon ->
        drawPolygonTriangle(
            image,
            polygon,
            Color3(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)),
        )
    }
    saveImage(image, "task11n1")
}

fun main() {
    task8n1()
    task10n1()
    task11n1("model_1.obj")
}
