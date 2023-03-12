package ru.ssau.ssau_graphics.draw.point

import ru.ssau.ssau_graphics.model.Image
import ru.ssau.ssau_graphics.model.Point2d

fun <ColorType> drawPoints(image: Image<ColorType>, points: List<Point2d>, color: ColorType) {
    points.forEach { point ->
        image[point.x, point.y] = color
    }
}
