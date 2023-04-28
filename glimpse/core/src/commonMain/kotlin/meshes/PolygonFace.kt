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

package graphics.glimpse.meshes

import graphics.glimpse.types.Mat3
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import kotlin.math.sign

internal data class PolygonFace(
    val positions: List<Vec3<Float>>,
    val texCoords: List<Vec2<Float>>,
    val normals: List<Vec3<Float>>
) {
    init {
        require(positions.size >= TRIANGLE_VERTICES) {
            "Face needs at least $TRIANGLE_VERTICES geometric vertices, but ${positions.size} found"
        }
        require(texCoords.size == positions.size) {
            "Face has ${texCoords.size} texture coordinates, but ${positions.size} geometric vertices"
        }
        require(normals.size == positions.size) {
            "Face has ${normals.size} normals, but ${positions.size} geometric vertices"
        }
    }

    fun triangulate(): List<Triangle> {
        if (positions.size == TRIANGLE_VERTICES) {
            return listOf(createTriangle(defaultTriangleIndices))
        }

        val segments = (positions + positions.first()).zipWithNext { a, b -> b - a }
        val angleVectors = (listOf(segments.last()) + segments).zipWithNext { a, b -> a cross b }
        val normal = angleVectors.reduce(Vec3<Float>::plus).normalize()
        val axisY = if (normal != Vec3.unitY<Float>()) Vec3.unitY<Float>() else Vec3.unitZ()
        val tangent = axisY cross normal
        val bitangent = normal cross tangent
        val tbn = Mat3(elements = tangent.toList() + bitangent.toList() + normal.toList()).transpose()

        return triangulate2D(vertices = positions.map { (tbn * it).toVec2() })
            .map { triple -> createTriangle(triple) }
    }

    private fun createTriangle(indices: Triple<Int, Int, Int>): Triangle =
        Triangle(
            position1 = positions[indices.first],
            position2 = positions[indices.second],
            position3 = positions[indices.third],
            texCoord1 = texCoords[indices.first],
            texCoord2 = texCoords[indices.second],
            texCoord3 = texCoords[indices.third],
            normal1 = normals[indices.first],
            normal2 = normals[indices.second],
            normal3 = normals[indices.third]
        )

    private fun triangulate2D(vertices: List<Vec2<Float>>): List<Triple<Int, Int, Int>> {
        fun Vec2<Float>.isInside(triangleVertices: List<Vec2<Float>>): Boolean =
            (triangleVertices + triangleVertices.first())
                .zipWithNext { a, b -> sign(((this - a) cross (b - this)).z) }
                .distinct()
                .size == 1

        fun findEar(indices: List<Int>): Triple<Int, Int, Int> =
            if (indices.size == TRIANGLE_VERTICES) {
                val (a, b, c) = indices
                Triple(a, b, c)
            } else {
                (listOf(indices.last()) + indices + indices.first()).asSequence()
                    .windowed(size = TRIANGLE_VERTICES)
                    .filter { (prev, curr, next) ->
                        ((vertices[curr] - vertices[prev]) cross (vertices[next] - vertices[curr])).z > 0f
                    }
                    .filter { triangleIndices ->
                        val triangleVertices = triangleIndices.map { vertices[it] }
                        val otherVertices = (indices - triangleIndices.toSet()).map { vertices[it] }
                        otherVertices.none { vertex -> vertex.isInside(triangleVertices) }
                    }
                    .map { (prev, curr, next) -> Triple(prev, curr, next) }
                    .firstOrNull()
                    ?: throw NoSuchElementException("No ear was found")
            }

        tailrec fun triangulate(indices: List<Int>, list: List<Triple<Int, Int, Int>>): List<Triple<Int, Int, Int>> {
            val ear: Triple<Int, Int, Int> = findEar(indices)
            return if (indices.size == TRIANGLE_VERTICES) {
                list + ear
            } else {
                triangulate(indices = indices - ear.second, list = list + ear)
            }
        }

        return triangulate(indices = vertices.indices.toList(), list = emptyList())
    }

    companion object {
        private val defaultTriangleIndices = Triple(first = 0, second = 1, third = 2)
    }
}
