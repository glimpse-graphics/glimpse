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

package graphics.glimpse.meshes

import graphics.glimpse.buffers.toFloatBufferData
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3

/**
 * A builder for [MeshData].
 */
class MeshDataBuilder {

    private val positions = mutableListOf<Vec3>()
    private val texCoords = mutableListOf<Vec2>()
    private val normals = mutableListOf<Vec3>()
    private val triangles = mutableListOf<Triangle>()

    /**
     * Adds another [vertex position][vertexPos] to the mesh.
     */
    fun addVertex(vertexPos: Vec3): MeshDataBuilder {
        positions.add(vertexPos)
        return this
    }

    /**
     * Adds another vertex position to the mesh.
     */
    fun addVertex(coordinates: List<Float>): MeshDataBuilder =
        addVertex(Vec3.fromList(coordinates))

    /**
     * Adds another texture [coordinates] to the mesh.
     */
    fun addTextureCoordinates(coordinates: Vec2): MeshDataBuilder {
        texCoords.add(coordinates)
        return this
    }

    /**
     * Adds another texture coordinates to the mesh.
     */
    fun addTextureCoordinates(coordinates: List<Float>): MeshDataBuilder =
        addTextureCoordinates(Vec2.fromList(coordinates))

    /**
     * Adds another [normal] vector to the mesh.
     */
    fun addNormal(normal: Vec3): MeshDataBuilder {
        normals.add(normal)
        return this
    }

    /**
     * Adds another normal vector to the mesh.
     */
    fun addNormal(coordinates: List<Float>): MeshDataBuilder =
        addNormal(Vec3.fromList(coordinates))

    /**
     * Adds another face to the mesh.
     */
    fun addFace(indices: List<FaceVertex>): MeshDataBuilder {
        val p1 = indices.first()
        indices.drop(n = 1).windowed(size  = 2) { (p2, p3) ->
            triangles.add(
                Triangle(
                    position1 = positions[p1.positionIndex],
                    position2 = positions[p2.positionIndex],
                    position3 = positions[p3.positionIndex],
                    texCoord1 = texCoords[p1.texCoordIndex],
                    texCoord2 = texCoords[p2.texCoordIndex],
                    texCoord3 = texCoords[p3.texCoordIndex],
                    normal1 = normals[p1.normalIndex],
                    normal2 = normals[p2.normalIndex],
                    normal3 = normals[p3.normalIndex]
                )
            )
        }
        return this
    }

    /**
     * Builds a container for the array buffers data related to a single mesh, without indices.
     */
    fun buildArrayMeshData(): ArrayMeshData {
        val meshData =  ArrayMeshData(
            vertexCount = triangles.size * TRIANGLE_VERTICES,
            positionsData = triangles.flatMap { triangle ->
                triangle.positions.flatMap { vector -> vector.toList() }
            }.toFloatBufferData(),
            texCoordsData = triangles.flatMap { triangle ->
                triangle.textureCoordinates.flatMap { vector -> vector.toList() }
            }.toFloatBufferData(),
            normalsData = triangles.flatMap { triangle ->
                triangle.normals.flatMap { vector -> vector.toList() }
            }.toFloatBufferData(),
            tangentsData = triangles.flatMap { triangle ->
                triangle.tangents.flatMap { vector -> vector.toList() }
            }.toFloatBufferData()
        )
        positions.clear()
        texCoords.clear()
        normals.clear()
        triangles.clear()
        return meshData
    }

    /**
     * Indices for a single face vertex.
     */
    data class FaceVertex(

        /**
         * Index of the vertex position.
         */
        val positionIndex: Int,

        /**
         * Index of the texture coordinates.
         */
        val texCoordIndex: Int,

        /**
         * Index of the normal vector.
         */
        val normalIndex: Int
    )

    private data class Triangle(
        val position1: Vec3,
        val position2: Vec3,
        val position3: Vec3,
        val texCoord1: Vec2,
        val texCoord2: Vec2,
        val texCoord3: Vec2,
        val normal1: Vec3,
        val normal2: Vec3,
        val normal3: Vec3
    ) {

        val tangent: Vec3

        val positions: List<Vec3>
            get() = listOf(position1, position2, position3)

        val textureCoordinates: List<Vec2>
            get() = listOf(texCoord1, texCoord2, texCoord3)

        val normals: List<Vec3>
            get() = listOf(normal1, normal2, normal3)

        val tangents: List<Vec3>
            get() = listOf(tangent, tangent, tangent)

        init {
            val edge1 = position2 - position1
            val edge2 = position3 - position1

            val deltaUV1 = texCoord2 - texCoord1
            val deltaUV2 = texCoord3 - texCoord1

            val factor = 1f / (deltaUV1.x * deltaUV2.y - deltaUV2.x * deltaUV1.y)

            tangent = (edge1 * deltaUV2.y - edge2 * deltaUV1.y) * factor
        }
    }

    companion object {
        private const val TRIANGLE_VERTICES = 3
    }
}
