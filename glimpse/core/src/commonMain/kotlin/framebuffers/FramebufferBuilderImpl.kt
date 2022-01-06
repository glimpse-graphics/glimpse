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

package graphics.glimpse.framebuffers

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.textures.Texture
import graphics.glimpse.textures.TextureType

internal class FramebufferBuilderImpl(private val gl: GlimpseAdapter) : Framebuffer.Builder {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    private val renderbuffers = mutableMapOf<FramebufferAttachmentType, Renderbuffer>()
    private val textures = mutableMapOf<FramebufferAttachmentType, Texture>()

    override fun attachRenderbuffer(type: FramebufferAttachmentType, renderbuffer: Renderbuffer): Framebuffer.Builder {
        textures.remove(type)
        renderbuffers[type] = renderbuffer
        return this
    }

    override fun attachTexture(type: FramebufferAttachmentType, texture: Texture): Framebuffer.Builder {
        renderbuffers.remove(type)
        textures[type] = texture
        return this
    }

    override fun build(): Framebuffer {
        val attachments = (renderbuffers + textures).asSequence()
            .joinToString(separator = "\n") { (type, attachment) -> "$type: $attachment" }
        logger.debug(message = "Building framebuffer with attachments:\n$attachments")

        val handles = IntArray(size = 1)
        gl.glGenFramebuffers(handles)

        val handle = handles.single()
        gl.glBindFramebuffer(handle)

        attachRenderbuffers()
        attachTextures()

        try {
            checkFramebufferStatus(handles)
        } finally {
            gl.glBindFramebuffer(framebufferHandle = 0)
        }

        return FramebufferImpl(handle, renderbuffers.toMap(), textures.toMap())
    }

    private fun attachRenderbuffers() {
        for ((type, renderbuffer) in renderbuffers) {
            gl.glFramebufferRenderbuffer(type, renderbuffer.handle)
        }
    }

    private fun attachTextures() {
        for ((type, texture) in textures) {
            gl.glFramebufferTexture2D(type, TextureType.TEXTURE_2D, texture.handle)
        }
    }

    private fun checkFramebufferStatus(framebufferHandles: IntArray) {
        val framebufferStatus = gl.glCheckFramebufferStatus()
        if (framebufferStatus != FramebufferStatus.COMPLETE) {
            gl.glDeleteFramebuffers(framebufferHandles)
            val errorMessage = "Framebuffer is not complete: $framebufferStatus"
            logger.error(errorMessage)
            throw IllegalStateException(errorMessage)
        }
    }

    data class FramebufferImpl(
        override val handle: Int,
        override val renderbuffers: Map<FramebufferAttachmentType, Renderbuffer>,
        override val textures: Map<FramebufferAttachmentType, Texture>
    ) : Framebuffer {

        override fun dispose(gl: GlimpseAdapter) {
            val handles = intArrayOf(handle)
            gl.glDeleteFramebuffers(handles)

            for (renderbuffer in renderbuffers.values) {
                renderbuffer.dispose(gl)
            }

            for (texture in textures.values) {
                texture.dispose(gl)
            }
        }
    }
}
