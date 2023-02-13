package ru.ssau.ssau_graphics

import ru.ssau.ssau_graphics.draw.line.*
import ru.ssau.ssau_graphics.model.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

const val extension = "png"

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
    val renderer = Renderer(formatName = extension)
    renderer.writeToFileTriple(
        image = image,
        filename = "task1n3.$extension",
    )
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
    val renderer = Renderer(formatName = extension)
    renderer.writeToFileTriple(
        image = image,
        filename = "task2n1.$extension",
    )
}

private fun task2n2(h: Int, w: Int, deltaT: Double) {
    val simpleLine = SimpleLine<Color3>(deltaT = deltaT)
    val image = Image(
        height = h,
        width = w,
        matrix = Array(h * w) {
            Color3(0, 0, 0)
        },
    )
    val start = Point2d(100, 100)
    image.drawSun(
        lineDrawer = simpleLine,
        color = Color3(255, 255, 255),
        start = start,
        radius = 95.0,
    )
    val renderer = Renderer(formatName = extension)
    renderer.writeToFileTriple(
        image = image,
        filename = "task2n2.$extension",
    )
}

private fun task2n3(h: Int, w: Int) {
    val simpleLine = ImprovementSimple<Color3>()
    val image = Image(
        height = h,
        width = w,
        matrix = Array(h * w) {
            Color3(0, 0, 0)
        },
    )
    image.drawSun(
        lineDrawer = simpleLine,
        color = Color3(255, 255, 255),
        start = Point2d(100, 100),
        radius = 95.0,
    )
    val renderer = Renderer(formatName = extension)
    renderer.writeToFileTriple(
        image = image,
        filename = "task2n3.$extension",
    )
}

private fun task2n4(h: Int, w: Int) {
    val simpleLine = Anysotropic<Color3>()
    val image = Image(
        height = h,
        width = w,
        matrix = Array(h * w) {
            Color3(0, 0, 0)
        },
    )
    image.drawSun(
        lineDrawer = simpleLine,
        color = Color3(255, 255, 255),
        start = Point2d(100, 100),
        radius = 95.0,
    )
    val renderer = Renderer(formatName = extension)
    renderer.writeToFileTriple(
        image = image,
        filename = "task2n4.$extension",
    )
}
private fun task2n5(h: Int, w: Int) {
    val simpleLine = AnysotropicImprovement<Color3>()
    val image = Image(
        height = h,
        width = w,
        matrix = Array(h * w) {
            Color3(0, 0, 0)
        },
    )
    image.drawSun(
        lineDrawer = simpleLine,
        color = Color3(255, 255, 255),
        start = Point2d(100, 100),
        radius = 95.0,
    )
    val renderer = Renderer(formatName = extension)
    renderer.writeToFileTriple(
        image = image,
        filename = "task2n5.$extension",
    )
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
