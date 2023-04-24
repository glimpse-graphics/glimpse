/*
 * Copyright 2020-2023 Glimpse Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.textures

/**
 * Texture presets.
 *
 * @since v1.1.0
 */
data class TexturePresets(
    val internalFormat: TextureInternalFormat,
    val pixelFormat: TexturePixelFormat,
    val pixelType: TexturePixelType
) {

    companion object {

        /**
         * Texture with depth component.
         */
        val depthComponent = TexturePresets(
            internalFormat = TextureInternalFormat.DEPTH_COMPONENT,
            pixelFormat = TexturePixelFormat.DEPTH_COMPONENT,
            pixelType = TexturePixelType.FLOAT
        )

        /**
         * Texture with depth and stencil components.
         *
         * @since v2.0.0
         */
        val depthStencil = TexturePresets(
            internalFormat = TextureInternalFormat.DEPTH_STENCIL,
            pixelFormat = TexturePixelFormat.DEPTH_STENCIL,
            pixelType = TexturePixelType.FLOAT
        )

        /**
         * Texture with red component.
         *
         * @since v2.0.0
         */
        val red = TexturePresets(
            internalFormat = TextureInternalFormat.RED,
            pixelFormat = TexturePixelFormat.RED,
            pixelType = TexturePixelType.UNSIGNED_BYTE
        )

        /**
         * Texture with red/green pairs.
         *
         * @since v2.0.0
         */
        val rg = TexturePresets(
            internalFormat = TextureInternalFormat.RG,
            pixelFormat = TexturePixelFormat.RG,
            pixelType = TexturePixelType.UNSIGNED_BYTE
        )

        /**
         * Texture with RGB image.
         */
        val rgb = TexturePresets(
            internalFormat = TextureInternalFormat.RGB,
            pixelFormat = TexturePixelFormat.RGB,
            pixelType = TexturePixelType.UNSIGNED_BYTE
        )

        /**
         * Texture with RGBA image.
         */
        val rgba = TexturePresets(
            internalFormat = TextureInternalFormat.RGBA,
            pixelFormat = TexturePixelFormat.RGBA,
            pixelType = TexturePixelType.UNSIGNED_BYTE
        )

        /**
         * Texture with RGB image with 16-bit floating point values.
         */
        val floatRGB = TexturePresets(
            internalFormat = TextureInternalFormat.RGB16F,
            pixelFormat = TexturePixelFormat.RGB,
            pixelType = TexturePixelType.FLOAT
        )

        /**
         * Texture with RGBA image with 16-bit floating point values.
         */
        val floatRGBA = TexturePresets(
            internalFormat = TextureInternalFormat.RGBA16F,
            pixelFormat = TexturePixelFormat.RGBA,
            pixelType = TexturePixelType.FLOAT
        )

        /**
         * Texture with RGB image with 32-bit floating point values.
         *
         * @since v2.0.0
         */
        val float32RGB = TexturePresets(
            internalFormat = TextureInternalFormat.RGB32F,
            pixelFormat = TexturePixelFormat.RGB,
            pixelType = TexturePixelType.FLOAT
        )

        /**
         * Texture with RGBA image with 32-bit floating point values.
         *
         * @since v2.0.0
         */
        val float32RGBA = TexturePresets(
            internalFormat = TextureInternalFormat.RGBA32F,
            pixelFormat = TexturePixelFormat.RGBA,
            pixelType = TexturePixelType.FLOAT
        )
    }
}
