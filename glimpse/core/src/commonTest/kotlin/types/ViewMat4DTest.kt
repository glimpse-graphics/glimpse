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

class ViewMat4DTest {

    @Test
    fun `GIVEN eye location, target location and up vector, WHEN lookAt, THEN should generate look-at matrix`() {
        val eye = Vec3(x = 0.5, y = 0.5, z = 0.7071067811865475)
        val target = Vec3(x = 0.0, y = 0.0, z = 0.0)
        val upVector = Vec3(x = 0.0, y = 0.0, z = 1.0)

        val result = lookAt(eye, target, upVector)

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
