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

import graphics.glimpse.testing.assertEqualsWithDelta
import kotlin.test.Test
import kotlin.test.assertEquals

class Mat3FTest {

    @Test
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
                    -0.10714286f, 0.071428575f, 0.10714286f,
                    0.18928571f, -0.19285715f, 0.11071429f,
                    -0.0035714286f, 0.13571429f, -0.09642857f
                )
            ),
            result
        )
    }

    @Test
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

    @Test
    fun `GIVEN a matrix, WHEN toMat2, THEN return a 2x2 matrix from its augmented matrix`() {
        val matrix = Mat3(
            listOf(
                1f, 2f, 3f,
                4f, 5f, 6f,
                7f, 8f, 9f
            )
        )

        val result = matrix.toMat2()

        assertEquals(
            Mat2(
                listOf(
                    1f, 2f,
                    4f, 5f
                )
            ),
            result
        )
    }
}
