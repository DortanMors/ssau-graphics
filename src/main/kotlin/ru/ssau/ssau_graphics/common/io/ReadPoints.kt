package ru.ssau.ssau_graphics.common.io

import ru.ssau.ssau_graphics.common.model.Coordinate
import ru.ssau.ssau_graphics.common.model.Polygon
import ru.ssau.ssau_graphics.common.model.PolygonalModel
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

const val CoordinateDimension = 3
const val CoordinateDelimiter = " "
const val CoordinatePrefix = "v$CoordinateDelimiter"
const val NormalPrefix = "vn$CoordinateDelimiter"
const val PolygonPrefix = "f$CoordinateDelimiter"

const val PolygonDelimiter = "/"

fun readVCoordinatesFromFile(filename: String): List<Coordinate> =
    File(filename).let { file ->
        runCatching {
            BufferedReader(FileReader(file)).use { reader ->
                reader.readLines()
                    .filter { it.startsWith(CoordinatePrefix) }
                    .map { line ->
                        val (x, y, z) = line.split(CoordinateDelimiter).takeLast(CoordinateDimension).map { it.toDouble() }
                        Coordinate(x, y, z)
                    }
            }
        }.getOrDefault(emptyList())
    }

fun readModelFromFile(coordinates: List<Coordinate>, filename: String): PolygonalModel {
    val lines = File(filename).readLines()
    val normals: List<Coordinate>? = lines.filter { it.startsWith(NormalPrefix) }.map { line ->
        val (x, y, z) = line.split(CoordinateDelimiter).drop(1).map { it.toDouble() }
        Coordinate(x, y, z)
    }.takeIf { it.isNotEmpty() }
    val polygons = lines.filter { it.startsWith(PolygonPrefix) }.map { line ->
        val vertexToNormal = line.trim().split(CoordinateDelimiter).drop(1).map { element ->
            val indices = element.split(PolygonDelimiter)
            coordinates[indices[0].toInt() - 1] to indices.getOrNull(2)?.toInt()?.let { normals?.getOrNull(it - 1) }
        }
        Polygon(
            v1 = vertexToNormal[0].first,
            v2 = vertexToNormal[1].first,
            v3 = vertexToNormal[2].first,
            n1 = vertexToNormal[0].second,
            n2 = vertexToNormal[1].second,
            n3 = vertexToNormal[2].second,
        )
    }
    return PolygonalModel(polygons)
}

fun readPolygonalModelFromFile(filename: String): PolygonalModel =
    readModelFromFile(readVCoordinatesFromFile(filename), filename)
