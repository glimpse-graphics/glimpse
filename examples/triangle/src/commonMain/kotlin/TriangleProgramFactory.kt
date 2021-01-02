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

package graphics.glimpse.examples.triangle

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.shaders.Program
import graphics.glimpse.shaders.Shader
import graphics.glimpse.shaders.ShaderType

/**
 * A factory for a _Triangle_ example program.
 */
class TriangleProgramFactory {

    /**
     * Creates a _Triangle_ example program.
     */
    fun createProgram(gl: GlimpseAdapter): Program {
        val shaderFactory = Shader.Factory.newInstance(gl)
        val vertexShader = shaderFactory.createShader(
            type = ShaderType.VERTEX_SHADER,
            source = VERTEX_SHADER_SOURCE.trimIndent()
        )
        val fragmentShader = shaderFactory.createShader(
            type = ShaderType.FRAGMENT_SHADER,
            source = FRAGMENT_SHADER_SOURCE.trimIndent()
        )

        return Program.Builder.newInstance(gl)
            .withVertexShader(vertexShader)
            .withFragmentShader(fragmentShader)
            .build()
    }

    companion object {

        private const val VERTEX_SHADER_SOURCE = """
            #version 100

            precision mediump float;

            uniform mat4 uProjMatrix;
            uniform mat4 uViewMatrix;
            uniform mat4 uModelMatrix;

            attribute vec3 aPos;
            attribute vec2 aTexCoords;

            varying vec2 vTexCoords;

            void main() {
                vec4 position = vec4(aPos, 1.0);
                vTexCoords = aTexCoords;
                gl_Position = uProjMatrix * uViewMatrix * uModelMatrix * position;
            }
        """

        private const val FRAGMENT_SHADER_SOURCE = """
            #version 100

            precision mediump float;

            varying vec2 vTexCoords;

            void main() {
                float yellow = 1.0 - vTexCoords.y;
                float blue = vTexCoords.y;
                float red = yellow * (1.0 - vTexCoords.x);
                float green = yellow * vTexCoords.x;
                gl_FragColor = vec4(red, green, blue, 1.0);
            }
        """
    }
}
