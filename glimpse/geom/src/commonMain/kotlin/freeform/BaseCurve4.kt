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

import graphics.glimpse.geom.PolygonalChain4
import graphics.glimpse.geom.interpolation.Curve4Interpolator
import graphics.glimpse.geom.interpolation.Interpolator
import graphics.glimpse.types.Vec4

/**
 * Base implementation of curve in 3D space in rational form.
 *
 * @since v2.0.0
 */
abstract class BaseCurve4<T> : Curve4<T> where T : Number, T : Comparable<T> {

    /**
     * Returns polygonal chain approximating this curve.
     *
     * Vertices of the new polygonal chain will be calculated at given [parameterValues].
     */
    override fun toPolygonalChain(parameterValues: Sequence<T>): PolygonalChain4<T> =
        PolygonalChain4(
            vertices = parameterValues.map { this[it] }.toList(),
            type = this.type
        )

    /**
     * Returns an interpolator using points on this curve as values.
     */
    override fun toInterpolator(): Interpolator<T, Vec4<T>> =
        Curve4Interpolator(curve = this)
}
