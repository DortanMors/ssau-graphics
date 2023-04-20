package ru.ssau.ssau_graphics.lab1.model

data class PolygonalModel(
    val polygons: List<Polygon>,
    val guroNormals: List<Coordinate?>? = null,
)
