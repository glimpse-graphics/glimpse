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

import graphics.glimpse.DrawingMode
import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.buffers.Buffer
import graphics.glimpse.logging.GlimpseLogger
import java.util.concurrent.atomic.AtomicBoolean

internal class MeshFactoryImpl(gl: GlimpseAdapter) : Mesh.Factory {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    private val bufferFactory = Buffer.Factory.newInstance(gl)

    override fun createMesh(arrayMeshData: ArrayMeshData): Mesh {
        logger.debug(message = "Creating array mesh with ${arrayMeshData.vertexCount} vertices")
        return ArrayMesh(
            vertexCount = arrayMeshData.vertexCount,
            buffers = with(arrayMeshData) {
                bufferFactory.createArrayBuffers(
                    positionsData,
                    texCoordsData,
                    normalsData,
                    tangentsData,
                    bitangentsData
                )
            }
        )
    }

    private data class ArrayMesh(
        override val vertexCount: Int,
        override val buffers: List<Buffer>
    ) : Mesh {

        private val disposed = AtomicBoolean(false)
        override val isDisposed: Boolean get() = disposed.get()

        override fun useBuffer(gl: GlimpseAdapter, bufferIndex: Int) {
            buffers[bufferIndex].use(gl)
        }

        override fun draw(gl: GlimpseAdapter) {
            gl.glDrawArrays(DrawingMode.TRIANGLES, vertexCount)
        }

        override fun dispose(gl: GlimpseAdapter) {
            check(disposed.compareAndSet(false, true)) { "Mesh is already disposed" }
            gl.glDeleteBuffers(buffers.map { it.handle }.toIntArray())
        }
    }
}
