package ru.ssau.ssau_graphics.lab4

import ru.ssau.ssau_graphics.lab1.model.*
import ru.ssau.ssau_graphics.lab2.draw.drawPolygonTriangleWithZ
import ru.ssau.ssau_graphics.lab2.math.findNormal
import ru.ssau.ssau_graphics.lab2.math.normalizedScalarMult
import ru.ssau.ssau_graphics.utils.createImage3
import ru.ssau.ssau_graphics.utils.saveImage
import kotlin.math.abs

fun main() {

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
