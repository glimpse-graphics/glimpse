/*
 * Copyright 2020 Slawomir Czerwinski
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

package graphics.glimpse.buffers

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.logging.GlimpseLogger

internal class BufferFactoryImpl(private val gl: GlimpseAdapter) : Buffer.Factory {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    override fun createArrayBuffers(vararg buffersData: FloatBufferData): List<Buffer> {
        val buffersCount = buffersData.size

        logger.debug(
            message = "Creating $buffersCount array buffers with sizes: " +
                    "${buffersData.map { it.sizeInBytes }} bytes"
        )

        val handles = IntArray(size = buffersCount)
        gl.glGenBuffers(handles)

        return buffersData.mapIndexed { index, bufferData ->
            gl.glBindBuffer(
                type = BufferType.ARRAY_BUFFER,
                bufferHandle = handles[index]
            )
            gl.glBufferData(
                type = BufferType.ARRAY_BUFFER,
                data = bufferData,
                usage = BufferUsage.STATIC_DRAW
            )
            BufferImpl(
                type = BufferType.ARRAY_BUFFER,
                handle = handles[index]
            )
        }
    }

    override fun createElementArrayBuffers(vararg buffersData: IntBufferData): List<Buffer> {
        val buffersCount = buffersData.size

        logger.debug(
            message = "Creating $buffersCount element array buffers with sizes: " +
                    "${buffersData.map { it.sizeInBytes }} bytes"
        )

        val handles = IntArray(size = buffersCount)
        gl.glGenBuffers(handles)

        return buffersData.mapIndexed { index, bufferData ->
            gl.glBindBuffer(
                type = BufferType.ELEMENT_ARRAY_BUFFER,
                bufferHandle = handles[index]
            )
            gl.glBufferData(
                type = BufferType.ELEMENT_ARRAY_BUFFER,
                data = bufferData,
                usage = BufferUsage.STATIC_DRAW
            )
            BufferImpl(
                type = BufferType.ELEMENT_ARRAY_BUFFER,
                handle = handles[index]
            )
        }
    }

    private data class BufferImpl(
        override val type: BufferType,
        override val handle: Int
    ) : Buffer {

        override fun use(gl: GlimpseAdapter) {
            gl.glBindBuffer(type, handle)
        }

        override fun dispose(gl: GlimpseAdapter) {
            gl.glDeleteBuffers(intArrayOf(handle))
        }
    }
}
