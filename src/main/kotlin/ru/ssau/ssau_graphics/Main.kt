package ru.ssau.ssau_graphics

import ru.ssau.ssau_graphics.model.Color1
import ru.ssau.ssau_graphics.model.Color3
import ru.ssau.ssau_graphics.model.Image
import ru.ssau.ssau_graphics.model.Renderer

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

fun main() {
    task1n1(100, 100)
    task1n2(100, 100)
    task1n3(100, 100)
    task1n4(1024, 1024)
}
