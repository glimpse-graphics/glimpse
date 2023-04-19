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
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class Vec2FTest {

    @Test
    fun `GIVEN a vector, WHEN u, THEN return x`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.u

        assertEqualsWithDelta(7f, result)
    }

    @Test
    fun `GIVEN a vector, WHEN v, THEN return y`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.v

        assertEqualsWithDelta(13f, result)
    }

    @Test
    fun `GIVEN a vector, WHEN unaryPlus, THEN return the same vector`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = +vector

        assertSame(vector, result)
    }

    @Test
    fun `GIVEN a vector, WHEN unaryMinus, THEN return the opposite vector`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = -vector

        assertEqualsWithDelta(Vec2(x = -7f, y = -13f), result)
    }

    @Test
    fun `GIVEN two vectors, WHEN plus, THEN return sum of two vectors`() {
        val v1 = Vec2(x = 10f, y = 20f)
        val v2 = Vec2(x = 1f, y = 2f)

        val result = v1 + v2

        assertEqualsWithDelta(Vec2(x = 11f, y = 22f), result)
    }

    @Test
    fun `GIVEN two vectors, WHEN minus, THEN return difference of two vectors`() {
        val v1 = Vec2(x = 10f, y = 20f)
        val v2 = Vec2(x = 1f, y = 2f)

        val result = v1 - v2

        assertEqualsWithDelta(Vec2(x = 9f, y = 18f), result)
    }

    @Test
    fun `GIVEN a vector and a number, WHEN times, THEN return product of the vector and the number`() {
        val vector = Vec2(x = 1f, y = 2f)
        val number = 3f

        val result = vector * number

        assertEqualsWithDelta(Vec2(x = 3f, y = 6f), result)
    }

    @Test
    fun `GIVEN a vector and a number, WHEN div, THEN return quotient of the vector and the number`() {
        val vector = Vec2(x = 5f, y = 7f)
        val number = 2f

        val result = vector / number

        assertEqualsWithDelta(Vec2(x = 2.5f, y = 3.5f), result)
    }

    @Test
    fun `GIVEN two vectors, WHEN dot, THEN return dot product of two vectors`() {
        val v1 = Vec2(x = 10f, y = 20f)
        val v2 = Vec2(x = 1f, y = 2f)

        val result = v1 dot v2

        assertEqualsWithDelta(50f, result)
    }

    @Test
    fun `GIVEN two vectors, WHEN cross, THEN return cross product of two vectors`() {
        val v1 = Vec2(x = 1f, y = 2f)
        val v2 = Vec2(x = 3f, y = 2f)

        val result = v1 cross v2

        assertEqualsWithDelta(Vec3(x = 0f, y = 0f, z = -4f), result)
    }

    @Test
    fun `GIVEN a vector, WHEN atan, THEN return arc tangent of the vector`() {
        val vector = Vec2(x = 1f, y = 1f)

        val result = vector.atan()

        assertEqualsWithDelta(Angle.fromDeg(deg = 45f), result)
    }

    @Test
    fun `GIVEN a vector, WHEN magnitude, THEN return vector's magnitude`() {
        val vector = Vec2(x = 3f, y = 4f)

        val result = vector.magnitude()

        assertEqualsWithDelta(5f, result)
    }

    @Test
    fun `GIVEN a vector, WHEN normalize, THEN return a unit vector in the same direction`() {
        val vector = Vec2(x = 3f, y = 4f)

        val result = vector.normalize()

        assertEqualsWithDelta(Vec2(x = 0.6f, y = 0.8f), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec3, THEN return the a 3D vector with z = 0`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.toVec3()

        assertEqualsWithDelta(Vec3(x = 7f, y = 13f, z = 0f), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec3 with z, THEN return the a 3D vector with z`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.toVec3(z = 29f)

        assertEqualsWithDelta(Vec3(x = 7f, y = 13f, z = 29f), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec4, THEN return the a 4D vector with z = 0 and w = 0`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.toVec4()

        assertEqualsWithDelta(Vec4(x = 7f, y = 13f, z = 0f, w = 0f), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toVec4 with z and w, THEN return the a 4D vector with z and w`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.toVec4(z = 29f, w = 31f)

        assertEqualsWithDelta(Vec4(x = 7f, y = 13f, z = 29f, w = 31f), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toList, THEN return vector's coordinates as list`() {
        val vector = Vec2(x = 10f, y = 20f)

        val result = vector.toList()

        assertEqualsWithDelta(listOf(10f, 20f), result)
    }

    @Test
    fun `GIVEN a vector, WHEN toFloatArray, THEN return vector's coordinates as array`() {
        val vector = Vec2(x = 10f, y = 20f)

        val result = vector.toFloatArray()

        assertEqualsWithDelta(listOf(10f, 20f), result.toList())
    }

    @Test
    fun `GIVEN a list, WHEN fromList, THEN return vector with coordinates from list`() {
        val list = listOf(1f, 2f)

        val result = Vec2.fromList(list)

        assertEqualsWithDelta(Vec2(x = 1f, y = 2f), result)
    }

    @Test
    fun `GIVEN a list of 3 numbers, WHEN fromList, THEN throw exception`() {
        val list = listOf(1f, 2f, 3f)

        assertFailsWith<IllegalArgumentException> {
            Vec2.fromList(list)
        }
    }

    @Test
    fun `GIVEN polar coordinates, WHEN fromPolarCoordinates, THEN return vector from polar coordinates`() {
        val distance = 5f
        val angle = Angle.fromDeg(deg = 135f)

        val result = Vec2.fromPolarCoordinates(distance, angle)

        assertEqualsWithDelta(Vec2(x = -3.535534f, y = 3.535534f), result)
    }
}
