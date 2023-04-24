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

package graphics.glimpse.lenses

import graphics.glimpse.types.Mat4
import graphics.glimpse.types.orthographic
import graphics.glimpse.types.unaryMinus
import kotlin.reflect.KClass

/**
 * A lens for an orthographic (parallel) projection defined by a given set
 * of clipping planes: [left], [right], [bottom], [top], [near] and [far].
 */
data class OrthographicLens<T>(
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
    override val projectionMatrix: Mat4<T> = orthographic(left, right, bottom, top, near, far, this.type)
}

/**
 * Returns a new lens for an orthographic (parallel) projection defined by a given set
 * of clipping planes: [left], [right], [bottom], [top], [near] and [far].
 */
@Suppress("FunctionNaming")
inline fun <reified T> OrthographicLens(
    left: T,
    right: T = -left,
    bottom: T,
    top: T = -bottom,
    near: T,
    far: T
) : OrthographicLens<T> where T : Number, T : Comparable<T> =
    OrthographicLens(left, right, bottom, top, near, far, T::class)
