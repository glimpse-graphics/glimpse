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

import graphics.glimpse.GlimpseAdapter

/**
 * A texture.
 */
interface Texture {

    /**
     * Texture handle.
     */
    val handle: Int

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
}
