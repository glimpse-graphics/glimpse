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
import kotlin.test.assertEquals

class Mat4FTest {

    @Test
    fun `GIVEN two matrices, WHEN times, THEN return product of the matrices`() {
        val m1 = Mat4(
            listOf(
                1f, 5f, 9f, 13f,
                2f, 6f, 10f, 14f,
                3f, 7f, 11f, 15f,
                4f, 8f, 12f, 16f
            )
        )
        val m2 = Mat4(
            listOf(
                17f, 21f, 25f, 29f,
                18f, 22f, 26f, 30f,
                19f, 23f, 27f, 31f,
                20f, 24f, 28f, 32f
            )
        )

        val result = m1 * m2

        assertEquals(
            Mat4(
                listOf(
                    250f, 618f, 986f, 1354f,
                    260f, 644f, 1028f, 1412f,
                    270f, 670f, 1070f, 1470f,
                    280f, 696f, 1112f, 1528f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix and a vector, WHEN times, THEN return product of the matrix and the vector`() {
        val matrix = Mat4(
            listOf(
                1f, 5f, 9f, 13f,
                2f, 6f, 10f, 14f,
                3f, 7f, 11f, 15f,
                4f, 8f, 12f, 16f
            )
        )
        val vector = Vec4(x = 17f, y = 18f, z = 19f, w = 20f)

        val result = matrix * vector

        assertEquals(Vec4(x = 190f, y = 486f, z = 782f, w = 1078f), result)
    }

    @Test
    fun `GIVEN a matrix and a number, WHEN times, THEN return product of the matrix and the number`() {
        val matrix = Mat4(
            listOf(
                1f, 5f, 9f, 13f,
                2f, 6f, 10f, 14f,
                3f, 7f, 11f, 15f,
                4f, 8f, 12f, 16f
            )
        )
        val number = 2f

        val result = matrix * number

        assertEquals(
            Mat4(
                listOf(
                    2f, 10f, 18f, 26f,
                    4f, 12f, 20f, 28f,
                    6f, 14f, 22f, 30f,
                    8f, 16f, 24f, 32f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN transpose, THEN return transposed matrix`() {
        val matrix = Mat4(
            listOf(
                1f, 5f, 9f, 13f,
                2f, 6f, 10f, 14f,
                3f, 7f, 11f, 15f,
                4f, 8f, 12f, 16f
            )
        )

        val result = matrix.transpose()

        assertEquals(
            Mat4(
                listOf(
                    1f, 2f, 3f, 4f,
                    5f, 6f, 7f, 8f,
                    9f, 10f, 11f, 12f,
                    13f, 14f, 15f, 16f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN det, THEN return determinant of the matrix`() {
        val matrix = Mat4(
            listOf(
                1f, 10f, 6f, 11f,
                5f, 2f, 9f, 15f,
                3f, 16f, 8f, 13f,
                7f, 4f, 12f, 14f
            )
        )

        val result = matrix.det()

        assertEquals(expected = -1038f, result)
    }

    @Test
    fun `GIVEN a matrix, WHEN adj, THEN return adjugate of the matrix`() {
        val matrix = Mat4(
            listOf(
                1f, 10f, 6f, 11f,
                5f, 2f, 9f, 15f,
                3f, 16f, 8f, 13f,
                7f, 4f, 12f, 14f
            )
        )

        val result = matrix.adj()

        assertEquals(
            Mat4(
                listOf(
                    764f, -288f, -480f, 154f,
                    79f, 30f, -123f, 20f,
                    -542f, 438f, 384f, -400f,
                    60f, -240f, -54f, 186f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN inverse, THEN return inverse of th matrix`() {
        val matrix = Mat4(
            listOf(
                1f, 10f, 6f, 11f,
                5f, 2f, 9f, 15f,
                3f, 16f, 8f, 13f,
                7f, 4f, 12f, 14f
            )
        )

        val result = matrix.inverse()

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    -0.7360308f, 0.27745664f, 0.46242774f, -0.14836223f,
                    -0.0761079f, -0.028901733f, 0.11849711f, -0.019267824f,
                    0.52215797f, -0.42196533f, -0.3699422f, 0.38535646f,
                    -0.057803467f, 0.23121387f, 0.05202312f, -0.17919075f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN toFloatArray, THEN return matrix coordinates as array`() {
        val matrix = Mat4(
            listOf(
                1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f
            )
        )

        val result = matrix.toFloatArray()

        assertEquals(
            listOf(
                1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f
            ),
            result.toList()
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN toMat2, THEN return a 2x2 matrix from its augmented matrix`() {
        val matrix = Mat4(
            listOf(
                1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f
            )
        )

        val result = matrix.toMat2()

        assertEquals(
            Mat2(
                listOf(
                    1f, 2f,
                    5f, 6f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a matrix, WHEN toMat3, THEN return a 3x3 matrix from its augmented matrix`() {
        val matrix = Mat4(
            listOf(
                1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f
            )
        )

        val result = matrix.toMat3()

        assertEquals(
            Mat3(
                listOf(
                    1f, 2f, 3f,
                    5f, 6f, 7f,
                    9f, 10f, 11f
                )
            ),
            result
        )
    }
}
