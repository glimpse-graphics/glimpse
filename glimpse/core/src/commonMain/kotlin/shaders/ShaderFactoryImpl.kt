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
import graphics.glimpse.logging.GlimpseLogger

internal class ShaderFactoryImpl(private val gl: GlimpseAdapter) : Shader.Factory {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    override fun createShader(type: ShaderType, source: String): Shader {
        logger.debug(message = "Compiling shader from sources:\n$source")

        val handle = gl.glCreateShader(type)
        gl.glShaderSource(handle, source)

        compileShader(handle, type)

        return ShaderImpl(handle)
    }

    private fun compileShader(handle: Int, type: ShaderType) {
        gl.glCompileShader(handle)

        if (!gl.glGetShaderCompileStatus(handle)) {
            val shaderInfoLog = gl.glGetShaderInfoLog(handle)
            logger.error(message = "$type compilation failed:\n$shaderInfoLog\nCleaning up")

            gl.glDeleteShader(handle)

            throw IllegalStateException("$type compilation failed:\n$shaderInfoLog")
        }
    }

    private data class ShaderImpl(override val handle: Int) : Shader {

        private val logger: GlimpseLogger = GlimpseLogger.create(this)

        override fun dispose(gl: GlimpseAdapter) {
            gl.glDeleteShader(handle)

            if (!gl.glGetShaderDeleteStatus(handle)) {
                val shaderInfoLog = gl.glGetShaderInfoLog(handle)
                logger.error(message = "Shader deletion failed:\n$shaderInfoLog")

                throw IllegalStateException("Shader deletion failed:\n$shaderInfoLog")
            }
        }
    }
}
