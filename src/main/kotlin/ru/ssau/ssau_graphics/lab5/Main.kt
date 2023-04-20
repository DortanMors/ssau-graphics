package ru.ssau.ssau_graphics.lab5

import ru.ssau.ssau_graphics.lab1.io.readPolygonalModelFromFile
import ru.ssau.ssau_graphics.lab1.model.*
import ru.ssau.ssau_graphics.lab2.draw.drawPolygonTriangleWithZ
import ru.ssau.ssau_graphics.lab2.draw.pivot
import ru.ssau.ssau_graphics.lab2.draw.prepare
import ru.ssau.ssau_graphics.lab2.math.findNormal
import ru.ssau.ssau_graphics.lab2.math.normalizedScalarMult
import ru.ssau.ssau_graphics.utils.createImage3
import ru.ssau.ssau_graphics.utils.saveImage
import kotlin.math.abs

fun main() {
    val modelName = "model_1.obj"
    val h = 2500
    val w = 2500
    val model = readPolygonalModelFromFile(modelName)
        .pivot(0.0, 180.0, 0.0)
        .prepare(100000.0, 100000.0, 400.0, 400.0, listOf(0.005, -0.045, 15.0)) // масштабирование
    task17n1(model, h, w)
}

fun task17n1(model: PolygonalModel, h: Int, w: Int) {
    val image = createImage3(h, w)
    val zMatrix = Image(h, w, Array(h * w) { Double.POSITIVE_INFINITY })
    val lightDirection = Coordinate(1.0, 0.0, 1.0)
    model.polygons.zip(model.guroNormals ?: Array<Coordinate?>(model.polygons.size) { null })
        .filter { (polygon, _) -> normalizedScalarMult(findNormal(polygon), lightDirection) > 0 } // отсеивание задней стороны
        .forEach { (polygon, guroNormal) ->
            val shadow = abs(normalizedScalarMult(findNormal(polygon), lightDirection))
            drawPolygonTriangleWithZ(
                image,
                zMatrix,
                polygon,
                Color3(
                    0,
                    255,
                    0,
                ),
                guroNormal = guroNormal,
            ) // отрисовка полигона с z-буффером
        }
    saveImage(image, "task17n1")
}
