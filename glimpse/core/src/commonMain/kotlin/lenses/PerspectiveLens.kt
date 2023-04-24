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

import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.perspective
import kotlin.reflect.KClass

/**
 * A lens for a perspective projection defined by a given frustum.
 *
 * The frustum is defined by its [near] and [far] depth clipping planes,
 * and its [field of view angle in the Y direction][fovY] and [aspect] ratio between X and Y
 * field of view.
 */
data class PerspectiveLens<T>(
    val fovY: Angle<T>,
    val aspect: T,
    val near: T,
    val far: T,
    val type: KClass<T>
) : Lens<T> where T : Number, T : Comparable<T> {

    /**
     * Projection matrix defined by the lens.
     */
    override val projectionMatrix: Mat4<T> = perspective(fovY, aspect, near, far, this.type)
}

/**
 * Returns a new lens for a perspective projection defined by a given frustum.
 *
 * The frustum is defined by its [near] and [far] depth clipping planes,
 * and its [field of view angle in the Y direction][fovY] and [aspect] ratio between X and Y
 * field of view.
 */
@Suppress("FunctionNaming")
inline fun <reified T> PerspectiveLens(
    fovY: Angle<T>,
    aspect: T,
    near: T,
    far: T
) : PerspectiveLens<T> where T : Number, T : Comparable<T> =
    PerspectiveLens(fovY, aspect, near, far, T::class)
