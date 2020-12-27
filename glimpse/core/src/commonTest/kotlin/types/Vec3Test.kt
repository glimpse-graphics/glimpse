/*
 * Copyright 2020 Slawomir Czerwinski
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
 *
 */

package graphics.glimpse.types

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class Vec3Test {

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_r_THEN_return_x")
    fun `GIVEN a vector, WHEN r, THEN return x`() {
        val vector = Vec3(x = 7f, y = 13f, z = 29f)

        val result = vector.r

        assertEquals(7f, result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_g_THEN_return_y")
    fun `GIVEN a vector, WHEN g, THEN return y`() {
        val vector = Vec3(x = 7f, y = 13f, z = 29f)

        val result = vector.g

        assertEquals(13f, result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_b_THEN_return_z")
    fun `GIVEN a vector, WHEN b, THEN return z`() {
        val vector = Vec3(x = 7f, y = 13f, z = 29f)

        val result = vector.b

        assertEquals(29f, result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_unaryPlus_THEN_return_the_same_vector")
    fun `GIVEN a vector, WHEN unaryPlus, THEN return the same vector`() {
        val vector = Vec3(x = 7f, y = 13f, z = 29f)

        val result = +vector

        assertSame(vector, result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_unaryMinus_THEN_return_the_opposite_vector")
    fun `GIVEN a vector, WHEN unaryMinus, THEN return the opposite vector`() {
        val vector = Vec3(x = 7f, y = 13f, z = 29f)

        val result = -vector

        assertEquals(Vec3(x = -7f, y = -13f, z = -29f), result)
    }

    @Test
    @JsName(name = "GIVEN_two_vectors_WHEN_plus_THEN_return_sum_of_two_vectors")
    fun `GIVEN two vectors, WHEN plus, THEN return sum of two vectors`() {
        val v1 = Vec3(x = 10f, y = 20f, z = 30f)
        val v2 = Vec3(x = 1f, y = 2f, z = 3f)

        val result = v1 + v2

        assertEquals(Vec3(x = 11f, y = 22f, z = 33f), result)
    }

    @Test
    @JsName(name = "GIVEN_two_vectors_WHEN_minus_THEN_return_difference_of_two_vectors")
    fun `GIVEN two vectors, WHEN minus, THEN return difference of two vectors`() {
        val v1 = Vec3(x = 10f, y = 20f, z = 30f)
        val v2 = Vec3(x = 1f, y = 2f, z = 3f)

        val result = v1 - v2

        assertEquals(Vec3(x = 9f, y = 18f, z = 27f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_and_a_number_WHEN_times_THEN_return_product_of_the_vector_and_the_number")
    fun `GIVEN a vector and a number, WHEN times, THEN return product of the vector and the number`() {
        val vector = Vec3(x = 10f, y = 20f, z = 30f)
        val number = 0.1f

        val result = vector * number

        assertEquals(Vec3(x = 1f, y = 2f, z = 3f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_and_a_number_WHEN_div_THEN_return_quotient_of_the_vector_and_the_number")
    fun `GIVEN a vector and a number, WHEN div, THEN return quotient of the vector and the number`() {
        val vector = Vec3(x = 10f, y = 20f, z = 30f)
        val number = 10f

        val result = vector / number

        assertEquals(Vec3(x = 1f, y = 2f, z = 3f), result)
    }

    @Test
    @JsName(name = "GIVEN_two_vectors_WHEN_dot_THEN_return_dot_product_of_two_vectors")
    fun `GIVEN two vectors, WHEN dot, THEN return dot product of two vectors`() {
        val v1 = Vec3(x = 10f, y = 20f, z = 30f)
        val v2 = Vec3(x = 1f, y = 2f, z = 3f)

        val result = v1 dot v2

        assertEquals(140f, result)
    }

    @Test
    @JsName(name = "GIVEN_two_vectors_WHEN_cross_THEN_return_cross_product_of_two_vectors")
    fun `GIVEN two vectors, WHEN cross, THEN return cross product of two vectors`() {
        val v1 = Vec3(x = 1f, y = 2f, z = 3f)
        val v2 = Vec3(x = 3f, y = 2f, z = 1f)

        val result = v1 cross v2

        assertEquals(Vec3(x = -4f, y = 8f, z = -4f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_toVec2_THEN_return_the_a_2D_vector")
    fun `GIVEN a vector, WHEN toVec2, THEN return the a 2D vector`() {
        val vector = Vec3(x = 1f, y = 2f, z = 3f)

        val result = vector.toVec2()

        assertEquals(Vec2(x = 1f, y = 2f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_toVec4_THEN_return_the_a_4D_vector_with_w_0")
    fun `GIVEN a vector, WHEN toVec4, THEN return the a 4D vector with w = 0`() {
        val vector = Vec3(x = 7f, y = 13f, z = 29f)

        val result = vector.toVec4()

        assertEquals(Vec4(x = 7f, y = 13f, z = 29f, w = 0f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_toVec4_with_w_THEN_return_the_a_4D_vector_with_w")
    fun `GIVEN a vector, WHEN toVec4 with w, THEN return the a 4D vector with w`() {
        val vector = Vec3(x = 7f, y = 13f, z = 29f)

        val result = vector.toVec4(w = 31f)

        assertEquals(Vec4(x = 7f, y = 13f, z = 29f, w = 31f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_toList_THEN_return_vectors_coordinates_as_list")
    fun `GIVEN a vector, WHEN toList, THEN return vector's coordinates as list`() {
        val vector = Vec3(x = 10f, y = 20f, z = 30f)

        val result = vector.toList()

        assertEquals(listOf(10f, 20f, 30f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_toFloatArray_THEN_return_vectors_coordinates_as_array")
    fun `GIVEN a vector, WHEN toFloatArray, THEN return vector's coordinates as array`() {
        val vector = Vec3(x = 10f, y = 20f, z = 30f)

        val result = vector.toFloatArray()

        assertEquals(listOf(10f, 20f, 30f), result.toList())
    }

    @Test
    @JsName(name = "GIVEN_a_list_WHEN_fromList_THEN_return_vector_with_coordinates_from_list")
    fun `GIVEN a list, WHEN fromList, THEN return vector with coordinates from list`() {
        val list = listOf(1f, 2f, 3f)

        val result = Vec3.fromList(list)

        assertEquals(Vec3(x = 1f, y = 2f, z = 3f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_list_of_2_numbers_WHEN_fromList_THEN_throw_exception")
    fun `GIVEN a list of 2 numbers, WHEN fromList, THEN throw exception`() {
        val list = listOf(1f, 2f)

        assertFailsWith(IllegalArgumentException::class) {
            Vec3.fromList(list)
        }
    }

    @Test
    @JsName(name = "GIVEN_spherical_coordinates_WHEN_fromSphericalCoordinates_THEN_return_vector_from_spherical_coordinates")
    fun `GIVEN spherical coordinates, WHEN fromSphericalCoordinates, THEN return vector from spherical coordinates`() {
        val distance = 5f
        val longitude = Angle.fromDeg(deg = 135f)
        val latitude = Angle.fromDeg(deg = 45f)

        val result = Vec3.fromSphericalCoordinates(distance, longitude, latitude)

        assertEquals(Vec3(x = -2.5f, y = 2.5f, z = 3.535533906f), result)
    }
}
