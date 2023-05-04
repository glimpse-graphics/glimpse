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

import graphics.glimpse.geom.interpolation.UniformLinearMultiInterpolator
import graphics.glimpse.geom.interpolation.UniformLinearVec2MultiInterpolator
import graphics.glimpse.geom.interpolation.UniformLinearVec3MultiInterpolator
import graphics.glimpse.meshes.ArrayMeshData
import graphics.glimpse.meshes.MeshDataBuilder
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec4
import kotlin.reflect.KClass

internal data class Surface4Impl<T>(
    private val freeformType: FreeformType,
    override val degree: Vec2<Int>,
    override val controlVertices: List<ControlVertex4<T>>,
    private val knotsU: List<T>,
    private val knotsV: List<T>,
    override val type: KClass<T>
) : Surface4<T> where T : Number, T : Comparable<T> {

    private val scaffoldingCurves: List<Curve4<T>> by lazy {
        controlVertices
            .chunked(size = degree.u + 1)
            .map { vertices ->
                Curve4.Builder.getInstance(this.type)
                    .ofType(freeformType)
                    .withControlPoints(vertices.map { it.controlPoint })
                    .withKnots(knotsU)
                    .build()
            }
    }

    private val scaffoldingWeights: List<UniformLinearMultiInterpolator<T>> by lazy {
        controlVertices
            .chunked(size = degree.u + 1)
            .map { vertices ->
                UniformLinearMultiInterpolator(
                    values = vertices.map { vertex ->
                        vertex.controlPoint.w
                    },
                    type = this.type
                )
            }
    }

    private val scaffoldingTextureCoordinates: List<UniformLinearVec2MultiInterpolator<T>> by lazy {
        controlVertices
            .chunked(size = degree.u + 1)
            .map { vertices ->
                UniformLinearVec2MultiInterpolator(
                    values = vertices.map { vertex -> vertex.textureCoordinates },
                    type = this.type
                )
            }
    }

    private val scaffoldingNormals: List<UniformLinearVec3MultiInterpolator<T>> by lazy {
        controlVertices
            .chunked(size = degree.u + 1)
            .map { vertices ->
                UniformLinearVec3MultiInterpolator(
                    values = vertices.map { vertex -> vertex.normal },
                    type = this.type
                )
            }
    }

    override fun get(parametersValues: Vec2<T>): Vec4<T> =
        getCurve(parametersValues.u)[parametersValues.v]

    private fun getCurve(parameterValueU: T) =
        Curve4.Builder.getInstance(this.type)
            .ofType(freeformType)
            .withControlPoints(
                (scaffoldingCurves zip scaffoldingWeights).map { (curve, weights) ->
                    val weight = weights[parameterValueU]
                    curve[parameterValueU].toNonRationalForm().toVec4(w = weight)
                }
            )
            .withKnots(knotsV)
            .build()

    override fun toMeshData(parameterValuesU: List<T>, parameterValuesV: List<T>): ArrayMeshData {
        val builder = MeshDataBuilder()

        for (u in parameterValuesU) {
            val curveU = getCurve(u)
            val textureCoordinatesU = UniformLinearVec2MultiInterpolator(
                values = scaffoldingTextureCoordinates.map { it[u] },
                type = this.type
            )
            val normalsU = UniformLinearVec3MultiInterpolator(
                values = scaffoldingNormals.map { it[u] },
                type = this.type
            )

            for (v in parameterValuesV) {
                builder.addVertex(curveU[v].toFloatVector().toNonRationalForm())
                builder.addTextureCoordinates(textureCoordinatesU[v].toFloatVector())
                builder.addNormal(normalsU[v].toFloatVector())
            }
        }

        val uSize = parameterValuesU.size
        val vSize = parameterValuesV.size

        for (u in 1 until uSize) {
            for (v in 1 until vSize) {
                val indices = listOf(
                    uSize * (v - 1) + u - 1,
                    uSize * (v - 1) + u,
                    uSize * v + u,
                    uSize * v + u - 1,
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