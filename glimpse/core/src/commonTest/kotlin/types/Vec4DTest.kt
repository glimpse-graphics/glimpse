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

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class Vec4DTest {

    @Test
    fun `GIVEN a vector, WHEN r, THEN return x`() {
        val vector = Vec4(x = 7.0, y = 13.0, z = 29.0, w = 31.0)

        val result = vector.r

        assertEquals(7.0, result)
    }

    @Test
    fun `GIVEN a vector, WHEN g, THEN return y`() {
        val vector = Vec4(x = 7.0, y = 13.0, z = 29.0, w = 31.0)

        val result = vector.g

        assertEquals(13.0, result)
    }

    @Test
    fun `GIVEN a vector, WHEN b, THEN return z`() {
        val vector = Vec4(x = 7.0, y = 13.0, z = 29.0, w = 31.0)

        val result = vector.b

        assertEquals(29.0, result)
    }

    @Test
    fun `GIVEN a vector, WHEN a, THEN return w`() {
        val vector = Vec4(x = 7.0, y = 13.0, z = 29.0, w = 31.0)

        val result = vector.a

        assertEquals(31.0, result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec2, THEN return the a 2D vector`() {
        val vector = Vec4(x = 7.0, y = 13.0, z = 29.0, w = 31.0)

        val result = vector.toVec2()

        assertEquals(Vec2(x = 7.0, y = 13.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec4, THEN return the a 3D vector`() {
        val vector = Vec4(x = 7.0, y = 13.0, z = 29.0, w = 31.0)

        val result = vector.toVec3()

        assertEquals(Vec3(x = 7.0, y = 13.0, z = 29.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toList, THEN return vector's coordinates as list`() {
        val vector = Vec4(x = 10.0, y = 20.0, z = 30.0, w = 40.0)

        val result = vector.toList()

        assertEquals(listOf(10.0, 20.0, 30.0, 40.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toDoubleArray, THEN return vector's coordinates as array`() {
        val vector = Vec4(x = 10.0, y = 20.0, z = 30.0, w = 40.0)

        val result = vector.toDoubleArray()

        assertEquals(listOf(10.0, 20.0, 30.0, 40.0), result.toList())
    }

    @Test
    fun `GIVEN a list, WHEN fromList, THEN return vector with coordinates from list`() {
        val list = listOf(1.0, 2.0, 3.0, 4.0)

        val result = Vec4.fromList(list)

        assertEquals(Vec4(x = 1.0, y = 2.0, z = 3.0, w = 4.0), result)
    }

    @Test
    fun `GIVEN a list of 3 numbers, WHEN fromList, THEN throw exception`() {
        val list = listOf(1.0, 2.0, 3.0)

        assertFailsWith(IllegalArgumentException::class) {
            Vec4.fromList(list)
        }
    }
}
