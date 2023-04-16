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
 * A 2×2 matrix.
 */
abstract class Mat2<T : Number> : BaseMat<T, Mat2<T>, Vec2<T>>(MATRIX_DIMENSION) {

    /**
     * Returns a determinant of this matrix.
     */
    @Suppress("MagicNumber")
    override fun det(): T =
        field.subtract(
            field.multiply(elements[0], elements[3]),
            field.multiply(elements[1], elements[2])
        )

    /**
     * Returns an adjugate of this matrix.
     */
    @Suppress("MagicNumber")
    override fun adj(): Mat2<T> =
        create(
            listOf(
                elements[3], field.additiveInverse(elements[1]),
                field.additiveInverse(elements[2]), elements[0]
            )
        )

    /**
     * Returns a 2×2 float matrix equal to this matrix.
     *
     * @since v1.3.0
     */
    abstract fun toFloatMatrix(): Mat2<Float>

    /**
     * Returns a 2×2 double-precision float matrix equal to this matrix.
     *
     * @since v1.3.0
     */
    abstract fun toDoubleMatrix(): Mat2<Double>

    companion object {
        private const val MATRIX_DIMENSION = 2

        /**
         * A 2×2 identity matrix.
         */
        val identity: Mat2<Float> = Mat2(listOf(1f, 0f, 0f, 1f))
    }
}

/**
 * Returns a new 2×2 float matrix.
 */
@Suppress("FunctionNaming")
@JvmName("FloatMat2")
fun Mat2(elements: List<Float>): Mat2<Float> = Mat2F(elements)

/**
 * Returns a new double-precision 2×2 float matrix.
 */
@Suppress("FunctionNaming")
@JvmName("DoubleMat2")
fun Mat2(elements: List<Double>): Mat2<Double> = Mat2D(elements)
