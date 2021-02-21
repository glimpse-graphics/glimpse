/*
 * Copyright 2020-2021 Slawomir Czerwinski
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

import graphics.glimpse.assertions.assertEqualsWithDelta
import kotlin.js.JsName
import kotlin.test.Test

class ProjectionMat4Test {

    @Test
    @JsName(name = "GIVEN_6_numbers_WHEN_frustum_THEN_should_generate_frustum_projection_matrix")
    fun `GIVEN 6 numbers, WHEN frustum, THEN should generate frustum projection matrix`() {
        val left = -2f
        val right = 2f
        val bottom = -1f
        val top = 1f
        val near = 1f
        val far = 10f

        val result = frustum(
            left = left,
            right = right,
            bottom = bottom,
            top = top,
            near = near,
            far = far
        )

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0.5f, 0f, 0f, 0f,
                    0f, 1f, 0f, 0f,
                    0f, 0f, -1.22222222f, -1f,
                    0f, 0f, -2.22222222f, 0f
                )
            ),
            result
        )
    }

    @Test
    @JsName(
        name = "GIVEN_an_angle_ratio_and_2_numbers_WHEN_perspective_THEN_should_generate_perspective_projection_matrix"
    )
    fun `GIVEN an angle, ratio and 2 numbers, WHEN perspective, THEN should generate perspective projection matrix`() {
        val angle = Angle.rightAngle
        val aspect = 2f
        val near = 1f
        val far = 10f

        val result = perspective(angle, aspect, near, far)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0.5f, 0f, 0f, 0f,
                    0f, 1f, 0f, 0f,
                    0f, 0f, -1.22222222f, -1f,
                    0f, 0f, -2.22222222f, 0f
                )
            ),
            result
        )
    }

    @Test
    @JsName(name = "GIVEN_6_numbers_WHEN_orthographic_THEN_should_generate_orthographic_projection_matrix")
    fun `GIVEN 6 numbers, WHEN orthographic, THEN should generate orthographic projection matrix`() {
        val left = -1f
        val right = 1f
        val bottom = -1f
        val top = 1f
        val near = 1f
        val far = -1f

        val result = orthographic(
            left = left,
            right = right,
            bottom = bottom,
            top = top,
            near = near,
            far = far
        )

        assertEqualsWithDelta(Mat4.identity, result)
    }
}
