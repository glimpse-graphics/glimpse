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
 * Base implementation of a square matrix with a given [dimension].
 */
abstract class BaseMat<T : Mat<T>>(
    private val dimension: Int
) : Mat<T> {

    /**
     * Implement this property to provide elements of the matrix.
     */
    internal abstract val elements: List<Float>

    /**
     * Indices of a row or a column of the matrix.
     */
    protected val indices = 0 until dimension

    /**
     * Validates the size of the matrix.
     */
    protected fun validate() {
        require(value = elements.size == dimension * dimension) {
            "Invalid matrix size: ${elements.size}"
        }
    }

    /**
     * Returns element of this matrix at a given [row] and a given [column][col].
     */
    override operator fun get(row: Int, col: Int): Float {
        require(row in indices) { "Invalid row: $row" }
        require(col in indices) { "Invalid column: $col" }
        return elements[col * dimension + row]
    }

    /**
     * Multiplies this matrix by the [other] matrix of the same size.
     */
    override operator fun times(other: T): T = create(
        transform { row, col ->
            indices.map { this[row, it] * other[it, col] }.sum()
        }
    )

    /**
     * Implement this function to provide a way to create a new matrix of the same size
     * from a list of its [elements].
     */
    protected abstract fun create(elements: List<Float>): T

    /**
     * Transforms the matrix using a given [function].
     */
    protected fun transform(function: (row: Int, col: Int) -> Float): List<Float> =
        indices.flatMap { col ->
            indices.map { row ->
                function(row, col)
            }
        }

    /**
     * Multiplies this matrix by a given [number].
     */
    override operator fun times(number: Float): T = create(map { it * number })

    private fun map(function: (Float) -> Float): List<Float> = elements.map(function)

    /**
     * Returns a transpose of this matrix.
     */
    override fun transpose(): T = create(transform { row, col -> this[col, row] })

    /**
     * Returns an inverse of this matrix.
     */
    override fun inverse(): T = adj() * (1f / det())

    /**
     * Returns an array of elements of this matrix.
     */
    override fun toFloatArray(): FloatArray = elements.toFloatArray()

    /**
     * Returns a string representation of this matrix.
     */
    protected fun toString(className: String): String =
        indices.joinToString(
            prefix = "$className(data=[\n",
            separator = "\n",
            postfix = "\n])"
        ) { row ->
            indices.map { col -> this[row, col] }
                .joinToString(prefix = "    ", separator = " ")
        }
}
