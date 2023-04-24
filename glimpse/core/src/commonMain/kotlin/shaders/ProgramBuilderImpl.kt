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

package graphics.glimpse.shaders

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.logging.GlimpseLogger
import java.util.concurrent.atomic.AtomicBoolean

internal class ProgramBuilderImpl(private val gl: GlimpseAdapter) : Program.Builder {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    private var vertexShaderHandle: Int = 0
    private var fragmentShaderHandle: Int = 0

    override fun withVertexShader(vertexShader: Shader): Program.Builder {
        vertexShaderHandle = vertexShader.handle
        return this
    }

    override fun withFragmentShader(fragmentShader: Shader): Program.Builder {
        fragmentShaderHandle = fragmentShader.handle
        return this
    }

    override fun build(): Program {
        check(value = vertexShaderHandle != 0) { "Vertex shader not set" }
        check(value = fragmentShaderHandle != 0) { "Fragment shader not set" }

        logger.debug(
            message = """
                |Linking program with:
                | - vertex shader $vertexShaderHandle
                | - fragment shader $fragmentShaderHandle
            """.trimMargin()
        )

        val handle = gl.glCreateProgram()

        gl.glAttachShader(handle, vertexShaderHandle)
        gl.glAttachShader(handle, fragmentShaderHandle)

        linkProgram(handle)
        validateProgram(handle)

        return ProgramImpl(handle, vertexShaderHandle, fragmentShaderHandle)
    }

    private fun linkProgram(handle: Int) {
        gl.glLinkProgram(handle)

        if (!gl.glGetProgramLinkStatus(handle)) {
            val programInfoLog = gl.glGetProgramInfoLog(handle)
            logger.error(message = "Program linking failed:\n$programInfoLog\nCleaning up")

            cleanUpAfterError(handle)

            error("Program linking failed:\n$programInfoLog")
        }
    }

    private fun validateProgram(handle: Int) {
        gl.glValidateProgram(handle)

        if (!gl.glGetProgramValidateStatus(handle)) {
            val programInfoLog = gl.glGetProgramInfoLog(handle)
            logger.error(message = "Program validation failed:\n$programInfoLog\nCleaning up")

            cleanUpAfterError(handle)

            error("Program validation failed:\n$programInfoLog")
        }
    }

    private fun cleanUpAfterError(handle: Int) {
        gl.glDeleteProgram(handle)
        gl.glDeleteShader(vertexShaderHandle)
        gl.glDeleteShader(fragmentShaderHandle)
    }

    private data class ProgramImpl(
        override val handle: Int,
        private val vertexShaderHandle: Int,
        private val fragmentShaderHandle: Int
    ) : Program {

        private val disposed = AtomicBoolean(false)
        override val isDisposed: Boolean get() = disposed.get()

        override fun use(gl: GlimpseAdapter) {
            gl.glUseProgram(handle)
        }

        override fun dispose(gl: GlimpseAdapter) {
            check(disposed.compareAndSet(false, true)) { "Program is already disposed" }

            if (!gl.glGetProgramDeleteStatus(handle)) {
                gl.glDeleteProgram(handle)
            }

            if (!gl.glGetShaderDeleteStatus(vertexShaderHandle)) {
                gl.glDeleteShader(vertexShaderHandle)
            }
            if (!gl.glGetShaderDeleteStatus(fragmentShaderHandle)) {
                gl.glDeleteShader(fragmentShaderHandle)
            }
        }
    }
}
