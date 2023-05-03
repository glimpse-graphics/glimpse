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
 * A curve in 3D space.
 *
 * @since v2.0.0
 */
interface Curve3<T> where T : Number, T : Comparable<T> {

    /**
     * Degree of this curve.
     */
    val degree: Int

    /**
     * Type of coordinates in this curve.
     */
    val type: KClass<T>

    /**
     * Returns point on this curve at a given [parameterValue].
     */
    operator fun get(parameterValue: T): Vec3<T>

    /**
     * Returns polygonal chain approximating this curve.
     *
     * Vertices of the new polygonal chain will be calculated at given [parameterValues].
     */
    fun toPolygonalChain(parameterValues: Sequence<T>): PolygonalChain3<T>

    /**
     * Returns projection of this curve on XY plane.
     */
    fun toCurve2(): Curve2<T>
}
