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

package graphics.glimpse.types

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Vec4FTest {

    @Test
    fun `GIVEN a vector, WHEN r, THEN return x`() {
        val vector = Vec4(x = 7f, y = 13f, z = 29f, w = 31f)

        val result = vector.r

        assertEquals(7f, result)
    }

    @Test
    fun `GIVEN a vector, WHEN g, THEN return y`() {
        val vector = Vec4(x = 7f, y = 13f, z = 29f, w = 31f)

        val result = vector.g

        assertEquals(13f, result)
    }

    @Test
    fun `GIVEN a vector, WHEN b, THEN return z`() {
        val vector = Vec4(x = 7f, y = 13f, z = 29f, w = 31f)

        val result = vector.b

        assertEquals(29f, result)
    }

    @Test
    fun `GIVEN a vector, WHEN a, THEN return w`() {
        val vector = Vec4(x = 7f, y = 13f, z = 29f, w = 31f)

        val result = vector.a

        assertEquals(31f, result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec2, THEN return the a 2D vector`() {
        val vector = Vec4(x = 7f, y = 13f, z = 29f, w = 31f)

        val result = vector.toVec2()

        assertEquals(Vec2(x = 7f, y = 13f), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec4, THEN return the a 3D vector`() {
        val vector = Vec4(x = 7f, y = 13f, z = 29f, w = 31f)

        val result = vector.toVec3()

        assertEquals(Vec3(x = 7f, y = 13f, z = 29f), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toList, THEN return vector's coordinates as list`() {
        val vector = Vec4(x = 10f, y = 20f, z = 30f, w = 40f)

        val result = vector.toList()

        assertEquals(listOf(10f, 20f, 30f, 40f), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toFloatArray, THEN return vector's coordinates as array`() {
        val vector = Vec4(x = 10f, y = 20f, z = 30f, w = 40f)

        val result = vector.toFloatArray()

        assertEquals(listOf(10f, 20f, 30f, 40f), result.toList())
    }

    @Test
    fun `GIVEN a list, WHEN fromList, THEN return vector with coordinates from list`() {
        val list = listOf(1f, 2f, 3f, 4f)

        val result = Vec4.fromList(list)

        assertEquals(Vec4(x = 1f, y = 2f, z = 3f, w = 4f), result)
    }

    @Test
    fun `GIVEN a list of 3 numbers, WHEN fromList, THEN throw exception`() {
        val list = listOf(1f, 2f, 3f)

        assertFailsWith<IllegalArgumentException> {
            Vec4.fromList(list)
        }
    }
}
