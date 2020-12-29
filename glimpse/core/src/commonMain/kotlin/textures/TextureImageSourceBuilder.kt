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

/**
 * A builder for a [TextureImageSource].
 */
expect class TextureImageSourceBuilder {

    /**
     * Will build a source of a [2D texture][TextureType.TEXTURE_2D].
     */
    fun forTexture2D(): TextureImageSourceBuilder

    /**
     * Will build a source of a given [side] of a [cubemap texture][TextureType.TEXTURE_CUBE_MAP].
     */
    fun forCubmap(side: CubemapSide): TextureImageSourceBuilder

    /**
     * Will build a texture source with a given [filename].
     *
     * Required by some implementations to determine image file type.
     */
    fun withFilename(filename: String): TextureImageSourceBuilder

    /**
     * Builds a [TextureImageSource] with the provided parameters.
     */
    fun build(): TextureImageSource

    companion object {

        /**
         * Creates a new texture image source builder.
         */
        fun getInstance(): TextureImageSourceBuilder
    }
}
