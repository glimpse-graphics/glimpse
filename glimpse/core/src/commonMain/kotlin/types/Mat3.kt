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
abstract class Mat3<T : Number> : BaseMat<T, Mat3<T>, Vec3<T>>(MATRIX_DIMENSION) {

    private val comatrix: Mat3<T> by lazy {
        create(transform { row, col -> cofactor(row, col) })
    }

    /**
     * Returns a determinant of this matrix.
     */
    override fun det(): T =
        indices.map { col -> field.multiply(this[0, col], cofactor(row = 0, col)) }.let { field.sum(it) }

    private fun cofactor(row: Int, col: Int): T =
        field.multiply(
            minor(row = row, col = col),
            if ((row + col) % 2 == 0) field.multiplicativeIdentity
            else field.additiveInverse(field.multiplicativeIdentity)
        )

    private fun minor(row: Int, col: Int): T = submatrix(row, col).det()

    /**
     * Implement this method to provide a submatrix without given
     * [row][withoutRow] and [column][withoutCol].
     */
    protected abstract fun submatrix(withoutRow: Int, withoutCol: Int): Mat2<T>

    /**
     * Returns an adjugate of this matrix.
     */
    override fun adj(): Mat3<T> = comatrix.transpose()

    /**
     * Returns a 3×3 float matrix equal to this matrix.
     *
     * @since v1.3.0
     */
    abstract fun toFloatMatrix(): Mat3<Float>

    /**
     * Returns a 3×3 double-precision float matrix equal to this matrix.
     *
     * @since v1.3.0
     */
    abstract fun toDoubleMatrix(): Mat3<Double>

    /**
     * Returns a 2×2 submatrix of this matrix, obtained by deleting the last row and the last column
     * of this 4×4 matrix.
     *
     * @since v1.1.0
     */
    fun toMat2(): Mat2<T> = submatrix(
        withoutRow = indices.last,
        withoutCol = indices.last
    )

    companion object {
        private const val MATRIX_DIMENSION = 3

        /**
         * A 3×3 identity matrix.
         */
        val identity: Mat3<Float> = Mat3(listOf(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f))
    }
}

/**
 * Returns a new 3×3 float matrix.
 */
@Suppress("FunctionNaming")
@JvmName("FloatMat3")
fun Mat3(elements: List<Float>): Mat3<Float> = Mat3F(elements)

/**
 * Returns a double-precision new 3×3 float matrix.
 */
@Suppress("FunctionNaming")
@JvmName("DoubleMat3")
fun Mat3(elements: List<Double>): Mat3<Double> = Mat3D(elements)
