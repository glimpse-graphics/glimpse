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

import java.awt.Color
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("MagicNumber")
class ColorsTest {

    @Test
    fun `GIVEN color, WHEN Vec3, THEN return Vec3 for that color`() {
        val color = Color(0.1f, 0.2f, 0.3f, 0.4f)

        val result = Vec3(color)

        assertEquals(Vec3(x = 0.1f, y = 0.2f, z = 0.3f), result)
    }

    @Test
    fun `GIVEN color, WHEN Vec4, THEN return Vec4 for that color`() {
        val color = Color(0.1f, 0.2f, 0.3f, 0.4f)

        val result = Vec4(color)

        assertEquals(Vec4(x = 0.1f, y = 0.2f, z = 0.3f, w = 0.4f), result)
    }
}
