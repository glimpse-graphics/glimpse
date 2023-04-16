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
 * Field of floating point numbers.
 */
object FloatField : Field<Float> {

    /**
     * Additive identity in this field.
     */
    override val additiveIdentity: Float = 0f

    /**
     * Multiplicative identity in this field.
     */
    override val multiplicativeIdentity: Float = 1f

    /**
     * Returns sum of [left] and [right].
     */
    override fun add(left: Float, right: Float): Float = left + right

    /**
     * Returns additive inverse of a given [number].
     */
    override fun additiveInverse(number: Float): Float = -number

    /**
     * Returns product of [left] and [right].
     */
    override fun multiply(left: Float, right: Float): Float = left * right

    /**
     * Returns multiplicative inverse of a given [number].
     */
    override fun multiplicativeInverse(number: Float): Float = multiplicativeIdentity / number
}
