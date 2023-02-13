package ru.ssau.ssau_graphics.model

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


class Renderer(
    val formatName: String,
) {
    fun writeToFileTriple(image: Image<Color3>, filename: String) {
        val bufferedImage = BufferedImage(
            image.width,
            image.height,
            BufferedImage.TYPE_INT_RGB,
        )
        for (row in 0 until image.height) {
            for (column in 0 until image.width) {
                image[row, column].run {
                    bufferedImage.setRGB(
                        column,
                        row,
                        Color(r, g, b).rgb,
                    )
                }
            }
        }
        val outputFile = File(filename)
        ImageIO.write(bufferedImage, formatName, outputFile)
    }

    fun writeToFileSingle(image: Image<Color1>, filename: String) {
        val bufferedImage = BufferedImage(
            image.width,
            image.height,
            BufferedImage.TYPE_INT_RGB,
        )
        for (row in 0 until image.height) {
            for (column in 0 until image.width) {
                image[row, column].run {
                    bufferedImage.setRGB(
                        column,
                        row,
                        Color(light, light, light).rgb,
                    )
                }
            }
        }
        val outputFile = File(filename)
        ImageIO.write(bufferedImage, formatName, outputFile)
    }
}
