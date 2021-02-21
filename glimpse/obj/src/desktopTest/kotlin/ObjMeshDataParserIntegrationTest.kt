/*
 * Copyright 2020-2021 Slawomir Czerwinski
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

package graphics.glimpse.meshes.obj

import graphics.glimpse.buffers.floatBufferDataOf
import graphics.glimpse.meshes.ArrayMeshData
import graphics.glimpse.meshes.MeshDataBuilder
import org.junit.Assert.assertEquals
import org.junit.Test

class ObjMeshDataParserIntegrationTest {

    @Test
    fun `GIVEN triangle OBJ file, WHEN parseMesh, THEN return mesh data with single triangle`() {
        val parser = ObjMeshDataParser(MeshDataBuilder())

        val result = parser.parseArrayMeshData(triangleObj.lines())

        assertEquals(
            @Suppress("MagicNumber")
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
