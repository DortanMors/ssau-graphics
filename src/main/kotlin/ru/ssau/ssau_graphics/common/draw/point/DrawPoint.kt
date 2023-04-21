package ru.ssau.ssau_graphics.common.draw.point

import ru.ssau.ssau_graphics.common.model.Image
import ru.ssau.ssau_graphics.common.model.Point2d

fun <ColorType> drawPoints(image: Image<ColorType>, points: List<Point2d>, color: ColorType) {
    points.forEach { point ->
        image[point.y, point.x] = color
    }
}
