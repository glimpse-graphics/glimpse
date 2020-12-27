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
 * A 2×2 matrix.
 */
data class Mat2(override val elements: List<Float>) : BaseMat<Mat2>(MATRIX_DIMENSION) {

    init { validate() }

    /**
     * Returns a new 2×2 matrix with the given [elements].
     */
    override fun create(elements: List<Float>): Mat2 = Mat2(elements)

    /**
     * Returns a determinant of this matrix.
     */
    override fun det(): Float =
        elements[0] * @Suppress("MagicNumber") elements[3] - elements[1] * elements[2]

    /**
     * Returns an adjugate of this matrix.
     */
    override fun adj(): Mat2 =
        @Suppress("MagicNumber")
        Mat2(listOf(elements[3], -elements[1], -elements[2], elements[0]))

    /**
     * Returns a string representation of this matrix.
     */
    override fun toString(): String = toString(className = "Mat2")

    companion object {
        private const val MATRIX_DIMENSION = 2

        /**
         * A 2×2 identity matrix.
         */
        val identity: Mat2 = Mat2(listOf(1f, 0f, 0f, 1f))
    }
}
