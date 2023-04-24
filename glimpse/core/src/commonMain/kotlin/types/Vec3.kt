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
 * 3D vector with coordinates ([x], [y], [z]).
 *
 * Can also be used to specify RGB color values.
 */
data class Vec3<T>(

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
     * Type of vector coordinates.
     *
     * @since v1.3.0
     */
    val type: KClass<T>
) : Vec<T> where T : Number, T : Comparable<T> {

    /**
     * Red channel of RGB color.
     */
    val r: T get() = x

    /**
     * Green channel of RGB color.
     */
    val g: T get() = y

    /**
     * Blue channel of RGB color.
     */
    val b: T get() = z

    /**
     * Returns this vector.
     */
    operator fun unaryPlus(): Vec3<T> = this

    /**
     * Returns a vector opposite to this vector.
     */
    operator fun unaryMinus(): Vec3<T> =
        copy(x = -this.x, y = -this.y, z = -this.z)

    /**
     * Adds the [other] vector to this vector.
     */
    operator fun plus(other: Vec3<T>): Vec3<T> =
        copy(x = this.x + other.x, y = this.y + other.y, z = this.z + other.z)

    /**
     * Subtracts the [other] vector from this vector.
     */
    operator fun minus(other: Vec3<T>): Vec3<T> =
        copy(x = this.x - other.x, y = this.y - other.y, z = this.z - other.z)

    /**
     * Multiplies this vector by the specified [number].
     */
    operator fun times(number: T): Vec3<T> =
        copy(x = this.x * number, y = this.y * number, z = this.z * number)

    /**
     * Divides this vector by the specified [number].
     */
    operator fun div(number: T): Vec3<T> =
        copy(x = this.x / number, y = this.y / number, z = this.z / number)

    /**
     * Calculates dot product of this vector and the [other] vector.
     */
    infix fun dot(other: Vec3<T>): T =
        (this.toList() zip other.toList()).map { (a, b) -> a * b }.sum(this.type)

    /**
     * Calculates cross product of this vector and the [other] vector.
     */
    infix fun cross(other: Vec3<T>): Vec3<T> =
        copy(
            x = this.y * other.z - this.z * other.y,
            y = this.z * other.x - this.x * other.z,
            z = this.x * other.y - this.y * other.x
        )

    /**
     * Returns the magnitude of this vector.
     *
     * @since v1.3.0
     */
    fun magnitude(): T =
        sqrt(x = this.x * this.x + this.y * this.y + this.z * this.z)

    /**
     * Returns a unit vector in the direction of this vector.
     *
     * @since v1.3.0
     */
    fun normalize(): Vec3<T> =
        this / this.magnitude()

    /**
     * Returns a 2D vector with `x` and `y` coordinates of this vector.
     */
    fun toVec2(): Vec2<T> =
        Vec2(x = this.x, y = this.y, type = this.type)

    /**
     * Returns a 4D vector with `x`, `y` and `z coordinates of this vector and the given [w]
     * coordinate.
     */
    fun toVec4(w: T = zero(this.type)): Vec4<T> =
        Vec4(x = this.x, y = this.y, z = this.z, w = w, type = this.type)

    /**
     * Returns a 3D integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    fun toIntVector(): Vec3<Int> =
        Vec3(x = this.x.toInt(), y = this.y.toInt(), z = this.z.toInt(), type = Int::class)

    /**
     * Returns a 3D long integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    fun toLongVector(): Vec3<Long> =
        Vec3(x = this.x.toLong(), y = this.y.toLong(), z = this.z.toLong(), type = Long::class)

    /**
     * Returns a 3D float vector equal to this vector.
     *
     * @since v1.3.0
     */
    fun toFloatVector(): Vec3<Float> =
        Vec3(x = this.x.toFloat(), y = this.y.toFloat(), z = this.z.toFloat(), type = Float::class)

    /**
     * Returns a 3D double-precision float vector equal to this vector.
     *
     * @since v1.3.0
     */
    fun toDoubleVector(): Vec3<Double> =
        Vec3(x = this.x.toDouble(), y = this.y.toDouble(), z = this.z.toDouble(), type = Double::class)

    /**
     * Returns a list of coordinates of this vector.
     */
    override fun toList(): List<T> = listOf(x, y, z)

    companion object {

        private const val SIZE = 3

        /**
         * A null vector.
         */
        @Deprecated(
            message = "Use Vec3.nullVector() instead.",
            replaceWith = ReplaceWith(expression = "Vec3.nullVector<Float>()")
        )
        val nullVector: Vec3<Float> = Vec3(x = 0f, y = 0f, z = 0f)

        /**
         * A standard unit vector in the direction of X axis.
         */
        @Deprecated(
            message = "Use Vec3.unitX() instead.",
            replaceWith = ReplaceWith(expression = "Vec3.unitX<Float>()")
        )
        val unitX: Vec3<Float> = Vec3(x = 1f, y = 0f, z = 0f)

        /**
         * A standard unit vector in the direction of Y axis.
         */
        @Deprecated(
            message = "Use Vec3.unitY() instead.",
            replaceWith = ReplaceWith(expression = "Vec3.unitY<Float>()")
        )
        val unitY: Vec3<Float> = Vec3(x = 0f, y = 1f, z = 0f)

        /**
         * A standard unit vector in the direction of Z axis.
         */
        @Deprecated(
            message = "Use Vec3.unitZ() instead.",
            replaceWith = ReplaceWith(expression = "Vec3.unitZ<Float>()")
        )
        val unitZ: Vec3<Float> = Vec3(x = 0f, y = 0f, z = 1f)

        /**
         * Returns a null vector.
         *
         * @since v1.3.0
         */
        inline fun <reified T> nullVector(): Vec3<T> where T : Number, T : Comparable<T> =
            nullVector(T::class)

        /**
         * Returns a null vector with coordinates of given [type].
         *
         * @since v1.3.0
         */
        fun <T> nullVector(type: KClass<T>): Vec3<T> where T : Number, T : Comparable<T> =
            Vec3(x = zero(type), y = zero(type), z = zero(type), type = type)

        /**
         * Returns a standard unit vector in the direction of X axis.
         *
         * @since v1.3.0
         */
        inline fun <reified T> unitX(): Vec3<T> where T : Number, T : Comparable<T> =
            unitX(T::class)

        /**
         * Returns a standard unit vector in the direction of X axis
         * with coordinates of given [type].
         *
         * @since v1.3.0
         */
        fun <T> unitX(type: KClass<T>): Vec3<T> where T : Number, T : Comparable<T> =
            Vec3(x = one(type), y = zero(type), z = zero(type), type = type)

        /**
         * Returns a standard unit vector in the direction of Y axis.
         *
         * @since v1.3.0
         */
        inline fun <reified T> unitY(): Vec3<T> where T : Number, T : Comparable<T> =
            unitY(T::class)

        /**
         * Returns a standard unit vector in the direction of Y axis
         * with coordinates of given [type].
         *
         * @since v1.3.0
         */
        fun <T> unitY(type: KClass<T>): Vec3<T> where T : Number, T : Comparable<T> =
            Vec3(x = zero(type), y = one(type), z = zero(type), type = type)

        /**
         * Returns a standard unit vector in the direction of Z axis.
         *
         * @since v1.3.0
         */
        inline fun <reified T> unitZ(): Vec3<T> where T : Number, T : Comparable<T> =
            unitZ(T::class)

        /**
         * Returns a standard unit vector in the direction of Z axis
         * with coordinates of given [type].
         *
         * @since v1.3.0
         */
        fun <T> unitZ(type: KClass<T>): Vec3<T> where T : Number, T : Comparable<T> =
            Vec3(x = zero(type), y = zero(type), z = one(type), type = type)

        /**
         * Returns an instance of [Vec3] with the given [list] of coordinates of given [type].
         *
         * If the size of the list of coordinates is different from 3, [IllegalArgumentException]
         * is thrown.
         *
         * @since v1.3.0
         */
        fun <T> fromList(list: List<T>, type: KClass<T>): Vec3<T> where T : Number, T : Comparable<T> {
            require(list.size == SIZE)
            val (x, y, z) = list
            return Vec3(x, y, z, type)
        }

        /**
         * Returns an instance of [Vec3] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 3, [IllegalArgumentException]
         * is thrown.
         *
         * @since v1.3.0
         */
        @JvmName("fromIntList")
        fun fromList(list: List<Int>): Vec3<Int> {
            require(list.size == SIZE)
            val (x, y, z) = list
            return Vec3(x, y, z)
        }

        /**
         * Returns an instance of [Vec3] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 3, [IllegalArgumentException]
         * is thrown.
         *
         * @since v1.3.0
         */
        @JvmName("fromLongList")
        fun fromList(list: List<Long>): Vec3<Long> {
            require(list.size == SIZE)
            val (x, y, z) = list
            return Vec3(x, y, z)
        }

        /**
         * Returns an instance of [Vec3] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 3, [IllegalArgumentException]
         * is thrown.
         */
        @JvmName("fromFloatList")
        fun fromList(list: List<Float>): Vec3<Float> {
            require(list.size == SIZE)
            val (x, y, z) = list
            return Vec3(x, y, z)
        }

        /**
         * Returns an instance of [Vec3] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different from 3, [IllegalArgumentException]
         * is thrown.
         *
         * @since v1.3.0
         */
        @JvmName("fromDoubleList")
        fun fromList(list: List<Double>): Vec3<Double> {
            require(list.size == SIZE)
            val (x, y, z) = list
            return Vec3(x, y, z)
        }

        /**
         * Returns a 3D vector defined by its spherical coordinates: radial [distance], [longitude]
         * and [latitude].
         */
        fun <T> fromSphericalCoordinates(
            distance: T,
            longitude: Angle<T>,
            latitude: Angle<T>,
            type: KClass<T>
        ): Vec3<T> where T : Number, T : Comparable<T> = Vec3(
            x = distance * cos(longitude) * cos(latitude),
            y = distance * sin(longitude) * cos(latitude),
            z = distance * sin(latitude),
            type = type
        )

        /**
         * Returns a 3D vector defined by its spherical coordinates: radial [distance], [longitude]
         * and [latitude].
         */
        fun fromSphericalCoordinates(
            distance: Float,
            longitude: Angle<Float>,
            latitude: Angle<Float>
        ): Vec3<Float> = Vec3(
            x = distance * cos(longitude) * cos(latitude),
            y = distance * sin(longitude) * cos(latitude),
            z = distance * sin(latitude)
        )

        /**
         * Returns a 3D vector defined by its spherical coordinates: radial [distance], [longitude]
         * and [latitude].
         *
         * @since v1.3.0
         */
        fun fromSphericalCoordinates(
            distance: Double,
            longitude: Angle<Double>,
            latitude: Angle<Double>
        ): Vec3<Double> = Vec3(
            x = distance * cos(longitude) * cos(latitude),
            y = distance * sin(longitude) * cos(latitude),
            z = distance * sin(latitude)
        )
    }
}

/**
 * Returns a new 3D vector with coordinates ([x], [y], [z]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> Vec3(x: T, y: T, z: T): Vec3<T> where T : Number, T : Comparable<T> =
    Vec3(x = x, y = y, z = z, type = T::class)

/**
 * Returns an array of coordinates of this vector.
 */
fun Vec3<Int>.toIntArray(): IntArray = intArrayOf(x, y, z)

/**
 * Returns an array of coordinates of this vector.
 */
fun Vec3<Long>.toLongArray(): LongArray = longArrayOf(x, y, z)

/**
 * Returns an array of coordinates of this vector.
 */
fun Vec3<Float>.toFloatArray(): FloatArray = floatArrayOf(x, y, z)

/**
 * Returns an array of coordinates of this vector.
 *
 * @since v1.3.0
 */
fun Vec3<Double>.toDoubleArray(): DoubleArray = doubleArrayOf(x, y, z)
