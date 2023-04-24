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

package graphics.glimpse.cameras

import graphics.glimpse.testing.assertEqualsWithDelta
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec3
import kotlin.test.Test

class TargetCameraDTest {

    @Test
    fun `GIVEN a TargetCamera, WHEN viewMatrix, THEN return correct matrix`() {
        val eye = Vec3(x = 0.5, y = 0.5, z = 0.7071067811865475)
        val target = Vec3.nullVector<Double>()
        val camera = TargetCamera(eye, target)

        val result = camera.viewMatrix

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    -0.7071067811865475, -0.5, 0.5, 0.0,
                    0.7071067811865475, -0.5, 0.5, 0.0,
                    0.0, 0.7071067811865475, 0.7071067811865475, 0.0,
                    0.0, 0.0, -1.0, 1.0
                )
            ),
            result
        )
    }
}
