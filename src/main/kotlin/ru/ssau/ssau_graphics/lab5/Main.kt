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
import java.util.*
import kotlin.math.min
import kotlin.system.exitProcess

fun main() {
    val scanner = Scanner(System.`in`)
    print("Введите имя файла с моделью: ")
    val filename = scanner.nextLine()

    print("Введите высоту изображения: ")
    val h = scanner.nextInt()
    checkInput(h > 0, "Высота должна быть положительным числом")

    print("Введите ширину изображения: ")
    val w = scanner.nextInt()
    checkInput(w > 0, "Ширина должна быть положительным числом")

    print("Введите угол поворота вокруг оси X (в градусах): ")
    val alpha = Math.toRadians(scanner.nextDouble())

    print("Введите угол поворота вокруг оси Y (в градусах): ")
    val beta = Math.toRadians(scanner.nextDouble())

    print("Введите угол поворота вокруг оси Z (в градусах): ")
    val gamma = Math.toRadians(scanner.nextDouble())

    val model = readPolygonalModelFromFile(filename)
        .pivot(alpha, beta, gamma) // Поворот модели
    // масштабирование
    // коэффициент соотношения центра модели с центром изображения
    val scaling = min(h/model.height, w/model.width)
    val c = model.center
    // расстояние между центрами увеличенной модели и изображения
    val deltaX = (w/2 - c.x * scaling)
    val deltaY = (h/2 - c.y * scaling)

    task17n1(model, h, w, scaling, scaling, deltaX, deltaY)
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

fun checkInput(condition: Boolean, message: String) {
    if (!condition) {
        println(message)
        exitProcess(1)
    }
}
