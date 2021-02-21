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

package graphics.glimpse.meshes

import graphics.glimpse.buffers.FloatBufferData
import graphics.glimpse.buffers.IntBufferData

/**
 * A container for the array buffers data related to a single mesh, without indices.
 */
data class ArrayMeshData(

    /**
     * Number of vertices defined for the mesh.
     *
     * Must be divisible by 3.
     */
    override val vertexCount: Int,

    /**
     * Buffer data for vertices positions.
     */
    override val positionsData: FloatBufferData,

    /**
     * Buffer data for vertices texture coordinates.
     */
    override val texCoordsData: FloatBufferData,

    /**
     * Buffer data for vertices normal vectors.
     */
    override val normalsData: FloatBufferData,

    /**
     * Buffer data for vertices tangent vectors.
     */
    override val tangentsData: FloatBufferData
) : MeshData {

    /**
     * No indices data.
     */
    override val indicesBufferData: IntBufferData? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ArrayMeshData

        if (vertexCount != other.vertexCount) return false
        if (!positionsData.contentEquals(other.positionsData)) return false
        if (!texCoordsData.contentEquals(other.texCoordsData)) return false
        if (!normalsData.contentEquals(other.normalsData)) return false
        if (!tangentsData.contentEquals(other.tangentsData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = vertexCount
        result = 31 * result + positionsData.contentHashCode()
        result = 31 * result + texCoordsData.contentHashCode()
        result = 31 * result + normalsData.contentHashCode()
        result = 31 * result + tangentsData.contentHashCode()
        return result
    }
}
