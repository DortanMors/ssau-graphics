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
import ru.ssau.ssau_graphics.lab2.math.dot
import ru.ssau.ssau_graphics.lab2.math.length
import kotlin.math.*

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

fun <ColorType : Color> drawPolygonTriangleWithZ(
    image: Image<ColorType>,
    zImage: Image<Double>,
    polygon: Polygon,
    color: ColorType,
    light: Coordinate? = null,
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
                    polygon.run {
                        val newColor = if (n1 !=null && n2 !=null && n3 != null && light != null) {
                            val l0 = abs(n1 dot light) / (n1.length * light.length)
                            val l1 = abs(n2 dot light) / (n2.length * light.length)
                            val l2 = abs(n3 dot light) / (n3.length * light.length)
                            val brightness = barCords.x * l0 + barCords.y * l1 + barCords.z * l2
                            color.withBrightness(brightness)
                        } else {
                            color
                        }
                        image[y, x] = newColor as ColorType
                        zImage[y, x] = zFlex
                    }
                }
            }
        }
    }
}

fun <ColorType : Color> drawPolygonTriangleWithZImproved(
    image: Image<ColorType>,
    zImage: Image<Double>,
    polygon: Polygon,
    color: ColorType,
    k: D2Array<Double>,
    light: Coordinate? = null,
) {
    val projectivePolygon = polygon.projective(k)

    // 1-2 Ограничивающий прямоугольник:
    val xMin = max(
        0,
        round(min(min(projectivePolygon.v1.x, projectivePolygon.v2.x), projectivePolygon.v3.x)).toInt(),
    )
    val yMin = max(
        0,
        round(min(min(projectivePolygon.v1.y, projectivePolygon.v2.y), projectivePolygon.v3.y)).toInt(),
    )

    val xMax = min(
        image.width - 1,
        round(max(max(projectivePolygon.v1.x, projectivePolygon.v2.x), projectivePolygon.v3.x)).toInt(),
    )
    val yMax = min(
        round(max(max(projectivePolygon.v1.y, projectivePolygon.v2.y), projectivePolygon.v3.y)).toInt(),
        image.height - 1,
    )

    // 3
    for (x in xMin..xMax) {
        for (y in yMin..yMax) {
            val barCords = toBarycentric(projectivePolygon, Point2d(x, y))
            if (barCords.x > 0 && barCords.y > 0 && barCords.z > 0) {
                val zFlex = barCords.x * polygon.v1.z + barCords.y * polygon.v2.z + barCords.z * polygon.v3.z
                if (zFlex < zImage[y, x]) {
                    polygon.run {
                        val newColor = if (n1 !=null && n2 !=null && n3 != null && light != null) {
                            val l0 = abs(n1 dot light) / (n1.length * light.length)
                            val l1 = abs(n2 dot light) / (n2.length * light.length)
                            val l2 = abs(n3 dot light) / (n3.length * light.length)
                            val brightness = barCords.x * l0 + barCords.y * l1 + barCords.z * l2
                            color.withBrightness(brightness)
                        } else {
                            color
                        }
                        image[y, x] = newColor as ColorType
                        zImage[y, x] = zFlex
                    }
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

fun Coordinate.scaleImproved(
    t: List<Double>,
): Coordinate {
    return Coordinate(
        x = x + t[0],
        y = y + t[1],
        z = z + t[2],
    )
}

fun Coordinate.projective(
    k: D2Array<Double>,
): Point2dDouble {
    val xyz = mk.ndarray(mk[x, y, 1.0]).transpose()
    val result = (k dot xyz).transpose()
    return Point2dDouble(
        x = result[0],
        y = result[1],
    )
}

fun Polygon.projective(
    k: D2Array<Double>,
): ProjectivePolygon =
    ProjectivePolygon(
        v1.projective(k),
        v2.projective(k),
        v3.projective(k),
    )

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

    return copy(
        polygons = polygons.map { polygon ->
            polygon.copy(
                v1 = polygon.v1.scale(k, t),
                v2 = polygon.v2.scale(k, t),
                v3 = polygon.v3.scale(k, t),
            )
        },
    )
}

// 15. Проективное преобразование
fun PolygonalModel.prepareImproved(
    t: List<Double>,
): PolygonalModel {
    return copy(
        polygons = polygons.map { polygon ->
            polygon.copy(
                v1 = polygon.v1.scaleImproved(t),
                v2 = polygon.v2.scaleImproved(t),
                v3 = polygon.v3.scaleImproved(t),
            )
        },
    )
}

fun Coordinate.pivot(
    rotate: D2Array<Double>,
): Coordinate {
    val xyz = mk.ndarray(mk[x, y, z])
    val newXYZ = rotate dot xyz.transpose()
    return Coordinate(
        x = newXYZ[0],
        y = newXYZ[1],
        z = newXYZ[2],
    )
}

// 16. Поворот модели
fun PolygonalModel.pivot(
    alphaDegree: Double,
    betaDegree: Double,
    gammaDegree: Double,
): PolygonalModel {
    val alpha = Math.toRadians(alphaDegree)
    val beta = Math.toRadians(betaDegree)
    val gamma = Math.toRadians(gammaDegree)
    val rotate = mk.ndarray(mk[
        mk[1.0, 0.0, 0.0],
        mk[0.0, cos(alpha), sin(alpha)],
        mk[0.0, -sin(alpha), cos(alpha)],
    ]) dot mk.ndarray(mk[
        mk[cos(beta), 0.0, sin(beta)],
        mk[0.0, 1.0, 0.0],
        mk[-sin(beta), 0.0, cos(beta)],
    ]) dot mk.ndarray(mk[
        mk[cos(gamma), sin(gamma), 0.0],
        mk[-sin(gamma), cos(gamma), 0.0],
        mk[0.0, 0.0, 1.0],
    ])
    return copy(
        polygons = polygons.map { polygon ->
            polygon.copy(
                v1 = polygon.v1.pivot(rotate),
                v2 = polygon.v2.pivot(rotate),
                v3 = polygon.v3.pivot(rotate),
            )
        },
    )
}

val PolygonalModel.width: Double
    get() = polygons.maxOf { max(max(it.v1.x, it.v2.x), it.v3.x) } - polygons.minOf { min(min(it.v1.x, it.v2.x), it.v3.x) }

val PolygonalModel.height: Double
    get() = polygons.maxOf { max(max(it.v1.y, it.v2.y), it.v3.y) } - polygons.minOf { min(min(it.v1.y, it.v2.y), it.v3.y) }

val PolygonalModel.depth: Double
    get() = polygons.maxOf { max(max(it.v1.z, it.v2.z), it.v3.z) } - polygons.minOf { min(min(it.v1.z, it.v2.z), it.v3.z) }

val PolygonalModel.center: Coordinate
    get() = Coordinate(
        x = polygons.minOf { min(min(it.v1.x, it.v2.x), it.v3.x)} + width/2,
        y = polygons.minOf { min(min(it.v1.y, it.v2.y), it.v3.y)} + height/2,
        z = polygons.minOf { min(min(it.v1.z, it.v2.z), it.v3.z)} + depth/2,
    )
