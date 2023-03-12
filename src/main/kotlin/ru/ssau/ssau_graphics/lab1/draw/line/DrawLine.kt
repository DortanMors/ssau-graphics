package ru.ssau.ssau_graphics.lab1.draw.line

import ru.ssau.ssau_graphics.lab1.model.Image
import ru.ssau.ssau_graphics.lab1.model.Point2d

interface DrawLine<ColorType> {
    fun drawLine(image: Image<ColorType>, startPoint: Point2d, endPoint: Point2d, color: ColorType)
}
