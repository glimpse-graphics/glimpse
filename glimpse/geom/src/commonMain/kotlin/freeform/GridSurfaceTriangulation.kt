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

package graphics.glimpse.geom.freeform

import graphics.glimpse.meshes.ArrayMeshData
import graphics.glimpse.meshes.MeshDataBuilder

internal class GridSurfaceTriangulation<T>(
    private val surface: BaseSurface<T>,
    private val parameterValuesU: List<T>,
    private val parameterValuesV: List<T>
) : SurfaceTriangulation<T> where T : Number, T : Comparable<T> {

    override fun generateMeshData(): ArrayMeshData {
        val builder = MeshDataBuilder()

        for (u in parameterValuesU) {
            val curveU = surface.helper.getPositions(u)
            val textureCoordinatesU = surface.helper.getTextureCoordinates(u)
            val normalsU = surface.helper.getNormals(u)

            for (v in parameterValuesV) {
                builder.addVertex(curveU[v].toFloatVector())
                builder.addTextureCoordinates(textureCoordinatesU[v].toFloatVector())
                builder.addNormal(normalsU[v].toFloatVector())
            }
        }

        val uSize = parameterValuesU.size
        val vSize = parameterValuesV.size

        for (u in 1 until uSize) {
            for (v in 1 until vSize) {
                val indices = listOf(
                    vSize * (u - 1) + v,
                    vSize * (u - 1) + v - 1,
                    vSize * u + v - 1,
                    vSize * u + v
                )
                builder.addFace(
                    indices = indices.map { index ->
                        MeshDataBuilder.FaceVertex(index, index, index)
                    }
                )
            }
        }

        return builder.buildArrayMeshData()
    }
}
