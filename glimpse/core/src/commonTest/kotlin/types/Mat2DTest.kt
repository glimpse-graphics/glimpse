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

class Mat2DTest {

    @Test
    fun `GIVEN two matrices, WHEN times, THEN return product of the matrices`() {
        val m1 = Mat2(listOf(1.0, 3.0, 2.0, 4.0))
        val m2 = Mat2(listOf(5.0, 7.0, 6.0, 8.0))

        val result = m1 * m2

        assertEquals(Mat2(listOf(19.0, 43.0, 22.0, 50.0)), result)
    }

    @Test
    fun `GIVEN a matrix and a vector, WHEN times, THEN return product of the matrix and the vector`() {
        val matrix = Mat2(listOf(1.0, 3.0, 2.0, 4.0))
        val vector = Vec2(x = 5.0, y = 6.0)

        val result = matrix * vector

        assertEquals(Vec2(x = 17.0, y = 39.0), result)
    }

    @Test
    fun `GIVEN a matrix and a number, WHEN times, THEN return product of the matrix and the number`() {
        val matrix = Mat2(listOf(1.0, 3.0, 2.0, 4.0))
        val number = 3.0

        val result = matrix * number

        assertEquals(Mat2(listOf(3.0, 9.0, 6.0, 12.0)), result)
    }

    @Test
    fun `GIVEN a matrix, WHEN transpose, THEN return transposed matrix`() {
        val matrix = Mat2(listOf(1.0, 3.0, 2.0, 4.0))

        val result = matrix.transpose()

        assertEquals(Mat2(listOf(1.0, 2.0, 3.0, 4.0)), result)
    }

    @Test
    fun `GIVEN a matrix, WHEN det, THEN return determinant of the matrix`() {
        val matrix = Mat2(listOf(1.0, 3.0, 2.0, 4.0))

        val result = matrix.det()

        assertEquals(expected = -2.0, result)
    }

    @Test
    fun `GIVEN a matrix, WHEN adj, THEN return adjugate of the matrix`() {
        val matrix = Mat2(listOf(1.0, 3.0, 2.0, 4.0))

        val result = matrix.adj()

        assertEquals(Mat2(listOf(4.0, -3.0, -2.0, 1.0)), result)
    }

    @Test
    fun `GIVEN a matrix, WHEN inverse, THEN return inverse of th matrix`() {
        val matrix = Mat2(listOf(1.0, 3.0, 2.0, 4.0))

        val result = matrix.inverse()

        assertEquals(Mat2(listOf(-2.0, 1.5, 1.0, -0.5)), result)
    }

    @Test
    fun `GIVEN a matrix, WHEN toDoubleArray, THEN return matrix coordinates as array`() {
        val matrix = Mat2(listOf(1.0, 2.0, 3.0, 4.0))

        val result = matrix.toDoubleArray()

        assertEquals(listOf(1.0, 2.0, 3.0, 4.0), result.toList())
    }
}
