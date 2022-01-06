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

package graphics.glimpse.buffers

import graphics.glimpse.GlimpseAdapter

/**
 * A wrapper for a vertex data buffer.
 */
interface Buffer {

    /**
     * Type of the buffer.
     */
    val type: BufferType

    /**
     * Buffer handle.
     */
    val handle: Int

    /**
     * Tells the given [OpenGL adapter][gl] to use this buffer.
     */
    fun use(gl: GlimpseAdapter)

    /**
     * Tells the given [OpenGL adapter][gl] to dispose this buffer.
     */
    fun dispose(gl: GlimpseAdapter)

    /**
     * A factory for vertex data buffers.
     */
    interface Factory {

        /**
         * Creates new buffers of vertex attributes, defined by given [buffersData].
         */
        fun createArrayBuffers(vararg buffersData: FloatBufferData): List<Buffer>

        /**
         * Creates new buffers of vertex array indices, defined by given [buffersData].
         */
        fun createElementArrayBuffers(vararg buffersData: IntBufferData): List<Buffer>

        companion object {

            /**
             * Returns a new instance of buffer factory.
             */
            fun newInstance(gl: GlimpseAdapter): Factory = BufferFactoryImpl(gl)
        }
    }
}
