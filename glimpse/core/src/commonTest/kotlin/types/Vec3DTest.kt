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
import kotlin.test.assertSame

class Vec3DTest {

    @Test
    fun `GIVEN a vector, WHEN r, THEN return x`() {
        val vector = Vec3(x = 7.0, y = 13.0, z = 29.0)

        val result = vector.r

        assertEquals(7.0, result)
    }

    @Test
    fun `GIVEN a vector, WHEN g, THEN return y`() {
        val vector = Vec3(x = 7.0, y = 13.0, z = 29.0)

        val result = vector.g

        assertEquals(13.0, result)
    }

    @Test
    fun `GIVEN a vector, WHEN b, THEN return z`() {
        val vector = Vec3(x = 7.0, y = 13.0, z = 29.0)

        val result = vector.b

        assertEquals(29.0, result)
    }

    @Test
    fun `GIVEN a vector, WHEN unaryPlus, THEN return the same vector`() {
        val vector = Vec3(x = 7.0, y = 13.0, z = 29.0)

        val result = +vector

        assertSame(vector, result)
    }

    @Test
    fun `GIVEN a vector, WHEN unaryMinus, THEN return the opposite vector`() {
        val vector = Vec3(x = 7.0, y = 13.0, z = 29.0)

        val result = -vector

        assertEquals(Vec3(x = -7.0, y = -13.0, z = -29.0), result)
    }

    @Test
    fun `GIVEN two vectors, WHEN plus, THEN return sum of two vectors`() {
        val v1 = Vec3(x = 10.0, y = 20.0, z = 30.0)
        val v2 = Vec3(x = 1.0, y = 2.0, z = 3.0)

        val result = v1 + v2

        assertEquals(Vec3(x = 11.0, y = 22.0, z = 33.0), result)
    }

    @Test
    fun `GIVEN two vectors, WHEN minus, THEN return difference of two vectors`() {
        val v1 = Vec3(x = 10.0, y = 20.0, z = 30.0)
        val v2 = Vec3(x = 1.0, y = 2.0, z = 3.0)

        val result = v1 - v2

        assertEquals(Vec3(x = 9.0, y = 18.0, z = 27.0), result)
    }

    @Test
    fun `GIVEN a vector and a number, WHEN times, THEN return product of the vector and the number`() {
        val vector = Vec3(x = 10.0, y = 20.0, z = 30.0)
        val number = 0.1

        val result = vector * number

        assertEquals(Vec3(x = 1.0, y = 2.0, z = 3.0), result)
    }

    @Test
    fun `GIVEN a vector and a number, WHEN div, THEN return quotient of the vector and the number`() {
        val vector = Vec3(x = 10.0, y = 20.0, z = 30.0)
        val number = 10.0

        val result = vector / number

        assertEquals(Vec3(x = 1.0, y = 2.0, z = 3.0), result)
    }

    @Test
    fun `GIVEN two vectors, WHEN dot, THEN return dot product of two vectors`() {
        val v1 = Vec3(x = 10.0, y = 20.0, z = 30.0)
        val v2 = Vec3(x = 1.0, y = 2.0, z = 3.0)

        val result = v1 dot v2

        assertEquals(140.0, result)
    }

    @Test
    fun `GIVEN two vectors, WHEN cross, THEN return cross product of two vectors`() {
        val v1 = Vec3(x = 1.0, y = 2.0, z = 3.0)
        val v2 = Vec3(x = 3.0, y = 2.0, z = 1.0)

        val result = v1 cross v2

        assertEquals(Vec3(x = -4.0, y = 8.0, z = -4.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN magnitude, THEN return vector's magnitude`() {
        val vector = Vec3(x = 3.0, y = 4.0, z = 12.0)

        val result = vector.magnitude()

        assertEquals(13.0, result)
    }

    @Test
    fun `GIVEN a vector, WHEN normalize, THEN return a unit vector in the same direction`() {
        val vector = Vec3(x = 3.0, y = 4.0, z = 12.0)

        val result = vector.normalize()

        assertEquals(Vec3(x = 0.23076923076923078, y = 0.3076923076923077, z = 0.9230769230769231), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec2, THEN return the a 2D vector`() {
        val vector = Vec3(x = 1.0, y = 2.0, z = 3.0)

        val result = vector.toVec2()

        assertEquals(Vec2(x = 1.0, y = 2.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec4, THEN return the a 4D vector with w = 0`() {
        val vector = Vec3(x = 7.0, y = 13.0, z = 29.0)

        val result = vector.toVec4()

        assertEquals(Vec4(x = 7.0, y = 13.0, z = 29.0, w = 0.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec4 with w, THEN return the a 4D vector with w`() {
        val vector = Vec3(x = 7.0, y = 13.0, z = 29.0)

        val result = vector.toVec4(w = 31.0)

        assertEquals(Vec4(x = 7.0, y = 13.0, z = 29.0, w = 31.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toRationalForm, THEN return the a 4D vector with w equal to 1`() {
        val vector = Vec3(x = 7.0, y = 13.0, z = 29.0)

        val result = vector.toRationalForm()

        assertEquals(Vec4(x = 7.0, y = 13.0, z = 29.0, w = 1.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toList, THEN return vector's coordinates as list`() {
        val vector = Vec3(x = 10.0, y = 20.0, z = 30.0)

        val result = vector.toList()

        assertEquals(listOf(10.0, 20.0, 30.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toDoubleArray, THEN return vector's coordinates as array`() {
        val vector = Vec3(x = 10.0, y = 20.0, z = 30.0)

        val result = vector.toDoubleArray()

        assertEquals(listOf(10.0, 20.0, 30.0), result.toList())
    }

    @Test
    fun `GIVEN a list, WHEN fromList, THEN return vector with coordinates from list`() {
        val list = listOf(1.0, 2.0, 3.0)

        val result = Vec3.fromList(list)

        assertEquals(Vec3(x = 1.0, y = 2.0, z = 3.0), result)
    }

    @Test
    fun `GIVEN a list of 2 numbers, WHEN fromList, THEN throw exception`() {
        val list = listOf(1.0, 2.0)

        assertFailsWith<IllegalArgumentException> {
            Vec3.fromList(list)
        }
    }

    @Test
    fun `GIVEN spherical coordinates, WHEN fromSphericalCoordinates, THEN return vector from spherical coordinates`() {
        val distance = 5.0
        val longitude = Angle.fromDeg(deg = 135.0)
        val latitude = Angle.fromDeg(deg = 60.0)

        val result = Vec3.fromSphericalCoordinates(distance, longitude, latitude)

        assertEquals(Vec3(x = -1.767766952966369, y = 1.7677669529663693, z = 4.330127018922193), result)
    }
}
