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
    task16n1(
        model,
        h, w, // Размер изображения
        0.0, 0.0, 0.0, // Углы поворота
        100000.0, 100000.0,  // Масштабирование
        400.0, 400.0,        // Смещение изображения
        listOf(0.005, -0.045, 15.0), // Смешение модели
        Coordinate(1.0, 0.0, 1.0), // Направление света
    )
}

// Рендер повёрнутой и масштабированной модели с z-буффером и простым освещением
fun task16n1(
    model: PolygonalModel,
    h: Int, w: Int, // Размер изображения
    alphaDegree: Double, betaDegree: Double, gammaDegree: Double, // Углы поворота
    ax: Double, ay: Double, // Масштабирование
    u0: Double, v0: Double, // Смещение изображения
    tList: List<Double>,    // Смешение модели
    lightDirection: Coordinate, // Направление света
) {
    val preparedModel = model
        .pivot(alphaDegree, betaDegree, gammaDegree) // поворот модели
        .prepare(ax, ay, u0, v0, tList)              // масштабирование
    val image = createImage3(h, w)
    val zMatrix = Image(h, w, Array(h * w) { Double.POSITIVE_INFINITY })
    preparedModel.polygons
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
