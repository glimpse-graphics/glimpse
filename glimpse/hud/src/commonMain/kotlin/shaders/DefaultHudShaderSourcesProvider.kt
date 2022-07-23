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

package graphics.glimpse.hud.shaders

import graphics.glimpse.shaders.ShaderType

internal object DefaultHudShaderSourcesProvider : HudShaderSourcesProvider {

    private const val VERTEX_SHADER_SOURCE = """
        #version 100
        precision lowp float;
        uniform highp mat4 uMVPMat;
        attribute highp vec2 aPos;
        attribute vec2 aTexCoords;
        varying vec2 vTexCoords;
        void main() {
            vTexCoords = aTexCoords;
            gl_Position = uMVPMat * vec4(aPos, 0.0, 1.0);
        }
    """

    private const val FRAGMENT_SHADER_SOURCE = """
        #version 100
        precision lowp float;
        uniform sampler2D uTex;
        varying vec2 vTexCoords;
        void main() {
            gl_FragColor = texture2D(uTex, vTexCoords);
        }
    """

    override fun getShaderSource(shaderType: ShaderType): String = when(shaderType) {
        ShaderType.VERTEX_SHADER -> VERTEX_SHADER_SOURCE
        ShaderType.FRAGMENT_SHADER -> FRAGMENT_SHADER_SOURCE
    }
}
