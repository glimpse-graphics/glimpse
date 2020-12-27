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

object ProjectionMat4 {

    @Suppress("LongParameterList")
    fun frustum(
        left: Float,
        right: Float,
        bottom: Float,
        top: Float,
        near: Float,
        far: Float
    ): Mat4 {
        val width = right - left
        val height = top - bottom
        val depth = near - far
        return Mat4(
            listOf(
                2f * near / width, 0f, 0f, 0f,
                0f, 2f * near / height, 0f, 0f,
                (right + left) / width, (top + bottom) / height, (far + near) / depth, -1f,
                0f, 0f, 2f * far * near / depth, 0f
            )
        )
    }

    fun perspective(
        fovY: Angle,
        aspect: Float,
        near: Float,
        far: Float
    ): Mat4 {
        val top = tan(fovY / 2f) * near
        val right = aspect * top
        val depth = near - far
        return Mat4(
            listOf(
                1f / right, 0f, 0f, 0f,
                0f, 1f / top, 0f, 0f,
                0f, 0f, (near + far) / depth, -1f,
                0f, 0f, 2 * near * far / depth, 0f
            )
        )
    }

    @Suppress("LongParameterList")
    fun orthographic(
        left: Float,
        right: Float,
        bottom: Float,
        top: Float,
        near: Float,
        far: Float
    ): Mat4 {
        val width = right - left
        val height = top - bottom
        val depth = far - near
        return Mat4(
            @Suppress("MagicNumber")
            listOf(
                2f / width, 0f, 0f, 0f,
                0f, 2f / height, 0f, 0f,
                0f, 0f, -2f / depth, 0f,
                -(right + left) / width, -(top + bottom) / height, -(near + far) / depth, 1f
            )
        )
    }
}
