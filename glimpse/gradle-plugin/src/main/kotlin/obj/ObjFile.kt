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

package graphics.glimpse.gradle.obj

import java.io.File

/**
 * Wavefront OBJ file wrapper.
 */
class ObjFile(private val file: File) {

    /**
     * Accepts Wavefront OBJ file visitor.
     */
    fun accept(visitor: ObjFileVisitor) {
        file.useLines { lines ->
            lines.forEach { line ->
                val tokens = line.trim().split("\\s".toRegex())
                when(tokens.first()) {
                    TOKEN_VERTEX_POSITION -> visitor.visitVertexPosition(tokens.dropFirst().toFloats())
                    TOKEN_TEXTURE_COORDINATES -> visitor.visitTextureCoordinates(tokens.dropFirst().toFloats())
                    TOKEN_VERTEX_NORMAL -> visitor.visitVertexNormal(tokens.dropFirst().toFloats())
                    TOKEN_FACE -> visitor.visitFace(tokens.dropFirst().toFaceVertices())
                }
            }
            visitor.afterLast()
        }
    }

    private fun List<String>.dropFirst(): List<String> = drop(n = 1)

    private fun List<String>.toFloats(): List<Float> = map { it.toFloat() }.toList()

    private fun List<String>.toFaceVertices(): List<Triple<Int?, Int?, Int?>> = map { vertex ->
        val indices = vertex.split(DELIMITER_FACE_VERTEX).map { it.toIntOrNull() }
        val (pos, texCoord, norm) = indices + listOf(null, null)
        return@map Triple(pos, texCoord, norm)
    }.toList()

    companion object {
        private const val TOKEN_VERTEX_POSITION = "v"
        private const val TOKEN_TEXTURE_COORDINATES = "vt"
        private const val TOKEN_VERTEX_NORMAL = "vn"
        private const val TOKEN_FACE = "f"
        private const val DELIMITER_FACE_VERTEX = "/"
    }
}
