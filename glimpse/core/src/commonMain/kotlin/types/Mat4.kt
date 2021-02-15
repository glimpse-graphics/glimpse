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

/**
 * A 4×4 matrix.
 */
data class Mat4(override val elements: List<Float>) : BaseMat<Mat4, Vec4>(MATRIX_DIMENSION) {

    private val comatrix: Mat4 by lazy {
        Mat4(transform { row, col -> cofactor(row, col) })
    }

    init { validate() }

    /**
     * Returns a new 4×4 matrix with the given [elements].
     */
    override fun create(elements: List<Float>): Mat4 = Mat4(elements)

    /**
     * Returns a new 4D vector with the given [elements].
     */
    override fun createVector(elements: List<Float>): Vec4 = Vec4.fromList(elements)

    /**
     * Returns a determinant of this matrix.
     */
    override fun det(): Float =
        indices.map { col -> this[0, col] * cofactor(row = 0, col) }.sum()

    private fun cofactor(row: Int, col: Int): Float =
        minor(row = row, col = col) * if ((row + col) % 2 == 0) 1f else -1f

    private fun minor(row: Int, col: Int): Float = submatrix(row, col).det()

    private fun submatrix(withoutRow: Int, withoutCol: Int) = Mat3(
            indices.flatMap { c -> indices.map { r -> r to c } }
                .filter { (r, c) -> r != withoutRow && c != withoutCol }
                .map { (r, c) -> this[r, c] }
        )

    /**
     * Returns an adjugate of this matrix.
     */
    override fun adj(): Mat4 = comatrix.transpose()

    /**
     * Returns a 3×3 submatrix of this matrix, obtained by deleting the last row and the last column
     * of this 4×4 matrix.
     */
    fun toMat3(): Mat3 = submatrix(
        withoutRow = indices.last,
        withoutCol = indices.last
    )

    /**
     * Returns a string representation of this matrix.
     */
    override fun toString(): String = toString(className = "Mat4")

    companion object {
        private const val MATRIX_DIMENSION = 4

        /**
         * A 4×4 identity matrix.
         */
        val identity: Mat4 = Mat4(
            listOf(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        )
    }
}
