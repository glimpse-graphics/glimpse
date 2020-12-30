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

/**
 * A mesh.
 */
interface Mesh {

    /**
     * Number of vertices drawn for the mesh.
     */
    val vertexCount: Int

    /**
     * Buffers of vertex data.
     */
    val buffers: List<Buffer>

    /**
     * Tells the given [OpenGL adapter][gl] to use a buffer at a given [bufferIndex].
     */
    fun useBuffer(gl: GlimpseAdapter, bufferIndex: Int)

    /**
     * Tells the given [OpenGL adapter][gl] to dispose all buffers defined for this mesh.
     */
    fun dispose(gl: GlimpseAdapter)

    /**
     * A factory for meshes.
     */
    interface Factory {

        /**
         * Creates a new mesh from given [arrayMeshData].
         */
        fun createMesh(arrayMeshData: ArrayMeshData): Mesh

        companion object {

            /**
             * Returns a new instance of mesh factory.
             */
            fun newInstance(gl: GlimpseAdapter): Factory = MeshFactoryImpl(gl)
        }
    }
}
