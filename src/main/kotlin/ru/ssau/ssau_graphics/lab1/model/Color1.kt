package ru.ssau.ssau_graphics.lab1.model

class Color1(
    val light: Int,
) : Color {
    override fun withBrightness(brightness: Double): Color {
        assert(brightness in 0.0..1.0) { "Яркость должна быть от 0 до 1!" }
        return Color1((light * brightness).toInt())
    }
}
