package ru.ssau.ssau_graphics.common.model

import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO


class Reader {
    fun readFromFile(filename: String) {
        val image: BufferedImage = ImageIO.read(
            URL(filename)
        )

        val w = image.width
        val h = image.height

        val dataBuffInt = image.getRGB(0, 0, w, h, null, 0, w)
    }
}
