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
 * Base implementation of a square matrix with a given [dimension].
 */
abstract class BaseMat<T : Number, M : Mat<T, M, V>, V : Vec<T>>(
    private val dimension: Int
) : Mat<T, M, V> {

    /**
     * Implement this property to provide elements of the matrix.
     */
    internal abstract val elements: List<T>

    /**
     * Indices of a row or a column of the matrix.
     */
    protected val indices = 0 until dimension

    /**
     * Implement this property to provide a field of numbers of type [T].
     *
     * @since v1.3.0
     */
   protected abstract val field: Field<T>

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
    override operator fun get(row: Int, col: Int): T {
        require(row in indices) { "Invalid row: $row" }
        require(col in indices) { "Invalid column: $col" }
        return elements[col * dimension + row]
    }

    /**
     * Multiplies this matrix by the [other] matrix of the same size.
     */
    override operator fun times(other: M): M = create(
        transform { row, col ->
            indices.map { field.multiply(this[row, it], other[it, col]) }.let { field.sum(it) }
        }
    )

    /**
     * Implement this function to provide a way to create a new matrix of the same size
     * from a list of its [elements].
     */
    protected abstract fun create(elements: List<T>): M

    /**
     * Transforms the matrix using a given [function].
     */
    protected fun transform(function: (row: Int, col: Int) -> T): List<T> =
        indices.flatMap { col ->
            indices.map { row ->
                function(row, col)
            }
        }

    /**
     * Multiplies this matrix by a given [vector].
     */
    override fun times(vector: V): V {
        val vectorValues = vector.toList()
        return createVector(
            indices.map { row ->
                indices.map { col ->
                    field.multiply(vectorValues[col], get(row, col))
                }.let { field.sum((it)) }
            }
        )
    }

    /**
     * Implement this function to provide a way to create a new vector of the same size
     * from a list of its [elements].
     */
    protected abstract fun createVector(elements: List<T>): V

    /**
     * Multiplies this matrix by a given [number].
     */
    override operator fun times(number: T): M = create(map { field.multiply(it, number) })

    /**
     * Transforms each element of this matrix using given [function].
     */
    protected fun map(function: (T) -> T): List<T> = elements.map(function)

    /**
     * Returns a transpose of this matrix.
     */
    override fun transpose(): M = create(transform { row, col -> this[col, row] })

    /**
     * Returns an inverse of this matrix.
     */
    override fun inverse(): M = adj() * (field.multiplicativeInverse(det()))

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

/**
 * Returns an array of elements of this matrix.
 */
fun <M : Mat<Float, M, V>, V : Vec<Float>> BaseMat<Float, M, V>.toFloatArray() = elements.toFloatArray()

/**
 * Returns an array of elements of this matrix.
 */
fun <M : Mat<Double, M, V>, V : Vec<Double>> BaseMat<Double, M, V>.toDoubleArray() = elements.toDoubleArray()
