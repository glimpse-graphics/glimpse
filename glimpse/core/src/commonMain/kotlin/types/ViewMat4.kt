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
 * Creates a view matrix defined by an [eye] position, a [target] point, and an [upVector].
 */
inline fun <reified T> lookAt(
    eye: Vec3<T>,
    target: Vec3<T>,
    upVector: Vec3<T>
): Mat4<T> where T : Number, T : Comparable<T> =
    lookAt(eye, target, upVector, T::class)

/**
 * Creates a view matrix defined by an [eye] position, a [target] point, and an [upVector].
 */
fun <T> lookAt(
    eye: Vec3<T>,
    target: Vec3<T>,
    upVector: Vec3<T>,
    type: KClass<T>
): Mat4<T> where T : Number, T : Comparable<T> {
    val forward = (target - eye).normalize()
    val right = (forward cross upVector).normalize()
    val up = right cross forward
    return Mat4(
        elements = listOf(
            right.x, up.x, -forward.x, zero(type),
            right.y, up.y, -forward.y, zero(type),
            right.z, up.z, -forward.z, zero(type),
            zero(type), zero(type), zero(type), one(type)
        ),
        type = type
    ) * translation(vector = -eye)
}
