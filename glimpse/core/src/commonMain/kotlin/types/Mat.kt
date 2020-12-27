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
 * A common interface for square matrix implementations.
 */
interface Mat<T : Mat<T>> {

    /**
     * Returns element of this matrix at a given [row] and a given [column][col].
     */
    operator fun get(row: Int, col: Int): Float

    /**
     * Multiplies this matrix by the [other] matrix of the same size.
     */
    operator fun times(other: T): T

    /**
     * Multiplies this matrix by a given [number].
     */
    operator fun times(number: Float): T

    /**
     * Returns a transpose of this matrix.
     */
    fun transpose(): T

    /**
     * Returns a determinant of this matrix.
     */
    fun det(): Float

    /**
     * Returns an adjugate of this matrix.
     */
    fun adj(): T

    /**
     * Returns an inverse of this matrix.
     */
    fun inverse(): T

    /**
     * Returns an array of elements of this matrix.
     */
    fun toFloatArray(): FloatArray
}
