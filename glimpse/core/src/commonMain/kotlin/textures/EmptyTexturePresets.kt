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
 * Empty texture presets.
 *
 * @since v1.0.0
 */
data class EmptyTexturePresets(
    val internalFormat: TextureInternalFormat,
    val pixelFormat: TexturePixelFormat,
    val pixelType: TexturePixelType
) {

    companion object {

        /**
         * Empty texture for depth component.
         */
        val depthComponent = EmptyTexturePresets(
            internalFormat = TextureInternalFormat.DEPTH_COMPONENT,
            pixelFormat = TexturePixelFormat.DEPTH_COMPONENT,
            pixelType = TexturePixelType.FLOAT
        )

        /**
         * Empty texture for RGB image.
         */
        val rgb = EmptyTexturePresets(
            internalFormat = TextureInternalFormat.RGB,
            pixelFormat = TexturePixelFormat.RGB,
            pixelType = TexturePixelType.UNSIGNED_BYTE
        )

        /**
         * Empty texture for RGBA image.
         */
        val rgba = EmptyTexturePresets(
            internalFormat = TextureInternalFormat.RGBA,
            pixelFormat = TexturePixelFormat.RGBA,
            pixelType = TexturePixelType.UNSIGNED_BYTE
        )

        /**
         * Empty texture for RGB image with floating point values.
         */
        val floatRGB = EmptyTexturePresets(
            internalFormat = TextureInternalFormat.RGB16F,
            pixelFormat = TexturePixelFormat.RGB,
            pixelType = TexturePixelType.FLOAT
        )

        /**
         * Empty texture for RGBA image with floating point values.
         */
        val floatRGBA = EmptyTexturePresets(
            internalFormat = TextureInternalFormat.RGBA16F,
            pixelFormat = TexturePixelFormat.RGBA,
            pixelType = TexturePixelType.FLOAT
        )
    }
}
