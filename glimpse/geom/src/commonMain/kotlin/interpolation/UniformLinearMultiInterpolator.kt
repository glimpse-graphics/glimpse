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

package graphics.glimpse.geom.interpolation

import kotlin.reflect.KClass

/**
 * Uniform linear multi-interpolator for floating-point number values.
 *
 * @since v2.0.0
 */
data class UniformLinearMultiInterpolator<T> (

    /**
     * Key values to interpolate between.
     */
    override val values: List<T>,

    /**
     * Type of the parameter and the values.
     */
    override val type: KClass<T>

) : BaseUniformMultiInterpolator<T, T>() where T : Number, T : Comparable<T> {

    /**
     * Returns value interpolated between [a] and [b], at given [parameterValue].
     */
    override fun interpolate(a: T, b: T, parameterValue: T): T =
        linearInterpolation(a, b, parameterValue, type)
}

/**
 * Returns a new [UniformLinearMultiInterpolator] with given key [values].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> UniformLinearMultiInterpolator(
    vararg values: T
): UniformLinearMultiInterpolator<T> where T : Number, T : Comparable<T> =
    UniformLinearMultiInterpolator(
        values = values.toList(),
        type = T::class
    )

/**
 * Returns a new [UniformLinearMultiInterpolator] with given key [values].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> UniformLinearMultiInterpolator(
    values: List<T>
): UniformLinearMultiInterpolator<T> where T : Number, T : Comparable<T> =
    UniformLinearMultiInterpolator(
        values = values.toList(),
        type = T::class
    )
