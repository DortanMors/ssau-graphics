package ru.ssau.ssau_graphics.io

import ru.ssau.ssau_graphics.model.Coordinate
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

const val CoordinateDimension = 3
const val CoordinateDelimiter = " "
const val CoordinatePrefix = "v$CoordinateDelimiter"

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
