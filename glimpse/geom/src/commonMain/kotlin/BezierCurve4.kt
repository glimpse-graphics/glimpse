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

import graphics.glimpse.types.Vec4
import graphics.glimpse.types.div
import graphics.glimpse.types.one
import kotlin.reflect.KClass

/**
 * Bézier curve in 3D space in rational form, defined by given [controlPoints].
 *
 * @since v2.0.0
 */
data class BezierCurve4<T>(

    /**
     * Control points defining this curve.
     */
    val controlPoints: List<Vec4<T>>,

    /**
     * Type of coordinates in this curve.
     */
    override val type: KClass<T>

) : BaseCurve4<T>() where T : Number, T : Comparable<T> {

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
    override fun get(parameterValue: T): Vec4<T> =
        reduce(points = this.controlPoints, parameterValue = parameterValue)

    private tailrec fun reduce(points: List<Vec4<T>>, parameterValue: T): Vec4<T> =
        points.singleOrNull() ?: reduce(
            points = points.zipWithNext { a, b ->
                val weightDenominator = linearInterpolation(a.w, b.w, parameterValue)
                val w1 = a.w / weightDenominator
                val w2 = b.w / weightDenominator
                linearInterpolation(p1 = a.toVec3() * w1, p2 = b.toVec3() * w2, parameterValue)
                    .toVec4(w = one(this.type))
            },
            parameterValue = parameterValue
        )
}

/**
 * Returns a new [BezierCurve4] with given [controlPoints].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> BezierCurve4(
    vararg controlPoints: Vec4<T>,
): BezierCurve4<T> where T : Number, T : Comparable<T> =
    BezierCurve4(
        controlPoints = controlPoints.toList(),
        type = T::class
    )

/**
 * Returns a new [BezierCurve4] with given [controlPoints].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> BezierCurve4(
    controlPoints: List<Vec4<T>>,
): BezierCurve4<T> where T : Number, T : Comparable<T> =
    BezierCurve4(
        controlPoints = controlPoints,
        type = T::class
    )
