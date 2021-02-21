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
import graphics.glimpse.logging.GlimpseLogger

internal class TextureBuilderImpl(private val gl: GlimpseAdapter) : Texture.Builder {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    private val params = mutableListOf<TextureParams>()
    private var generateMipmaps = false

    override fun addTexture(source: TextureImageSource): Texture.Builder {
        params.add(TextureParams(TextureType.TEXTURE_2D, side = null, source))
        return this
    }

    override fun addCubemapTexture(
        side: CubemapSide,
        source: TextureImageSource
    ): Texture.Builder {
        params.add(TextureParams(TextureType.TEXTURE_CUBE_MAP, side, source))
        return this
    }

    override fun generateMipmaps(): Texture.Builder {
        generateMipmaps = true
        return this
    }

    override fun build(): List<Texture> {
        check(params.isNotEmpty()) { "No textures to generate" }
        val texturesCount = params.size
        logger.debug(message = "Generating $texturesCount textures")
        val handles = IntArray(size = texturesCount)
        gl.glGenTextures(handles)
        for ((index, textureParams) in params.withIndex()) {
            gl.glBindTexture(textureParams.type, handles[index])
            when (textureParams.type) {
                TextureType.TEXTURE_2D -> {
                    textureParams.source.glTexImage2D(gl, generateMipmaps)
                }
                TextureType.TEXTURE_CUBE_MAP -> {
                    textureParams.source.glTexImage2D(
                        gl,
                        checkNotNull(textureParams.side) { "Null side for cubemap texture" },
                        generateMipmaps
                    )
                }
            }

        }
        val textures = handles.mapIndexed { index, handle ->
            TextureImpl(handle, params[index].type)
        }
        params.clear()
        generateMipmaps = false
        return textures
    }

    private data class TextureParams(
        val type: TextureType,
        val side: CubemapSide?,
        val source: TextureImageSource
    )

    data class TextureImpl(
        override val handle: Int,
        private val type: TextureType
    ) : Texture {

        override fun useAtIndex(gl: GlimpseAdapter, textureIndex: Int) {
            gl.glActiveTexture(textureIndex)
            gl.glBindTexture(type, handle)
        }

        override fun dispose(gl: GlimpseAdapter) {
            val handles = intArrayOf(handle)
            gl.glDeleteTextures(handles)
        }
    }
}
