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

package graphics.glimpse.shaders

import graphics.glimpse.GlimpseAdapter

internal class ShaderFactoryImpl(private val gl: GlimpseAdapter) : Shader.Factory {

    override fun createShader(type: ShaderType, source: String): Shader {
        gl.logger.debug(message = "Compiling shader from sources:\n$source")

        val handle = gl.glCreateShader(type)
        gl.glShaderSource(handle, source)
        gl.glCompileShader(handle)

        if (gl.glGetShaderCompileStatus(handle)) {
            return ShaderImpl(handle)
        } else {
            val shaderInfoLog = gl.glGetShaderInfoLog(handle)
            gl.logger.error(message = "$type compilation failed:\n$shaderInfoLog\nCleaning up")

            gl.glDeleteShader(handle)

            throw IllegalStateException("$type compilation failed:\n$shaderInfoLog")
        }
    }

    private data class ShaderImpl(override val handle: Int) : Shader {

        override fun dispose(gl: GlimpseAdapter) {
            gl.glDeleteShader(handle)

            if (!gl.glGetShaderDeleteStatus(handle)) {
                val shaderInfoLog = gl.glGetShaderInfoLog(handle)
                gl.logger.error(message = "Shader deletion failed:\n$shaderInfoLog")

                throw IllegalStateException("Shader deletion failed:\n$shaderInfoLog")
            }
        }
    }
}
