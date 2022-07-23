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

package graphics.glimpse.hud.text

import graphics.glimpse.textures.TextureImageSource
import graphics.glimpse.types.Vec4

/**
 * A builder for a [TextureImageSource] containing text.
 *
 * @since v1.2.0
 */
expect class TextTextureImageSourceBuilder constructor() {

    /**
     * Will build a texture source containing given [text].
     */
    fun fromText(text: String): TextTextureImageSourceBuilder

    /**
     * Will build a texture source containing text drawn with given [font].
     */
    fun withFont(font: Font): TextTextureImageSourceBuilder

    /**
     * Will build a texture source containing text drawn with given [color].
     */
    fun withColor(color: Vec4): TextTextureImageSourceBuilder

    /**
     * Will build a texture source containing text with given padding, equal on [all] sides.
     */
    fun withPadding(all: Int): TextTextureImageSourceBuilder

    /**
     * Will build a texture source containing text with given [horizontal] and [vertical] padding.
     */
    fun withPadding(horizontal: Int = 0, vertical: Int = 0): TextTextureImageSourceBuilder

    /**
     * Will build a texture source containing text with given [left], [top], [right] and [bottom] padding.
     */
    fun withPadding(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0): TextTextureImageSourceBuilder

    /**
     * Will build a texture source containing text with given [width] and [height].
     *
     * If either [width] or [height] is 0, the dimension of the resulting texture
     * will be adjusted to wrap the text with padding.
     */
    fun withSize(width: Int = 0, height: Int = 0): TextTextureImageSourceBuilder

    /**
     * Builds a [TextureImageSource] with the provided parameters.
     */
    fun build(): TextureImageSource
}
