package ru.ssau.ssau_graphics.common.draw.line

import ru.ssau.ssau_graphics.common.model.Image
import ru.ssau.ssau_graphics.common.model.Point2d

interface DrawLine<ColorType> {
    fun drawLine(image: Image<ColorType>, startPoint: Point2d, endPoint: Point2d, color: ColorType)
}
