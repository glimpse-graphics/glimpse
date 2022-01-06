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

/**
 * Texture magnifying function.
 */
enum class TextureMagFilter {

    /**
     * Returns the value of the texture pixel that is nearest (in taxicab metric)
     * to the center of the fragment being textured.
     */
    NEAREST,

    /**
     * Returns the weighted average of the four texture pixels that are closest
     * to the center of the fragment being textured.
     */
    LINEAR
}
