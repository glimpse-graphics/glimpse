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
 * 2D vector with coordinates ([x], [y]).
 *
 * Can also be used to specify texture coordinates in UV mapping.
 */
data class Vec2<T>(

    /**
     * X coordinate of this vector.
     */
    val x: T,

    /**
     * Y coordinate of this vector.
     */
    val y: T,

    /**
     * Type of vector coordinates.
     *
     * @since v1.3.0
     */
    val type: KClass<T>
) : Vec<T> where T : Number, T : Comparable<T> {

    /**
     * U coordinate for UV mapping.
     */
    val u: T get() = x

    /**
     * V coordinate for UV mapping.
     */
    val v: T get() = y

    /**
     * Returns this vector.
     */
    operator fun unaryPlus(): Vec2<T> = this

    /**
     * Returns a vector opposite to this vector.
     */
    operator fun unaryMinus(): Vec2<T> =
        copy(x = -this.x, y = -this.y)

    /**
     * Adds the [other] vector to this vector.
     */
    operator fun plus(other: Vec2<T>): Vec2<T> =
        copy(x = this.x + other.x, y = this.y + other.y)

    /**
     * Subtracts the [other] vector from this vector.
     */
    operator fun minus(other: Vec2<T>): Vec2<T> =
        copy(x = this.x - other.x, y = this.y - other.y)

    /**
     * Multiplies this vector by the specified [number].
     */
    operator fun times(number: T): Vec2<T> =
        copy(x = this.x * number, y = this.y * number)

    /**
     * Divides this vector by the specified [number].
     */
    operator fun div(number: T): Vec2<T> =
        copy(x = this.x / number, y = this.y / number)

    /**
     * Calculates dot product of this vector and the [other] vector.
     *
     * @since v1.1.0
     */
    infix fun dot(other: Vec2<T>): T =
        (this.toList() zip other.toList()).map { (a, b) -> a * b }.sum(this.type)

    /**
     * Calculates cross product of this vector and the [other] vector.
     *
     * Cross product of two 2D vectors is always a 3D vector in the direction of Z axis.
     *
     * @since v1.3.0
     */
    infix fun cross(other: Vec2<T>): Vec3<T> =
        Vec3(
            x = zero(this.type),
            y = zero(this.type),
            z = this.x * other.y - this.y * other.x,
            type = this.type
        )

    /**
     * Returns the arc tangent of value [y]/[x] for this vector.
     *
     * @since v1.1.0
     */
    fun atan(): Angle<T> =
        Angle.atan2(y = this.y, x = this.x)

    /**
     * Returns the magnitude of this vector.
     *
     * @since v1.3.0
     */
    fun magnitude(): T =
        sqrt(x = this.x * this.x + this.y * this.y)

    /**
     * Returns a unit vector in the direction of this vector.
     *
     * @since v1.3.0
     */
    fun normalize(): Vec2<T> =
        this / this.magnitude()

    /**
     * Returns a 3D vector with `x` and `y` coordinates of this vector and the given [z] coordinate.
     */
    fun toVec3(z: T = zero(this.type)): Vec3<T> =
        Vec3(x = this.x, y = this.y, z = z, type = this.type)

    /**
     * Returns a 4D vector with `x` and `y` coordinates of this vector and the given [z] and [w]
     * coordinates.
     */
    fun toVec4(z: T = zero(this.type), w: T = zero(this.type)): Vec4<T> =
        Vec4(x = this.x, y = this.y, z = z, w = w, type = this.type)

    /**
     * Returns a 2D integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    fun toIntVector(): Vec2<Int> =
        Vec2(x = this.x.toInt(), y = this.y.toInt(), type = Int::class)

    /**
     * Returns a 2D long integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    fun toLongVector(): Vec2<Long> =
        Vec2(x = this.x.toLong(), y = this.y.toLong(), type = Long::class)

    /**
     * Returns a 2D float vector equal to this vector.
     *
     * @since v1.3.0
     */
    fun toFloatVector(): Vec2<Float> =
        Vec2(x = this.x.toFloat(), y = this.y.toFloat(), type = Float::class)

    /**
     * Returns a 2D double-precision float vector equal to this vector.
     *
     * @since v1.3.0
     */
    fun toDoubleVector(): Vec2<Double> =
        Vec2(x = this.x.toDouble(), y = this.y.toDouble(), type = Double::class)

    /**
     * Returns a list of coordinates of this vector.
     */
    override fun toList(): List<T> = listOf(x, y)

    companion object {

        private const val SIZE = 2

        /**
         * A null vector.
         *
         * @since v1.1.0
         */
        @Deprecated(
            message = "Use Vec2.nullVector() instead.",
            replaceWith = ReplaceWith(expression = "Vec2.nullVector<Float>()")
        )
        val nullVector: Vec2<Float> = Vec2(x = 0f, y = 0f)

        /**
         * A standard unit vector in the direction of X axis.
         *
         * @since v1.1.0
         */
        @Deprecated(
            message = "Use Vec2.unitX() instead.",
            replaceWith = ReplaceWith(expression = "Vec2.unitX<Float>()")
        )
        val unitX: Vec2<Float> = Vec2(x = 1f, y = 0f)

        /**
         * A standard unit vector in the direction of Y axis.
         *
         * @since v1.1.0
         */
        @Deprecated(
            message = "Use Vec2.unitY() instead.",
            replaceWith = ReplaceWith(expression = "Vec2.unitY<Float>()")
        )
        val unitY: Vec2<Float> = Vec2(x = 0f, y = 1f)

        /**
         * Returns a null vector.
         *
         * @since v1.3.0
         */
        inline fun <reified T> nullVector(): Vec2<T> where T : Number, T : Comparable<T> =
            nullVector(T::class)

        /**
         * Returns a null vector with coordinates of given [type].
         *
         * @since v1.3.0
         */
        fun <T> nullVector(type: KClass<T>): Vec2<T> where T : Number, T : Comparable<T> =
            Vec2(x = zero(type), y = zero(type), type = type)

        /**
         * Returns a standard unit vector in the direction of X axis.
         *
         * @since v1.3.0
         */
        inline fun <reified T> unitX(): Vec2<T> where T : Number, T : Comparable<T> =
            unitX(T::class)

        /**
         * Returns a standard unit vector in the direction of X axis
         * with coordinates of given [type].
         *
         * @since v1.3.0
         */
        fun <T> unitX(type: KClass<T>): Vec2<T> where T : Number, T : Comparable<T> =
            Vec2(x = one(type), y = zero(type), type = type)

        /**
         * Returns a standard unit vector in the direction of Y axis.
         *
         * @since v1.3.0
         */
        inline fun <reified T> unitY(): Vec2<T> where T : Number, T : Comparable<T> =
            unitY(T::class)

        /**
         * Returns a standard unit vector in the direction of Y axis
         * with coordinates of given [type].
         *
         * @since v1.3.0
         */
        fun <T> unitY(type: KClass<T>): Vec2<T> where T : Number, T : Comparable<T> =
            Vec2(x = zero(type), y = one(type), type = type)

        /**
         * Returns an instance of [Vec2] with the given [list] of coordinates of given [type].
         *
         * If the size of the list of coordinates is different from 2,
         * [IllegalArgumentException] is thrown.
         *
         * @since v1.3.0
         */
        internal fun <T> fromList(list: List<T>, type: KClass<T>): Vec2<T> where T : Number, T : Comparable<T> {
            require(list.size == SIZE)
            val (x, y) = list
            return Vec2(x, y, type)
        }

        /**
         * Returns an instance of [Vec2] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 2,
         * [IllegalArgumentException] is thrown.
         *
         * @since v1.3.0
         */
        @JvmName("fromIntList")
        fun fromList(list: List<Int>): Vec2<Int> {
            require(list.size == SIZE)
            val (x, y) = list
            return Vec2(x, y)
        }

        /**
         * Returns an instance of [Vec2] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 2,
         * [IllegalArgumentException] is thrown.
         *
         * @since v1.3.0
         */
        @JvmName("fromLongList")
        fun fromList(list: List<Long>): Vec2<Long> {
            require(list.size == SIZE)
            val (x, y) = list
            return Vec2(x, y)
        }

        /**
         * Returns an instance of [Vec2] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 2,
         * [IllegalArgumentException] is thrown.
         */
        @JvmName("fromFloatList")
        fun fromList(list: List<Float>): Vec2<Float> {
            require(list.size == SIZE)
            val (x, y) = list
            return Vec2(x, y)
        }

        /**
         * Returns an instance of [Vec2] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 2,
         * [IllegalArgumentException] is thrown.
         *
         * @since v1.3.0
         */
        @JvmName("fromDoubleList")
        fun fromList(list: List<Double>): Vec2<Double> {
            require(list.size == SIZE)
            val (x, y) = list
            return Vec2(x, y)
        }

        /**
         * Returns a 2D vector defined by its polar coordinates of given [type]:
         * radial [distance] and [angle].
         *
         * @since v1.3.0
         */
        fun <T> fromPolarCoordinates(
            distance: T,
            angle: Angle<T>,
            type: KClass<T>
        ): Vec2<T> where T : Number, T : Comparable<T> = Vec2(
            x = distance * cos(angle),
            y = distance * sin(angle),
            type = type
        )

        /**
         * Returns a 2D vector defined by its polar coordinates: radial [distance] and [angle].
         *
         * @since v1.1.0
         */
        fun fromPolarCoordinates(
            distance: Float,
            angle: Angle<Float>
        ): Vec2<Float> = Vec2(
            x = distance * cos(angle),
            y = distance * sin(angle)
        )

        /**
         * Returns a 2D vector defined by its polar coordinates: radial [distance] and [angle].
         *
         * @since v1.3.0
         */
        fun fromPolarCoordinates(
            distance: Double,
            angle: Angle<Double>
        ): Vec2<Double> = Vec2(
            x = distance * cos(angle),
            y = distance * sin(angle)
        )
    }
}

/**
 * Returns a new 2D vector with coordinates ([x], [y]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> Vec2(x: T, y: T): Vec2<T> where T : Number, T : Comparable<T> =
    Vec2(x = x, y = y, type = T::class)

/**
 * Returns an array of coordinates of this vector.
 *
 * @since v1.3.0
 */
fun Vec2<Int>.toIntArray(): IntArray = intArrayOf(x, y)

/**
 * Returns an array of coordinates of this vector.
 *
 * @since v1.3.0
 */
fun Vec2<Long>.toLongArray(): LongArray = longArrayOf(x, y)

/**
 * Returns an array of coordinates of this vector.
 */
fun Vec2<Float>.toFloatArray(): FloatArray = floatArrayOf(x, y)

/**
 * Returns an array of coordinates of this vector.
 *
 * @since v1.3.0
 */
fun Vec2<Double>.toDoubleArray(): DoubleArray = doubleArrayOf(x, y)
