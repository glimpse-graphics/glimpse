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

package graphics.glimpse.shaders

import graphics.glimpse.GlimpseAdapter

/**
 * A shader.
 */
interface Shader {

    /**
     * Shader handle.
     */
    val handle: Int

    /**
     * Tells the given [OpenGL adapter][gl] to dispose this shader.
     */
    fun dispose(gl: GlimpseAdapter)

    /**
     * A factory for shaders.
     */
    interface Factory {

        /**
         * Creates a new shader of a given [type] from the given shader [source].
         */
        fun createShader(type: ShaderType, source: String): Shader

        companion object {

            /**
             * Returns a new instance of shader factory.
             */
            fun newInstance(gl: GlimpseAdapter): Factory = ShaderFactoryImpl(gl)
        }
    }
}
