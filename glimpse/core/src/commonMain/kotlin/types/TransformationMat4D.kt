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
fun translation(vector: Vec3<Double>): Mat4<Double> {
    val (x, y, z) = vector
    return Mat4(
        listOf(
            1.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0,
            x, y, z, 1.0
        )
    )
}

/**
 * Creates a transformation matrix for a rotation by a given [angle] around a given [axis].
 */
fun rotation(axis: Vec3<Double>, angle: Angle<Double>): Mat4<Double> {
    val (x, y, z) = axis.normalize()
    val sin = sin(angle)
    val cos = cos(angle)
    val nCos = 1.0 - cos(angle)
    return Mat4(
        listOf(
            cos + x * x * nCos, x * y * nCos + z * sin, x * z * nCos - y * sin, 0.0,
            x * y * nCos - z * sin, cos + y * y * nCos, y * z * nCos + x * sin, 0.0,
            x * z * nCos + y * sin, y * z * nCos - x * sin, cos + z * z * nCos, 0.0,
            0.0, 0.0, 0.0, 1.0
        )
    )
}

/**
 * Creates a transformation matrix for a rotation by a given [angle] around X axis.
 */
fun rotationX(angle: Angle<Double>): Mat4<Double> {
    val sin = sin(angle)
    val cos = cos(angle)
    return Mat4(
        listOf(
            1.0, 0.0, 0.0, 0.0,
            0.0, cos, sin, 0.0,
            0.0, -sin, cos, 0.0,
            0.0, 0.0, 0.0, 1.0
        )
    )
}

/**
 * Creates a transformation matrix for a rotation by a given [angle] around Y axis.
 */
fun rotationY(angle: Angle<Double>): Mat4<Double> {
    val sin = sin(angle)
    val cos = cos(angle)
    return Mat4(
        listOf(
            cos, 0.0, -sin, 0.0,
            0.0, 1.0, 0.0, 0.0,
            sin, 0.0, cos, 0.0,
            0.0, 0.0, 0.0, 1.0
        )
    )
}

/**
 * Creates a transformation matrix for a rotation by a given [angle] around Z axis.
 */
fun rotationZ(angle: Angle<Double>): Mat4<Double> {
    val sin = sin(angle)
    val cos = cos(angle)
    return Mat4(
        listOf(
            cos, sin, 0.0, 0.0,
            -sin, cos, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0,
            0.0, 0.0, 0.0, 1.0
        )
    )
}

/**
 * Creates a transformation matrix for scaling by a given [scale].
 */
fun scale(scale: Double): Mat4<Double> = scale(scale, scale, scale)

/**
 * Creates a transformation matrix for scaling by a given scale in [x], [y] and [z] directions.
 */
fun scale(x: Double = 1.0, y: Double = 1.0, z: Double = 1.0): Mat4<Double> =
    Mat4(
        listOf(
            x, 0.0, 0.0, 0.0,
            0.0, y, 0.0, 0.0,
            0.0, 0.0, z, 0.0,
            0.0, 0.0, 0.0, 1.0
        )
    )

/**
 * Creates a transformation matrix for mirroring through a plane passing through the [origin] point,
 * and perpendicular a given [normal] vector.
 */
fun mirror(normal: Vec3<Double>, origin: Vec3<Double>): Mat4<Double> {
    val (a, b, c) = normal.normalize()
    val d = -origin dot normal.normalize()
    return Mat4(
        @Suppress("MagicNumber")
        listOf(
            1.0 - 2.0 * a * a, -2.0 * b * a, -2.0 * c * a, 0.0,
            -2.0 * a * b, 1.0 - 2.0 * b * b, -2.0 * c * b, 0.0,
            -2.0 * a * c, -2.0 * b * c, 1.0 - 2.0 * c * c, 0.0,
            -2.0 * a * d, -2.0 * b * d, -2.0 * c * d, 1.0
        )
    )
}
