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

package graphics.glimpse.framebuffers

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.textures.TextureInternalFormat
import java.util.concurrent.atomic.AtomicBoolean

internal class RenderbufferFactoryImpl(private val gl: GlimpseAdapter) : Renderbuffer.Factory {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    override fun createRenderbuffer(width: Int, height: Int, internalFormat: TextureInternalFormat): Renderbuffer {
        logger.debug(message = "Creating renderbuffer with internal format: $internalFormat")

        val handles = IntArray(size = 1)
        gl.glGenRenderbuffers(handles)

        val handle = handles.single()
        gl.glBindRenderbuffer(handle)

        gl.glRenderbufferStorage(internalFormat, width, height)

        return RenderbufferImpl(handle)
    }

    data class RenderbufferImpl(override val handle: Int) : Renderbuffer {

        private val disposed = AtomicBoolean(false)
        override val isDisposed: Boolean get() = disposed.get()

        override fun dispose(gl: GlimpseAdapter) {
            check(disposed.compareAndSet(false, true)) { "Renderbuffer is already disposed" }
            val handles = intArrayOf(handle)
            gl.glDeleteRenderbuffers(handles)
        }
    }
}
