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
import kotlin.test.assertEquals

class Mat4DTest {

    @Test
    fun `GIVEN two matrices, WHEN times, THEN return product of the matrices`() {
        val m1 = Mat4(
            listOf(
                1.0, 5.0, 9.0, 13.0,
                2.0, 6.0, 10.0, 14.0,
                3.0, 7.0, 11.0, 15.0,
                4.0, 8.0, 12.0, 16.0
            )
        )
        val m2 = Mat4(
            listOf(
                17.0, 21.0, 25.0, 29.0,
                18.0, 22.0, 26.0, 30.0,
                19.0, 23.0, 27.0, 31.0,
                20.0, 24.0, 28.0, 32.0
            )
        )

        val result = m1 * m2

        assertEquals(
            Mat4(
                listOf(
                    250.0, 618.0, 986.0, 1354.0,
                    260.0, 644.0, 1028.0, 1412.0,
                    270.0, 670.0, 1070.0, 1470.0,
                    280.0, 696.0, 1112.0, 1528.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix and a vector, WHEN times, THEN return product of the matrix and the vector`() {
        val matrix = Mat4(
            listOf(
                1.0, 5.0, 9.0, 13.0,
                2.0, 6.0, 10.0, 14.0,
                3.0, 7.0, 11.0, 15.0,
                4.0, 8.0, 12.0, 16.0
            )
        )
        val vector = Vec4(x = 17.0, y = 18.0, z = 19.0, w = 20.0)

        val result = matrix * vector

        assertEquals(Vec4(x = 190.0, y = 486.0, z = 782.0, w = 1078.0), result)
    }

    @Test
    fun `GIVEN a matrix and a number, WHEN times, THEN return product of the matrix and the number`() {
        val matrix = Mat4(
            listOf(
                1.0, 5.0, 9.0, 13.0,
                2.0, 6.0, 10.0, 14.0,
                3.0, 7.0, 11.0, 15.0,
                4.0, 8.0, 12.0, 16.0
            )
        )
        val number = 2.0

        val result = matrix * number

        assertEquals(
            Mat4(
                listOf(
                    2.0, 10.0, 18.0, 26.0,
                    4.0, 12.0, 20.0, 28.0,
                    6.0, 14.0, 22.0, 30.0,
                    8.0, 16.0, 24.0, 32.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN transpose, THEN return transposed matrix`() {
        val matrix = Mat4(
            listOf(
                1.0, 5.0, 9.0, 13.0,
                2.0, 6.0, 10.0, 14.0,
                3.0, 7.0, 11.0, 15.0,
                4.0, 8.0, 12.0, 16.0
            )
        )

        val result = matrix.transpose()

        assertEquals(
            Mat4(
                listOf(
                    1.0, 2.0, 3.0, 4.0,
                    5.0, 6.0, 7.0, 8.0,
                    9.0, 10.0, 11.0, 12.0,
                    13.0, 14.0, 15.0, 16.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN det, THEN return determinant of the matrix`() {
        val matrix = Mat4(
            listOf(
                1.0, 10.0, 6.0, 11.0,
                5.0, 2.0, 9.0, 15.0,
                3.0, 16.0, 8.0, 13.0,
                7.0, 4.0, 12.0, 14.0
            )
        )

        val result = matrix.det()

        assertEquals(expected = -1038.0, result)
    }

    @Test
    fun `GIVEN a matrix, WHEN adj, THEN return adjugate of the matrix`() {
        val matrix = Mat4(
            listOf(
                1.0, 10.0, 6.0, 11.0,
                5.0, 2.0, 9.0, 15.0,
                3.0, 16.0, 8.0, 13.0,
                7.0, 4.0, 12.0, 14.0
            )
        )

        val result = matrix.adj()

        assertEquals(
            Mat4(
                listOf(
                    764.0, -288.0, -480.0, 154.0,
                    79.0, 30.0, -123.0, 20.0,
                    -542.0, 438.0, 384.0, -400.0,
                    60.0, -240.0, -54.0, 186.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN inverse, THEN return inverse of th matrix`() {
        val matrix = Mat4(
            listOf(
                1.0, 10.0, 6.0, 11.0,
                5.0, 2.0, 9.0, 15.0,
                3.0, 16.0, 8.0, 13.0,
                7.0, 4.0, 12.0, 14.0
            )
        )

        val result = matrix.inverse()

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    -0.7360308285, 0.2774566474, 0.4624277457, -0.1483622351,
                    -0.07610789981, -0.0289017341, 0.1184971098, -0.01926782274,
                    0.5221579961, -0.4219653179, -0.3699421965, 0.3853564547,
                    -0.05780346821, 0.2312138728, 0.05202312139, -0.1791907514
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN toDoubleArray, THEN return matrix coordinates as array`() {
        val matrix = Mat4(
            listOf(
                1.0, 2.0, 3.0, 4.0,
                5.0, 6.0, 7.0, 8.0,
                9.0, 10.0, 11.0, 12.0,
                13.0, 14.0, 15.0, 16.0
            )
        )

        val result = matrix.toDoubleArray()

        assertEquals(
            listOf(
                1.0, 2.0, 3.0, 4.0,
                5.0, 6.0, 7.0, 8.0,
                9.0, 10.0, 11.0, 12.0,
                13.0, 14.0, 15.0, 16.0
            ),
            result.toList()
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN toMat2, THEN return a 2x2 matrix from its augmented matrix`() {
        val matrix = Mat4(
            listOf(
                1.0, 2.0, 3.0, 4.0,
                5.0, 6.0, 7.0, 8.0,
                9.0, 10.0, 11.0, 12.0,
                13.0, 14.0, 15.0, 16.0
            )
        )

        val result = matrix.toMat2()

        assertEquals(
            Mat2(
                listOf(
                    1.0, 2.0,
                    5.0, 6.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN toMat3, THEN return a 3x3 matrix from its augmented matrix`() {
        val matrix = Mat4(
            listOf(
                1.0, 2.0, 3.0, 4.0,
                5.0, 6.0, 7.0, 8.0,
                9.0, 10.0, 11.0, 12.0,
                13.0, 14.0, 15.0, 16.0
            )
        )

        val result = matrix.toMat3()

        assertEquals(
            Mat3(
                listOf(
                    1.0, 2.0, 3.0,
                    5.0, 6.0, 7.0,
                    9.0, 10.0, 11.0
                )
            ),
            result
        )
    }
}
