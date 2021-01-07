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

package graphics.glimpse.meshes.obj

import graphics.glimpse.meshes.ArrayMeshData
import graphics.glimpse.meshes.MeshDataBuilder
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

class ObjMeshDataParserTest {

    @MockK
    lateinit var builder: MeshDataBuilder

    @MockK
    lateinit var meshData: ArrayMeshData

    @Before
    fun setUpMockK() {
        MockKAnnotations.init(this)
        every { builder.addVertex(any<List<Float>>()) } returns builder
        every { builder.addTextureCoordinates(any<List<Float>>()) } returns builder
        every { builder.addNormal(any<List<Float>>()) } returns builder
        every { builder.addFace(any()) } returns builder
        every { builder.buildArrayMeshData() } returns meshData
    }

    @Test
    fun `GIVEN triangle OBJ file, WHEN parseMesh, THEN build mesh data with single triangle`() {
        val parser = ObjMeshDataParser(builder)

        val result = parser.parseArrayMeshData(triangleObj.lines())

        assertSame(meshData, result)

        verifySequence {
            builder.addVertex(listOf(-1f, -1f, 0f))
            builder.addVertex(listOf(1f, 1f, 0f))
            builder.addVertex(listOf(0f, 0f, 1f))
            builder.addTextureCoordinates(listOf(0f, 0f))
            builder.addTextureCoordinates(listOf(2f, 0f))
            builder.addTextureCoordinates(listOf(1f, 1f))
            builder.addNormal(listOf(0f, -1f, 0f))
            builder.addNormal(listOf(1f, 0f, 0f))
            builder.addNormal(listOf(0.7f, -0.7f, 0f))
            builder.addFace(
                listOf(
                    MeshDataBuilder.FaceVertex(positionIndex = 0, texCoordIndex = 0, normalIndex = 0),
                    MeshDataBuilder.FaceVertex(positionIndex = 1, texCoordIndex = 1, normalIndex = 1),
                    MeshDataBuilder.FaceVertex(positionIndex = 2, texCoordIndex = 2, normalIndex = 2),
                )
            )
            builder.buildArrayMeshData()
        }
    }
}
