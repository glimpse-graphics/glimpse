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
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.div
import graphics.glimpse.types.minus
import kotlin.reflect.KClass

/**
 * B-spline curve in 2D space, defined by given [controlPoints] and [knots].
 *
 * @since v2.0.0
 */
data class BSplineCurve2<T>(

    /**
     * Control points of this curve.
     */
    override val controlPoints: List<Vec2<T>>,

    /**
     * Knots of this curve.
     */
    val knots: List<T>,

    /**
     * Type of coordinates in this curve.
     */
    override val type: KClass<T>

) : BaseCurve2<T>() where T : Number, T : Comparable<T> {

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
    override fun get(parameterValue: T): Vec2<T> {
        val minKnotIndex = degree
        val maxKnotIndex = knots.size - (degree + 1)

        val knotIndex = knots.zipWithNext()
            .indexOfFirst { (a, b) -> a <= parameterValue && parameterValue < b }
            .coerceIn(minKnotIndex, maxKnotIndex)

        val points = this.controlPoints
            .drop(n = knotIndex - degree)
            .take(n = degree + 1)

        return reduce(points = points, parameterValue = parameterValue, knotIndex = knotIndex)
    }

    private tailrec fun reduce(points: List<Vec2<T>>, parameterValue: T, knotIndex: Int, iteration: Int = 1): Vec2<T> =
        points.singleOrNull() ?: reduce(
            points = points.zipWithNext().mapIndexed { index, (a, b) ->
                val i = index + iteration
                val numerator = parameterValue - knots[i + knotIndex - degree]
                val denominator = knots[i + knotIndex - iteration + 1] - knots[i + knotIndex - degree]
                linearInterpolation(a, b, parameterValue = numerator / denominator, type = this.type)
            },
            parameterValue = parameterValue,
            knotIndex = knotIndex,
            iteration = iteration + 1
        )

    /**
     * Returns this curve, but defined in 3D space.
     */
    override fun toCurve3(): Curve3<T> =
        BSplineCurve3(
            controlPoints = this.controlPoints.map { it.toVec3() },
            knots = this.knots,
            type = this.type
        )
}

/**
 * Returns a new [BSplineCurve2] with given [controlPoints] and [knots].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> BSplineCurve2(
    controlPoints: List<Vec2<T>>,
    knots: List<T>
): BSplineCurve2<T> where T : Number, T : Comparable<T> =
    BSplineCurve2(
        controlPoints = controlPoints,
        knots = knots,
        type = T::class
    )
