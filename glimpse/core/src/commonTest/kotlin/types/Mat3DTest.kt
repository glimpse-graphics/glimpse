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

class Mat3DTest {

    @Test
    fun `GIVEN two matrices, WHEN times, THEN return product of the matrices`() {
        val m1 = Mat3(
            listOf(
                1.0, 4.0, 7.0,
                2.0, 5.0, 8.0,
                3.0, 6.0, 9.0
            )
        )
        val m2 = Mat3(
            listOf(
                10.0, 13.0, 16.0,
                11.0, 14.0, 17.0,
                12.0, 15.0, 18.0
            )
        )

        val result = m1 * m2

        assertEquals(
            Mat3(
                listOf(
                    84.0, 201.0, 318.0,
                    90.0, 216.0, 342.0,
                    96.0, 231.0, 366.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix and a vector, WHEN times, THEN return product of the matrix and the vector`() {
        val matrix = Mat3(
            listOf(
                1.0, 4.0, 7.0,
                2.0, 5.0, 8.0,
                3.0, 6.0, 9.0
            )
        )
        val vector = Vec3(x = 10.0, y = 11.0, z = 12.0)

        val result = matrix * vector

        assertEquals(Vec3(x = 68.0, y = 167.0, z = 266.0), result)
    }

    @Test
    fun `GIVEN a matrix and a number, WHEN times, THEN return product of the matrix and the number`() {
        val matrix = Mat3(
            listOf(
                1.0, 4.0, 7.0,
                2.0, 5.0, 8.0,
                3.0, 6.0, 9.0
            )
        )
        val number = 2.0

        val result = matrix * number

        assertEquals(
            Mat3(
                listOf(
                    2.0, 8.0, 14.0,
                    4.0, 10.0, 16.0,
                    6.0, 12.0, 18.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN transpose, THEN return transposed matrix`() {
        val matrix = Mat3(
            listOf(
                1.0, 4.0, 7.0,
                2.0, 5.0, 8.0,
                3.0, 6.0, 9.0
            )
        )

        val result = matrix.transpose()

        assertEquals(
            Mat3(
                listOf(
                    1.0, 2.0, 3.0,
                    4.0, 5.0, 6.0,
                    7.0, 8.0, 9.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN det, THEN return determinant of the matrix`() {
        val matrix = Mat3(
            listOf(
                1.0, 6.0, 8.0,
                5.0, 3.0, 9.0,
                7.0, 4.0, 2.0
            )
        )

        val result = matrix.det()

        assertEquals(expected = 280.0, result)
    }

    @Test
    fun `GIVEN a matrix, WHEN adj, THEN return adjugate of the matrix`() {
        val matrix = Mat3(
            listOf(
                1.0, 4.0, 7.0,
                2.0, 5.0, 8.0,
                3.0, 6.0, 9.0
            )
        )

        val result = matrix.adj()

        assertEquals(
            Mat3(
                listOf(
                    -3.0, 6.0, -3.0,
                    6.0, -12.0, 6.0,
                    -3.0, 6.0, -3.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN inverse, THEN return inverse of th matrix`() {
        val matrix = Mat3(
            listOf(
                1.0, 6.0, 8.0,
                5.0, 3.0, 9.0,
                7.0, 4.0, 2.0
            )
        )

        val result = matrix.inverse()

        assertEqualsWithDelta(
            Mat3(
                listOf(
                    -0.1071428571, 0.07142857143, 0.1071428571,
                    0.1892857143, -0.1928571429, 0.1107142857,
                    -0.003571428571, 0.1357142857, -0.09642857143
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN toDoubleArray, THEN return matrix coordinates as array`() {
        val matrix = Mat3(
            listOf(
                1.0, 4.0, 7.0,
                2.0, 5.0, 8.0,
                3.0, 6.0, 9.0
            )
        )

        val result = matrix.toDoubleArray()

        assertEquals(listOf(1.0, 4.0, 7.0, 2.0, 5.0, 8.0, 3.0, 6.0, 9.0), result.toList())
    }

    @Test
    fun `GIVEN a matrix, WHEN toMat2, THEN return a 2x2 matrix from its augmented matrix`() {
        val matrix = Mat3(
            listOf(
                1.0, 2.0, 3.0,
                4.0, 5.0, 6.0,
                7.0, 8.0, 9.0
            )
        )

        val result = matrix.toMat2()

        assertEquals(
            Mat2(
                listOf(
                    1.0, 2.0,
                    4.0, 5.0
                )
            ),
            result
        )
    }
}
