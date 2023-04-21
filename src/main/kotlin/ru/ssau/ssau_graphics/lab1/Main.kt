package ru.ssau.ssau_graphics.lab1

import ru.ssau.ssau_graphics.common.draw.line.*
import ru.ssau.ssau_graphics.common.draw.point.drawPoints
import ru.ssau.ssau_graphics.common.draw.polygon.drawPolygonXY
import ru.ssau.ssau_graphics.common.io.readModelFromFile
import ru.ssau.ssau_graphics.common.io.readVCoordinatesFromFile
import ru.ssau.ssau_graphics.common.model.*
import ru.ssau.ssau_graphics.common.utils.createImage3
import ru.ssau.ssau_graphics.common.utils.extension
import ru.ssau.ssau_graphics.common.utils.saveImage
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

private fun task1n1(h: Int, w: Int) {
    val image = Image(
        height = h,
        width = w,
        matrix = Array(h * w) { Color1(0) },
    )
    val renderer = Renderer(formatName = extension)
    renderer.writeToFileSingle(
        image = image,
        filename = "task1n1.$extension",
    )
}

private fun task1n2(h: Int, w: Int) {
    val image = Image(
        height = h,
        width = w,
        matrix = Array(h * w) { Color1(255) },
    )
    val renderer = Renderer(formatName = extension)
    renderer.writeToFileSingle(
        image = image,
        filename = "task1n2.$extension",
    )
}

private fun task1n3(h: Int, w: Int) {
    val image = Image(
        height = h,
        width = w,
        matrix = Array(h * w) { Color3(255, 0, 0) },
    )
    saveImage(image, "task1n3")
}

private fun task1n4(h: Int, w: Int) {
    val image = Image(
        height = h,
        width = w,
        matrix = Array(h * w) { index ->
            val x = index % 256
            Color1(x)
        },
    )
    val renderer = Renderer(formatName = extension)
    renderer.writeToFileSingle(
        image = image,
        filename = "task1n4.$extension",
    )
}

private fun task2n1(h: Int, w: Int, deltaT: Double) {
    val simpleLine = SimpleLine<Color3>(deltaT = deltaT)
    val image = Image(
        height = h,
        width = w,
        matrix = Array(h * w) {
            Color3(0, 255, 0)
        },
    )
    simpleLine.drawLine(
        image,
        Point2d(0, 0),
        Point2d(w, h),
        Color3(255, 0, 0),
    )
    saveImage(image, "task2n1")
}

private fun task2n2(h: Int, w: Int, deltaT: Double) {
    val simpleLine = SimpleLine<Color3>(deltaT = deltaT)
    val image = createImage3(h, w)
    testSun(image, simpleLine)
    saveImage(image, "task2n2")
}

private fun task2n3(h: Int, w: Int) {
    val simpleLine = ImprovementSimple<Color3>()
    val image = createImage3(h, w)
    testSun(image, simpleLine)
    saveImage(image, "task2n3")
}

private fun task2n4(h: Int, w: Int) {
    val simpleLine = Anysotropic<Color3>()
    val image = createImage3(h, w)
    testSun(image, simpleLine)
    saveImage(image, "task2n4")
}
private fun task2n5(h: Int, w: Int) {
    val simpleLine = AnysotropicImprovement<Color3>()
    val image = createImage3(h, w)
    testSun(image, simpleLine)
    saveImage(image, "task2n5")
}

private fun task3(filename: String) = readVCoordinatesFromFile(filename)

private fun task4(h: Int, w: Int, coordinates: List<Coordinate>, color: Color3) {
    listOf(
        Task4Params(xMult = 50, xDelta = 500, yMult = 50, yDelta = 500, filename = "task4n1"),
        Task4Params(xMult = 100, xDelta = 500, yMult = 100, yDelta = 500, filename = "task4n2"),
        Task4Params(xMult = 500, xDelta = 500, yMult = 500, yDelta = 500, filename = "task4n3"),
        Task4Params(xMult = 4000, xDelta = 500, yMult = 4000, yDelta = 500, filename = "task4n4"),
    ).forEach { params ->
        createImage3(h, w).let { image ->
            drawPoints(
                image = image,
                color = color,
                points = coordinates.map { point ->
                    Point2d(
                        x = round(params.xMult * point.x + params.xDelta).toInt(),
                        y = round(params.yMult * point.y + params.yDelta).toInt(),
                    )
                },
            )
            saveImage(image, params.filename)
        }
    }
}

private fun task5n1(inputName: String, coordinates: List<Coordinate>): PolygonalModel =
    readModelFromFile(coordinates, inputName)

private fun task6(h: Int, w: Int, model: PolygonalModel, color: Color3) {
    val drawer = AnysotropicImprovement<Color3>()
    val image = createImage3(h, w)
    model.polygons.forEach { polygon ->
        drawPolygonXY(
            image = image,
            polygon = polygon,
            drawer = drawer,
            color = color,
            xMult = 5000, xDelta = 500, yMult = 5000, yDelta = 500,
        )
    }
    saveImage(image, "task6")
}

fun main() {
    task1n1(100, 100)
    task1n2(100, 100)
    task1n3(100, 100)
    task1n4(1024, 1024)

    task2n1(256, 256, 0.01)
    task2n2(200, 200, 0.01)
    task2n3(200, 200)
    task2n4(200, 200)
    task2n5(200, 200)

    val coordinates = task3("model_1.obj")
    task4(1000, 1000, coordinates, Color3(255, 255, 255))
    val model = task5n1("model_1.obj", coordinates)
    task6(1000, 1000, model, Color3(255, 255, 255))
}

private fun <ColorType> Image<ColorType>.drawSun(
    lineDrawer: DrawLine<ColorType>,
    color: ColorType,
    start: Point2d,
    radius: Double,
) {
    for (i in 0..12) {
        val alpha = 2 * PI * i / 13
        lineDrawer.drawLine(
            image = this,
            startPoint = start,
            endPoint = Point2d(
                x = (start.x + radius * cos(alpha)).toInt(),
                y = (start.y + radius * sin(alpha)).toInt(),
            ),
            color = color,
        )
    }
}

private fun testSun(image: Image<Color3>, drawer: DrawLine<Color3>) {
    image.drawSun(
        lineDrawer = drawer,
        color = Color3(255, 255, 255),
        start = Point2d(100, 100),
        radius = 95.0,
    )
}

data class Task4Params(
    val xMult: Int,
    val xDelta: Int,
    val yMult: Int,
    val yDelta: Int,
    val filename: String,
)
