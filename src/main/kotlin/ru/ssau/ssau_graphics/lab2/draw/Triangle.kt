package ru.ssau.ssau_graphics.lab2.draw

import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D1Array
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.operations.plus
import ru.ssau.ssau_graphics.lab1.model.*
import ru.ssau.ssau_graphics.lab2.barycentric.toBarycentric
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

fun <ColorType> drawPolygonTriangle(
    image: Image<ColorType>,
    polygon: Polygon,
    color: ColorType,
) {
    // 1-2 Ограничивающий прямоугольник:
    val xMin = max(
        0,
        round(min(min(polygon.v1.x, polygon.v2.x), polygon.v3.x)).toInt(),
    )
    val yMin = max(
        0,
        round(min(min(polygon.v1.y, polygon.v2.y), polygon.v3.y)).toInt(),
    )

    val xMax = min(
        image.width - 1,
        round(max(max(polygon.v1.x, polygon.v2.x), polygon.v3.x)).toInt(),
    )
    val yMax = min(
        image.height - 1,
        round(max(max(polygon.v1.y, polygon.v2.y), polygon.v3.y)).toInt(),
    )

    // 3
    for (x in xMin..xMax) {
        for (y in yMin..yMax) {
            val barCords = toBarycentric(polygon, Point2d(x, y))
            if (barCords.x > 0 && barCords.y > 0 && barCords.z > 0) {
                image[y, x] = color
            }
        }
    }
}

fun <ColorType> drawPolygonTriangleWithZ(
    image: Image<ColorType>,
    zImage: Image<Double>,
    polygon: Polygon,
    color: ColorType,
) {
    // 1-2 Ограничивающий прямоугольник:
    val xMin = max(
        0,
        round(min(min(polygon.v1.x, polygon.v2.x), polygon.v3.x)).toInt(),
    )
    val yMin = max(
        0,
        round(min(min(polygon.v1.y, polygon.v2.y), polygon.v3.y)).toInt(),
    )

    val xMax = min(
        image.width - 1,
        round(max(max(polygon.v1.x, polygon.v2.x), polygon.v3.x)).toInt(),
    )
    val yMax = min(
        round(max(max(polygon.v1.y, polygon.v2.y), polygon.v3.y)).toInt(),
        image.height - 1,
    )

    // 3
    for (x in xMin..xMax) {
        for (y in yMin..yMax) {
            val barCords = toBarycentric(polygon, Point2d(x, y))
            if (barCords.x > 0 && barCords.y > 0 && barCords.z > 0) {
                val zFlex = barCords.x * polygon.v1.z + barCords.y * polygon.v2.z + barCords.z * polygon.v3.z
                if (zFlex < zImage[y, x]) {
                    image[y, x] = color
                    zImage[y, x] = zFlex
                }
            }
        }
    }
}

fun Coordinate.scale(
    k: D2Array<Double>,
    t: D1Array<Double>,
): Coordinate {
    val xyz = mk.ndarray(mk[x, y, z])
    val right = (xyz + t).transpose()
    val result = (k dot right).transpose()
    return Coordinate(
        x = result[0] / result[2],
        y = result[1] / result[2],
        z = z * 1600,
    )
}

// 15. Проективное преобразование
fun PolygonalModel.prepare(
    ax: Double,
    ay: Double,
    u0: Double,
    v0: Double,
    tList: List<Double>,
): PolygonalModel {
    val k = mk.ndarray(mk[
        mk[ax, 0.0, u0],
        mk[0.0, ay, v0],
        mk[0.0, 0.0, 1.0],
    ])
    val t = mk.ndarray(tList)

    return PolygonalModel(
        polygons = polygons.map { polygon ->
            Polygon(
                polygon.v1.scale(k, t),
                polygon.v2.scale(k, t),
                polygon.v3.scale(k, t),
            )
        },
    )
}
