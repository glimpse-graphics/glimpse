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

package graphics.glimpse.meshes

import graphics.glimpse.buffers.floatBufferDataOf
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class MeshDataBuilderTest {

    @Test
    @JsName(
        name = "GIVEN_MeshDataBuilder_WHEN_build_triangle_array_mesh_data_THEN_return_mesh_data_with_single_triangle"
    )
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
                tangentsData = floatBufferDataOf(1f, 1f, 0f, 1f, 1f, 0f, 1f, 1f, 0f)
            ),
            result
        )
    }
}
