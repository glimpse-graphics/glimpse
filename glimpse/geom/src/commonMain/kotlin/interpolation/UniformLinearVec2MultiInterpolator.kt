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

import graphics.glimpse.types.Vec2
import graphics.glimpse.types.minus
import graphics.glimpse.types.one
import kotlin.reflect.KClass

/**
 * Uniform linear multi-interpolator for 2D vectors.
 *
 * @since v2.0.0
 */
data class UniformLinearVec2MultiInterpolator<T> (

    /**
     * Key vectors to interpolate between.
     */
    override val values: List<Vec2<T>>,

    /**
     * Type of the parameter and vector coordinates.
     */
    override val type: KClass<T>

) : BaseUniformMultiInterpolator<T, Vec2<T>>() where T : Number, T : Comparable<T> {

    /**
     * Returns vector interpolated between [a] and [b], at given [parameterValue].
     */
    override fun interpolate(a: Vec2<T>, b: Vec2<T>, parameterValue: T): Vec2<T> =
        a * (one(this.type) - parameterValue) + b * parameterValue
}

/**
 * Returns a new [UniformLinearVec2MultiInterpolator] with given key [values].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> UniformLinearVec2MultiInterpolator(
    vararg values: Vec2<T>
): UniformLinearVec2MultiInterpolator<T> where T : Number, T : Comparable<T> =
    UniformLinearVec2MultiInterpolator(
        values = values.toList(),
        type = T::class
    )

/**
 * Returns a new [UniformLinearVec2MultiInterpolator] with given key [values].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> UniformLinearVec2MultiInterpolator(
    values: List<Vec2<T>>
): UniformLinearVec2MultiInterpolator<T> where T : Number, T : Comparable<T> =
    UniformLinearVec2MultiInterpolator(
        values = values.toList(),
        type = T::class
    )
