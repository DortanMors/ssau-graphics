package ru.ssau.ssau_graphics.lab4

import ru.ssau.ssau_graphics.lab1.io.readPolygonalModelFromFile
import ru.ssau.ssau_graphics.lab1.model.*
import ru.ssau.ssau_graphics.lab2.draw.drawPolygonTriangleWithZ
import ru.ssau.ssau_graphics.lab2.draw.prepare
import ru.ssau.ssau_graphics.lab2.math.findNormal
import ru.ssau.ssau_graphics.lab2.math.normalizedScalarMult
import ru.ssau.ssau_graphics.utils.createImage3
import ru.ssau.ssau_graphics.utils.saveImage
import kotlin.math.abs

fun main() {
    val modelName = "model_1.obj"
    val h = 1000
    val w = 1000
    val model = readPolygonalModelFromFile(modelName)
        .prepare(h, w, 10000.0, 10000.0, listOf(0.005, -0.045, 15.0)) // масштабирование
    task15n2(model, h, w)
}

fun task15n2(model: PolygonalModel, h: Int, w: Int) {
    val image = createImage3(h, w)
    val zMatrix = Image(h, w, Array(h * w) { 10000.0 })
    val lightDirection = Coordinate(1.0, 0.0, 0.4)
    model.polygons
        .filter { polygon -> normalizedScalarMult(findNormal(polygon), lightDirection) < 0 } // отсеивание задней стороны
        .forEach { polygon ->
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
    saveImage(image, "task15n2")
}
