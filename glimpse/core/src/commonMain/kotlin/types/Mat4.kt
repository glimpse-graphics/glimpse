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
 * A 4×4 matrix.
 */
abstract class Mat4<T : Number> : BaseMat<T, Mat4<T>, Vec4<T>>(MATRIX_DIMENSION) {

    private val comatrix: Mat4<T> by lazy {
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
    protected abstract fun submatrix(withoutRow: Int, withoutCol: Int): Mat3<T>

    /**
     * Returns an adjugate of this matrix.
     */
    override fun adj(): Mat4<T> = comatrix.transpose()

    /**
     * Returns a 4×4 float matrix equal to this matrix.
     *
     * @since v1.3.0
     */
    abstract fun toFloatMatrix(): Mat4<Float>

    /**
     * Returns a 4×4 double-precision float matrix equal to this matrix.
     *
     * @since v1.3.0
     */
    abstract fun toDoubleMatrix(): Mat4<Double>

    /**
     * Returns a 2×2 submatrix of this matrix, obtained by deleting the last two rows and the last two columns
     * of this 4×4 matrix.
     *
     * @since v1.1.0
     */
    fun toMat2(): Mat2<T> = toMat3().toMat2()

    /**
     * Returns a 3×3 submatrix of this matrix, obtained by deleting the last row and the last column
     * of this 4×4 matrix.
     */
    fun toMat3(): Mat3<T> = submatrix(
        withoutRow = indices.last,
        withoutCol = indices.last
    )

    companion object {
        private const val MATRIX_DIMENSION = 4

        /**
         * A 4×4 identity matrix.
         */
        val identity: Mat4<Float> = Mat4(
            listOf(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        )
    }
}

/**
 * Returns a new 4×4 float matrix.
 */
@Suppress("FunctionNaming")
@JvmName("FloatMat4")
fun Mat4(elements: List<Float>): Mat4<Float> = Mat4F(elements)

/**
 * Returns a double-precision new 4×4 float matrix.
 */
@Suppress("FunctionNaming")
@JvmName("DoubleMat4")
fun Mat4(elements: List<Double>): Mat4<Double> = Mat4D(elements)
