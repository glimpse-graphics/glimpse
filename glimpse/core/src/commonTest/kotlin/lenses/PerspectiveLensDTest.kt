/*
 * Copyright 2020-2023 Glimpse Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.lenses

import graphics.glimpse.testing.assertEqualsWithDelta
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import kotlin.test.Test

class PerspectiveLensDTest {

    @Test
    fun `GIVEN a PerspectiveLens, WHEN projectionMatrix, THEN return correct matrix`() {
        val fovY = Angle.rightAngle<Double>()
        val aspect = 2.0
        val near = 1.0
        val far = 10.0
        val lens = PerspectiveLens(fovY, aspect, near, far)

        val result = lens.projectionMatrix

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0.5, 0.0, 0.0, 0.0,
                    0.0, 1.0, 0.0, 0.0,
                    0.0, 0.0, -1.222222222222222, -1.0,
                    0.0, 0.0, -2.222222222222222, 0.0
                )
            ),
            result
        )
    }
}
