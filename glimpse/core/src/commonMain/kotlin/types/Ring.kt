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
 * Ring of numbers of type [T].
 */
interface Ring<T : Number> {

    /**
     * Additive identity in this ring.
     */
    val additiveIdentity: T

    /**
     * Multiplicative identity in this ring.
     */
    val multiplicativeIdentity: T

    /**
     * Returns sum of [left] and [right].
     */
    fun add(left: T, right: T): T

    /**
     * Returns sum of given [elements].
     */
    fun sum(elements: List<T>): T = elements.reduce(this::add)

    /**
     * Returns additive inverse of a given [number].
     */
    fun additiveInverse(number: T): T

    /**
     * Returns difference of [left] and [right].
     */
    fun subtract(left: T, right: T): T = add(left, additiveInverse(right))

    /**
     * Returns product of [left] and [right].
     */
    fun multiply(left: T, right: T): T

    /**
     * Returns product of given [elements].
     */
    fun product(elements: List<T>): T = elements.reduce(this::multiply)
}
