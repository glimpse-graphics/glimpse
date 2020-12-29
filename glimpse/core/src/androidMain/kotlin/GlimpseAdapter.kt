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

package graphics.glimpse

import android.opengl.GLES20
import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.textures.TextureMagFilter
import graphics.glimpse.textures.TextureMinFilter
import graphics.glimpse.textures.TextureType
import graphics.glimpse.textures.TextureWrap
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4

/**
 * Glimpse OpenGL adapter.
 */
actual class GlimpseAdapter {

    /**
     * Glimpse logger.
     */
    actual val logger: GlimpseLogger = GlimpseLogger.create(this)

    /**
     * Returns a boolean value for the given integer [value].
     */
    actual fun booleanOf(value: Int): Boolean = value != GLES20.GL_FALSE

    /**
     * Sets clear values for color buffers to [color] with alpha channel set to fully opaque.
     */
    actual fun glClearColor(color: Vec3) {
        GLES20.glClearColor(color.r, color.g, color.b, 1f)
    }

    /**
     * Sets clear values for color buffers to [color].
     */
    actual fun glClearColor(color: Vec4) {
        GLES20.glClearColor(color.r, color.g, color.b, color.a)
    }

    /**
     * Sets clear value for the depth buffer to [depth].
     */
    actual fun glClearDepth(depth: Float) {
        GLES20.glClearDepthf(depth)
    }

    /**
     * Sets the given [depthTestFunction].
     */
    actual fun glDepthTest(depthTestFunction: DepthTestFunction) {
        if (depthTestFunction.isDepthTestEnabled) {
            GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        } else {
            GLES20.glDisable(GLES20.GL_DEPTH_TEST)
        }
        depthTestFunction.toInt()?.let { depthFunc -> GLES20.glDepthFunc(depthFunc) }
    }

    private fun DepthTestFunction.toInt(): Int? = when (this) {
        DepthTestFunction.DISABLED -> null
        DepthTestFunction.NEVER -> GLES20.GL_NEVER
        DepthTestFunction.LESS -> GLES20.GL_LESS
        DepthTestFunction.EQUAL -> GLES20.GL_EQUAL
        DepthTestFunction.LESS_OR_EQUAL -> GLES20.GL_LEQUAL
        DepthTestFunction.GREATER -> GLES20.GL_GREATER
        DepthTestFunction.NOT_EQUAL -> GLES20.GL_NOTEQUAL
        DepthTestFunction.GREATER_OR_EQUAL -> GLES20.GL_GEQUAL
        DepthTestFunction.ALWAYS -> GLES20.GL_ALWAYS
    }

    /**
     * Sets facets to be culled to [faceCullingMode].
     */
    actual fun glCullFace(faceCullingMode: FaceCullingMode) {
        if (faceCullingMode.isFaceCullingEnabled) {
            GLES20.glEnable(GLES20.GL_CULL_FACE)
        } else {
            GLES20.glDisable(GLES20.GL_CULL_FACE)
        }
        faceCullingMode.toInt()?.let { depthFunc -> GLES20.glDepthFunc(depthFunc) }
    }

    private fun FaceCullingMode.toInt(): Int? = when (this) {
        FaceCullingMode.DISABLED -> null
        FaceCullingMode.FRONT -> GLES20.GL_FRONT
        FaceCullingMode.BACK -> GLES20.GL_BACK
        FaceCullingMode.FRONT_AND_BACK -> GLES20.GL_FRONT_AND_BACK
    }

    /**
     * Sets the viewport to have its bottom-left corner at ([x], [y]),
     * and a given [width] and [height].
     */
    actual fun glViewport(x: Int, y: Int, width: Int, height: Int) {
        GLES20.glViewport(x, y, width, height)
    }

    /**
     * Clears given buffers to the predefined clear values.
     */
    actual fun glClear(vararg buffers: ClearableBufferType) {
        if (buffers.isEmpty()) return
        GLES20.glClear(
            buffers.map { clearTarget -> clearTarget.toInt() }
                .reduce { a, b -> a or b }
        )
    }

    private fun ClearableBufferType.toInt(): Int = when (this) {
        ClearableBufferType.COLOR_BUFFER -> GLES20.GL_COLOR_BUFFER_BIT
        ClearableBufferType.DEPTH_BUFFER -> GLES20.GL_DEPTH_BUFFER_BIT
        ClearableBufferType.STENCIL_BUFFER -> GLES20.GL_STENCIL_BUFFER_BIT
    }

    /**
     * Generates texture handles and writes them to a given [textureHandles] array.
     *
     * The number of generated texture handles is equal to the given [textureHandles] array.
     */
    actual fun glGenTextures(textureHandles: IntArray) {
        GLES20.glGenTextures(textureHandles.size, textureHandles, 0)
    }

    /**
     * Binds a given [textureHandle] to a given texture [type].
     */
    actual fun glBindTexture(type: TextureType, textureHandle: Int) {
        GLES20.glBindTexture(type.toInt(), textureHandle)
    }

    private fun TextureType.toInt(): Int = when (this) {
        TextureType.TEXTURE_2D -> GLES20.GL_TEXTURE_2D
        TextureType.TEXTURE_CUBE_MAP -> GLES20.GL_TEXTURE_CUBE_MAP
    }

    /**
     * Gets maximum size of a texture of a given [type].
     */
    actual fun glMaxTextureSize(type: TextureType): Int = when (type) {
        TextureType.TEXTURE_2D -> glGetInteger(GLES20.GL_MAX_TEXTURE_SIZE)
        TextureType.TEXTURE_CUBE_MAP -> glGetInteger(GLES20.GL_MAX_CUBE_MAP_TEXTURE_SIZE)
    }

    private fun glGetInteger(parameter: Int): Int {
        val result = IntArray(size = 1)
        GLES20.glGetIntegerv(parameter, result, 0)
        return result.first()
    }

    /**
     * Generates mipmaps for the currently selected texture of a given [type].
     */
    actual fun glGenerateMipmap(type: TextureType) {
        GLES20.glGenerateMipmap(type.toInt())
    }

    /**
     * Sets texture [minifying][minFilter] and [magnifying][magFilter] filters for the currently
     * selected texture of a given [type].
     */
    actual fun glTexParameterFilter(
        type: TextureType,
        minFilter: TextureMinFilter,
        magFilter: TextureMagFilter
    ) {
        GLES20.glTexParameteri(type.toInt(), GLES20.GL_TEXTURE_MIN_FILTER, minFilter.toInt())
        GLES20.glTexParameteri(type.toInt(), GLES20.GL_TEXTURE_MAG_FILTER, magFilter.toInt())
    }

    private fun TextureMinFilter.toInt(): Int = when (this) {
        TextureMinFilter.NEAREST -> GLES20.GL_NEAREST
        TextureMinFilter.LINEAR -> GLES20.GL_LINEAR
        TextureMinFilter.NEAREST_MIPMAP_NEAREST -> GLES20.GL_NEAREST_MIPMAP_NEAREST
        TextureMinFilter.LINEAR_MIPMAP_NEAREST -> GLES20.GL_LINEAR_MIPMAP_NEAREST
        TextureMinFilter.NEAREST_MIPMAP_LINEAR -> GLES20.GL_NEAREST_MIPMAP_LINEAR
        TextureMinFilter.LINEAR_MIPMAP_LINEAR -> GLES20.GL_LINEAR_MIPMAP_LINEAR
    }

    private fun TextureMagFilter.toInt(): Int = when (this) {
        TextureMagFilter.NEAREST -> GLES20.GL_NEAREST
        TextureMagFilter.LINEAR -> GLES20.GL_LINEAR
    }

    /**
     * Sets wrap parameter for texture coordinates [S][wrapS] and [T][wrapT] for the currently
     * selected texture of a given [type].
     */
    actual fun glTexParameterWrap(
        type: TextureType,
        wrapS: TextureWrap,
        wrapT: TextureWrap
    ) {
        GLES20.glTexParameteri(type.toInt(), GLES20.GL_TEXTURE_WRAP_S, wrapS.toInt())
        GLES20.glTexParameteri(type.toInt(), GLES20.GL_TEXTURE_WRAP_T, wrapT.toInt())
    }

    private fun TextureWrap.toInt(): Int = when (this) {
        TextureWrap.CLAMP_TO_EDGE -> GLES20.GL_CLAMP_TO_EDGE
        TextureWrap.REPEAT -> GLES20.GL_REPEAT
        TextureWrap.MIRRORED_REPEAT -> GLES20.GL_MIRRORED_REPEAT
    }

    /**
     * Deletes textures represented by given [textureHandles].
     */
    actual fun glDeleteTextures(textureHandles: IntArray) {
        GLES20.glDeleteTextures(textureHandles.size, textureHandles, 0)
    }

    /**
     * Selects active [textureIndex] starting with 0.
     */
    actual fun glActiveTexture(textureIndex: Int) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + textureIndex)
    }

    /**
     * Returns a range of indices supported by [glActiveTexture].
     */
    actual fun glTextureIndices(): IntRange =
        0 until glGetInteger(GLES20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS)
}