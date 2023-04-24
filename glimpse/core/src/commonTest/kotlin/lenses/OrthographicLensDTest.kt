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
import graphics.glimpse.types.Mat4
import kotlin.test.Test

class OrthographicLensDTest {

    @Test
    fun `GIVEN a OrthographicLens, WHEN projectionMatrix, THEN return correct matrix`() {
        val left = -1.0
        val right = 1.0
        val bottom = -1.0
        val top = 1.0
        val near = 1.0
        val far = -1.0
        val lens = OrthographicLens(left, right, bottom, top, near, far)

        val result = lens.projectionMatrix

        assertEqualsWithDelta(Mat4.identity(), result)
    }
}
