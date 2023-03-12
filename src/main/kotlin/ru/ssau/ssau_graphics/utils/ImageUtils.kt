package ru.ssau.ssau_graphics.utils

import ru.ssau.ssau_graphics.lab1.model.Color3
import ru.ssau.ssau_graphics.lab1.model.Image
import ru.ssau.ssau_graphics.lab1.model.Renderer

const val extension = "png"

fun createImage3(h: Int, w: Int): Image<Color3> =
    Image(
        height = h,
        width = w,
        matrix = Array(h * w) {
            Color3(0, 0, 0)
        },
    )

fun saveImage(image: Image<Color3>, name: String) =
    Renderer(formatName = extension).writeToFileTriple(
        image = image,
        filename = "$name.$extension",
    )
