package ru.ssau.ssau_graphics.lab5

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import ru.ssau.ssau_graphics.lab1.io.readPolygonalModelFromFile
import ru.ssau.ssau_graphics.lab1.model.*
import ru.ssau.ssau_graphics.lab2.draw.*
import ru.ssau.ssau_graphics.lab2.math.findNormal
import ru.ssau.ssau_graphics.lab2.math.normalizedScalarMult
import ru.ssau.ssau_graphics.utils.createImage3
import ru.ssau.ssau_graphics.utils.saveImage

fun main() {
    val modelName = "model_1.obj"
    val h = 1000
    val w = 1000
    val model = readPolygonalModelFromFile(modelName)
        .pivot(0.0, 0.0, 0.0)
        .prepareImproved(listOf(0.005, -0.045, 0.3)) // масштабирование
    task17n1(model, h, w, 2000.0, 2000.0, 400.0, 400.0,)
}

fun task17n1(model: PolygonalModel, h: Int, w: Int, ax: Double, ay: Double, u0: Double, v0: Double) {
    val image = createImage3(h, w)
    val zMatrix = Image(h, w, Array(h * w) { Double.POSITIVE_INFINITY })
    val lightDirection = Coordinate(0.0, 0.0, 1.0)
    model.polygons
        .filter { polygon -> -normalizedScalarMult(findNormal(polygon), lightDirection) > 0 } // отсеивание задней стороны
        .forEach { polygon ->
            drawPolygonTriangleWithZImproved(
                image = image,
                zImage = zMatrix,
                polygon = polygon,
                color = Color3(
                    255,
                    255,
                    255,
                ),
                k = mk.ndarray(mk[
                    mk[ax, 0.0, u0],
                    mk[0.0, ay, v0],
                    mk[0.0, 0.0, 1.0],
                ]),
                light = lightDirection,
            ) // отрисовка полигона с z-буффером
        }
    saveImage(image, "task17n1")
}
