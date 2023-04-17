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
 * A 2×2 matrix.
 */
data class Mat2<T>(

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

) : BaseMat<T, Mat2<T>, Vec2<T>>(
    dimension = MATRIX_DIMENSION
) where T : Number, T : Comparable<T> {

    init { validate() }

    /**
     * Returns a 2×2 matrix with given [elements].
     */
    override fun create(elements: List<T>): Mat2<T> =
        copy(elements = elements)

    /**
     * Returns a 2D vector with given [coordinates].
     */
    override fun createVector(coordinates: List<T>): Vec2<T> =
        Vec2.fromList(coordinates, type = this.type)

    /**
     * Returns a determinant of this matrix.
     */
    @Suppress("MagicNumber")
    override fun det(): T =
        elements[0] * elements[3] - elements[1] * elements[2]

    /**
     * Returns an adjugate of this matrix.
     */
    @Suppress("MagicNumber")
    override fun adj(): Mat2<T> =
        copy(elements = listOf(elements[3], -elements[1], -elements[2], elements[0]))

    /**
     * Returns a 2×2 float matrix equal to this matrix.
     *
     * @since v1.3.0
     */
    fun toFloatMatrix(): Mat2<Float> =
        Mat2(elements = this.elements.map { it.toFloat() }, type = Float::class)

    /**
     * Returns a 2×2 double-precision float matrix equal to this matrix.
     *
     * @since v1.3.0
     */
    fun toDoubleMatrix(): Mat2<Double> =
        Mat2(elements = this.elements.map { it.toDouble() }, type = Double::class)

    companion object {
        private const val MATRIX_DIMENSION = 2

        /**
         * A 2×2 identity matrix.
         */
        val identity: Mat2<Float> = Mat2(listOf(1f, 0f, 0f, 1f))
    }
}

/**
 * Returns a new 2×2 matrix from given [elements].
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> Mat2(elements: List<T>): Mat2<T> where T : Number, T : Comparable<T> =
    Mat2(elements = elements, type = T::class)
