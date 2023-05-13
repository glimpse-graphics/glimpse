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

import graphics.glimpse.geom.freeform.Curve4
import graphics.glimpse.types.Vec4
import graphics.glimpse.types.one
import graphics.glimpse.types.zero

/**
 * Curve interpolator for 3D vectors in rational form.
 *
 * @since v2.0.0
 */
class Curve4Interpolator<T>(
    private val curve: Curve4<T>
) : Interpolator<T, Vec4<T>> where T : Number, T : Comparable<T> {

    /**
     * Key value of this interpolator at parameter 0.
     */
    override val startValue: Vec4<T> get() = curve[zero(curve.type)]

    /**
     * Key value of this interpolator at parameter 1.
     */
    override val endValue: Vec4<T> get() = curve[one(curve.type)]

    /**
     * Value of this interpolator at given [parameterValue].
     */
    override fun get(parameterValue: T): Vec4<T> = curve[parameterValue]
}
