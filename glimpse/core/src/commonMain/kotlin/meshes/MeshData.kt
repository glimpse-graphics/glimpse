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

import graphics.glimpse.buffers.FloatBufferData
import graphics.glimpse.buffers.IntBufferData

/**
 * A container for the buffers data related to a single mesh.
 */
interface MeshData {

    /**
     * Number of vertices defined for the mesh.
     */
    val vertexCount: Int

    /**
     * Buffer data for vertices positions.
     */
    val positionsData: FloatBufferData

    /**
     * Buffer data for vertices texture coordinates.
     */
    val texCoordsData: FloatBufferData

    /**
     * Buffer data for vertices normal vectors.
     */
    val normalsData: FloatBufferData

    /**
     * Buffer data for vertices tangent vectors.
     */
    val tangentsData: FloatBufferData

    /**
     * Buffer data for vertices bitangent vectors.
     *
     * @since v1.1.0
     */
    val bitangentsData: FloatBufferData

    /**
     * Indices of vertices for subsequent mesh triangles.
     *
     * If not provided, the mesh will be drawn sequentially for all defined vertices.
     */
    val indicesBufferData: IntBufferData?
}
