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

import graphics.glimpse.geom.interpolation.Interpolator
import graphics.glimpse.geom.interpolation.UniformLinearMultiInterpolator
import graphics.glimpse.geom.interpolation.UniformLinearVec2MultiInterpolator
import graphics.glimpse.geom.interpolation.map
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4
import kotlin.reflect.KClass

internal class Surface4Impl<T>(
    freeformType: FreeformType,
    degree: Vec2<Int>,
    override val controlVertices: List<ControlVertex4<T>>,
    knotsU: List<T>,
    knotsV: List<T>,
    type: KClass<T>
) : BaseSurface<T>(freeformType, degree, knotsU, knotsV, type), Surface4<T> where T : Number, T : Comparable<T> {

    private val chunkedControlVertices: List<List<ControlVertex4<T>>> =
        controlVertices.chunked(size = gridSize.u)

    private val scaffoldingCurves: List<Curve4<T>> by lazy {
        chunkedControlVertices
            .map { vertices ->
                Curve4.Builder.getInstance(this.type)
                    .ofType(freeformType)
                    .withControlPoints(vertices.map { it.controlPoint })
                    .withKnots(knotsU)
                    .build()
            }
    }

    private val scaffoldingWeights: List<UniformLinearMultiInterpolator<T>> by lazy {
        chunkedControlVertices
            .map { vertices ->
                UniformLinearMultiInterpolator(
                    values = vertices.map { vertex ->
                        vertex.controlPoint.w
                    },
                    type = this.type
                )
            }
    }

    override val helper: SurfaceHelper<T> by lazy { Surface4Helper() }

    init {
        val expectedControlVerticesSize = gridSize.u * gridSize.v
        val actualControlVerticesSize = controlVertices.size
        require(expectedControlVerticesSize == actualControlVerticesSize) {
            "Invalid number of control points. Expected $expectedControlVerticesSize, got $actualControlVerticesSize"
        }
    }

    override fun get(parametersValues: Vec2<T>): Vec4<T> =
        getCurve(parametersValues.u)[parametersValues.v]

    internal fun getCurve(parameterValueU: T): Curve4<T> =
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

    override fun createTriangulation(parameterValuesU: List<T>, parameterValuesV: List<T>): SurfaceTriangulation<T> =
        GridSurfaceTriangulation(surface = this, parameterValuesU, parameterValuesV)

    private inner class Surface4Helper : SurfaceHelper<T> {

        private val scaffoldingTextureCoordinates: List<Interpolator<T, Vec2<T>>> by lazy {
            chunkedControlVertices
                .map { vertices ->
                    UniformLinearVec2MultiInterpolator(
                        values = vertices.map { vertex -> vertex.textureCoordinates },
                        type = type
                    )
                }
        }

        private val scaffoldingNormalCurves: List<Curve3<T>> by lazy {
            chunkedControlVertices
                .map { vertices ->
                    Curve3.Builder.getInstance(type)
                        .ofType(freeformType)
                        .withControlPoints(vertices.map { it.normal })
                        .withKnots(knotsU)
                        .build()
                }
        }

        override fun getPositions(parameterValueU: T): Interpolator<T, Vec3<T>> =
            getCurve(parameterValueU).toInterpolator().map { it.toNonRationalForm() }

        override fun getTextureCoordinates(parameterValueU: T): Interpolator<T, Vec2<T>> =
            UniformLinearVec2MultiInterpolator(
                values = scaffoldingTextureCoordinates.map { it[parameterValueU] },
                type = type
            )

        override fun getNormals(parameterValueU: T): Interpolator<T, Vec3<T>> =
            Curve3.Builder.getInstance(type)
                .ofType(freeformType)
                .withControlPoints(scaffoldingNormalCurves.map { it[parameterValueU] })
                .withKnots(knotsV)
                .build()
                .toInterpolator()
    }
}
