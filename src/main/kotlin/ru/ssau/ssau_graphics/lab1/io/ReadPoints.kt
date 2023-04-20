package ru.ssau.ssau_graphics.lab1.io

import ru.ssau.ssau_graphics.lab1.model.Coordinate
import ru.ssau.ssau_graphics.lab1.model.Polygon
import ru.ssau.ssau_graphics.lab1.model.PolygonalModel
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

const val CoordinateDimension = 3
const val CoordinateDelimiter = " "
const val CoordinatePrefix = "v$CoordinateDelimiter"
const val NormalPrefix = "vn$CoordinateDelimiter"

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

fun readModelFromFileSplit(coordinates: List<Coordinate>, filename: String): PolygonalModel {
    val polygons = mutableListOf<Polygon>()
    File(filename).readLines().forEach { line ->
        val vertexIndices = line.split(CoordinateDelimiter).takeLast(3)
            .map { triple -> triple.split(PolygonDelimiter).first().toInt() }
    }
    return PolygonalModel(polygons)
}

fun readModelFromFile(coordinates: List<Coordinate>, filename: String): PolygonalModel {
    val polygons = mutableListOf<Polygon>()
    val words = mutableListOf<String>()
    val lines = File(filename).readLines()
    val normals: List<Coordinate>? = lines.filter { it.startsWith(NormalPrefix) }.map { line ->
        val (x, y, z) = line.split(CoordinateDelimiter).drop(1).map { it.toDouble() }
        Coordinate(x, y, z)
    }.takeIf { it.isNotEmpty() }
    for (line in lines) {
        when {
            line[0] == 'f' -> {
                var i: Int = 1
                for (k in 0..2) {
                    words.add("")
                    i++
                    while (line[i] != '/') { //считываем 3 слова, занося первое в words
                        words[k] = words[k] + line[i]
                        i++
                    }
                    i++ // минуем '/'
                    while (line[i] != '/') {
                        i++
                    }
                    i++ // минуем '/'
                    while (line[i] != ' ') {
                        i++
                        if (i == line.length) {
                            break
                        }
                    }
                }
                polygons.add(
                    Polygon(
                        v1 = coordinates[words[0].toInt() - 1],
                        v2 = coordinates[words[1].toInt() - 1],
                        v3 = coordinates[words[2].toInt() - 1],
                        n1 = normals?.getOrNull(words[0].toInt() - 1),
                        n2 = normals?.getOrNull(words[1].toInt() - 1),
                        n3 = normals?.getOrNull(words[2].toInt() - 1),
                    )
                )
                words.clear()
            }
        }
    }
    return PolygonalModel(polygons)
}

fun readPolygonalModelFromFile(filename: String): PolygonalModel =
    readModelFromFile(readVCoordinatesFromFile(filename), filename)
