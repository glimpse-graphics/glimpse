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

package graphics.glimpse.textures

import com.jogamp.opengl.GL2ES2
import com.jogamp.opengl.GLProfile
import com.jogamp.opengl.util.texture.TextureData
import com.jogamp.opengl.util.texture.TextureIO
import com.jogamp.opengl.util.texture.awt.AWTTextureIO
import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.logging.GlimpseLogger
import java.util.*

/**
 * A builder for a [TextureImageSource].
 */
actual class TextureImageSourceBuilder {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    private var filename: String = ""
    private var inputStreamProvider: InputStreamProvider = InputStreamProvider { null }
    private var bufferedImageProvider: BufferedImageProvider = BufferedImageProvider { null }
    private var imageSourceType: ImageSourceType? = null

    /**
     * Will build a texture source with a given [filename].
     */
    fun withFilename(filename: String): TextureImageSourceBuilder {
        this.filename = filename
        return this
    }

    /**
     * Will build a texture source from an input stream provided by [inputStreamProvider].
     */
    fun fromInputStream(inputStreamProvider: InputStreamProvider): TextureImageSourceBuilder {
        this.inputStreamProvider = inputStreamProvider
        this.imageSourceType = ImageSourceType.INPUT_STREAM
        return this
    }

    /**
     * Will build a texture source from a buffered image provided by [bufferedImageProvider].
     *
     * @since v1.2.0
     */
    fun fromBufferedImage(bufferedImageProvider: BufferedImageProvider): TextureImageSourceBuilder {
        this.bufferedImageProvider = bufferedImageProvider
        this.imageSourceType = ImageSourceType.BUFFERED_IMAGE
        return this
    }

    /**
     * Builds a [TextureImageSource] with the provided parameters.
     */
    actual fun build(): TextureImageSource {
        return when (imageSourceType) {
            ImageSourceType.INPUT_STREAM -> buildFromInputStream()
            ImageSourceType.BUFFERED_IMAGE -> buildFromBufferedImage()
            null -> error("No image source provided")
        }
    }

    private fun buildFromInputStream(): TextureImageSource {
        check(value = filename.isNotBlank()) {
            "Filename cannot be blank. Must call withFilename()."
        }
        logger.debug(message = "Building image source from: '$filename'")
        return TextureImageSourceImpl(filename, inputStreamProvider)
    }

    private fun buildFromBufferedImage(): TextureImageSource {
        logger.debug(message = "Building image source from buffered image")
        return BufferedImageTextureImageSourceImpl(bufferedImageProvider)
    }

    /**
     * Builds a prepared [TextureImageSource] with the provided parameters and
     * a given OpenGL [profile].
     *
     * _Note: The resulting [TextureImageSource] will be quicker in setting
     * a texture image, but it will also consume more memory._
     */
    fun buildPrepared(profile: GLProfile): TextureImageSource {
        val textureData = when (imageSourceType) {
            ImageSourceType.INPUT_STREAM -> createTextureDataFromInputStream(profile)
            ImageSourceType.BUFFERED_IMAGE -> createTextureDataFromBufferedImage(profile)
            null -> error("No image source provided")
        }
        logger.debug(message = "Decoded texture data: $textureData")
        return PreparedTextureImageSourceImpl(textureData)
    }

    private fun createTextureDataFromInputStream(profile: GLProfile): TextureData {
        check(value = filename.isNotBlank()) {
            "Filename cannot be blank. Must call withFilename()."
        }
        logger.debug(message = "Building image source from: '$filename'")
        val inputStream = checkNotNull(inputStreamProvider.createInputStream()) {
            "Texture input stream cannot be null"
        }
        logger.debug(message = "Decoding texture data: '$filename'")
        val fileType = filename.split('.').last().lowercase(Locale.ENGLISH)
        return TextureIO.newTextureData(profile, inputStream, false, fileType)
    }

    private fun createTextureDataFromBufferedImage(profile: GLProfile): TextureData {
        logger.debug(message = "Building image source from buffered image")
        return AWTTextureIO.newTextureData(profile, bufferedImageProvider.createBufferedImage(), false)
    }

    private enum class ImageSourceType {
        INPUT_STREAM,
        BUFFERED_IMAGE
    }

    private abstract class BaseTextureImageSourceImpl : TextureImageSource {

        protected val logger: GlimpseLogger by lazy { GlimpseLogger.create(this) }

        final override fun glTexImage2D(gl: GlimpseAdapter, withMipmaps: Boolean) {
            glTexImage2D(gl, TextureType.TEXTURE_2D, GL2ES2.GL_TEXTURE_2D, withMipmaps)
        }

        final override fun glTexImage2D(gl: GlimpseAdapter, side: CubemapSide, withMipmaps: Boolean) {
            glTexImage2D(gl, TextureType.TEXTURE_CUBE_MAP, side.toInt(), withMipmaps)
        }

        private fun CubemapSide.toInt(): Int = when (this) {
            CubemapSide.RIGHT -> GL2ES2.GL_TEXTURE_CUBE_MAP_POSITIVE_X
            CubemapSide.LEFT -> GL2ES2.GL_TEXTURE_CUBE_MAP_NEGATIVE_X
            CubemapSide.TOP -> GL2ES2.GL_TEXTURE_CUBE_MAP_POSITIVE_Y
            CubemapSide.BOTTOM -> GL2ES2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y
            CubemapSide.FAR -> GL2ES2.GL_TEXTURE_CUBE_MAP_POSITIVE_Z
            CubemapSide.NEAR -> GL2ES2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z
        }

        protected abstract fun glTexImage2D(
            gl: GlimpseAdapter,
            textureType: TextureType,
            target: Int,
            withMipmaps: Boolean
        )

        protected fun glTexImage2D(
            gl: GlimpseAdapter,
            textureType: TextureType,
            textureData: TextureData,
            target: Int,
            withMipmaps: Boolean
        ) {
            logger.debug(message = "Decoded texture data: $textureData")
            gl.gles.glPixelStorei(GL2ES2.GL_UNPACK_ALIGNMENT, textureData.alignment)
            gl.gles.glTexImage2D(
                target, 0, textureData.internalFormat,
                textureData.width, textureData.height, 0,
                textureData.pixelFormat, textureData.pixelType, textureData.buffer
            )
            if (withMipmaps) {
                gl.glGenerateMipmap(textureType)
            }
        }
    }

    private class TextureImageSourceImpl(
        private val filename: String,
        private val inputStreamProvider: InputStreamProvider
    ) : BaseTextureImageSourceImpl() {

        override fun glTexImage2D(
            gl: GlimpseAdapter,
            textureType: TextureType,
            target: Int,
            withMipmaps: Boolean
        ) {
            val inputStream = checkNotNull(inputStreamProvider.createInputStream()) {
                "Texture input stream cannot be null"
            }
            logger.debug(message = "Creating texture: '$filename'")
            val fileType = filename.split('.').last().lowercase(Locale.ENGLISH)
            val textureData = TextureIO.newTextureData(
                gl.gles.glProfile, inputStream, false, fileType
            )
            glTexImage2D(gl, textureType, textureData, target, withMipmaps)
            textureData.destroy()
        }

        override fun dispose() = Unit
    }

    private class BufferedImageTextureImageSourceImpl(
        private val bufferedImageProvider: BufferedImageProvider
    ) : BaseTextureImageSourceImpl() {

        override fun glTexImage2D(
            gl: GlimpseAdapter,
            textureType: TextureType,
            target: Int,
            withMipmaps: Boolean
        ) {
            logger.debug(message = "Creating texture from buffered image")
            val textureData = AWTTextureIO.newTextureData(
                gl.gles.glProfile, bufferedImageProvider.createBufferedImage(), false
            )
            glTexImage2D(gl, textureType, textureData, target, withMipmaps)
            textureData.destroy()
        }

        override fun dispose() = Unit
    }

    private class PreparedTextureImageSourceImpl(
        private val textureData: TextureData
    ) : BaseTextureImageSourceImpl() {

        override fun glTexImage2D(
            gl: GlimpseAdapter,
            textureType: TextureType,
            target: Int,
            withMipmaps: Boolean
        ) {
            glTexImage2D(gl, textureType, textureData, target, withMipmaps)
        }

        override fun dispose() {
            textureData.destroy()
        }
    }

    actual companion object {

        /**
         * Creates a new texture image source builder.
         */
        actual fun getInstance(): TextureImageSourceBuilder = TextureImageSourceBuilder()
    }
}
