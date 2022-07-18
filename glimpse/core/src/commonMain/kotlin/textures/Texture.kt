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

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.framebuffers.Framebuffer

/**
 * A texture.
 */
interface Texture {

    /**
     * Texture handle.
     */
    val handle: Int

    /**
     * Width of this texture.
     *
     * @since v1.2.0
     */
    val width: Int

    /**
     * Height of this texture.
     *
     * @since v1.2.0
     */
    val height: Int

    /**
     * Uses this texture at a given [textureIndex].
     */
    fun useAtIndex(gl: GlimpseAdapter, textureIndex: Int)

    /**
     * Disposes this texture.
     */
    fun dispose(gl: GlimpseAdapter)

    /**
     * An interface for a texture builder.
     */
    interface Builder {

        /**
         * Adds a [source] of a [2D texture][TextureType.TEXTURE_2D].
         */
        fun addTexture(source: TextureImageSource): Builder

        /**
         * Adds a [source] of a [cubemap texture][TextureType.TEXTURE_CUBE_MAP] for a given [side].
         */
        fun addCubemapTexture(side: CubemapSide, source: TextureImageSource): Builder

        /**
         * Sets [minifying][minFilter] and [magnifying][magFilter] filters all created textures.
         *
         * Default values are [TextureMinFilter.LINEAR] or [TextureMinFilter.LINEAR_MIPMAP_LINEAR] for minifying
         * filter and [TextureMagFilter.LINEAR] for magnifying filter.
         *
         * @since v1.1.0
         */
        fun setTextureFilter(minFilter: TextureMinFilter, magFilter: TextureMagFilter): Builder

        /**
         * Sets wrap parameter for texture coordinates [S][wrapS] and [T][wrapT] filters all created textures.
         *
         * Default values are [TextureWrap.REPEAT].
         *
         * @since v1.1.0
         */
        fun setTextureWrap(wrapS: TextureWrap, wrapT: TextureWrap = wrapS): Builder

        /**
         * Tells the builder to generate mipmaps for all created textures.
         */
        fun generateMipmaps(): Builder

        /**
         * Builds textures from all added sources.
         */
        fun build(): List<Texture>

        companion object {

            /**
             * Gets a new instance of a texture builder.
             */
            fun getInstance(gl: GlimpseAdapter): Builder = TextureBuilderImpl(gl)
        }
    }

    companion object {

        /**
         * Creates an empty texture of the given [width] and [height], and with the given [presets].
         *
         * Empty textures are intended to be used with [Framebuffer]s.
         *
         * @since v1.1.0
         */
        fun createEmpty(gl: GlimpseAdapter, width: Int, height: Int, presets: EmptyTexturePresets): Texture =
            EmptyTextureFactory(gl).create(width, height, presets)
    }
}
