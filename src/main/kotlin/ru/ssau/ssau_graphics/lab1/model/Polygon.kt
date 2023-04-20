package ru.ssau.ssau_graphics.lab1.model

data class Polygon(
    val v1: Coordinate,
    val v2: Coordinate,
    val v3: Coordinate,
    val n1: Coordinate? = null,
    val n2: Coordinate? = null,
    val n3: Coordinate? = null,
)
