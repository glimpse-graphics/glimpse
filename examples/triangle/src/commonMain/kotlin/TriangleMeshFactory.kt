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

package graphics.glimpse.examples.triangle

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.meshes.ArrayMeshData
import graphics.glimpse.meshes.Mesh
import graphics.glimpse.meshes.MeshDataBuilder
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3

/**
 * A factory for a triangle mesh.
 */
class TriangleMeshFactory {

    /**
     * Creates a new triangle mesh.
     */
    fun createTriangleMesh(gl: GlimpseAdapter): Mesh =
        Mesh.Factory.newInstance(gl)
            .createMesh(createTriangleMeshData())

    @Suppress("MagicNumber")
    private fun createTriangleMeshData(): ArrayMeshData =
        MeshDataBuilder()
            .addVertex(Vec3(x = 0f, y = 1f, z = 0f))
            .addVertex(Vec3(x = -1f, y = -1f, z = 0f))
            .addVertex(Vec3(x = 1f, y = -1f, z = 0f))
            .addTextureCoordinates(Vec2(x = 0.5f, y = 1f))
            .addTextureCoordinates(Vec2(x = 0f, y = 0f))
            .addTextureCoordinates(Vec2(x = 1f, y = 0f))
            .addNormal(Vec3(x = 0f, y = 0f, z = 1f))
            .addFace(
                listOf(
                    MeshDataBuilder.FaceVertex(
                        positionIndex = 0,
                        texCoordIndex = 0,
                        normalIndex = 0
                    ),
                    MeshDataBuilder.FaceVertex(
                        positionIndex = 1,
                        texCoordIndex = 1,
                        normalIndex = 0
                    ),
                    MeshDataBuilder.FaceVertex(
                        positionIndex = 2,
                        texCoordIndex = 2,
                        normalIndex = 0
                    )
                )
            )
            .buildArrayMeshData()
}
