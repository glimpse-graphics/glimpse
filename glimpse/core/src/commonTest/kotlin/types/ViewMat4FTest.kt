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

class ViewMat4FTest {

    @Test
    fun `GIVEN eye location, target location and up vector, WHEN lookAt, THEN should generate look-at matrix`() {
        val eye = Vec3(x = 0.5f, y = 0.5f, z = 0.70710677f)
        val target = Vec3(x = 0f, y = 0f, z = 0f)
        val upVector = Vec3(x = 0f, y = 0f, z = 1f)

        val result = lookAt(eye, target, upVector)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    -0.70710677f, -0.5f, 0.5f, 0f,
                    0.70710677f, -0.5f, 0.5f, 0f,
                    0f, 0.70710677f, 0.70710677f, 0f,
                    0f, 0f, -1f, 1f
                )
            ),
            result
        )
    }
}
