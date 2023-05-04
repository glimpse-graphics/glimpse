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

import graphics.glimpse.types.div
import graphics.glimpse.types.one
import graphics.glimpse.types.rem
import kotlin.reflect.KClass

/**
 * Base class for uniform multi-interpolator.
 *
 * Uniform multi-interpolator is one that has its key [values] distributed uniformly across
 * the range [0, 1].
 *
 * @since v2.0.0
 */
abstract class BaseUniformMultiInterpolator<T, R> :
    MultiInterpolator<T, R> where T : Number, T : Comparable<T> {

    /**
     * Implement this property to return type of the parameter.
     */
    abstract val type: KClass<T>

    private val rangeLength: T by lazy { one(this.type) / (values.size - 1) }

    /**
     * Returns interpolated value for given [parameterValue].
     */
    override operator fun get(parameterValue: T): R {
        val index = (parameterValue / rangeLength).toInt()
        val rangeParameter = (parameterValue % rangeLength) / rangeLength
        return when {
            index < 0 -> startValue
            index >= values.lastIndex -> endValue
            else -> interpolate(values[index], values[index + 1], rangeParameter)
        }
    }

    /**
     * Implement this function to return value interpolated between [a] and [b],
     * at given [parameterValue].
     */
    protected abstract fun interpolate(a: R, b: R, parameterValue: T): R
}
