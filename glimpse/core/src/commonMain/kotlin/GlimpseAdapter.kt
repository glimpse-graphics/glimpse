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
expect class GlimpseAdapter {

    /**
     * Glimpse logger.
     */
    val logger: GlimpseLogger

    /**
     * Returns a boolean value for the given integer [value].
     */
    fun booleanOf(value: Int): Boolean

    /**
     * Sets clear values for color buffers to [color] with alpha channel set to fully opaque.
     */
    fun glClearColor(color: Vec3)

    /**
     * Sets clear values for color buffers to [color].
     */
    fun glClearColor(color: Vec4)

    /**
     * Sets clear value for the depth buffer to [depth].
     */
    fun glClearDepth(depth: Float)

    /**
     * Sets the given [depthTestFunction].
     */
    fun glDepthTest(depthTestFunction: DepthTestFunction)

    /**
     * Sets facets to be culled to [faceCullingMode].
     */
    fun glCullFace(faceCullingMode: FaceCullingMode)

    /**
     * Sets the viewport to have its bottom-left corner at ([x], [y]),
     * and a given [width] and [height].
     */
    fun glViewport(x: Int = 0, y: Int = 0, width: Int, height: Int)

    /**
     * Clears given buffers to the predefined clear values.
     */
    fun glClear(vararg buffers: ClearableBufferType)

    /**
     * Generates texture handles and writes them to a given [textureHandles] array.
     *
     * The number of generated texture handles is equal to the given [textureHandles] array.
     */
    fun glGenTextures(textureHandles: IntArray)

    /**
     * Binds a given [textureHandle] to a given texture [type].
     */
    fun glBindTexture(type: TextureType, textureHandle: Int)

    /**
     * Gets maximum size of a texture of a given [type].
     */
    fun glMaxTextureSize(type: TextureType): Int

    /**
     * Generates mipmaps for the currently selected texture of a given [type].
     */
    fun glGenerateMipmap(type: TextureType)

    /**
     * Sets texture [minifying][minFilter] and [magnifying][magFilter] filters for the currently
     * selected texture of a given [type].
     */
    fun glTexParameterFilter(
        type: TextureType,
        minFilter: TextureMinFilter,
        magFilter: TextureMagFilter
    )

    /**
     * Sets wrap parameter for texture coordinates [S][wrapS] and [T][wrapT] for the currently
     * selected texture of a given [type].
     */
    fun glTexParameterWrap(
        type: TextureType,
        wrapS: TextureWrap,
        wrapT: TextureWrap
    )

    /**
     * Deletes textures represented by given [textureHandles].
     */
    fun glDeleteTextures(textureHandles: IntArray)

    /**
     * Selects active [textureIndex] starting with 0.
     */
    fun glActiveTexture(textureIndex: Int)

    /**
     * Returns a range of indices supported by [glActiveTexture].
     */
    fun glTextureIndices(): IntRange
}
