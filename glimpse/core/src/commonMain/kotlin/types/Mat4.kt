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

import kotlin.reflect.KClass

/**
 * A 4×4 matrix.
 */
data class Mat4<T>(

    /**
     * Elements of this matrix.
     */
    override val elements: List<T>,

    /**
     * Type of matrix elements.
     *
     * @since v1.3.0
     */
    override val type: KClass<T>

) : BaseMat<T, Mat4<T>, Vec4<T>>(
    dimension = MATRIX_DIMENSION
) where T : Number, T : Comparable<T> {

    private val comatrix: Mat4<T> by lazy {
        create(transform { row, col -> cofactor(row, col) })
    }

    init { validate() }

    /**
     * Returns a 4×4 matrix with given [elements].
     */
    override fun create(elements: List<T>): Mat4<T> =
        copy(elements = elements)

    /**
     * Returns a 3D vector with given [coordinates].
     */
    override fun createVector(coordinates: List<T>): Vec4<T> =
        Vec4.fromList(coordinates, type = this.type)

    /**
     * Returns a determinant of this matrix.
     */
    override fun det(): T =
        indices.map { col -> this[0, col] * cofactor(row = 0, col) }.sum(this.type)

    private fun cofactor(row: Int, col: Int): T =
        minor(row = row, col = col) * (if ((row + col) % 2 == 0) one(this.type) else -one(this.type))

    private fun minor(row: Int, col: Int): T = submatrix(row, col).det()

    private fun submatrix(withoutRow: Int, withoutCol: Int): Mat3<T> =
        Mat3(
            elements = indices.flatMap { col -> indices.map { row -> row to col } }
                .filter { (row, col) -> row != withoutRow && col != withoutCol }
                .map { (row, col) -> this[row, col] },
            type = this.type
        )

    /**
     * Returns an adjugate of this matrix.
     */
    override fun adj(): Mat4<T> = comatrix.transpose()

    /**
     * Returns a 4×4 float matrix equal to this matrix.
     *
     * @since v1.3.0
     */
    fun toFloatMatrix(): Mat4<Float> =
        Mat4(elements = this.elements.map { it.toFloat() }, type = Float::class)

    /**
     * Returns a 4×4 double-precision float matrix equal to this matrix.
     *
     * @since v1.3.0
     */
    fun toDoubleMatrix(): Mat4<Double> =
        Mat4(elements = this.elements.map { it.toDouble() }, type = Double::class)

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
        @Deprecated(
            message = "Use Mat4.identity() instead.",
            replaceWith = ReplaceWith(expression = "Mat4.identity<Float>()")
        )
        val identity: Mat4<Float> = Mat4(
            listOf(
                1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        )

        /**
         * Returns a 4×4 identity matrix.
         *
         * @since v1.3.0
         */
        inline fun <reified T> identity(): Mat4<T> where T : Number, T : Comparable<T> =
            identity(T::class)

        /**
         * Returns a 4×4 identity matrix with elements of given [type].
         *
         * @since v1.3.0
         */
        fun <T> identity(type: KClass<T>): Mat4<T> where T : Number, T : Comparable<T> {
            val zero = zero(type)
            val one = one(type)
            return Mat4(
                elements = listOf(
                    one, zero, zero, zero,
                    zero, one, zero, zero,
                    zero, zero, one, zero,
                    zero, zero, zero, one
                ),
                type = type
            )
        }
    }
}

/**
 * Returns a new 4×4 matrix from given [elements].
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> Mat4(elements: List<T>): Mat4<T> where T : Number, T : Comparable<T> =
    Mat4(elements = elements, type = T::class)
