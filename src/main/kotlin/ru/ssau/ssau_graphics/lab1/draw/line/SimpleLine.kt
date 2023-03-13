package ru.ssau.ssau_graphics.lab1.draw.line

import ru.ssau.ssau_graphics.lab1.model.Image
import ru.ssau.ssau_graphics.lab1.model.Point2d

class SimpleLine<ColorType>(
    private val deltaT: Double,
) : DrawLine<ColorType> {
    override fun drawLine(image: Image<ColorType>, startPoint: Point2d, endPoint: Point2d, color: ColorType) {
        var t = 0.0
        while (t < 1.0) {
            val x = (startPoint.x * (1 - t) + endPoint.x * t).toInt()
            val y = (startPoint.y * (1 - t) + endPoint.y * t).toInt()
            image[y, x] = color
            t += deltaT
        }
    }
}
