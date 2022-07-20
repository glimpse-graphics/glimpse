/*
 * Copyright 2020-2022 Slawomir Czerwinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.hud.text

import com.jogamp.opengl.GLProfile
import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.textures.BufferedImageProvider
import graphics.glimpse.textures.TextureImageSource
import graphics.glimpse.types.Vec4
import java.awt.Color
import java.awt.Rectangle
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import kotlin.math.ceil

/**
 * A builder for a [TextureImageSource] containing text.
 *
 * @since v1.2.0
 */
actual class TextTextureImageSourceBuilder {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    private var text: String = ""

    private var font: Font = Font.DEFAULT

    private var color: Vec4 = Vec4(x = 1f, y = 1f, z = 1f, w = 1f)

    private var paddingLeft: Int = 0
    private var paddingRight: Int = 0
    private var paddingTop: Int = 0
    private var paddingBottom: Int = 0

    private var width: Int = 0
    private var height: Int = 0

    /**
     * Will build a texture source containing given [text].
     */
    actual fun fromText(text: String): TextTextureImageSourceBuilder {
        this.text = text
        return this
    }

    /**
     * Will build a texture source containing text drawn with given [font].
     */
    actual fun withFont(font: Font): TextTextureImageSourceBuilder {
        this.font = font
        return this
    }

    /**
     * Will build a texture source containing text drawn with given [color].
     */
    actual fun withColor(color: Vec4): TextTextureImageSourceBuilder {
        this.color = color
        return this
    }

    /**
     * Will build a texture source containing text with given padding, equal on [all] sides.
     */
    actual fun withPadding(all: Int): TextTextureImageSourceBuilder =
        withPadding(left = all, top = all, right = all, bottom = all)

    /**
     * Will build a texture source containing text with given [horizontal] and [vertical] padding.
     */
    actual fun withPadding(horizontal: Int, vertical: Int): TextTextureImageSourceBuilder =
        withPadding(left = horizontal, top = vertical, right = horizontal, bottom = vertical)

    /**
     * Will build a texture source containing text with given [left], [top], [right] and [bottom] padding.
     */
    actual fun withPadding(left: Int, top: Int, right: Int, bottom: Int): TextTextureImageSourceBuilder {
        paddingLeft = left
        paddingRight = right
        paddingTop = top
        paddingBottom = bottom
        return this
    }

    /**
     * Will build a texture source containing text with given [width] and [height].
     *
     * If either [width] or [height] is 0, the dimension of the resulting texture
     * will be adjusted to wrap the text with padding.
     */
    actual fun withSize(width: Int, height: Int): TextTextureImageSourceBuilder {
        this.width = width
        this.height = height
        return this
    }

    /**
     * Builds a [TextureImageSource] containing text, with the provided parameters.
     */
    actual fun build(): TextureImageSource {
        return TextureImageSource.builder()
            .fromBufferedImage(TextBufferedImageProvider())
            .build()
    }

    /**
     * Builds a prepared [TextureImageSource] containing text, with the provided parameters and
     * a given OpenGL [profile].
     *
     * _Note: The resulting [TextureImageSource] will be quicker in setting
     * a texture image, but it will also consume more memory._
     */
    fun buildPrepared(profile: GLProfile): TextureImageSource {
        return TextureImageSource.builder()
            .fromBufferedImage(TextBufferedImageProvider())
            .buildPrepared(profile)
    }

    private inner class TextBufferedImageProvider : BufferedImageProvider {

        override fun createBufferedImage(): BufferedImage {
            val textBounds = calculateTextBounds()
            val imageWidth = if (width > 0) width else (textBounds.width + paddingLeft + paddingRight)
            val imageHeight = if (height > 0) height else (textBounds.height + paddingTop + paddingBottom)
            val textAreaWidth = imageWidth - paddingLeft - paddingRight
            val textAreaHeight = imageHeight - paddingTop - paddingBottom

            logger.debug(
                message = "Creating texture image for text: '$text' of size: ${imageWidth}x${imageHeight}"
            )

            val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR)

            val graphics = image.createGraphics()
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            graphics.font = font.awtFont
            graphics.color = Color(color.r, color.g, color.b, color.a)
            graphics.drawString(
                text,
                paddingLeft + (textAreaWidth - textBounds.width) / 2,
                paddingTop + (textAreaHeight - textBounds.height) / 2 + graphics.fontMetrics.ascent
            )
            graphics.dispose()

            return image
        }

        private fun calculateTextBounds(): Rectangle {
            val image = BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY)
            val graphics = image.createGraphics()
            graphics.font = font.awtFont
            val textBounds = graphics.fontMetrics.getStringBounds(text, graphics)
            graphics.dispose()
            return Rectangle(ceil(textBounds.width).toInt(), ceil(textBounds.height).toInt())
        }
    }
}
