package ru.ssau.ssau_graphics.lab2

import ru.ssau.ssau_graphics.common.io.readPolygonalModelFromFile
import ru.ssau.ssau_graphics.common.model.*
import ru.ssau.ssau_graphics.common.barycentric.toBarycentric
import ru.ssau.ssau_graphics.common.draw.drawPolygonTriangle
import ru.ssau.ssau_graphics.common.utils.createImage3
import ru.ssau.ssau_graphics.common.utils.saveImage
import kotlin.random.Random

fun main() {
    val modelName = "model_1.obj"
    task7n1()
    task8n1()
    val model = readPolygonalModelFromFile(modelName)
    task10n1(model)
}

// Барицентрические координаты
private fun task7n1() {
    val point = Point2d(1, 5)
    val polygon = Polygon(
        v1 = Coordinate(0.0, 0.0, 0.0),
        v2 = Coordinate(10.0, 10.0, 10.0),
        v3 = Coordinate(0.0, 10.0, 1.0),
    )
    toBarycentric(polygon, point).run {
        println("task7: Сумма барицентрических координат = ($x) + ($y) + ($z) = ${x + y + z}")
    }
}

// Отрисовка треугольников
private fun task8n1(h: Int = 100, w: Int = 100) {
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
        saveImage(image, "task8n$i")
    }
}

// Отрисовка полигонов трёхмерной модели
fun task10n1(model: PolygonalModel, h: Int = 1000, w: Int = 1000, xMult: Int = 5000, xDelta: Int = 500, yMult: Int = 5000, yDelta: Int = 500) {
    val image = createImage3(h, w)
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
    saveImage(image, "task10n1")
}
