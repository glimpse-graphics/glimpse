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

package graphics.glimpse.lenses

import graphics.glimpse.assertions.assertEqualsWithDelta
import graphics.glimpse.types.Mat4
import kotlin.js.JsName
import kotlin.test.Test

class OrthographicLensTest {

    @Test
    @JsName(name = "GIVEN_a_OrthographicLens_WHEN_projectionMatrix_THEN_return_correct_matrix")
    fun `GIVEN a OrthographicLens, WHEN projectionMatrix, THEN return correct matrix`() {
        val left = -1f
        val right = 1f
        val bottom = -1f
        val top = 1f
        val near = 1f
        val far = -1f
        val lens = OrthographicLens(left, right, bottom, top, near, far)

        val result = lens.projectionMatrix

        assertEqualsWithDelta(Mat4.identity, result)
    }
}
