package ru.ssau.ssau_graphics.common.draw.line

import ru.ssau.ssau_graphics.common.model.Image
import ru.ssau.ssau_graphics.common.model.Point2d
import kotlin.math.abs

class AnysotropicImprovement<ColorType>(
    private val eps: Double = 0.5,
    private val deltaT: Double = 1.0
) : DrawLine<ColorType> {
    override fun drawLine(image: Image<ColorType>, startPoint: Point2d, endPoint: Point2d, color: ColorType) {
        var steep = false
        var (x0, y0) = startPoint
        var (x1, y1) = endPoint
        if (abs(startPoint.x - endPoint.x) < abs(startPoint.y - endPoint.y)) {
            (x0 to y0).let { (left, right) ->
                x0 = right
                y0 = left
            }

            (x1 to y1).let { (left, right) ->
                x1 = right
                y1 = left
            }

            steep = true
        }
        if (x0 > x1) {
            (x0 to x1).let { (left, right) ->
                x0 = right
                x1 = left
            }

            (y0 to y1).let { (left, right) ->
                y0 = right
                y1 = left
            }
        }
        val dx = x1 - x0
        val dy = y1 - y0
        val dError = abs(dy.toDouble() / dx.toDouble())
        var error = 0.0
        val y = y0

        var x = x0.toDouble()
        while (x <= x1) {
            val t = (x - x0) / (x1 - x0)
            var y = y0 * (1 - t) + y1 * t
            if (steep) {
                image[x.toInt(), y.toInt()] = color
            } else {
                image[y.toInt(), x.toInt()] = color
            }
            error += dError
            if (error > eps) {
                y += if (y1 > y0) 1 else -1
                error -= 1
            }
            x += deltaT
        }
    }
}
