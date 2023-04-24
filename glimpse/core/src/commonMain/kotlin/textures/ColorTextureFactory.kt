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

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4
import graphics.glimpse.types.toFloatArray
import java.nio.ByteBuffer
import java.nio.ByteOrder

internal class ColorTextureFactory(private val gl: GlimpseAdapter) {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    fun create(color: Vec3<*>, presets: TexturePresets): Texture {
        require(presets.pixelFormat == TexturePixelFormat.RGB) {
            "Generating texture from RGB color, but pixel format is ${presets.pixelFormat}"
        }
        return create(color.toFloatVector().toFloatArray(), presets)
    }

    private fun create(
        colorComponents: FloatArray,
        presets: TexturePresets
    ): TextureImpl {
        logger.debug(message = "Generating color texture with presets: $presets")

        val bytesCount = Float.SIZE_BYTES * colorComponents.size
        val bytes = ByteArray(size = bytesCount)
        val buffer = ByteBuffer.allocate(bytesCount)
        buffer.order(ByteOrder.nativeOrder())
        buffer.asFloatBuffer().put(colorComponents)
        buffer.rewind()
        buffer.get(bytes)

        val handles = IntArray(size = 1)
        gl.glGenTextures(handles)

        val textureHandle = handles.single()

        gl.glBindTexture(TextureType.TEXTURE_2D, textureHandle)
        gl.glTexImage2D(
            internalFormat = presets.internalFormat,
            width = 1,
            height = 1,
            pixelFormat = presets.pixelFormat,
            pixelType = presets.pixelType,
            pixelData = bytes
        )
        gl.glTexParameterFilter(TextureType.TEXTURE_2D, TextureMinFilter.LINEAR, TextureMagFilter.LINEAR)
        gl.glTexParameterWrap(TextureType.TEXTURE_2D, TextureWrap.CLAMP_TO_EDGE, TextureWrap.CLAMP_TO_EDGE)

        return TextureImpl(textureHandle)
    }

    fun create(color: Vec4<*>, presets: TexturePresets): Texture {
        require(presets.pixelFormat == TexturePixelFormat.RGBA) {
            "Generating texture from RGBA color, but pixel format is ${presets.pixelFormat}"
        }
        return create(color.toFloatVector().toFloatArray(), presets)
    }

    data class TextureImpl(
        override val handle: Int
    ) : BaseTexture() {

        override val width: Int get() = 1
        override val height: Int get() = 1

        override val type: TextureType get() = TextureType.TEXTURE_2D
    }
}
