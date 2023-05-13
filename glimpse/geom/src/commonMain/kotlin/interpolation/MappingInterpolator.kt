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

/**
 * A mapping interpolator, returning values from given [interpolator],
 * transformed using given [transform] function.
 */
class MappingInterpolator<T, R1, R2>(
    private val interpolator: Interpolator<T, R1>,
    private val transform: (R1) -> R2
) : Interpolator<T, R2> where T : Number, T : Comparable<T> {

    /**
     * Key value of this mapping interpolator at parameter 0.
     */
    override val startValue: R2 get() = transform(interpolator.startValue)

    /**
     * Key value of this mapping interpolator at parameter 1.
     */
    override val endValue: R2 get() = transform(interpolator.endValue)

    /**
     * Returns mapped interpolated value for given [parameterValue].
     */
    override fun get(parameterValue: T): R2 = transform(interpolator[parameterValue])
}
