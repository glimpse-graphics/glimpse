/*
 * Copyright 2020 Slawomir Czerwinski
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

import com.jogamp.opengl.GLES2
import com.jogamp.opengl.util.texture.TextureIO
import graphics.glimpse.GlimpseAdapter
import java.util.*

/**
 * A builder for a [TextureImageSource].
 */
actual class TextureImageSourceBuilder {

    private var target: Int = 0
    private var filename: String = ""
    private var inputStreamProvider: InputStreamProvider = InputStreamProvider { null }

    /**
     * Will build a source of a [2D texture][TextureType.TEXTURE_2D].
     */
    actual fun forTexture2D(): TextureImageSourceBuilder {
        target = GLES2.GL_TEXTURE_2D
        return this
    }

    /**
     * Will build a source of a given [side] of a [cubemap texture][TextureType.TEXTURE_CUBE_MAP].
     */
    actual fun forCubmap(side: CubemapSide): TextureImageSourceBuilder {
        target = side.toInt()
        return this
    }

    private fun CubemapSide.toInt(): Int = when (this) {
        CubemapSide.RIGHT -> GLES2.GL_TEXTURE_CUBE_MAP_POSITIVE_X
        CubemapSide.LEFT -> GLES2.GL_TEXTURE_CUBE_MAP_NEGATIVE_X
        CubemapSide.TOP -> GLES2.GL_TEXTURE_CUBE_MAP_POSITIVE_Y
        CubemapSide.BOTTOM -> GLES2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y
        CubemapSide.FAR -> GLES2.GL_TEXTURE_CUBE_MAP_POSITIVE_Z
        CubemapSide.NEAR -> GLES2.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z
    }

    /**
     * Will build a texture source with a given [filename].
     */
    actual fun withFilename(filename: String): TextureImageSourceBuilder {
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
        check(value = target != 0) {
            "Texture type not set. Must call forTexture2D() or forCubmap()"
        }
        check(value = filename.isNotBlank()) {
            "Filename cannot be blank. Must call withFilename()."
        }
        return TextureImageSourceImpl(target, filename, inputStreamProvider)
    }

    private class TextureImageSourceImpl(
        private val target: Int,
        override val filename: String,
        private val inputStreamProvider: InputStreamProvider
    ) : TextureImageSource {

        override fun glTexImage2D(gl: GlimpseAdapter, withMipmaps: Boolean) {
            val inputStream = checkNotNull(inputStreamProvider.createInputStream()) {
                "Texture input stream cannot be null"
            }
            val fileType = "." + filename.split('.').last().toLowerCase(Locale.ENGLISH)
            val textureData = TextureIO.newTextureData(
                gl.gles.glProfile, inputStream, withMipmaps, fileType
            )
            gl.gles.glTexImage2D(
                target, 0, GLES2.GL_RGBA,
                textureData.width, textureData.height, 0,
                GLES2.GL_RGBA, GLES2.GL_UNSIGNED_BYTE, textureData.buffer
            )
        }
    }

    actual companion object {

        /**
         * Creates a new texture image source builder.
         */
        actual fun getInstance(): TextureImageSourceBuilder = TextureImageSourceBuilder()
    }
}
