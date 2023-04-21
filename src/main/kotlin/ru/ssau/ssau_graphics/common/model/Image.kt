package ru.ssau.ssau_graphics.common.model

class Image<ColorType>(
    val height: Int,
    val width: Int,
    val matrix: Array<ColorType>,
) {
    operator fun set(row: Int, column: Int, color: ColorType) {
        matrix[normalRow(row) * width + column] = color
    }

    operator fun get(row: Int, column: Int): ColorType =
        matrix[normalRow(row) * width + column]

    fun getNotInversed(row: Int, column: Int): ColorType =
        matrix[row * width + column]

    fun normalRow(row: Int) = (height - 1) - row
}
