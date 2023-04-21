package ru.ssau.ssau_graphics.common.model

class Color3(
    val r: Int,
    val g: Int,
    val b: Int,
) : Color {
    override fun withBrightness(brightness: Double): Color {
        assert(brightness in 0.0..1.0) { "Яркость должна быть от 0 до 1!" }
        return Color3(
            (r * brightness).toInt(),
            (g * brightness).toInt(),
            (b * brightness).toInt(),
        )
    }
}
