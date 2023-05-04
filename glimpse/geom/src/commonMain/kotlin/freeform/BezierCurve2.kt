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
import kotlin.reflect.KClass

/**
 * Bézier curve in 2D space, defined by given [controlPoints].
 *
 * @since v2.0.0
 */
data class BezierCurve2<T>(

    /**
     * Control points defining this curve.
     */
    override val controlPoints: List<Vec2<T>>,

    /**
     * Type of coordinates in this curve.
     */
    override val type: KClass<T>

) : BaseCurve2<T>() where T : Number, T : Comparable<T> {

    /**
     * Degree of this curve.
     */
    override val degree: Int
        get() = controlPoints.size - 1

    init {
        require(controlPoints.isNotEmpty()) { "Control points must not be empty" }
    }

    /**
     * Returns point on this curve at a given [parameterValue].
     *
     * The point is calculated using de Casteljau's algorithm.
     */
    override fun get(parameterValue: T): Vec2<T> =
        reduce(points = this.controlPoints, parameterValue = parameterValue)

    private tailrec fun reduce(points: List<Vec2<T>>, parameterValue: T): Vec2<T> =
        points.singleOrNull() ?: reduce(
            points = points.zipWithNext { a, b -> linearInterpolation(a, b, parameterValue, this.type) },
            parameterValue = parameterValue
        )

    /**
     * Returns this curve, but defined in 3D space.
     */
    override fun toCurve3(): Curve3<T> =
        BezierCurve3(controlPoints = this.controlPoints.map { it.toVec3() }, type = this.type)
}

/**
 * Returns a new [BezierCurve2] with given [controlPoints].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> BezierCurve2(
    vararg controlPoints: Vec2<T>,
): BezierCurve2<T> where T : Number, T : Comparable<T> =
    BezierCurve2(
        controlPoints = controlPoints.toList(),
        type = T::class
    )

/**
 * Returns a new [BezierCurve2] with given [controlPoints].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> BezierCurve2(
    controlPoints: List<Vec2<T>>,
): BezierCurve2<T> where T : Number, T : Comparable<T> =
    BezierCurve2(
        controlPoints = controlPoints,
        type = T::class
    )
