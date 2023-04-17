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
 * Computes the sine of the given [angle].
 */
fun <T> sin(angle: Angle<T>): T where T : Number, T : Comparable<T> =
    kotlin.math.sin(angle.rad.toDouble()).run {
        when (angle.rad) {
            is Byte -> toInt().toByte()
            is Short -> toInt().toShort()
            is Int -> toInt()
            is Long -> toLong()
            is Float -> toFloat()
            is Double -> toDouble()
            else -> throw UnsupportedOperationException("Sine not defined for type ${this::class.simpleName}")
        }
    } as T

/**
 * Computes the cosine of the given [angle].
 */
fun <T> cos(angle: Angle<T>): T where T : Number, T : Comparable<T> =
    kotlin.math.cos(angle.rad.toDouble()).run {
        when (angle.rad) {
            is Byte -> toInt().toByte()
            is Short -> toInt().toShort()
            is Int -> toInt()
            is Long -> toLong()
            is Float -> toFloat()
            is Double -> toDouble()
            else -> throw UnsupportedOperationException("Sine not defined for type ${this::class.simpleName}")
        }
    } as T

/**
 * Computes the tangent of the given [angle].
 */
fun <T> tan(angle: Angle<T>): T where T : Number, T : Comparable<T> =
    kotlin.math.tan(angle.rad.toDouble()).run {
        when (angle.rad) {
            is Byte -> toInt().toByte()
            is Short -> toInt().toShort()
            is Int -> toInt()
            is Long -> toLong()
            is Float -> toFloat()
            is Double -> toDouble()
            else -> throw UnsupportedOperationException("Sine not defined for type ${this::class.simpleName}")
        }
    } as T
