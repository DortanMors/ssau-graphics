package ru.ssau.ssau_graphics.lab3

import ru.ssau.ssau_graphics.common.draw.drawPolygonTriangle
import ru.ssau.ssau_graphics.common.draw.drawPolygonTriangleWithZ
import ru.ssau.ssau_graphics.common.io.readPolygonalModelFromFile
import ru.ssau.ssau_graphics.common.math.findNormal
import ru.ssau.ssau_graphics.common.math.normalizedScalarMult
import ru.ssau.ssau_graphics.common.model.*
import ru.ssau.ssau_graphics.common.utils.createImage3
import ru.ssau.ssau_graphics.common.utils.saveImage
import kotlin.math.abs
import kotlin.random.Random

fun main() {
    val modelName = "model_1.obj"
    val model = readPolygonalModelFromFile(modelName)
    task12n1(model)
    task13n1(model)
    task14n1(model)
}

// Вычисление нормали к поверхности треугольника, отсечение нелицевых граней
fun task12n1(model: PolygonalModel, h: Int = 1000, w: Int = 1000, xMult: Int = 5000, xDelta: Int = 500, yMult: Int = 5000, yDelta: Int = 500) {
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
    saveImage(image, "task12n1")
}

// Базовое освещение
fun task13n1(model: PolygonalModel, h: Int = 1000, w: Int = 1000, xMult: Int = 5000, xDelta: Int = 500, yMult: Int = 5000, yDelta: Int = 500) {
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
    saveImage(image, "task13n1")
}

// z-буффер
fun task14n1(model: PolygonalModel, h: Int = 1000, w: Int = 1000, xMult: Int = 5000, xDelta: Int = 500, yMult: Int = 5000, yDelta: Int = 500) {
    val image = createImage3(h, w)
    val zMatrix = Image(h, w, Array(h * w) { Double.POSITIVE_INFINITY })
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
    saveImage(image, "task14n1")
}
