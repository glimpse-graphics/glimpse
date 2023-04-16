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
 * Field of double-precision floating point numbers.
 */
object DoubleField : Field<Double> {

    /**
     * Additive identity in this field.
     */
    override val additiveIdentity: Double = 0.0

    /**
     * Multiplicative identity in this field.
     */
    override val multiplicativeIdentity: Double = 1.0

    /**
     * Returns sum of [left] and [right].
     */
    override fun add(left: Double, right: Double): Double = left + right

    /**
     * Returns additive inverse of a given [number].
     */
    override fun additiveInverse(number: Double): Double = -number

    /**
     * Returns product of [left] and [right].
     */
    override fun multiply(left: Double, right: Double): Double = left * right

    /**
     * Returns multiplicative inverse of a given [number].
     */
    override fun multiplicativeInverse(number: Double): Double = multiplicativeIdentity / number
}
