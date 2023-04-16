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

import graphics.glimpse.assertions.assertEqualsWithDelta
import kotlin.test.Test

class ProjectionMat4DTest {

    @Test
    fun `GIVEN 6 numbers, WHEN frustum, THEN should generate frustum projection matrix`() {
        val left = -2.0
        val right = 2.0
        val bottom = -1.0
        val top = 1.0
        val near = 1.0
        val far = 10.0

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
                    0.5, 0.0, 0.0, 0.0,
                    0.0, 1.0, 0.0, 0.0,
                    0.0, 0.0, -1.22222222222222, -1.0,
                    0.0, 0.0, -2.22222222222222, 0.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN an angle, ratio and 2 numbers, WHEN perspective, THEN should generate perspective projection matrix`() {
        val angle = Angle.rightAngle.toDoubleAngle()
        val aspect = 2.0
        val near = 1.0
        val far = 10.0

        val result = perspective(angle, aspect, near, far)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0.5, 0.0, 0.0, 0.0,
                    0.0, 1.0, 0.0, 0.0,
                    0.0, 0.0, -1.22222222222222, -1.0,
                    0.0, 0.0, -2.22222222222222, 0.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN 6 numbers, WHEN orthographic, THEN should generate orthographic projection matrix`() {
        val left = -1.0
        val right = 1.0
        val bottom = -1.0
        val top = 1.0
        val near = 1.0
        val far = -1.0

        val result = orthographic(
            left = left,
            right = right,
            bottom = bottom,
            top = top,
            near = near,
            far = far
        )

        assertEqualsWithDelta(Mat4.identity.toDoubleMatrix(), result)
    }
}
