/*
 * Copyright 2020 Slawomir Czerwinski
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
 *
 */

package graphics.glimpse.types

object TransformationMat4 {

    fun translation(vector: Vec3): Mat4 {
        val (x, y, z) = vector
        return Mat4(
            listOf(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                x, y, z, 1f
            )
        )
    }

    fun rotation(axis: Vec3, angle: Angle): Mat4 {
        val (x, y, z) = normalize(axis)
        val sin = sin(angle)
        val cos = cos(angle)
        val nCos = 1f - cos(angle)
        return Mat4(
            listOf(
                cos + x * x * nCos, x * y * nCos + z * sin, x * z * nCos - y * sin, 0f,
                x * y * nCos - z * sin, cos + y * y * nCos, y * z * nCos + x * sin, 0f,
                x * z * nCos + y * sin, y * z * nCos - x * sin, cos + z * z * nCos, 0f,
                0f, 0f, 0f, 1f
            )
        )
    }

    fun rotationX(angle: Angle): Mat4 {
        val sin = sin(angle)
        val cos = cos(angle)
        return Mat4(
            listOf(
                1f, 0f, 0f, 0f,
                0f, cos, sin, 0f,
                0f, -sin, cos, 0f,
                0f, 0f, 0f, 1f
            )
        )
    }

    fun rotationY(angle: Angle): Mat4 {
        val sin = sin(angle)
        val cos = cos(angle)
        return Mat4(
            listOf(
                cos, 0f, -sin, 0f,
                0f, 1f, 0f, 0f,
                sin, 0f, cos, 0f,
                0f, 0f, 0f, 1f
            )
        )
    }

    fun rotationZ(angle: Angle): Mat4 {
        val sin = sin(angle)
        val cos = cos(angle)
        return Mat4(
            listOf(
                cos, sin, 0f, 0f,
                -sin, cos, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        )
    }

    fun scale(scale: Float): Mat4 = scale(scale, scale, scale)

    fun scale(x: Float = 1f, y: Float = 1f, z: Float = 1f): Mat4 =
        Mat4(
            listOf(
                x, 0f, 0f, 0f,
                0f, y, 0f, 0f,
                0f, 0f, z, 0f,
                0f, 0f, 0f, 1f
            )
        )

    fun mirror(normal: Vec3, center: Vec3): Mat4 {
        val (a, b, c) = normalize(normal)
        val d = -center dot normalize(normal)
        return Mat4(
            @Suppress("MagicNumber")
            listOf(
                1f - 2f * a * a, -2f * b * a, -2f * c * a, 0f,
                -2f * a * b, 1f - 2f * b * b, -2f * c * b, 0f,
                -2f * a * c, -2f * b * c, 1f - 2f * c * c, 0f,
                -2f * a * d, -2f * b * d, -2f * c * d, 1f
            )
        )
    }
}
