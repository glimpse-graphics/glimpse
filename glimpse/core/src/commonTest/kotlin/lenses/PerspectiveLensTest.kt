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

package graphics.glimpse.lenses

import graphics.glimpse.assertions.assertEqualsWithDelta
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import kotlin.test.Test

class PerspectiveLensTest {

    @Test
    fun `GIVEN a PerspectiveLens, WHEN projectionMatrix, THEN return correct matrix`() {
        val fovY = Angle.rightAngle
        val aspect = 2f
        val near = 1f
        val far = 10f
        val lens = PerspectiveLens(fovY, aspect, near, far)

        val result = lens.projectionMatrix

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
}
