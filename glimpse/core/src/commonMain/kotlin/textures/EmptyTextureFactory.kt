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

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.logging.GlimpseLogger

internal class EmptyTextureFactory(private val gl: GlimpseAdapter) {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    fun create(width: Int, height: Int, presets: EmptyTexturePresets): Texture {
        logger.debug(message = "Generating empty texture with presets: $presets")

        val handles = IntArray(size = 1)
        gl.glGenTextures(handles)

        val textureHandle = handles.single()

        gl.glBindTexture(TextureType.TEXTURE_2D, textureHandle)
        gl.glTexImage2D(
            internalFormat = presets.internalFormat,
            width = width,
            height = height,
            pixelFormat = presets.pixelFormat,
            pixelType = presets.pixelType,
            pixelData = null
        )
        gl.glTexParameterFilter(TextureType.TEXTURE_2D, TextureMinFilter.LINEAR, TextureMagFilter.LINEAR)
        gl.glTexParameterWrap(TextureType.TEXTURE_2D, TextureWrap.CLAMP_TO_EDGE, TextureWrap.CLAMP_TO_EDGE)

        return TextureImpl(textureHandle)
    }

    data class TextureImpl(override val handle: Int) : Texture {

        override fun useAtIndex(gl: GlimpseAdapter, textureIndex: Int) {
            gl.glActiveTexture(textureIndex)
            gl.glBindTexture(TextureType.TEXTURE_2D, handle)
        }

        override fun dispose(gl: GlimpseAdapter) {
            val handles = intArrayOf(handle)
            gl.glDeleteTextures(handles)
        }
    }
}
