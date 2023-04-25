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

import androidx.compose.material.Colors
import graphics.glimpse.meshes.ArrayMeshData
import graphics.glimpse.shaders.ShaderType
import graphics.glimpse.textures.TextureImageSource

/**
 * Application resources provider.
 */
expect class AppResources {

    /**
     * Returns application name.
     */
    fun getAppName(): String

    /**
     * Returns application theme colors.
     */
    fun getThemeColors(): Colors

    /**
     * Returns mesh data.
     */
    fun getMeshData(): ArrayMeshData

    /**
     * Returns source of a shader of a given [type].
     */
    fun getShaderSource(type: ShaderType): String

    /**
     * Returns texture image source.
     */
    fun getTextureSource(): TextureImageSource

    /**
     * Returns normal map image source.
     */
    fun getNormalMapSource(): TextureImageSource

    /**
     * Returns mesh data for blur effect.
     */
    fun getBlurMeshData(): ArrayMeshData

    /**
     * Returns source of a blur effect shader of a given [type].
     */
    fun getBlurXShaderSource(type: ShaderType): String

    /**
     * Returns source of a blur effect shader of a given [type].
     */
    fun getBlurYShaderSource(type: ShaderType): String
}
