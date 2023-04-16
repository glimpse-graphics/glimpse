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
 * 4D vector with coordinates ([x], [y], [z], [w]).
 *
 * Can also be used to specify RGBA color values.
 */
abstract class Vec4<T : Number> : BaseVec<T>() {

    /**
     * X coordinate of this vector.
     */
    abstract val x: T

    /**
     * Y coordinate of this vector.
     */
    abstract val y: T

    /**
     * Z coordinate of this vector.
     */
    abstract val z: T

    /**
     * W coordinate of this vector.
     */
    abstract val w: T

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
     * First component of this vector.
     */
    abstract operator fun component1(): T

    /**
     * Second component of this vector.
     */
    abstract operator fun component2(): T

    /**
     * Third component of this vector.
     */
    abstract operator fun component3(): T

    /**
     * Fourth component of this vector.
     */
    abstract operator fun component4(): T

    /**
     * Returns a 2D vector with `x` and `y` coordinates of this vector.
     */
    abstract fun toVec2(): Vec2<T>

    /**
     * Returns a 3D vector with `x`, `y` and `z` coordinates of this vector.
     */
    abstract fun toVec3(): Vec3<T>

    /**
     * Returns a 4D integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    abstract fun toIntVector(): Vec4<Int>

    /**
     * Returns a 4D long integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    abstract fun toLongVector(): Vec4<Long>

    /**
     * Returns a 4D float vector equal to this vector.
     *
     * @since v1.3.0
     */
    abstract fun toFloatVector(): Vec4<Float>

    /**
     * Returns a 4D double-precision float vector equal to this vector.
     *
     * @since v1.3.0
     */
    abstract fun toDoubleVector(): Vec4<Double>

    companion object {

        private const val SIZE = 4

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
 * Returns a new 4D integer vector with coordinates ([x], [y], [z], [w]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
fun Vec4(x: Int, y: Int, z: Int, w: Int): Vec4<Int> = Vec4I(x, y, z, w)

/**
 * Returns a new 4D long integer vector with coordinates ([x], [y], [z], [w]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
fun Vec4(x: Long, y: Long, z: Long, w: Long): Vec4<Long> = Vec4L(x, y, z, w)

/**
 * Returns a new 4D float vector with coordinates ([x], [y], [z], [w]).
 */
@Suppress("FunctionNaming")
fun Vec4(x: Float, y: Float, z: Float, w: Float): Vec4<Float> = Vec4F(x, y, z, w)

/**
 * Returns a new 4D double-precision float vector with coordinates ([x], [y], [z], [w]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
fun Vec4(x: Double, y: Double, z: Double, w: Double): Vec4<Double> = Vec4D(x, y, z, w)

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
