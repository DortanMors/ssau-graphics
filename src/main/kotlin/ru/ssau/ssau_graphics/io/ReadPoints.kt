package ru.ssau.ssau_graphics.io

import ru.ssau.ssau_graphics.model.Coordinate
import ru.ssau.ssau_graphics.model.Polygon
import ru.ssau.ssau_graphics.model.PolygonalModel
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

const val CoordinateDimension = 3
const val CoordinateDelimiter = " "
const val CoordinatePrefix = "v$CoordinateDelimiter"

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
    for (line in File(filename).readLines()) {
        if (line[0] == 'f') {
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
                    coordinates[words[0].toInt() - 1],
                    coordinates[words[1].toInt() - 1],
                    coordinates[words[2].toInt() - 1],
                )
            )
            words.clear()
        }
    }
    return PolygonalModel(polygons)
}
