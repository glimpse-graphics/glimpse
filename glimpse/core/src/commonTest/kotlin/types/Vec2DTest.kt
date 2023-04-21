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

import graphics.glimpse.testing.assertEqualsWithDelta
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class Vec2DTest {

    @Test
    fun `GIVEN a vector, WHEN u, THEN return x`() {
        val vector = Vec2(x = 7.0, y = 13.0)

        val result = vector.u

        assertEqualsWithDelta(7.0, result)
    }

    @Test
    fun `GIVEN a vector, WHEN v, THEN return y`() {
        val vector = Vec2(x = 7.0, y = 13.0)

        val result = vector.v

        assertEqualsWithDelta(13.0, result)
    }

    @Test
    fun `GIVEN a vector, WHEN unaryPlus, THEN return the same vector`() {
        val vector = Vec2(x = 7.0, y = 13.0)

        val result = +vector

        assertSame(vector, result)
    }

    @Test
    fun `GIVEN a vector, WHEN unaryMinus, THEN return the opposite vector`() {
        val vector = Vec2(x = 7.0, y = 13.0)

        val result = -vector

        assertEqualsWithDelta(Vec2(x = -7.0, y = -13.0), result)
    }

    @Test
    fun `GIVEN two vectors, WHEN plus, THEN return sum of two vectors`() {
        val v1 = Vec2(x = 10.0, y = 20.0)
        val v2 = Vec2(x = 1.0, y = 2.0)

        val result = v1 + v2

        assertEqualsWithDelta(Vec2(x = 11.0, y = 22.0), result)
    }

    @Test
    fun `GIVEN two vectors, WHEN minus, THEN return difference of two vectors`() {
        val v1 = Vec2(x = 10.0, y = 20.0)
        val v2 = Vec2(x = 1.0, y = 2.0)

        val result = v1 - v2

        assertEqualsWithDelta(Vec2(x = 9.0, y = 18.0), result)
    }

    @Test
    fun `GIVEN a vector and a number, WHEN times, THEN return product of the vector and the number`() {
        val vector = Vec2(x = 1.0, y = 2.0)
        val number = 3.0

        val result = vector * number

        assertEqualsWithDelta(Vec2(x = 3.0, y = 6.0), result)
    }

    @Test
    fun `GIVEN a vector and a number, WHEN div, THEN return quotient of the vector and the number`() {
        val vector = Vec2(x = 5.0, y = 7.0)
        val number = 2.0

        val result = vector / number

        assertEqualsWithDelta(Vec2(x = 2.5, y = 3.5), result)
    }

    @Test
    fun `GIVEN two vectors, WHEN dot, THEN return dot product of two vectors`() {
        val v1 = Vec2(x = 10.0, y = 20.0)
        val v2 = Vec2(x = 1.0, y = 2.0)

        val result = v1 dot v2

        assertEqualsWithDelta(50.0, result)
    }

    @Test
    fun `GIVEN two vectors, WHEN cross, THEN return cross product of two vectors`() {
        val v1 = Vec2(x = 1.0, y = 2.0)
        val v2 = Vec2(x = 3.0, y = 2.0)

        val result = v1 cross v2

        assertEqualsWithDelta(Vec3(x = 0.0, y = 0.0, z = -4.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN atan, THEN return arc tangent of the vector`() {
        val vector = Vec2(x = 1.0, y = 1.0)

        val result = vector.atan()

        assertEqualsWithDelta(Angle.fromDeg(deg = 45.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN magnitude, THEN return vector's magnitude`() {
        val vector = Vec2(x = 3.0, y = 4.0)

        val result = vector.magnitude()

        assertEqualsWithDelta(5.0, result)
    }

    @Test
    fun `GIVEN a vector, WHEN normalize, THEN return a unit vector in the same direction`() {
        val vector = Vec2(x = 3.0, y = 4.0)

        val result = vector.normalize()

        assertEqualsWithDelta(Vec2(x = 0.6, y = 0.8), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec3, THEN return the a 3D vector with z = 0`() {
        val vector = Vec2(x = 7.0, y = 13.0)

        val result = vector.toVec3()

        assertEqualsWithDelta(Vec3(x = 7.0, y = 13.0, z = 0.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec3 with z, THEN return the a 3D vector with z`() {
        val vector = Vec2(x = 7.0, y = 13.0)

        val result = vector.toVec3(z = 29.0)

        assertEqualsWithDelta(Vec3(x = 7.0, y = 13.0, z = 29.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec4, THEN return the a 4D vector with z = 0 and w = 0`() {
        val vector = Vec2(x = 7.0, y = 13.0)

        val result = vector.toVec4()

        assertEqualsWithDelta(Vec4(x = 7.0, y = 13.0, z = 0.0, w = 0.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec4 with z and w, THEN return the a 4D vector with z and w`() {
        val vector = Vec2(x = 7.0, y = 13.0)

        val result = vector.toVec4(z = 29.0, w = 31.0)

        assertEqualsWithDelta(Vec4(x = 7.0, y = 13.0, z = 29.0, w = 31.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toList, THEN return vector's coordinates as list`() {
        val vector = Vec2(x = 10.0, y = 20.0)

        val result = vector.toList()

        assertEqualsWithDelta(listOf(10.0, 20.0), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toDoubleArray, THEN return vector's coordinates as array`() {
        val vector = Vec2(x = 10.0, y = 20.0)

        val result = vector.toDoubleArray()

        assertEqualsWithDelta(listOf(10.0, 20.0), result.toList())
    }

    @Test
    fun `GIVEN a list, WHEN fromList, THEN return vector with coordinates from list`() {
        val list = listOf(1.0, 2.0)

        val result = Vec2.fromList(list)

        assertEqualsWithDelta(Vec2(x = 1.0, y = 2.0), result)
    }

    @Test
    fun `GIVEN a list of 3 numbers, WHEN fromList, THEN throw exception`() {
        val list = listOf(1.0, 2.0, 3.0)

        assertFailsWith<IllegalArgumentException> {
            Vec2.fromList(list)
        }
    }

    @Test
    fun `GIVEN polar coordinates, WHEN fromPolarCoordinates, THEN return vector from polar coordinates`() {
        val distance = 5.0
        val angle = Angle.fromDeg(deg = 135.0)

        val result = Vec2.fromPolarCoordinates(distance, angle)

        assertEqualsWithDelta(Vec2(x = -3.5355339059327373, y = 3.5355339059327378), result)
    }
}
