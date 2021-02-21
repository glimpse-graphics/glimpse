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
 * Texture image source.
 */
interface TextureImageSource {

    /**
     * Sets a 2D texture image.
     */
    fun glTexImage2D(gl: GlimpseAdapter, withMipmaps: Boolean)

    /**
     * Sets a Cubemap side texture image.
     */
    fun glTexImage2D(gl: GlimpseAdapter, side: CubemapSide, withMipmaps: Boolean)

    /**
     * Disposes this texture image source.
     *
     * Disposes any prepared or cached texture image data.
     *
     * _Note: This method does not dispose the texture itself, but only the image source._
     */
    fun dispose()

    companion object {

        /**
         * Creates a new texture image source builder.
         */
        fun builder(): TextureImageSourceBuilder = TextureImageSourceBuilder.getInstance()
    }
}
