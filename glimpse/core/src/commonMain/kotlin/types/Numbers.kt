/*
 * Copyright 2020-2023 Glimpse Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
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
 * Returns value of zero for type [T].
 *
 * @since v1.3.0
 */
inline fun <reified T : Number> zero(): T = zero(T::class)

/**
 * Returns value of zero for given [type].
 *
 * @since v1.3.0
 */
fun <T : Number> zero(type: KClass<T>): T =
    when (type) {
        Byte::class -> 0.toByte()
        Short::class -> 0.toShort()
        Int::class -> 0
        Long::class -> 0L
        Float::class -> 0f
        Double::class -> 0.0
        else -> throw UnsupportedOperationException("Zero not defined for type ${type.simpleName}")
    } as T

/**
 * Returns value of one for type [T].
 *
 * @since v1.3.0
 */
inline fun <reified T : Number> one(): T = one(T::class)

/**
 * Returns value of one for given [type].
 *
 * @since v1.3.0
 */
fun <T : Number> one(type: KClass<T>): T =
    when (type) {
        Byte::class -> 1.toByte()
        Short::class -> 1.toShort()
        Int::class -> 1
        Long::class -> 1L
        Float::class -> 1f
        Double::class -> 1.0
        else -> throw UnsupportedOperationException("Zero not defined for type ${type.simpleName}")
    } as T

/**
 * Returns this number.
 *
 * @since v1.3.0
 */
operator fun <T : Number> T.unaryPlus(): T = this

/**
 * Returns a number opposite to this number.
 *
 * @since v1.3.0
 */
operator fun <T : Number> T.unaryMinus(): T =
    when (this) {
        is Byte -> -this
        is Short -> -this
        is Int -> -this
        is Long -> -this
        is Float -> -this
        is Double -> -this
        else -> throw UnsupportedOperationException("Negative not defined for type ${this::class.simpleName}")
    } as T

/**
 * Returns sum of this number and the [other] number.
 *
 * @since v1.3.0
 */
operator fun <T : Number> T.plus(other: Number): T =
    when (this) {
        is Byte -> (this + other.toByte()).toByte()
        is Short -> (this + other.toShort()).toShort()
        is Int -> (this + other.toInt())
        is Long -> (this + other.toLong())
        is Float -> (this + other.toFloat())
        is Double -> (this + other.toDouble())
        else -> throw UnsupportedOperationException("Addition not defined for type ${this::class.simpleName}")
    } as T

/**
 * Returns difference of this number and the [other] number.
 *
 * @since v1.3.0
 */
operator fun <T : Number> T.minus(other: Number): T =
    when (this) {
        is Byte -> (this - other.toByte()).toByte()
        is Short -> (this - other.toShort()).toShort()
        is Int -> (this - other.toInt())
        is Long -> (this - other.toLong())
        is Float -> (this - other.toFloat())
        is Double -> (this - other.toDouble())
        else -> throw UnsupportedOperationException("Subtraction not defined for type ${this::class.simpleName}")
    } as T

/**
 * Returns product of this number and the [other] number.
 *
 * @since v1.3.0
 */
operator fun <T : Number> T.times(other: Number): T =
    when (this) {
        is Byte -> (this * other.toByte()).toByte()
        is Short -> (this * other.toShort()).toShort()
        is Int -> (this * other.toInt())
        is Long -> (this * other.toLong())
        is Float -> (this * other.toFloat())
        is Double -> (this * other.toDouble())
        else -> throw UnsupportedOperationException("Multiplication not defined for type ${this::class.simpleName}")
    } as T

/**
 * Returns quotient of this number and the [other] number.
 *
 * @since v1.3.0
 */
operator fun <T : Number> T.div(other: Number): T =
    when (this) {
        is Byte -> (this / other.toByte()).toByte()
        is Short -> (this / other.toShort()).toShort()
        is Int -> (this / other.toInt())
        is Long -> (this / other.toLong())
        is Float -> (this / other.toFloat())
        is Double -> (this / other.toDouble())
        else -> throw UnsupportedOperationException("Division not defined for type ${this::class.simpleName}")
    } as T

/**
 * Returns remainder of dividing this number by the [other] number.
 *
 * @since v1.3.0
 */
operator fun <T : Number> T.rem(other: Number): T =
    when (this) {
        is Byte -> (this % other.toByte()).toByte()
        is Short -> (this % other.toShort()).toShort()
        is Int -> (this % other.toInt())
        is Long -> (this % other.toLong())
        is Float -> (this % other.toFloat())
        is Double -> (this % other.toDouble())
        else -> throw UnsupportedOperationException("Remainder not defined for type ${this::class.simpleName}")
    } as T

/**
 * Returns square root of number [x].
 *
 * @since v1.3.0
 */
fun <T : Number> sqrt(x: T): T =
    when (x) {
        is Float -> kotlin.math.sqrt(x)
        is Double -> kotlin.math.sqrt(x)
        else -> throw UnsupportedOperationException("Square root not defined for type ${x::class.simpleName}")
    } as T

/**
 * Returns tangent of number [x].
 *
 * @since v1.3.0
 */
fun <T : Number> atan(x: T): T =
    when (x) {
        is Float -> kotlin.math.atan(x)
        is Double -> kotlin.math.atan(x)
        else -> throw UnsupportedOperationException("Arc tan not defined for type ${x::class.simpleName}")
    } as T

/**
 * Returns tangent of number ([y]/[x]).
 *
 * @since v1.3.0
 */
fun <T : Number> atan2(y: T, x: T): T =
    when (x) {
        is Float -> kotlin.math.atan2(y.toFloat(), x)
        is Double -> kotlin.math.atan2(y.toDouble(), x)
        else -> throw UnsupportedOperationException("Arc tan not defined for type ${x::class.simpleName}")
    } as T

/**
 * Returns sum of the numbers in this list.
 *
 * @since v1.3.0
 */
inline fun <reified T : Number> List<T>.sum(): T = sum(T::class)

/**
 * Returns sum of the numbers in this list.
 *
 * @since v1.3.0
 */
fun <T : Number> List<T>.sum(type: KClass<T>): T {
    var sum: T = zero(type)
    for (element in this) {
        sum += element
    }
    return sum
}

/**
 * Returns lesser of the given values [a] and [b].
 *
 * @since v1.3.0
 */
fun <T> min(a: T, b: T): T where T : Number, T : Comparable<T> =
    if (a <= b) a else b

/**
 * Returns larger of the given values [a] and [b].
 *
 * @since v1.3.0
 */
fun <T> max(a: T, b: T): T where T : Number, T : Comparable<T> =
    if (a >= b) a else b
