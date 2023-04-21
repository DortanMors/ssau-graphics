package ru.ssau.ssau_graphics.lab5

import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import ru.ssau.ssau_graphics.common.draw.*
import ru.ssau.ssau_graphics.common.io.readPolygonalModelFromFile
import ru.ssau.ssau_graphics.common.model.*
import ru.ssau.ssau_graphics.common.math.findNormal
import ru.ssau.ssau_graphics.common.math.normalizedScalarMult
import ru.ssau.ssau_graphics.common.utils.createImage3
import ru.ssau.ssau_graphics.common.utils.saveImage
import kotlin.math.min

fun main() {
    val modelName = "model_1.obj"
    val h = 5000
    val w = 5000
    val model = readPolygonalModelFromFile(modelName)
        .pivot(0.0, 180.0, 180.0)
    // масштабирование
    // коэффициент соотношения центра модели с центром изображения
    val scaling = min(h/model.height, w/model.width)
    val c = model.center
    // расстояние между центрами увеличенной модели и изображения
    val deltaX = (w/2 - c.x * scaling)
    val deltaY = (h/2 - c.y * scaling)

    task17n1(model, h, w, scaling, scaling, deltaX, deltaY,)
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
