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

import graphics.glimpse.meshes.ArrayMeshData
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec4
import kotlin.reflect.KClass

/**
 * A surface in 3D space in rational form.
 *
 * @since v2.0.0
 */
interface Surface4<T> where T : Number, T : Comparable<T> {

    /**
     * Degree of this surface.
     */
    val degree: Vec2<Int>

    /**
     * Control vertices of this surface.
     */
    val controlVertices: List<ControlVertex4<T>>

    /**
     * Type of coordinates in this surface.
     */
    val type: KClass<T>

    /**
     * Returns point on this surface at given [parametersValues].
     */
    operator fun get(parametersValues: Vec2<T>): Vec4<T>

    /**
     * Returns array mesh data for this surface.
     *
     * Vertices in the mesh data will be calculated at given parameter values
     * in the directions [U][parameterValuesU] and [V][parameterValuesV].
     */
    fun toMeshData(parameterValuesU: List<T>, parameterValuesV: List<T>): ArrayMeshData

    /**
     * Builder of surfaces in 3D space in rational form.
     */
    interface Builder<T> where T : Number, T : Comparable<T> {

        /**
         * Sets type of the surface.
         */
        fun ofType(freeformType: FreeformType): Builder<T>

        /**
         * Sets degree of the surface in both directions.
         */
        fun withDegree(degree: Vec2<Int>): Builder<T>

        /**
         * Sets control vertices defining the surface.
         */
        fun withControlVertices(controlVertices: List<ControlVertex4<T>>): Builder<T>

        /**
         * Sets control vertices defining the surface.
         */
        fun withControlVertices(vararg controlVertices: ControlVertex4<T>): Builder<T>

        /**
         * Sets knots of the surface in the direction U.
         */
        fun withKnotsU(knotsU: List<T>): Builder<T>

        /**
         * Sets knots of the surface in the direction U.
         */
        fun withKnotsU(vararg knotsU: T): Builder<T>

        /**
         * Sets knots of the surface in the direction U.
         */
        fun withKnotsV(knotsV: List<T>): Builder<T>

        /**
         * Sets knots of the surface in the direction U.
         */
        fun withKnotsV(vararg knotsV: T): Builder<T>

        /**
         * Returns a new surface with provided configuration.
         */
        fun build(): Surface4<T>

        companion object {

            /**
             * Gets a new instance of a 3D surface in rational form builder.
             */
            inline fun <reified T> getInstance(): Builder<T> where T : Number, T : Comparable<T> =
                getInstance(T::class)

            /**
             * Gets a new instance of a 3D surface in rational form builder.
             */
            fun <T> getInstance(type: KClass<T>): Builder<T> where T : Number, T : Comparable<T> =
                Surface4BuilderImpl(type)
        }
    }
}
