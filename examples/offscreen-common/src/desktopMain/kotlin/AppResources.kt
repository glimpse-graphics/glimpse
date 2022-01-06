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

package graphics.glimpse.examples.offscreen

import graphics.glimpse.meshes.ArrayMeshData
import graphics.glimpse.meshes.obj.ObjMeshDataParser
import graphics.glimpse.meshes.obj.parseArrayMeshData
import graphics.glimpse.shaders.ShaderType
import graphics.glimpse.textures.TextureImageSource
import graphics.glimpse.textures.fromResource
import java.util.*

/**
 * Application resources provider.
 */
actual class AppResources {

    private val bundle = ResourceBundle.getBundle(javaClass.simpleName, Locale.getDefault())

    /**
     * Returns application name.
     */
    actual fun getAppName(): String = bundle.getString("appName")

    /**
     * Returns mesh data.
     */
    actual fun getMeshData(): ArrayMeshData =
        ObjMeshDataParser().parseArrayMeshData(javaClass.getResourceAsStream(MESH_FILE_NAME))

    /**
     * Returns source of a shader of a given [type].
     */
    actual fun getShaderSource(type: ShaderType): String =
        javaClass.getResourceAsStream(getShaderResourceName(type))
            .use { it.bufferedReader().readText() }

    private fun getShaderResourceName(type: ShaderType): String =
        "/${type.name.lowercase(Locale.ENGLISH)}.glsl"

    /**
     * Returns texture image source.
     */
    actual fun getTextureSource(): TextureImageSource =
        TextureImageSource.builder()
            .fromResource(this, TEXTURE_FILE_NAME)
            .build()

    /**
     * Returns normal map image source.
     */
    actual fun getNormalMapSource(): TextureImageSource =
        TextureImageSource.builder()
            .fromResource(this, NORMAL_MAP_FILE_NAME)
            .build()

    companion object {
        private const val MESH_FILE_NAME = "/quad.obj"
        private const val TEXTURE_FILE_NAME = "/bricks_texture.png"
        private const val NORMAL_MAP_FILE_NAME = "/bricks_normalmap.png"
    }
}
