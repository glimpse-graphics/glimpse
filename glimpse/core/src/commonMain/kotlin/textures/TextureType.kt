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
 * Texture type.
 */
enum class TextureType {

    /**
     * 2D Texture.
     */
    TEXTURE_2D,

    /**
     * Cubemap texture.
     *
     * Cubemaps consist of 6 2D images representing 6 faces of a cube.
     */
    TEXTURE_CUBE_MAP
}
