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

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class Vec2Test {

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_u_THEN_return_x")
    fun `GIVEN a vector, WHEN u, THEN return x`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.u

        assertEquals(7f, result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_v_THEN_return_y")
    fun `GIVEN a vector, WHEN v, THEN return y`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.v

        assertEquals(13f, result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_unaryPlus_THEN_return_the_same_vector")
    fun `GIVEN a vector, WHEN unaryPlus, THEN return the same vector`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = +vector

        assertSame(vector, result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_unaryMinus_THEN_return_the_opposite_vector")
    fun `GIVEN a vector, WHEN unaryMinus, THEN return the opposite vector`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = -vector

        assertEquals(Vec2(x = -7f, y = -13f), result)
    }

    @Test
    @JsName(name = "GIVEN_two_vectors_WHEN_plus_THEN_return_sum_of_two_vectors")
    fun `GIVEN two vectors, WHEN plus, THEN return sum of two vectors`() {
        val v1 = Vec2(x = 10f, y = 20f)
        val v2 = Vec2(x = 1f, y = 2f)

        val result = v1 + v2

        assertEquals(Vec2(x = 11f, y = 22f), result)
    }

    @Test
    @JsName(name = "GIVEN_two_vectors_WHEN_minus_THEN_return_difference_of_two_vectors")
    fun `GIVEN two vectors, WHEN minus, THEN return difference of two vectors`() {
        val v1 = Vec2(x = 10f, y = 20f)
        val v2 = Vec2(x = 1f, y = 2f)

        val result = v1 - v2

        assertEquals(Vec2(x = 9f, y = 18f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_and_a_number_WHEN_times_THEN_return_product_of_the_vector_and_the_number")
    fun `GIVEN a vector and a number, WHEN times, THEN return product of the vector and the number`() {
        val vector = Vec2(x = 1f, y = 2f)
        val number = 3f

        val result = vector * number

        assertEquals(Vec2(x = 3f, y = 6f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_and_a_number_WHEN_div_THEN_return_quotient_of_the_vector_and_the_number")
    fun `GIVEN a vector and a number, WHEN div, THEN return quotient of the vector and the number`() {
        val vector = Vec2(x = 5f, y = 7f)
        val number = 2f

        val result = vector / number

        assertEquals(Vec2(x = 2.5f, y = 3.5f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_toVec3_THEN_return_the_a_3D_vector_with_z_0")
    fun `GIVEN a vector, WHEN toVec3, THEN return the a 3D vector with z = 0`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.toVec3()

        assertEquals(Vec3(x = 7f, y = 13f, z = 0f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_toVec3_with_z_THEN_return_the_a_3D_vector_with_z")
    fun `GIVEN a vector, WHEN toVec3 with z, THEN return the a 3D vector with z`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.toVec3(z = 29f)

        assertEquals(Vec3(x = 7f, y = 13f, z = 29f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_toVec4_THEN_return_the_a_4D_vector_with_z_0_and_w_0")
    fun `GIVEN a vector, WHEN toVec4, THEN return the a 4D vector with z = 0 and w = 0`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.toVec4()

        assertEquals(Vec4(x = 7f, y = 13f, z = 0f, w = 0f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_toVec4_with_z_and_w_THEN_return_the_a_4D_vector_with_z_and_w")
    fun `GIVEN a vector, WHEN toVec4 with z and w, THEN return the a 4D vector with z and w`() {
        val vector = Vec2(x = 7f, y = 13f)

        val result = vector.toVec4(z = 29f, w = 31f)

        assertEquals(Vec4(x = 7f, y = 13f, z = 29f, w = 31f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_toList_THEN_return_vectors_coordinates_as_list")
    fun `GIVEN a vector, WHEN toList, THEN return vector's coordinates as list`() {
        val vector = Vec2(x = 10f, y = 20f)

        val result = vector.toList()

        assertEquals(listOf(10f, 20f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_toFloatArray_THEN_return_vectors_coordinates_as_array")
    fun `GIVEN a vector, WHEN toFloatArray, THEN return vector's coordinates as array`() {
        val vector = Vec2(x = 10f, y = 20f)

        val result = vector.toFloatArray()

        assertEquals(listOf(10f, 20f), result.toList())
    }

    @Test
    @JsName(name = "GIVEN_a_list_WHEN_fromList_THEN_return_vector_with_coordinates_from_list")
    fun `GIVEN a list, WHEN fromList, THEN return vector with coordinates from list`() {
        val list = listOf(1f, 2f)

        val result = Vec2.fromList(list)

        assertEquals(Vec2(x = 1f, y = 2f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_list_of_3_numbers_WHEN_fromList_THEN_throw_exception")
    fun `GIVEN a list of 3 numbers, WHEN fromList, THEN throw exception`() {
        val list = listOf(1f, 2f, 3f)

        assertFailsWith(IllegalArgumentException::class) {
            Vec2.fromList(list)
        }
    }
}
