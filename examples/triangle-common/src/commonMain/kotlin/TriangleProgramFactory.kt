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

package graphics.glimpse.examples.triangle

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.shaders.Program
import graphics.glimpse.shaders.Shader
import graphics.glimpse.shaders.ShaderType

/**
 * A factory for a _Triangle_ example program.
 */
class TriangleProgramFactory(private val resources: AppResources) {

    /**
     * Creates a _Triangle_ example program.
     */
    fun createProgram(gl: GlimpseAdapter): Program {
        val shaderFactory = Shader.Factory.newInstance(gl)
        val vertexShader = shaderFactory.createShader(
            type = ShaderType.VERTEX_SHADER,
            source = resources.getShaderSource(ShaderType.VERTEX_SHADER)
        )
        val fragmentShader = shaderFactory.createShader(
            type = ShaderType.FRAGMENT_SHADER,
            source = resources.getShaderSource(ShaderType.FRAGMENT_SHADER)
        )

        return Program.Builder.newInstance(gl)
            .withVertexShader(vertexShader)
            .withFragmentShader(fragmentShader)
            .build()
    }
}
