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

/**
 * Creates a transformation matrix for a translation by a given [vector].
 */
inline fun <reified T> translation(vector: Vec3<T>): Mat4<T> where T : Number, T : Comparable<T> {
    val (x, y, z) = vector
    return Mat4(
        listOf(
            one(), zero(), zero(), zero(),
            zero(), one(), zero(), zero(),
            zero(), zero(), one(), zero(),
            x, y, z, one()
        )
    )
}

/**
 * Creates a transformation matrix for a rotation by a given [angle] around a given [axis].
 */
inline fun <reified T> rotation(axis: Vec3<T>, angle: Angle<T>): Mat4<T> where T : Number, T : Comparable<T> {
    val (x, y, z) = axis.normalize()
    val sin = sin(angle)
    val cos = cos(angle)
    val nCos = one<T>() - cos(angle)
    return Mat4(
        listOf(
            cos + x * x * nCos, x * y * nCos + z * sin, x * z * nCos - y * sin, zero(),
            x * y * nCos - z * sin, cos + y * y * nCos, y * z * nCos + x * sin, zero(),
            x * z * nCos + y * sin, y * z * nCos - x * sin, cos + z * z * nCos, zero(),
            zero(), zero(), zero(), one()
        )
    )
}

/**
 * Creates a transformation matrix for a rotation by a given [angle] around X axis.
 */
inline fun <reified T> rotationX(angle: Angle<T>): Mat4<T> where T : Number, T : Comparable<T> {
    val sin = sin(angle)
    val cos = cos(angle)
    return Mat4(
        listOf(
            one(), zero(), zero(), zero(),
            zero(), cos, sin, zero(),
            zero(), -sin, cos, zero(),
            zero(), zero(), zero(), one()
        )
    )
}

/**
 * Creates a transformation matrix for a rotation by a given [angle] around Y axis.
 */
inline fun <reified T> rotationY(angle: Angle<T>): Mat4<T> where T : Number, T : Comparable<T> {
    val sin = sin(angle)
    val cos = cos(angle)
    return Mat4(
        listOf(
            cos, zero(), -sin, zero(),
            zero(), one(), zero(), zero(),
            sin, zero(), cos, zero(),
            zero(), zero(), zero(), one()
        )
    )
}

/**
 * Creates a transformation matrix for a rotation by a given [angle] around Z axis.
 */
inline fun <reified T> rotationZ(angle: Angle<T>): Mat4<T> where T : Number, T : Comparable<T> {
    val sin = sin(angle)
    val cos = cos(angle)
    return Mat4(
        listOf(
            cos, sin, zero(), zero(),
            -sin, cos, zero(), zero(),
            zero(), zero(), one(), zero(),
            zero(), zero(), zero(), one()
        )
    )
}

/**
 * Creates a transformation matrix for scaling by a given [scale].
 */
inline fun <reified T> scale(scale: T): Mat4<T> where T : Number, T : Comparable<T> =
    scale(scale, scale, scale)

/**
 * Creates a transformation matrix for scaling by a given scale in [x], [y] and [z] directions.
 */
inline fun <reified T> scale(x: T = one(), y: T = one(), z: T = one()): Mat4<T> where T : Number, T : Comparable<T> =
    Mat4(
        listOf(
            x, zero(), zero(), zero(),
            zero(), y, zero(), zero(),
            zero(), zero(), z, zero(),
            zero(), zero(), zero(), one()
        )
    )

/**
 * Creates a transformation matrix for mirroring through a plane passing through the [origin] point,
 * and perpendicular a given [normal] vector.
 */
inline fun <reified T> mirror(normal: Vec3<T>, origin: Vec3<T>): Mat4<T> where T : Number, T : Comparable<T> {
    val (a, b, c) = normal.normalize()
    val d = -origin dot normal.normalize()

    val two = one<T>() + one<T>()

    return Mat4(
        @Suppress("MagicNumber")
        listOf(
            one<T>() - two * a * a, -two * b * a, -two * c * a, zero(),
            -two * a * b, one<T>() - two * b * b, -two * c * b, zero(),
            -two * a * c, -two * b * c, one<T>() - two * c * c, zero(),
            -two * a * d, -two * b * d, -two * c * d, one()
        )
    )
}
