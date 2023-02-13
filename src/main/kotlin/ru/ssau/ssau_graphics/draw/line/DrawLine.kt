package ru.ssau.ssau_graphics.draw.line

import ru.ssau.ssau_graphics.model.Image
import ru.ssau.ssau_graphics.model.Point2d

interface DrawLine<ColorType> {
    fun drawLine(image: Image<ColorType>, startPoint: Point2d, endPoint: Point2d, color: ColorType)
}
