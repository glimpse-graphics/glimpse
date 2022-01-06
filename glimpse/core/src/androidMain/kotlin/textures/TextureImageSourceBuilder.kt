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

import android.graphics.Bitmap
import android.graphics.Matrix
import android.opengl.GLES20
import android.opengl.GLUtils
import graphics.glimpse.GlimpseAdapter

/**
 * A builder for a [TextureImageSource].
 */
actual class TextureImageSourceBuilder {

    private var bitmapProvider: BitmapProvider = BitmapProvider { null }

    /**
     * Will build a texture source from a bitmap provided by [bitmapProvider].
     *
     * _Note: After the bitmap provided by [bitmapProvider] is used to set a texture image,
     * it will be recycled. It is not safe to provide any instance of a bitmap used elsewhere._
     */
    fun fromBitmap(bitmapProvider: BitmapProvider): TextureImageSourceBuilder {
        this.bitmapProvider = bitmapProvider
        return this
    }

    /**
     * Builds a [TextureImageSource] with the provided parameters.
     */
    actual fun build(): TextureImageSource =
        TextureImageSourceImpl(bitmapProvider)

    /**
     * Builds a prepared [TextureImageSource] with the provided parameters.
     *
     * _Note: The resulting [TextureImageSource] will be quicker in setting
     * a texture image, but it will also consume more memory._
     */
    fun buildPrepared(): TextureImageSource {
        val bitmap = checkNotNull(bitmapProvider.createBitmap()) {
            "Texture bitmap cannot be null"
        }.flipVertically()
        return PreparedTextureImageSourceImpl(bitmap)
    }

    private abstract class BaseTextureImageSourceImpl : TextureImageSource {

        final override fun glTexImage2D(gl: GlimpseAdapter, withMipmaps: Boolean) {
            glTexImage2D(gl, TextureType.TEXTURE_2D, GLES20.GL_TEXTURE_2D, withMipmaps)
        }

        final override fun glTexImage2D(gl: GlimpseAdapter, side: CubemapSide, withMipmaps: Boolean) {
            glTexImage2D(gl, TextureType.TEXTURE_CUBE_MAP, side.toInt(), withMipmaps)
        }

        private fun CubemapSide.toInt(): Int = when (this) {
            CubemapSide.RIGHT -> GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X
            CubemapSide.LEFT -> GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_X
            CubemapSide.TOP -> GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Y
            CubemapSide.BOTTOM -> GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y
            CubemapSide.FAR -> GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Z
            CubemapSide.NEAR -> GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z
        }

        protected abstract fun glTexImage2D(
            gl: GlimpseAdapter,
            textureType: TextureType,
            target: Int,
            withMipmaps: Boolean
        )
    }

    private class TextureImageSourceImpl(
        private val bitmapProvider: BitmapProvider
    ) : BaseTextureImageSourceImpl() {

        override fun glTexImage2D(
            gl: GlimpseAdapter,
            textureType: TextureType,
            target: Int,
            withMipmaps: Boolean
        ) {
            val bitmap = checkNotNull(bitmapProvider.createBitmap()) {
                "Texture bitmap cannot be null"
            }.flipVertically()
            GLUtils.texImage2D(target, 0, GLES20.GL_RGBA, bitmap, 0)
            bitmap.recycle()
            if (withMipmaps) gl.glGenerateMipmap(textureType)
        }

        override fun dispose() = Unit
    }

    private class PreparedTextureImageSourceImpl(
        private val bitmap: Bitmap
    ) : BaseTextureImageSourceImpl() {

        override fun glTexImage2D(
            gl: GlimpseAdapter,
            textureType: TextureType,
            target: Int,
            withMipmaps: Boolean
        ) {
            GLUtils.texImage2D(target, 0, GLES20.GL_RGBA, bitmap, 0)
            if (withMipmaps) gl.glGenerateMipmap(textureType)
        }

        override fun dispose() {
            bitmap.recycle()
        }
    }

    actual companion object {

        /**
         * Creates a new texture image source builder.
         */
        actual fun getInstance(): TextureImageSourceBuilder = TextureImageSourceBuilder()

        private fun Bitmap.flipVertically(): Bitmap {
            val matrix = Matrix()
            matrix.postScale(1f, -1f, width / 2f, width / 2f)
            val result = Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
            recycle()
            return result
        }
    }
}
