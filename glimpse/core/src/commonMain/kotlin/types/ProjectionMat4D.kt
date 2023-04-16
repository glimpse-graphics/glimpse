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
 * Creates a projection matrix for a perspective projection defined by a given frustum.
 *
 * The frustum is defined by its [near] and [far] depth clipping planes, and its [left], [right],
 * [bottom] and [top] clipping planes (specified at the near depth).
 */
fun frustum(
    left: Double,
    right: Double,
    bottom: Double,
    top: Double,
    near: Double,
    far: Double
): Mat4<Double> {
    require(value = near > 0.0) { "Near depth clipping plane must be at a positive distance" }
    require(value = far > 0.0) { "Far depth clipping plane must be at a positive distance" }

    val width = right - left
    val height = top - bottom
    val depth = near - far

    return Mat4(
        listOf(
            2.0 * near / width, 0.0, 0.0, 0.0,
            0.0, 2.0 * near / height, 0.0, 0.0,
            (right + left) / width, (top + bottom) / height, (far + near) / depth, -1.0,
            0.0, 0.0, 2.0 * far * near / depth, 0.0
        )
    )
}

/**
 * Creates a projection matrix for a perspective projection defined by a given frustum.
 *
 * The frustum is defined by its [near] and [far] depth clipping planes,
 * and its [field of view angle in the Y direction][fovY] and [aspect] ratio between X and Y
 * field of view.
 */
fun perspective(
    fovY: Angle<Double>,
    aspect: Double,
    near: Double,
    far: Double
): Mat4<Double> {
    require(value = fovY > Angle.nullAngle.toDoubleAngle()) { "Field of view must be at a positive angle" }
    require(value = fovY < Angle.straightAngle.toDoubleAngle()) { "Field of view must be less than 180 degrees" }
    require(value = aspect > 0.0) { "Aspect ratio must be a positive number" }
    require(value = near > 0.0) { "Near depth clipping plane must be at a positive distance" }
    require(value = far > 0.0) { "Far depth clipping plane must be at a positive distance" }

    val top = tan(fovY / 2.0)
    val right = aspect * top
    val depth = near - far

    return Mat4(
        listOf(
            1.0 / right, 0.0, 0.0, 0.0,
            0.0, 1.0 / top, 0.0, 0.0,
            0.0, 0.0, (near + far) / depth, -1.0,
            0.0, 0.0, 2 * near * far / depth, 0.0
        )
    )
}

/**
 * Creates a projection matrix for an orthographic (parallel) projection defined by a given set
 * of clipping planes: [left], [right], [bottom], [top], [near] and [far].
 */
fun orthographic(
    left: Double,
    right: Double,
    bottom: Double,
    top: Double,
    near: Double,
    far: Double
): Mat4<Double> {
    val width = right - left
    val height = top - bottom
    val depth = far - near

    return Mat4(
        @Suppress("MagicNumber")
        listOf(
            2.0 / width, 0.0, 0.0, 0.0,
            0.0, 2.0 / height, 0.0, 0.0,
            0.0, 0.0, -2.0 / depth, 0.0,
            -(right + left) / width, -(top + bottom) / height, -(near + far) / depth, 1.0
        )
    )
}
