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
import kotlin.reflect.KClass

/**
 * A curve in 3D space in rational form.
 *
 * @since v2.0.0
 */
interface Curve4<T> where T : Number, T : Comparable<T> {

    /**
     * Degree of this curve.
     */
    val degree: Int

    /**
     * Control points defining this curve.
     */
    val controlPoints: List<Vec4<T>>

    /**
     * Type of coordinates in this curve.
     */
    val type: KClass<T>

    /**
     * Returns point on this curve at a given [parameterValue].
     */
    operator fun get(parameterValue: T): Vec4<T>

    /**
     * Returns polygonal chain approximating this curve.
     *
     * Vertices of the new polygonal chain will be calculated at given [parameterValues].
     */
    fun toPolygonalChain(parameterValues: Sequence<T>): PolygonalChain4<T>

    /**
     * Builder of curves in 3D space in rational form.
     */
    interface Builder<T> where T : Number, T : Comparable<T> {

        /**
         * Sets type of the curve.
         */
        fun ofType(curveType: CurveType): Builder<T>

        /**
         * Sets control points defining the curve.
         */
        fun withControlPoints(controlPoints: List<Vec4<T>>): Builder<T>

        /**
         * Sets control points defining the curve.
         */
        fun withControlPoints(vararg controlPoints: Vec4<T>): Builder<T>

        /**
         * Sets knots of the curve.
         */
        fun withKnots(knots: List<T>): Builder<T>

        /**
         * Sets knots of the curve.
         */
        fun withKnots(vararg knots: T): Builder<T>

        /**
         * Returns a new curve with provided configuration.
         */
        fun build(): Curve4<T>

        companion object {

            /**
             * Gets a new instance of a builder of 3D curve in rational form.
             */
            inline fun <reified T> getInstance(): Builder<T> where T : Number, T : Comparable<T> =
                getInstance(T::class)

            /**
             * Gets a new instance of a builder of 3D curve in rational form.
             */
            fun <T> getInstance(type: KClass<T>): Builder<T> where T : Number, T : Comparable<T> =
                Curve4BuilderImpl(type)
        }
    }
}
