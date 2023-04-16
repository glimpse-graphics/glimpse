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

import kotlin.test.Test
import kotlin.test.assertEquals

class Mat2FTest {

    @Test
    fun `GIVEN two matrices, WHEN times, THEN return product of the matrices`() {
        val m1 = Mat2(listOf(1f, 3f, 2f, 4f))
        val m2 = Mat2(listOf(5f, 7f, 6f, 8f))

        val result = m1 * m2

        assertEquals(Mat2(listOf(19f, 43f, 22f, 50f)), result)
    }

    @Test
    fun `GIVEN a matrix and a vector, WHEN times, THEN return product of the matrix and the vector`() {
        val matrix = Mat2(listOf(1f, 3f, 2f, 4f))
        val vector = Vec2(x = 5f, y = 6f)

        val result = matrix * vector

        assertEquals(Vec2(x = 17f, y = 39f), result)
    }

    @Test
    fun `GIVEN a matrix and a number, WHEN times, THEN return product of the matrix and the number`() {
        val matrix = Mat2(listOf(1f, 3f, 2f, 4f))
        val number = 3f

        val result = matrix * number

        assertEquals(Mat2(listOf(3f, 9f, 6f, 12f)), result)
    }

    @Test
    fun `GIVEN a matrix, WHEN transpose, THEN return transposed matrix`() {
        val matrix = Mat2(listOf(1f, 3f, 2f, 4f))

        val result = matrix.transpose()

        assertEquals(Mat2(listOf(1f, 2f, 3f, 4f)), result)
    }

    @Test
    fun `GIVEN a matrix, WHEN det, THEN return determinant of the matrix`() {
        val matrix = Mat2(listOf(1f, 3f, 2f, 4f))

        val result = matrix.det()

        assertEquals(expected = -2f, result)
    }

    @Test
    fun `GIVEN a matrix, WHEN adj, THEN return adjugate of the matrix`() {
        val matrix = Mat2(listOf(1f, 3f, 2f, 4f))

        val result = matrix.adj()

        assertEquals(Mat2(listOf(4f, -3f, -2f, 1f)), result)
    }

    @Test
    fun `GIVEN a matrix, WHEN inverse, THEN return inverse of th matrix`() {
        val matrix = Mat2(listOf(1f, 3f, 2f, 4f))

        val result = matrix.inverse()

        assertEquals(Mat2(listOf(-2f, 1.5f, 1f, -0.5f)), result)
    }

    @Test
    fun `GIVEN a matrix, WHEN toFloatArray, THEN return matrix coordinates as array`() {
        val matrix = Mat2(listOf(1f, 2f, 3f, 4f))

        val result = matrix.toFloatArray()

        assertEquals(listOf(1f, 2f, 3f, 4f), result.toList())
    }
}
