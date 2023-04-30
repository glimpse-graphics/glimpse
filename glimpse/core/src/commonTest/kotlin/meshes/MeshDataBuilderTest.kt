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

import graphics.glimpse.buffers.floatBufferDataOf
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import kotlin.test.Test
import kotlin.test.assertEquals

class MeshDataBuilderTest {

    @Test
    fun `GIVEN MeshDataBuilder, WHEN build triangle array mesh data, THEN return mesh data with single triangle`() {
        val result = MeshDataBuilder()
            .addVertex(Vec3(x = -1f, y = -1f, z = 0f))
            .addVertex(Vec3(x = 1f, y = 1f, z = 0f))
            .addVertex(Vec3(x = 0f, y = 0f, z = 1f))
            .addTextureCoordinates(Vec2(x = 0f, y = 0f))
            .addTextureCoordinates(Vec2(x = 2f, y = 0f))
            .addTextureCoordinates(Vec2(x = 1f, y = 1f))
            .addNormal(Vec3(x = 0f, y = -1f, z = 0f))
            .addNormal(Vec3(x = 1f, y = 0f, z = 0f))
            .addNormal(Vec3(x = 0.7f, y = -0.7f, z = 0f))
            .addFace(
                listOf(
                    MeshDataBuilder.FaceVertex(positionIndex = 0, texCoordIndex = 0, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 1, texCoordIndex = 1, normalIndex = 1),
                    MeshDataBuilder.FaceVertex(positionIndex = 2, texCoordIndex = 2, normalIndex = 2),
                )
            )
            .buildArrayMeshData()

        assertEquals(
            ArrayMeshData(
                vertexCount = 3,
                positionsData = floatBufferDataOf(-1f, -1f, 0f, 1f, 1f, 0f, 0f, 0f, 1f),
                texCoordsData = floatBufferDataOf(0f, 0f, 2f, 0f, 1f, 1f),
                normalsData = floatBufferDataOf(0f, -1f, 0f, 1f, 0f, 0f, 0.7f, -0.7f, 0f),
                tangentsData = floatBufferDataOf(1f, 1f, 0f, 1f, 1f, 0f, 1f, 1f, 0f),
                bitangentsData = floatBufferDataOf(0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f)
            ),
            result
        )
    }

    @Test
    @Suppress("LongMethod")
    fun `GIVEN MeshDataBuilder, WHEN build concave XY polygon array mesh data, THEN return mesh data with triangles`() {
        val result = MeshDataBuilder()
            .addVertex(Vec3(x = 0f, y = 0f, z = 0f))
            .addVertex(Vec3(x = 1f, y = 1f, z = 0f))
            .addVertex(Vec3(x = 2f, y = 0f, z = 0f))
            .addVertex(Vec3(x = 3f, y = 2f, z = 0f))
            .addVertex(Vec3(x = 2f, y = 1f, z = 0f))
            .addVertex(Vec3(x = 1f, y = 2f, z = 0f))
            .addTextureCoordinates(Vec2(x = 0f, y = 0f))
            .addTextureCoordinates(Vec2(x = 1f, y = 1f))
            .addTextureCoordinates(Vec2(x = 2f, y = 0f))
            .addTextureCoordinates(Vec2(x = 3f, y = 2f))
            .addTextureCoordinates(Vec2(x = 2f, y = 1f))
            .addTextureCoordinates(Vec2(x = 1f, y = 2f))
            .addNormal(Vec3(x = 0f, y = 0f, z = 1f))
            .addFace(
                listOf(
                    MeshDataBuilder.FaceVertex(positionIndex = 0, texCoordIndex = 0, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 1, texCoordIndex = 1, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 2, texCoordIndex = 2, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 3, texCoordIndex = 3, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 4, texCoordIndex = 4, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 5, texCoordIndex = 5, normalIndex = 0)
                )
            )
            .buildArrayMeshData()

        assertEquals(
            ArrayMeshData(
                vertexCount = 12,
                positionsData = floatBufferDataOf(
                    1f, 2f, 0f, 0f, 0f, 0f, 1f, 1f, 0f,
                    1f, 2f, 0f, 1f, 1f, 0f, 2f, 0f, 0f,
                    2f, 0f, 0f, 3f, 2f, 0f, 2f, 1f, 0f,
                    2f, 0f, 0f, 2f, 1f, 0f, 1f, 2f, 0f
                ),
                texCoordsData = floatBufferDataOf(
                    1f, 2f, 0f, 0f, 1f, 1f,
                    1f, 2f, 1f, 1f, 2f, 0f,
                    2f, 0f, 3f, 2f, 2f, 1f,
                    2f, 0f, 2f, 1f, 1f, 2f
                ),
                normalsData = floatBufferDataOf(
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f
                ),
                tangentsData = floatBufferDataOf(
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f
                ),
                bitangentsData = floatBufferDataOf(
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f
                )
            ),
            result
        )
    }

