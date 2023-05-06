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

import graphics.glimpse.geom.interpolation.UniformLinearVec2MultiInterpolator
import graphics.glimpse.meshes.ArrayMeshData
import graphics.glimpse.meshes.MeshDataBuilder
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import kotlin.reflect.KClass

internal data class Surface3Impl<T>(
    private val freeformType: FreeformType,
    override val degree: Vec2<Int>,
    override val controlVertices: List<ControlVertex3<T>>,
    private val knotsU: List<T>,
    private val knotsV: List<T>,
    override val type: KClass<T>
) : Surface3<T> where T : Number, T : Comparable<T> {

    private val chunkedControlVertices: List<List<ControlVertex3<T>>> =
        controlVertices.chunked(
            size = when (freeformType) {
                FreeformType.BEZIER -> degree.u + 1
                FreeformType.B_SPLINE -> knotsU.size - degree.u - 1
            }
        )

    private val scaffoldingCurves: List<Curve3<T>> by lazy {
        chunkedControlVertices
            .map { vertices ->
                Curve3.Builder.getInstance(this.type)
                    .ofType(freeformType)
                    .withControlPoints(vertices.map { it.controlPoint })
                    .withKnots(knotsU)
                    .build()
            }
    }

    private val scaffoldingTextureCoordinates: List<UniformLinearVec2MultiInterpolator<T>> by lazy {
        chunkedControlVertices
            .map { vertices ->
                UniformLinearVec2MultiInterpolator(
                    values = vertices.map { vertex -> vertex.textureCoordinates },
                    type = this.type
                )
            }
    }

    private val scaffoldingNormalCurves: List<Curve3<T>> by lazy {
        chunkedControlVertices
            .map { vertices ->
                Curve3.Builder.getInstance(this.type)
                    .ofType(freeformType)
                    .withControlPoints(vertices.map { it.normal })
                    .withKnots(knotsU)
                    .build()
            }
    }

    override fun get(parametersValues: Vec2<T>): Vec3<T> =
        getCurve(parametersValues.u)[parametersValues.v]

    private fun getCurve(parameterValueU: T) =
        Curve3.Builder.getInstance(this.type)
            .ofType(freeformType)
            .withControlPoints(scaffoldingCurves.map { it[parameterValueU] })
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
            val normalsU = Curve3.Builder.getInstance(this.type)
                .ofType(freeformType)
                .withControlPoints(scaffoldingNormalCurves.map { it[u] })
                .withKnots(knotsV)
                .build()

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
                    uSize * (v - 1) + u,
                    uSize * (v - 1) + u - 1,
                    uSize * v + u - 1,
                    uSize * v + u
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
