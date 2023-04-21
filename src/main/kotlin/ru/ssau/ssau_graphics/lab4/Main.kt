package ru.ssau.ssau_graphics.lab4

import ru.ssau.ssau_graphics.common.io.readPolygonalModelFromFile
import ru.ssau.ssau_graphics.common.model.*
import ru.ssau.ssau_graphics.common.draw.drawPolygonTriangleWithZ
import ru.ssau.ssau_graphics.common.draw.pivot
import ru.ssau.ssau_graphics.common.draw.prepare
import ru.ssau.ssau_graphics.common.math.findNormal
import ru.ssau.ssau_graphics.common.math.normalizedScalarMult
import ru.ssau.ssau_graphics.common.utils.createImage3
import ru.ssau.ssau_graphics.common.utils.saveImage
import kotlin.math.abs

fun main() {
    val modelName = "model_1.obj"
    val h = 2500
    val w = 2500
    val model = readPolygonalModelFromFile(modelName)
        .pivot(0.0, 180.0, 0.0)
        .prepare(100000.0, 100000.0, 400.0, 400.0, listOf(0.005, -0.045, 15.0)) // масштабирование
    task16n1(model, h, w)
}

fun task16n1(model: PolygonalModel, h: Int, w: Int) {
    val image = createImage3(h, w)
    val zMatrix = Image(h, w, Array(h * w) { Double.POSITIVE_INFINITY })
    val lightDirection = Coordinate(1.0, 0.0, 1.0)
    model.polygons
        .filter { polygon -> normalizedScalarMult(findNormal(polygon), lightDirection) > 0 } // отсеивание задней стороны
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
    saveImage(image, "task16n1")
}
