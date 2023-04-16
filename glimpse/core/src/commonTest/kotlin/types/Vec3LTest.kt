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
import kotlin.test.assertSame

class Vec3LTest {

    @Test
    fun `GIVEN a vector, WHEN r, THEN return x`() {
        val vector = Vec3(x = 7L, y = 13L, z = 29L)

        val result = vector.r

        assertEquals(7L, result)
    }

    @Test
    fun `GIVEN a vector, WHEN g, THEN return y`() {
        val vector = Vec3(x = 7L, y = 13L, z = 29L)

        val result = vector.g

        assertEquals(13L, result)
    }

    @Test
    fun `GIVEN a vector, WHEN b, THEN return z`() {
        val vector = Vec3(x = 7L, y = 13L, z = 29L)

        val result = vector.b

        assertEquals(29L, result)
    }

    @Test
    fun `GIVEN a vector, WHEN unaryPlus, THEN return the same vector`() {
        val vector = Vec3(x = 7L, y = 13L, z = 29L)

        val result = +vector

        assertSame(vector, result)
    }

    @Test
    fun `GIVEN a vector, WHEN unaryMinus, THEN return the opposite vector`() {
        val vector = Vec3(x = 7L, y = 13L, z = 29L)

        val result = -vector

        assertEquals(Vec3(x = -7L, y = -13L, z = -29L), result)
    }

    @Test
    fun `GIVEN two vectors, WHEN plus, THEN return sum of two vectors`() {
        val v1 = Vec3(x = 10L, y = 20L, z = 30L)
        val v2 = Vec3(x = 1L, y = 2L, z = 3L)

        val result = v1 + v2

        assertEquals(Vec3(x = 11L, y = 22L, z = 33L), result)
    }

    @Test
    fun `GIVEN two vectors, WHEN minus, THEN return difference of two vectors`() {
        val v1 = Vec3(x = 10L, y = 20L, z = 30L)
        val v2 = Vec3(x = 1L, y = 2L, z = 3L)

        val result = v1 - v2

        assertEquals(Vec3(x = 9L, y = 18L, z = 27L), result)
    }

    @Test
    fun `GIVEN a vector and a number, WHEN times, THEN return product of the vector and the number`() {
        val vector = Vec3(x = 10L, y = 20L, z = 30L)
        val number = 2L

        val result = vector * number

        assertEquals(Vec3(x = 20L, y = 40L, z = 60L), result)
    }

    @Test
    fun `GIVEN a vector and a number, WHEN div, THEN return quotient of the vector and the number`() {
        val vector = Vec3(x = 10L, y = 20L, z = 30L)
        val number = 10L

        assertFailsWith<UnsupportedOperationException> {
            vector / number
        }
    }

    @Test
    fun `GIVEN two vectors, WHEN dot, THEN return dot product of two vectors`() {
        val v1 = Vec3(x = 10L, y = 20L, z = 30L)
        val v2 = Vec3(x = 1L, y = 2L, z = 3L)

        val result = v1 dot v2

        assertEquals(140L, result)
    }

    @Test
    fun `GIVEN two vectors, WHEN cross, THEN return cross product of two vectors`() {
        val v1 = Vec3(x = 1L, y = 2L, z = 3L)
        val v2 = Vec3(x = 3L, y = 2L, z = 1L)

        val result = v1 cross v2

        assertEquals(Vec3(x = -4L, y = 8L, z = -4L), result)
    }

    @Test
    fun `GIVEN a vector, WHEN magnitude, THEN return vector's magnitude`() {
        val vector = Vec3(x = 3L, y = 4L, z = 12L)

        assertFailsWith<UnsupportedOperationException> {
            vector.magnitude()
        }
    }

    @Test
    fun `GIVEN a vector, WHEN normalize, THEN return a unit vector in the same direction`() {
        val vector = Vec3(x = 3L, y = 4L, z = 12L)

        assertFailsWith<UnsupportedOperationException> {
            vector.normalize()
        }
    }

    @Test
    fun `GIVEN a vector, WHEN toVec2, THEN return the a 2D vector`() {
        val vector = Vec3(x = 1L, y = 2L, z = 3L)

        val result = vector.toVec2()

        assertEquals(Vec2(x = 1L, y = 2L), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec4, THEN return the a 4D vector with w = 0`() {
        val vector = Vec3(x = 7L, y = 13L, z = 29L)

        val result = vector.toVec4()

        assertEquals(Vec4(x = 7L, y = 13L, z = 29L, w = 0L), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec4 with w, THEN return the a 4D vector with w`() {
        val vector = Vec3(x = 7L, y = 13L, z = 29L)

        val result = vector.toVec4(w = 31L)

        assertEquals(Vec4(x = 7L, y = 13L, z = 29L, w = 31L), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toList, THEN return vector's coordinates as list`() {
        val vector = Vec3(x = 10L, y = 20L, z = 30L)

        val result = vector.toList()

        assertEquals(listOf(10L, 20L, 30L), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toLongArray, THEN return vector's coordinates as array`() {
        val vector = Vec3(x = 10L, y = 20L, z = 30L)

        val result = vector.toLongArray()

        assertEquals(listOf(10L, 20L, 30L), result.toList())
    }

    @Test
    fun `GIVEN a list, WHEN fromList, THEN return vector with coordinates from list`() {
        val list = listOf(1L, 2L, 3L)

        val result = Vec3.fromList(list)

        assertEquals(Vec3(x = 1L, y = 2L, z = 3L), result)
    }

    @Test
    fun `GIVEN a list of 2 numbers, WHEN fromList, THEN throw exception`() {
        val list = listOf(1L, 2L)

        assertFailsWith<IllegalArgumentException> {
            Vec3.fromList(list)
        }
    }
}