    @Test
    @Suppress("LongMethod")
    fun `GIVEN MeshDataBuilder, WHEN build concave XZ polygon array mesh data, THEN return mesh data with triangles`() {
        val result = MeshDataBuilder()
            .addVertex(Vec3(x = 0f, y = 0f, z = 0f))
            .addVertex(Vec3(x = 1f, y = 0f, z = 1f))
            .addVertex(Vec3(x = 2f, y = 0f, z = 0f))
            .addVertex(Vec3(x = 3f, y = 0f, z = 2f))
            .addVertex(Vec3(x = 2f, y = 0f, z = 1f))
            .addVertex(Vec3(x = 1f, y = 0f, z = 2f))
            .addTextureCoordinates(Vec2(x = 0f, y = 0f))
            .addTextureCoordinates(Vec2(x = 1f, y = 1f))
            .addTextureCoordinates(Vec2(x = 2f, y = 0f))
            .addTextureCoordinates(Vec2(x = 3f, y = 2f))
            .addTextureCoordinates(Vec2(x = 2f, y = 1f))
            .addTextureCoordinates(Vec2(x = 1f, y = 2f))
            .addNormal(Vec3(x = 0f, y = 1f, z = 0f))
            .addFace(
                listOf(
                    MeshDataBuilder.FaceVertex(positionIndex = 0, texCoordIndex = 0, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 1, texCoordIndex = 1, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 2, texCoordIndex = 2, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 3, texCoordIndex = 3, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 4, texCoordIndex = 4, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 5, texCoordIndex = 5, normalIndex = 0)
                )
            )
            .buildArrayMeshData()

        assertEquals(
            ArrayMeshData(
                vertexCount = 12,
                positionsData = floatBufferDataOf(
                    1f, 0f, 2f, 0f, 0f, 0f, 1f, 0f, 1f,
                    1f, 0f, 2f, 1f, 0f, 1f, 2f, 0f, 0f,
                    2f, 0f, 0f, 3f, 0f, 2f, 2f, 0f, 1f,
                    2f, 0f, 0f, 2f, 0f, 1f, 1f, 0f, 2f
                ),
                texCoordsData = floatBufferDataOf(
                    1f, 2f, 0f, 0f, 1f, 1f,
                    1f, 2f, 1f, 1f, 2f, 0f,
                    2f, 0f, 3f, 2f, 2f, 1f,
                    2f, 0f, 2f, 1f, 1f, 2f
                ),
                normalsData = floatBufferDataOf(
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f
                ),
                tangentsData = floatBufferDataOf(
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f
                ),
                bitangentsData = floatBufferDataOf(
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f
                )
            ),
            result
        )
    }

    @Test
    @Suppress("LongMethod")
    fun `GIVEN MeshDataBuilder, WHEN build concave YZ polygon array mesh data, THEN return mesh data with triangles`() {
        val result = MeshDataBuilder()
            .addVertex(Vec3(x = 0f, y = 0f, z = 0f))
            .addVertex(Vec3(x = 0f, y = 1f, z = 1f))
            .addVertex(Vec3(x = 0f, y = 2f, z = 0f))
            .addVertex(Vec3(x = 0f, y = 3f, z = 2f))
            .addVertex(Vec3(x = 0f, y = 2f, z = 1f))
            .addVertex(Vec3(x = 0f, y = 1f, z = 2f))
            .addTextureCoordinates(Vec2(x = 0f, y = 0f))
            .addTextureCoordinates(Vec2(x = 1f, y = 1f))
            .addTextureCoordinates(Vec2(x = 2f, y = 0f))
            .addTextureCoordinates(Vec2(x = 3f, y = 2f))
            .addTextureCoordinates(Vec2(x = 2f, y = 1f))
            .addTextureCoordinates(Vec2(x = 1f, y = 2f))
            .addNormal(Vec3(x = 1f, y = 0f, z = 0f))
            .addFace(
                listOf(
                    MeshDataBuilder.FaceVertex(positionIndex = 0, texCoordIndex = 0, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 1, texCoordIndex = 1, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 2, texCoordIndex = 2, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 3, texCoordIndex = 3, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 4, texCoordIndex = 4, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 5, texCoordIndex = 5, normalIndex = 0)
                )
            )
            .buildArrayMeshData()

        assertEquals(
            ArrayMeshData(
                vertexCount = 12,
                positionsData = floatBufferDataOf(
                    0f, 1f, 2f, 0f, 0f, 0f, 0f, 1f, 1f,
                    0f, 1f, 2f, 0f, 1f, 1f, 0f, 2f, 0f,
                    0f, 2f, 0f, 0f, 3f, 2f, 0f, 2f, 1f,
                    0f, 2f, 0f, 0f, 2f, 1f, 0f, 1f, 2f
                ),
                texCoordsData = floatBufferDataOf(
                    1f, 2f, 0f, 0f, 1f, 1f,
                    1f, 2f, 1f, 1f, 2f, 0f,
                    2f, 0f, 3f, 2f, 2f, 1f,
                    2f, 0f, 2f, 1f, 1f, 2f
                ),
                normalsData = floatBufferDataOf(
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f,
                    1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f
                ),
                tangentsData = floatBufferDataOf(
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f,
                    0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f
                ),
                bitangentsData = floatBufferDataOf(
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f,
                    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f
                )
            ),
            result
        )
    }

