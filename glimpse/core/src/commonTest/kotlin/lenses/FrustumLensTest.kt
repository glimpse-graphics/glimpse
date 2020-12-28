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

package graphics.glimpse.lenses

import graphics.glimpse.assertions.assertEqualsWithDelta
import graphics.glimpse.types.Mat4
import kotlin.js.JsName
import kotlin.test.Test

class FrustumLensTest {

    @Test
    @JsName(name = "GIVEN_a_FrustumLens_WHEN_projectionMatrix_THEN_return_correct_matrix")
    fun `GIVEN a FrustumLens, WHEN projectionMatrix, THEN return correct matrix`() {
        val left = -2f
        val right = 2f
        val bottom = -1f
        val top = 1f
        val near = 1f
        val far = 10f
        val lens = FrustumLens(left, right, bottom, top, near, far)

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
