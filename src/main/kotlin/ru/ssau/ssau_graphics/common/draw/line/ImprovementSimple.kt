package ru.ssau.ssau_graphics.common.draw.line

import ru.ssau.ssau_graphics.common.model.Image
import ru.ssau.ssau_graphics.common.model.Point2d

class ImprovementSimple<ColorType>(
    private val deltaT: Double = 1.0,
) : DrawLine<ColorType> {
    override fun drawLine(image: Image<ColorType>, startPoint: Point2d, endPoint: Point2d, color: ColorType) {
        var x = startPoint.x.toDouble()
        while (x <= endPoint.x) {
            val t = (x - startPoint.x) / (endPoint.x - startPoint.x)
            val y = startPoint.y * (1 - t) + endPoint.y * t
            image[y.toInt(), x.toInt()] = color
            x += deltaT
        }
    }
}
