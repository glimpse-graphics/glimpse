/*
 * Copyright 2020-2021 Slawomir Czerwinski
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
 *
 */

package graphics.glimpse.textures

import com.jogamp.opengl.GL2ES2
import com.jogamp.opengl.util.texture.TextureIO
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
        return this
    }

    /**
     * Builds a [TextureImageSource] with the provided parameters.
     */
    actual fun build(): TextureImageSource {
        check(value = filename.isNotBlank()) {
            "Filename cannot be blank. Must call withFilename()."
        }
        logger.debug(message = "Building image source from: '$filename'")
        return TextureImageSourceImpl(filename, inputStreamProvider)
    }

    private class TextureImageSourceImpl(
        private val filename: String,
        private val inputStreamProvider: InputStreamProvider
    ) : TextureImageSource {

        private val logger: GlimpseLogger = GlimpseLogger.create(this)

        override fun glTexImage2D(gl: GlimpseAdapter, withMipmaps: Boolean) {
            glTexImage2D(gl, TextureType.TEXTURE_2D, GL2ES2.GL_TEXTURE_2D, withMipmaps)
        }

        override fun glTexImage2D(gl: GlimpseAdapter, side: CubemapSide, withMipmaps: Boolean) {
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

        private fun glTexImage2D(
            gl: GlimpseAdapter,
            textureType: TextureType,
            target: Int,
            withMipmaps: Boolean
        ) {
            val inputStream = checkNotNull(inputStreamProvider.createInputStream()) {
                "Texture input stream cannot be null"
            }
            logger.debug(message = "Creating texture: '$filename'")
            val fileType = filename.split('.').last().toLowerCase(Locale.ENGLISH)
            val textureData = TextureIO.newTextureData(
                gl.gles.glProfile, inputStream, false, fileType
            )
            logger.debug(message = "Decoded texture data: $textureData")
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

    actual companion object {

        /**
         * Creates a new texture image source builder.
         */
        actual fun getInstance(): TextureImageSourceBuilder = TextureImageSourceBuilder()
    }
}
