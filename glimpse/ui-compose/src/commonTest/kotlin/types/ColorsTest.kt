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

package graphics.glimpse.ui.compose.types

import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertEquals

class ColorsTest {

    @Test
    fun `GIVEN color, WHEN toVec3, THEN return Vec3 for that color`() {
        val color = Color(red = 0.1f, green = 0.2f, blue = 0.3f, alpha = 0.4f)

        val result = color.toVec3()

        assertEquals(graphics.glimpse.types.Vec3(x = 0.101960786f, y = 0.2f, z = 0.3019608f), result)
    }

    @Test
    fun `GIVEN color, WHEN toVec4, THEN return Vec4 for that color`() {
        val color = Color(red = 0.1f, green = 0.2f, blue = 0.3f, alpha = 0.4f)

        val result = color.toVec4()

        assertEquals(graphics.glimpse.types.Vec4(x = 0.101960786f, y = 0.2f, z = 0.3019608f, w = 0.4f), result)
    }

    @Test
    fun `GIVEN Vec3, WHEN toComposeColor, THEN return color for that Vec3`() {
        val vector = graphics.glimpse.types.Vec3(x = 0.1f, y = 0.2f, z = 0.3f)

        val result = vector.toComposeColor()

        assertEquals(Color(red = 0.1f, green = 0.2f, blue = 0.3f, alpha = 1f), result)
    }

    @Test
    fun `GIVEN Vec4, WHEN toComposeColor, THEN return color for that Vec4`() {
        val vector = graphics.glimpse.types.Vec4(x = 0.1f, y = 0.2f, z = 0.3f, w = 0.4f)

        val result = vector.toComposeColor()

        assertEquals(Color(red = 0.1f, green = 0.2f, blue = 0.3f, alpha = 0.4f), result)
    }
}
