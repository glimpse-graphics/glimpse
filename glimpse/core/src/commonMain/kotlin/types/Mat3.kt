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

/**
 * A 3×3 matrix.
 */
data class Mat3(override val elements: List<Float>) : BaseMat<Mat3, Vec3>(MATRIX_DIMENSION) {

    private val comatrix: Mat3 by lazy {
        Mat3(transform { row, col -> cofactor(row, col) })
    }

    init { validate() }

    /**
     * Returns a new 3×3 matrix with the given [elements].
     */
    override fun create(elements: List<Float>): Mat3 = Mat3(elements)

    /**
     * Returns a new 3D vector with the given [elements].
     */
    override fun createVector(elements: List<Float>): Vec3 = Vec3.fromList(elements)

    /**
     * Returns a determinant of this matrix.
     */
    override fun det(): Float =
        indices.map { col -> this[0, col] * cofactor(row = 0, col) }.sum()

    private fun cofactor(row: Int, col: Int): Float =
        minor(row = row, col = col) * if ((row + col) % 2 == 0) 1f else -1f

    private fun minor(row: Int, col: Int): Float = Mat2(
        indices.flatMap { c -> indices.map { r -> r to c } }
            .filter { (r, c) -> r != row && c != col }
            .map { (r, c) -> this[r, c] }
    ).det()

    /**
     * Returns an adjugate of this matrix.
     */
    override fun adj(): Mat3 = comatrix.transpose()

    /**
     * Returns a 2×2 submatrix of this matrix, obtained by deleting the last row and the last column
     * of this 4×4 matrix.
     */
    fun toMat2(): Mat2 = Mat2(
        listOf(
            this[0, 0], this[1, 0],
            this[0, 1], this[1, 1]
        )
    )

    /**
     * Returns a string representation of this matrix.
     */
    override fun toString(): String = toString(className = "Mat3")

    companion object {
        private const val MATRIX_DIMENSION = 3

        /**
         * A 3×3 identity matrix.
         */
        val identity: Mat3 = Mat3(listOf(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f))
    }
}
