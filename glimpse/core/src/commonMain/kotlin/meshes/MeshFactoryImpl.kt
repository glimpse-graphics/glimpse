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

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.buffers.Buffer

internal class MeshFactoryImpl(gl: GlimpseAdapter) : Mesh.Factory {

    private val bufferFactory = Buffer.Factory.newInstance(gl)

    override fun createMesh(arrayMeshData: ArrayMeshData): Mesh = ArrayMesh(
        vertexCount = arrayMeshData.vertexCount,
        buffers = with(arrayMeshData) {
            bufferFactory.createArrayBuffers(
                positionsData,
                texCoordsData,
                normalsData,
                tangentsData
            )
        }
    )

    private data class ArrayMesh(
        override val vertexCount: Int,
        override val buffers: List<Buffer>
    ) : Mesh {

        override fun useBuffer(gl: GlimpseAdapter, bufferIndex: Int) {
            buffers[bufferIndex].use(gl)
        }

        override fun dispose(gl: GlimpseAdapter) {
            gl.glDeleteBuffers(buffers.map { it.handle }.toIntArray())
        }
    }
}
