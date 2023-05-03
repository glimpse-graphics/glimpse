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
import graphics.glimpse.types.minus
import graphics.glimpse.types.one
import graphics.glimpse.types.plus
import graphics.glimpse.types.times

/**
 * Base implementation of curve in 3D space.
 *
 * @since v2.0.0
 */
abstract class BaseCurve3<T> : Curve3<T> where T : Number, T : Comparable<T> {

    /**
     * Returns polygonal chain approximating this curve.
     *
     * Vertices of the new polygonal chain will be calculated at given [parameterValues].
     */
    override fun toPolygonalChain(parameterValues: Sequence<T>): PolygonalChain3<T> =
        PolygonalChain3(
            vertices = parameterValues.map { this[it] }.toList(),
            type = this.type
        )

    /**
     * Returns linear interpolation of values [a] and [b] at given [parameterValue].
     */
    protected fun linearInterpolation(a: T, b: T, parameterValue: T): T =
        a * (one(this.type) - parameterValue) + b * parameterValue

    /**
     * Returns linear interpolation of points [p1] and [p2] at given [parameterValue].
     */
    protected fun linearInterpolation(p1: Vec3<T>, p2: Vec3<T>, parameterValue: T): Vec3<T> =
        p1 * (one(this.type) - parameterValue) + p2 * parameterValue
}
