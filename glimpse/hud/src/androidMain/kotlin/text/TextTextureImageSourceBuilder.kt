/*
 * Copyright 2020-2023 Glimpse Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.hud.text

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.textures.BitmapProvider
import graphics.glimpse.textures.TextureImageSource
import graphics.glimpse.types.Vec4
import kotlin.math.roundToInt

/**
 * A builder for a [TextureImageSource] containing text.
 *
 * @since v1.2.0
 */
actual class TextTextureImageSourceBuilder {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    private var text: String = ""

    private var font: Font = Font.DEFAULT

    private var color: Vec4<Float> = Vec4(x = 1f, y = 1f, z = 1f, w = 1f)

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
    actual fun withColor(color: Vec4<Float>): TextTextureImageSourceBuilder {
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
     * Builds a [TextureImageSource] with the provided parameters.
     */
    actual fun build(): TextureImageSource {
        return TextureImageSource.builder()
            .fromBitmap(TextBitmapProvider())
            .build()
    }

    /**
     * Builds a prepared [TextureImageSource] containing text, with the provided parameters.
     *
     * _Note: The resulting [TextureImageSource] will be quicker in setting
     * a texture image, but it will also consume more memory._
     */
    fun buildPrepared(): TextureImageSource {
        return TextureImageSource.builder()
            .fromBitmap(TextBitmapProvider())
            .buildPrepared()
    }

    private inner class TextBitmapProvider : BitmapProvider {

        override fun createBitmap(): Bitmap? {
            val textBounds = Rect()
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.typeface = font.typeface
            paint.textSize = font.textSize
            paint.color = Color.argb(
                (color.a * COLOR_CHANNEL_MAX).roundToInt(),
                (color.r * COLOR_CHANNEL_MAX).roundToInt(),
                (color.g * COLOR_CHANNEL_MAX).roundToInt(),
                (color.b * COLOR_CHANNEL_MAX).roundToInt()
            )
            paint.getTextBounds(text, 0, text.length, textBounds)

            val imageWidth = if (width > 0) width else (textBounds.width() + paddingLeft + paddingRight)
            val imageHeight = if (height > 0) height else (textBounds.height() + paddingTop + paddingBottom)
            val textAreaWidth = imageWidth - paddingLeft - paddingRight
            val textAreaHeight = imageHeight - paddingTop - paddingBottom

            logger.debug(
                message = "Creating texture image for text: '$text' of size: ${imageWidth}x$imageHeight"
            )

            val bitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.scale(1f, -1f)
            canvas.translate(0f, -imageHeight.toFloat())
            canvas.drawText(
                text,
                paddingLeft.toFloat() + (textAreaWidth - textBounds.width()).toFloat() / 2f,
                paddingTop.toFloat() + (textAreaHeight + textBounds.height()).toFloat() / 2f,
                paint
            )
            return bitmap
        }
    }

    companion object {
        private const val COLOR_CHANNEL_MAX = 255
    }
}
