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

import graphics.glimpse.buffers.toFloatBufferData
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3

/**
 * A builder for [MeshData].
 */
class MeshDataBuilder {

    private val positions = mutableListOf<Vec3<Float>>()
    private val texCoords = mutableListOf<Vec2<Float>>()
    private val normals = mutableListOf<Vec3<Float>>()
    private val triangles = mutableListOf<Triangle>()

    /**
     * Adds another [vertex position][vertexPos] to the mesh.
     */
    fun addVertex(vertexPos: Vec3<Float>): MeshDataBuilder {
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
    fun addTextureCoordinates(coordinates: Vec2<Float>): MeshDataBuilder {
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
    fun addNormal(normal: Vec3<Float>): MeshDataBuilder {
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
     *
     * The face is triangulated using ear clipping method.
     * For triangulation purposes, the polygon is projected on the average plane of its angles.
     */
    fun addFace(indices: List<FaceVertex>): MeshDataBuilder {
        PolygonFace(
            positions = indices.map { faceVertex -> positions[faceVertex.positionIndex] },
            texCoords = indices.map { faceVertex -> texCoords[faceVertex.texCoordIndex] },
            normals = indices.map { faceVertex -> normals[faceVertex.normalIndex] }
        )
            .triangulate()
            .forEach { triangle -> triangles.add(triangle) }
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
            }.toFloatBufferData(),
            bitangentsData = triangles.flatMap { triangle ->
                triangle.bitangents.flatMap { vector -> vector.toList() }
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

}
