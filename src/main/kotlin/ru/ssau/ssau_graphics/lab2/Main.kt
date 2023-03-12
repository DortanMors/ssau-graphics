package ru.ssau.ssau_graphics.lab2

import ru.ssau.ssau_graphics.lab1.io.readPolygonalModelFromFile
import ru.ssau.ssau_graphics.lab1.model.*
import ru.ssau.ssau_graphics.lab2.barycentric.toBarycentric
import ru.ssau.ssau_graphics.lab2.draw.drawPolygonTriangle
import ru.ssau.ssau_graphics.lab2.draw.drawPolygonTriangleWithZ
import ru.ssau.ssau_graphics.lab2.math.findNormal
import ru.ssau.ssau_graphics.lab2.math.normalizedScalarMult
import ru.ssau.ssau_graphics.utils.createImage3
import ru.ssau.ssau_graphics.utils.saveImage
import kotlin.math.abs
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

fun task11n1(model: PolygonalModel, h: Int = 1000, w: Int = 1000, xMult: Int = 5000, xDelta: Int = 500, yMult: Int = 5000, yDelta: Int = 500) {
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
    saveImage(image, "task11n1")
}

fun task13n1(model: PolygonalModel, h: Int = 1000, w: Int = 1000, xMult: Int = 5000, xDelta: Int = 500, yMult: Int = 5000, yDelta: Int = 500) {
    val image = createImage3(h, w)
    val lightDirection = Coordinate(0.0, 0.0, 1.0)
    model.polygons
        .filter { polygon ->
            normalizedScalarMult(findNormal(polygon), lightDirection) < 0
        }.map { p ->
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
    saveImage(image, "task13n1")
}

fun task14n1(model: PolygonalModel, h: Int = 1000, w: Int = 1000, xMult: Int = 5000, xDelta: Int = 500, yMult: Int = 5000, yDelta: Int = 500) {
    val image = createImage3(h, w)
    val lightDirection = Coordinate(1.0, 0.0, 0.4)
    model.polygons
        .filter { polygon ->
            normalizedScalarMult(findNormal(polygon), lightDirection) < 0
        }.map { p ->
            Polygon(
                Coordinate(xMult * p.v1.x + xDelta, yMult * p.v1.y + yDelta, 0.0),
                Coordinate(xMult * p.v2.x + xDelta, yMult * p.v2.y + yDelta, 0.0),
                Coordinate(xMult * p.v3.x + xDelta, yMult * p.v3.y + yDelta, 0.0),
            )
        }.forEach { polygon ->
            val shadow = abs(normalizedScalarMult(findNormal(polygon), lightDirection))
            drawPolygonTriangle(
                image,
                polygon,
                Color3(
                    0,
                    (shadow * 255).toInt(),
                    0,
                ),
            )
        }
    saveImage(image, "task14n1")
}

fun task15n1(model: PolygonalModel, h: Int = 1000, w: Int = 1000, xMult: Int = 5000, xDelta: Int = 500, yMult: Int = 5000, yDelta: Int = 500) {
    val image = createImage3(h, w)
    val zMatrix = Image(h, w, Array(h * w) { 10000.0 })
    val lightDirection = Coordinate(1.0, 0.0, 0.4)
    model.polygons
        .filter { polygon ->
            normalizedScalarMult(findNormal(polygon), lightDirection) < 0 // отсеивание задней стороны
        }.map { p ->
            Polygon(
                Coordinate(xMult * p.v1.x + xDelta, yMult * p.v1.y + yDelta, p.v1.z),
                Coordinate(xMult * p.v2.x + xDelta, yMult * p.v2.y + yDelta, p.v2.z),
                Coordinate(xMult * p.v3.x + xDelta, yMult * p.v3.y + yDelta, p.v3.z),
            ) // масштабирование
        }.forEach { polygon ->
            val shadow = abs(normalizedScalarMult(findNormal(polygon), lightDirection))
            drawPolygonTriangleWithZ(
                image,
                zMatrix,
                polygon,
                Color3(
                    0,
                    (shadow * 255).toInt(),
                    0,
                ),
            ) // отрисовка полигона с z-буффером
        }
    saveImage(image, "task15n1")
}

fun main() {
    val modelName = "model_1.obj"
//    task8n1()
//    task10n1()
    val model = readPolygonalModelFromFile(modelName)
//    task11n1(model)
//    task13n1(model)
//    task14n1(model)
    task15n1(model)
}
