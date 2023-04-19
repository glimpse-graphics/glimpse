/*
 * Copyright 2020-2022 Slawomir Czerwinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.lenses

import graphics.glimpse.types.Mat4
import graphics.glimpse.types.frustum
import graphics.glimpse.types.unaryMinus
import kotlin.reflect.KClass

/**
 * A lens for a perspective projection defined by a given frustum.
 *
 * The frustum is defined by its [near] and [far] depth clipping planes, and its [left], [right],
 * [bottom] and [top] clipping planes (specified at the near depth).
 */
data class FrustumLens<T>(
    val left: T,
    val right: T = -left,
    val bottom: T,
    val top: T = -bottom,
    val near: T,
    val far: T,
    val type: KClass<T>
) : Lens<T> where T : Number, T : Comparable<T> {

    /**
     * Projection matrix defined by the lens.
     */
    override val projectionMatrix: Mat4<T> = frustum(left, right, bottom, top, near, far, this.type)
}

/**
 * Returns a new lens for a perspective projection defined by a given frustum.
 *
 * The frustum is defined by its [near] and [far] depth clipping planes, and its [left], [right],
 * [bottom] and [top] clipping planes (specified at the near depth).
 */
@Suppress("FunctionNaming")
inline fun <reified T> FrustumLens(
    left: T,
    right: T = -left,
    bottom: T,
    top: T = -bottom,
    near: T,
    far: T
): FrustumLens<T> where T : Number, T : Comparable<T> =
    FrustumLens(left, right, bottom, top, near, far, T::class)
