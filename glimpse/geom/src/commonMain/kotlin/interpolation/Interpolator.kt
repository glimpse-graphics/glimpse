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
 * Interpolator for values of type [R], from parameter value of type [T].
 *
 * @since v2.0.0
 */
interface Interpolator<T, R> where T : Number, T : Comparable<T> {

    /**
     * Key value of this interpolator at parameter 0.
     */
    val startValue: R

    /**
     * Key value of this interpolator at parameter 1.
     */
    val endValue: R

    /**
     * Returns interpolated value for given [parameterValue].
     */
    operator fun get(parameterValue: T): R
}
