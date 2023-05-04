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

import graphics.glimpse.geom.interpolation.linearInterpolation
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4
import graphics.glimpse.types.div
import graphics.glimpse.types.minus
import kotlin.reflect.KClass

/**
 * B-spline curve in 3D space in rational form, defined by given [controlPoints] and [knots].
 *
 * @since v2.0.0
 */
data class BSplineCurve4<T>(

    /**
     * Control points of this curve.
     */
    override val controlPoints: List<Vec4<T>>,

    /**
     * Knots of this curve.
     */
    val knots: List<T>,

    /**
     * Type of coordinates in this curve.
     */
    override val type: KClass<T>

) : BaseCurve4<T>() where T : Number, T : Comparable<T> {

    /**
     * Degree of this curve.
     */
    override val degree: Int
        get() = knots.size - controlPoints.size - 1

    init {
        require(controlPoints.isNotEmpty()) { "Control points must not be empty" }
        require(knots.size > controlPoints.size) {
            "The number of knots must be greater than the number of control points"
        }
        require(controlPoints.size > degree) {
            "The number of control points must be greater than degree of the curve"
        }
        knots.zipWithNext()
            .forEach { (a, b) ->
                require (a <= b) { "Knot vector must be non-decreasing" }
            }
    }

    /**
     * Returns point on this curve at a given [parameterValue].
     *
     * The point is calculated using de Boor's algorithm.
     */
    override fun get(parameterValue: T): Vec4<T> {
        val minKnotIndex = degree
        val maxKnotIndex = knots.size - (degree + 1)

        val knotIndex = if (parameterValue == knots[maxKnotIndex + 1]) {
            maxKnotIndex
        } else {
            knots.zipWithNext().indexOfFirst { (a, b) -> a <= parameterValue && parameterValue < b }
        }

        if (knotIndex < minKnotIndex || knotIndex > maxKnotIndex) {
            return Vec3.nullVector(this.type).toRationalForm()
        }

        val points = this.controlPoints
            .drop(n = knotIndex - degree)
            .take(n = degree + 1)

        return reduce(points = points, parameterValue = parameterValue, knotIndex = knotIndex)
    }

    private tailrec fun reduce(points: List<Vec4<T>>, parameterValue: T, knotIndex: Int, iteration: Int = 1): Vec4<T> =
        points.singleOrNull() ?: reduce(
            points = points.zipWithNext().mapIndexed { index, (a, b) ->
                val i = index + iteration
                val numerator = parameterValue - knots[i + knotIndex - degree]
                val denominator = knots[i + knotIndex - iteration + 1] - knots[i + knotIndex - degree]
                val weightDenominator = linearInterpolation(
                    a = a.w,
                    b = b.w,
                    parameterValue = numerator / denominator,
                    type = this.type
                )
                val w1 = a.w / weightDenominator
                val w2 = b.w / weightDenominator
                linearInterpolation(
                    v1 = a.toVec3() * w1,
                    v2 = b.toVec3() * w2,
                    parameterValue = numerator / denominator,
                    type = this.type
                ).toRationalForm()
            },
            parameterValue = parameterValue,
            knotIndex = knotIndex,
            iteration = iteration + 1
        )
}

/**
 * Returns a new [BSplineCurve2] with given [controlPoints] and [knots].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> BSplineCurve4(
    controlPoints: List<Vec4<T>>,
    knots: List<T>
): BSplineCurve4<T> where T : Number, T : Comparable<T> =
    BSplineCurve4(
        controlPoints = controlPoints,
        knots = knots,
        type = T::class
    )
