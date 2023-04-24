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
 * 4D vector with coordinates ([x], [y], [z], [w]).
 *
 * Can also be used to specify RGBA color values.
 */
data class Vec4<T>(

    /**
     * X coordinate of this vector.
     */
    val x: T,

    /**
     * Y coordinate of this vector.
     */
    val y: T,

    /**
     * Z coordinate of this vector.
     */
    val z: T,

    /**
     * W coordinate of this vector.
     */
    val w: T,

    /**
     * Type of vector coordinates.
     *
     * @since v1.3.0
     */
    val type: KClass<T>
) : Vec<T> where T : Number, T : Comparable<T> {

    /**
     * Red channel of RGBA color.
     */
    val r: T get() = x

    /**
     * Green channel of RGBA color.
     */
    val g: T get() = y

    /**
     * Blue channel of RGBA color.
     */
    val b: T get() = z

    /**
     * Alpha channel of RGBA color.
     */
    val a: T get() = w

    /**
     * Returns a 2D vector with `x` and `y` coordinates of this vector.
     */
    fun toVec2(): Vec2<T> =
        Vec2(x = this.x, y = this.y, type = this.type)

    /**
     * Returns a 3D vector with `x`, `y` and `z` coordinates of this vector.
     */
    fun toVec3(): Vec3<T> =
        Vec3(x = this.x, y = this.y, z = this.z, type = this.type)

    /**
     * Returns a 4D integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    fun toIntVector(): Vec4<Int> =
        Vec4(
            x = this.x.toInt(),
            y = this.y.toInt(),
            z = this.z.toInt(),
            w = this.w.toInt(),
            type = Int::class
        )

    /**
     * Returns a 4D long integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    fun toLongVector(): Vec4<Long> =
        Vec4(
            x = this.x.toLong(),
            y = this.y.toLong(),
            z = this.z.toLong(),
            w = this.w.toLong(),
            type = Long::class
        )

    /**
     * Returns a 4D float vector equal to this vector.
     *
     * @since v1.3.0
     */
    fun toFloatVector(): Vec4<Float> =
        Vec4(
            x = this.x.toFloat(),
            y = this.y.toFloat(),
            z = this.z.toFloat(),
            w = this.w.toFloat(),
            type = Float::class
        )

    /**
     * Returns a 4D double-precision float vector equal to this vector.
     *
     * @since v1.3.0
     */
    fun toDoubleVector(): Vec4<Double> =
        Vec4(
            x = this.x.toDouble(),
            y = this.y.toDouble(),
            z = this.z.toDouble(),
            w = this.w.toDouble(),
            type = Double::class
        )

    /**
     * Returns a list of coordinates of this vector.
     */
    override fun toList(): List<T> = listOf(x, y, z, w)

    companion object {

        private const val SIZE = 4

        /**
         * Returns an instance of [Vec4] with the given [list] of coordinates of given [type].
         *
         * If the size of the list of coordinates is different from 4, [IllegalArgumentException]
         * is thrown.
         *
         * @since v1.3.0
         */
        fun <T> fromList(list: List<T>, type: KClass<T>): Vec4<T> where T : Number, T : Comparable<T> {
            require(list.size == SIZE)
            val (x, y, z, w) = list
            return Vec4(x, y, z, w, type)
        }

        /**
         * Returns an instance of [Vec4] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 4, [IllegalArgumentException]
         * is thrown.
         *
         * @since v1.3.0
         */
        @JvmName("fromIntList")
        fun fromList(list: List<Int>): Vec4<Int> {
            require(list.size == SIZE)
            val (x, y, z, w) = list
            return Vec4(x, y, z, w)
        }

        /**
         * Returns an instance of [Vec4] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 4, [IllegalArgumentException]
         * is thrown.
         *
         * @since v1.3.0
         */
        @JvmName("fromLongList")
        fun fromList(list: List<Long>): Vec4<Long> {
            require(list.size == SIZE)
            val (x, y, z, w) = list
            return Vec4(x, y, z, w)
        }

        /**
         * Returns an instance of [Vec4] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 4, [IllegalArgumentException]
         * is thrown.
         */
        @JvmName("fromFloatList")
        fun fromList(list: List<Float>): Vec4<Float> {
            require(list.size == SIZE)
            val (x, y, z, w) = list
            return Vec4(x, y, z, w)
        }

        /**
         * Returns an instance of [Vec4] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 4, [IllegalArgumentException]
         * is thrown.
         *
         * @since v1.3.0
         */
        @JvmName("fromDoubleList")
        fun fromList(list: List<Double>): Vec4<Double> {
            require(list.size == SIZE)
            val (x, y, z, w) = list
            return Vec4(x, y, z, w)
        }
    }
}

/**
 * Returns a new 4D vector with coordinates ([x], [y], [z], [w]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> Vec4(x: T, y: T, z: T, w: T): Vec4<T> where T : Number, T : Comparable<T> =
    Vec4(x = x, y = y, z = z, w = w, type = T::class)

/**
 * Returns an array of coordinates of this vector.
 */
fun Vec4<Int>.toIntArray(): IntArray = intArrayOf(x, y, z, w)

/**
 * Returns an array of coordinates of this vector.
 */
fun Vec4<Long>.toLongArray(): LongArray = longArrayOf(x, y, z, w)

/**
 * Returns an array of coordinates of this vector.
 */
fun Vec4<Float>.toFloatArray(): FloatArray = floatArrayOf(x, y, z, w)

/**
 * Returns an array of coordinates of this vector.
 *
 * @since v1.3.0
 */
fun Vec4<Double>.toDoubleArray(): DoubleArray = doubleArrayOf(x, y, z, w)
