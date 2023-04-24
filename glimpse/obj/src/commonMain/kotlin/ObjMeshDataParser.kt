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

package graphics.glimpse.meshes.obj

import graphics.glimpse.meshes.ArrayMeshData
import graphics.glimpse.meshes.MeshData
import graphics.glimpse.meshes.MeshDataBuilder

/**
 * A parser of [MeshData] from Wavefront OBJ format.
 */
class ObjMeshDataParser(private val builder: MeshDataBuilder = MeshDataBuilder()) {

    /**
     * Returns a container for the array buffers data related to a single mesh loaded from a
     * Wavefront OBJ format.
     */
    fun parseArrayMeshData(lines: List<String>): ArrayMeshData =
        builder
            .addParsedLines(lines)
            .buildArrayMeshData()

    private fun MeshDataBuilder.addParsedLines(lines: List<String>): MeshDataBuilder {
        for (line in lines) {
            addParsedLine(line)
        }
        return this
    }

    private fun MeshDataBuilder.addParsedLine(line: String) {
        val (command, paramsString) = line.split(whiteSpaceRegex, limit = 2)
        val params = paramsString.split(whiteSpaceRegex)
        when (command) {
            VERTEX_KEYWORD -> addParsedVertex(params)
            TEXTURE_COORDINATES_KEYWORD -> addParsedTextureCoordinates(params)
            NORMAL_KEYWORD -> addParsedVertexNormal(params)
            FACE_KEYWORD -> addParsedFace(params)
        }
    }

    private fun MeshDataBuilder.addParsedVertex(params: List<String>) {
        addVertex(params.map { it.toFloat() })
    }

    private fun MeshDataBuilder.addParsedTextureCoordinates(params: List<String>) {
        val values = params.map { it.toFloat() }
        addTextureCoordinates(listOf(values[0], values[1]))
    }

    private fun MeshDataBuilder.addParsedVertexNormal(params: List<String>) {
        addNormal(params.map { it.toFloat() })
    }

    private fun MeshDataBuilder.addParsedFace(params: List<String>) {
        addFace(
            params.map { param ->
                val (vertexIndex, textureCoordinatesIndex, normalIndex) =
                    param.split(INDICES_DELIMITER).map { it.toInt() }
                return@map MeshDataBuilder.FaceVertex(
                    positionIndex = vertexIndex - 1,
                    texCoordIndex = textureCoordinatesIndex - 1,
                    normalIndex = normalIndex - 1
                )
            }
        )
    }

    companion object {
        private val whiteSpaceRegex = "\\s+".toRegex()
        private const val VERTEX_KEYWORD = "v"
        private const val TEXTURE_COORDINATES_KEYWORD = "vt"
        private const val NORMAL_KEYWORD = "vn"
        private const val FACE_KEYWORD = "f"
        private const val INDICES_DELIMITER = '/'
    }
}
