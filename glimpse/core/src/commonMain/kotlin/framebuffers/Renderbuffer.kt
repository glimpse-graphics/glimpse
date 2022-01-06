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
import graphics.glimpse.textures.TextureInternalFormat

/**
 * A renderbuffer.
 *
 * @since v1.1.0
 */
interface Renderbuffer {

    /**
     * Renderbuffer handle.
     */
    val handle: Int

    /**
     * Disposes this renderbuffer.
     */
    fun dispose(gl: GlimpseAdapter)

    /**
     * A factory for renderbuffers.
     */
    interface Factory {

        /**
         * Creates a new renderbuffer of the given [width] and [height], and with the given [internalFormat].
         */
        fun createRenderbuffer(width: Int, height: Int, internalFormat: TextureInternalFormat): Renderbuffer

        companion object {

            /**
             * Returns a new instance of renderbuffer factory.
             */
            fun newInstance(gl: GlimpseAdapter): Factory = RenderbufferFactoryImpl(gl)
        }
    }
}
