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

package graphics.glimpse.hud.shaders

import graphics.glimpse.shaders.ShaderType

/**
 * Provider of HUD shader sources.
 *
 * Shader sources returned by implementations of this interface
 * can use the following input variables:
 *  * `attribute vec2 aPos` – vertex position in 2D space,
 *  * `attribute vec2 aTexCoords` – texture coordinates,
 *  * `uniform mat4 uMVPMat` – MVP matrix,
 *  * `uniform sampler2D uTex` – texture of the [atom][graphics.glimpse.hud.HudAtom].
 */
interface HudShaderSourcesProvider {

    /**
     * Returns shader source for given [shaderType].
     */
    fun getShaderSource(shaderType: ShaderType): String

    companion object {

        val DEFAULT: HudShaderSourcesProvider = DefaultHudShaderSourcesProvider
    }
}
