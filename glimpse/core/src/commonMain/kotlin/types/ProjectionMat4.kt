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

package graphics.glimpse.types

import kotlin.reflect.KClass

/**
 * Creates a projection matrix for a perspective projection defined by a given frustum.
 *
 * The frustum is defined by its [near] and [far] depth clipping planes, and its [left], [right],
 * [bottom] and [top] clipping planes (specified at the near depth).
 */
inline fun <reified T> frustum(
    left: T,
    right: T,
    bottom: T,
    top: T,
    near: T,
    far: T
): Mat4<T> where T : Number, T : Comparable<T> =
    frustum(left, right, bottom, top, near, far, T::class)

/**
 * Creates a projection matrix for a perspective projection defined by a given frustum.
 *
 * The frustum is defined by its [near] and [far] depth clipping planes, and its [left], [right],
 * [bottom] and [top] clipping planes (specified at the near depth).
 */
fun <T> frustum(
    left: T,
    right: T,
    bottom: T,
    top: T,
    near: T,
    far: T,
    type: KClass<T>
): Mat4<T> where T : Number, T : Comparable<T> {
    val zero = zero(type)
    val one = one(type)
    val two = one + one

    require(value = near > zero) { "Near depth clipping plane must be at a positive distance" }
    require(value = far > zero) { "Far depth clipping plane must be at a positive distance" }

    val width = right - left
    val height = top - bottom
    val depth = near - far

    return Mat4(
        elements = listOf(
            two * near / width, zero, zero, zero,
            zero, two * near / height, zero, zero,
            (right + left) / width, (top + bottom) / height, (far + near) / depth, -one,
            zero, zero, two * far * near / depth, zero
        ),
        type = type
    )
}

/**
 * Creates a projection matrix for a perspective projection defined by a given frustum.
 *
 * The frustum is defined by its [near] and [far] depth clipping planes,
 * and its [field of view angle in the Y direction][fovY] and [aspect] ratio between X and Y
 * field of view.
 */
inline fun <reified T> perspective(
    fovY: Angle<T>,
    aspect: T,
    near: T,
    far: T
): Mat4<T> where T : Number, T : Comparable<T> =
    perspective(fovY, aspect, near, far, T::class)

/**
 * Creates a projection matrix for a perspective projection defined by a given frustum.
 *
 * The frustum is defined by its [near] and [far] depth clipping planes,
 * and its [field of view angle in the Y direction][fovY] and [aspect] ratio between X and Y
 * field of view.
 */
fun <T> perspective(
    fovY: Angle<T>,
    aspect: T,
    near: T,
    far: T,
    type: KClass<T>
): Mat4<T> where T : Number, T : Comparable<T> {
    val zero = zero(type)
    val one = one(type)
    val two = one + one

    require(value = fovY.toFloatAngle() > Angle.nullAngle()) { "Field of view must be at a positive angle" }
    require(value = fovY.toFloatAngle() < Angle.straightAngle()) { "Field of view must be less than 180 degrees" }
    require(value = aspect > zero) { "Aspect ratio must be a positive number" }
    require(value = near > zero) { "Near depth clipping plane must be at a positive distance" }
    require(value = far > zero) { "Far depth clipping plane must be at a positive distance" }

    val top = tan(angle = fovY / two)
    val right = aspect * top
    val depth = near - far

    return Mat4(
        elements = listOf(
            one / right, zero, zero, zero,
            zero, one / top, zero, zero,
            zero, zero, (near + far) / depth, -one,
            zero, zero, two * near * far / depth, zero
        ),
        type = type
    )
}

/**
 * Creates a projection matrix for an orthographic (parallel) projection defined by a given set
 * of clipping planes: [left], [right], [bottom], [top], [near] and [far].
 */
inline fun <reified T> orthographic(
    left: T,
    right: T,
    bottom: T,
    top: T,
    near: T,
    far: T
): Mat4<T> where T : Number, T : Comparable<T> =
    orthographic(left, right, bottom, top, near, far, T::class)

/**
 * Creates a projection matrix for an orthographic (parallel) projection defined by a given set
 * of clipping planes: [left], [right], [bottom], [top], [near] and [far].
 */
fun <T> orthographic(
    left: T,
    right: T,
    bottom: T,
    top: T,
    near: T,
    far: T,
    type: KClass<T>
): Mat4<T> where T : Number, T : Comparable<T> {
    val zero = zero(type)
    val one = one(type)
    val two = one + one

    val width = right - left
    val height = top - bottom
    val depth = far - near

    return Mat4(
        elements = listOf(
            two / width, zero, zero, zero,
            zero, two / height, zero, zero,
            zero, zero, -two / depth, zero,
            -(right + left) / width, -(top + bottom) / height, -(near + far) / depth, one
        ),
        type = type
    )
}
