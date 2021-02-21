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

/**
 * Texture minifying function.
 */
enum class TextureMinFilter {

    /**
     * Returns the value of the texture pixel that is nearest (in taxicab metric)
     * to the center of the fragment being textured.
     */
    NEAREST,

    /**
     * Returns the weighted average of the four texture pixels that are closest
     * to the center of the fragment being textured.
     */
    LINEAR,

    /**
     * Finds the mipmap most closely matching the size of the fragment being textured
     * and uses the [NEAREST] function to calculate a texture value.
     */
    NEAREST_MIPMAP_NEAREST,

    /**
     * Finds the mipmap most closely matching the size of the fragment being textured
     * and uses the [LINEAR] function to calculate a texture value.
     */
    LINEAR_MIPMAP_NEAREST,

    /**
     * Finds the two mipmaps most closely matching the size of the fragment being textured,
     * uses the [NEAREST] function to calculate a texture value from each mipmap, and
     * returns a weighted average of those two values.
     */
    NEAREST_MIPMAP_LINEAR,

    /**
     * Finds the two mipmaps most closely matching the size of the fragment being textured,
     * uses the [LINEAR] function to calculate a texture value from each mipmap, and
     * returns a weighted average of those two values.
     */
    LINEAR_MIPMAP_LINEAR
}
