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
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Mat3Test {

    @Test
    @JsName(name = "GIVEN_two_matrices_WHEN_times_THEN_return_product_of_the_matrices")
    fun `GIVEN two matrices, WHEN times, THEN return product of the matrices`() {
        val m1 = Mat3(
            listOf(
                1f, 4f, 7f,
                2f, 5f, 8f,
                3f, 6f, 9f
            )
        )
        val m2 = Mat3(
            listOf(
                10f, 13f, 16f,
                11f, 14f, 17f,
                12f, 15f, 18f
            )
        )

        val result = m1 * m2

        assertEquals(
            Mat3(
                listOf(
                    84f, 201f, 318f,
                    90f, 216f, 342f,
                    96f, 231f, 366f
                )
            ),
            result
        )
    }

    @Test
    @JsName(name = "GIVEN_a_matrix_and_a_vector_WHEN_times_THEN_return_product_of_the_matrix_and_the_vector")
    fun `GIVEN a matrix and a vector, WHEN times, THEN return product of the matrix and the vector`() {
        val matrix = Mat3(
            listOf(
                1f, 4f, 7f,
                2f, 5f, 8f,
                3f, 6f, 9f
            )
        )
        val vector = Vec3(x = 10f, y = 11f, z = 12f)

        val result = matrix * vector

        assertEquals(Vec3(x = 68f, y = 167f, z = 266f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_matrix_and_a_number_WHEN_times_THEN_return_product_of_the_matrix_and_the_number")
    fun `GIVEN a matrix and a number, WHEN times, THEN return product of the matrix and the number`() {
        val matrix = Mat3(
            listOf(
                1f, 4f, 7f,
                2f, 5f, 8f,
                3f, 6f, 9f
            )
        )
        val number = 2f

        val result = matrix * number

        assertEquals(
            Mat3(
                listOf(
                    2f, 8f, 14f,
                    4f, 10f, 16f,
                    6f, 12f, 18f
                )
            ),
            result
        )
    }

    @Test
    @JsName(name = "GIVEN_a_matrix_WHEN_transpose_THEN_return_transposed_matrix")
    fun `GIVEN a matrix, WHEN transpose, THEN return transposed matrix`() {
        val matrix = Mat3(
            listOf(
                1f, 4f, 7f,
                2f, 5f, 8f,
                3f, 6f, 9f
            )
        )

        val result = matrix.transpose()

        assertEquals(
            Mat3(
                listOf(
                    1f, 2f, 3f,
                    4f, 5f, 6f,
                    7f, 8f, 9f
                )
            ),
            result
        )
    }

    @Test
    @JsName(name = "GIVEN_a_matrix_WHEN_det_THEN_return_determinant_of_the_matrix")
    fun `GIVEN a matrix, WHEN det, THEN return determinant of the matrix`() {
        val matrix = Mat3(
            listOf(
                1f, 6f, 8f,
                5f, 3f, 9f,
                7f, 4f, 2f
            )
        )

        val result = matrix.det()

        assertEquals(expected = 280f, result)
    }

    @Test
    @JsName(name = "GIVEN_a_matrix_WHEN_adj_THEN_return_adjugate_of_the_matrix")
    fun `GIVEN a matrix, WHEN adj, THEN return adjugate of the matrix`() {
        val matrix = Mat3(
            listOf(
                1f, 4f, 7f,
                2f, 5f, 8f,
                3f, 6f, 9f
            )
        )

        val result = matrix.adj()

        assertEquals(
            Mat3(
                listOf(
                    -3f, 6f, -3f,
                    6f, -12f, 6f,
                    -3f, 6f, -3f
                )
            ),
            result
        )
    }

    @Test
    @JsName(name = "GIVEN_a_matrix_WHEN_inverse_THEN_return_inverse_of_th_matrix")
    fun `GIVEN a matrix, WHEN inverse, THEN return inverse of th matrix`() {
        val matrix = Mat3(
            listOf(
                1f, 6f, 8f,
                5f, 3f, 9f,
                7f, 4f, 2f
            )
        )

        val result = matrix.inverse()

        assertEqualsWithDelta(
            Mat3(
                listOf(
                    -0.1071428571f, 0.07142857143f, 0.1071428571f,
                    0.1892857143f, -0.1928571429f, 0.1107142857f,
                    -0.003571428571f, 0.1357142857f, -0.09642857143f
                )
            ),
            result
        )
    }

    @Test
    @JsName(name = "GIVEN_a_matrix_WHEN_toFloatArray_THEN_return_matrix_coordinates_as_array")
    fun `GIVEN a matrix, WHEN toFloatArray, THEN return matrix coordinates as array`() {
        val matrix = Mat3(
            listOf(
                1f, 4f, 7f,
                2f, 5f, 8f,
                3f, 6f, 9f
            )
        )

        val result = matrix.toFloatArray()

        assertEquals(listOf(1f, 4f, 7f, 2f, 5f, 8f, 3f, 6f, 9f), result.toList())
    }
}
