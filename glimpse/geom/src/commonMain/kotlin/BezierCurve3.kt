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

package graphics.glimpse.geom

import graphics.glimpse.types.Vec3
import kotlin.reflect.KClass

/**
 * Bézier curve in 3D space, defined by given [controlPoints].
 *
 * @since v2.0.0
 */
data class BezierCurve3<T>(

    /**
     * Control points defining this curve.
     */
    override val controlPoints: List<Vec3<T>>,

    /**
     * Type of coordinates in this curve.
     */
    override val type: KClass<T>

) : BaseCurve3<T>() where T : Number, T : Comparable<T> {

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
    override fun get(parameterValue: T): Vec3<T> =
        reduce(points = this.controlPoints, parameterValue = parameterValue)

    private tailrec fun reduce(points: List<Vec3<T>>, parameterValue: T): Vec3<T> =
        points.singleOrNull() ?: reduce(
            points = points.zipWithNext { a, b -> linearInterpolation(a, b, parameterValue) },
            parameterValue = parameterValue
        )

    /**
     * Returns projection of this curve on XY plane.
     */
    override fun toCurve2(): Curve2<T> =
        BezierCurve2(controlPoints = this.controlPoints.map { it.toVec2() }, type = this.type)
}

/**
 * Returns a new [BezierCurve3] with given [controlPoints].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> BezierCurve3(
    vararg controlPoints: Vec3<T>,
): BezierCurve3<T> where T : Number, T : Comparable<T> =
    BezierCurve3(
        controlPoints = controlPoints.toList(),
        type = T::class
    )

/**
 * Returns a new [BezierCurve3] with given [controlPoints].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> BezierCurve3(
    controlPoints: List<Vec3<T>>,
): BezierCurve3<T> where T : Number, T : Comparable<T> =
    BezierCurve3(
        controlPoints = controlPoints,
        type = T::class
    )