    @Test
    @Suppress("LongMethod")
    fun `GIVEN MeshDataBuilder, WHEN build straight polygon array mesh data, THEN return mesh data with triangles`() {
        val result = MeshDataBuilder()
            .addVertex(Vec3(x = 0.10f, y = -0.55f, z = 0.00f))
            .addVertex(Vec3(x = 0.10f, y = 0.55f, z = 0.00f))
            .addVertex(Vec3(x = 0.13f, y = 0.57f, z = 0.05f))
            .addVertex(Vec3(x = 0.13f, y = 0.20f, z = 0.05f))
            .addVertex(Vec3(x = 0.13f, y = -0.20f, z = 0.05f))
            .addVertex(Vec3(x = 0.13f, y = -0.57f, z = 0.05f))
            .addTextureCoordinates(Vec2(x = 0f, y = 0f))
            .addTextureCoordinates(Vec2(x = 0f, y = 0f))
            .addTextureCoordinates(Vec2(x = 0f, y = 0f))
            .addTextureCoordinates(Vec2(x = 0f, y = 0f))
            .addTextureCoordinates(Vec2(x = 0f, y = 0f))
            .addTextureCoordinates(Vec2(x = 0f, y = 0f))
            .addNormal(Vec3(x = 1f, y = 0f, z = -1f))
            .addFace(
                listOf(
                    MeshDataBuilder.FaceVertex(positionIndex = 0, texCoordIndex = 0, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 1, texCoordIndex = 1, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 2, texCoordIndex = 2, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 3, texCoordIndex = 3, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 4, texCoordIndex = 4, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 5, texCoordIndex = 5, normalIndex = 0)
                )
            )
            .buildArrayMeshData()

        assertEquals(
            ArrayMeshData(
                vertexCount = 12,
                positionsData = floatBufferDataOf(
                    0.13f, -0.57f, 0.05f, 0.10f, -0.55f, 0.00f, 0.10f, 0.55f, 0.00f,
                    0.13f, -0.57f, 0.05f, 0.10f, 0.55f, 0.00f, 0.13f, 0.57f, 0.05f,
                    0.13f, -0.20f, 0.05f, 0.13f, -0.57f, 0.05f, 0.13f, 0.57f, 0.05f,
                    0.13f, 0.57f, 0.05f, 0.13f, 0.20f, 0.05f, 0.13f, -0.20f, 0.05f
                ),
                texCoordsData = floatBufferDataOf(
                    0f, 0f, 0f, 0f, 0f, 0f,
                    0f, 0f, 0f, 0f, 0f, 0f,
                    0f, 0f, 0f, 0f, 0f, 0f,
                    0f, 0f, 0f, 0f, 0f, 0f
                ),
                normalsData = floatBufferDataOf(
                    1f, 0f, -1f, 1f, 0f, -1f, 1f, 0f, -1f,
                    1f, 0f, -1f, 1f, 0f, -1f, 1f, 0f, -1f,
                    1f, 0f, -1f, 1f, 0f, -1f, 1f, 0f, -1f,
                    1f, 0f, -1f, 1f, 0f, -1f, 1f, 0f, -1f
                ),
                tangentsData = floatBufferDataOf(
                    Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN,
                    Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN,
                    Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN,
                    Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN
                ),
                bitangentsData = floatBufferDataOf(
                    Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN,
                    Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN,
                    Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN,
                    Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN, Float.NaN
                )
            ),
            result
        )
    }
}
