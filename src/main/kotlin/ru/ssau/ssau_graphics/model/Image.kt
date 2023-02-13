package ru.ssau.ssau_graphics.model

class Image<ColorType>(
    val height: Int,
    val width: Int,
    val matrix: Array<ColorType>,
) {
    operator fun set(row: Int, column: Int, color: ColorType) {
        matrix[row * width + column] = color
    }

    operator fun get(row: Int, column: Int): ColorType =
        matrix[row * width + column]
}
