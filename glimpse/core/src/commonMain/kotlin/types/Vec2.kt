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
 * 2D vector with coordinates ([x], [y]).
 *
 * Can also be used to specify texture coordinates in UV mapping.
 */
interface Vec2<T : Number> : Vec<T> {

    /**
     * X coordinate of this vector.
     */
    val x: T

    /**
     * Y coordinate of this vector.
     */
    val y: T

    /**
     * U coordinate for UV mapping.
     */
    val u: T get() = x

    /**
     * V coordinate for UV mapping.
     */
    val v: T get() = y

    /**
     * First component of this vector.
     */
    operator fun component1(): T

    /**
     * Second component of this vector.
     */
    operator fun component2(): T

    /**
     * Returns this vector.
     */
    operator fun unaryPlus(): Vec2<T>

    /**
     * Returns a vector opposite to this vector.
     */
    operator fun unaryMinus(): Vec2<T>

    /**
     * Adds the [other] vector to this vector.
     */
    operator fun plus(other: Vec2<T>): Vec2<T>

    /**
     * Subtracts the [other] vector from this vector.
     */
    operator fun minus(other: Vec2<T>): Vec2<T>

    /**
     * Multiplies this vector by the specified [number].
     */
    operator fun times(number: T): Vec2<T>

    /**
     * Divides this vector by the specified [number].
     */
    operator fun div(number: T): Vec2<T>

    /**
     * Calculates dot product of this vector and the [other] vector.
     *
     * @since v1.1.0
     */
    infix fun dot(other: Vec2<T>): T

    /**
     * Returns the arc tangent of value [y]/[x] for this vector.
     *
     * @since v1.1.0
     */
    fun atan(): Angle<T>

    /**
     * Returns the magnitude of this vector.
     *
     * @since v1.3.0
     */
    fun magnitude(): T

    /**
     * Returns a unit vector in the direction of this vector.
     *
     * @since v1.3.0
     */
    fun normalize(): Vec2<T>

    /**
     * Returns a 3D vector with `x` and `y` coordinates of this vector and z coordinate equal to zero.
     *
     * @since v1.3.0
     */
    fun toVec3(): Vec3<T>

    /**
     * Returns a 3D vector with `x` and `y` coordinates of this vector and the given [z] coordinate.
     */
    fun toVec3(z: T): Vec3<T>

    /**
     * Returns a 4D vector with `x` and `y` coordinates of this vector and z and w coordinates equal to zero.
     *
     * @since v1.3.0
     */
    fun toVec4(): Vec4<T>

    /**
     * Returns a 4D vector with `x` and `y` coordinates of this vector, z coordinate equal to zero
     * and the given [w] coordinates.
     *
     * @since v1.3.0
     */
    fun toVec4(w: T): Vec4<T>

    /**
     * Returns a 4D vector with `x` and `y` coordinates of this vector and the given [z] and [w]
     * coordinates.
     */
    fun toVec4(z: T, w: T): Vec4<T>

    /**
     * Returns a 2D float vector equal to this vector.
     *
     * @since v1.3.0
     */
    fun toFloatVector(): Vec2<Float>

    /**
     * Returns a 2D double-precision float vector equal to this vector.
     *
     * @since v1.3.0
     */
    fun toDoubleVector(): Vec2<Double>

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
        val nullVector: Vec2<Float> = Vec2(x = 0f, y = 0f)

        /**
         * A standard unit vector in the direction of X axis.
         *
         * @since v1.1.0
         */
        val unitX: Vec2<Float> = Vec2(x = 1f, y = 0f)

        /**
         * A standard unit vector in the direction of Y axis.
         *
         * @since v1.1.0
         */
        val unitY: Vec2<Float> = Vec2(x = 0f, y = 1f)

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
 * Returns a new 2D float vector with coordinates ([x], [y]).
 */
@Suppress("FunctionNaming")
fun Vec2(x: Float, y: Float): Vec2<Float> = Vec2F(x, y)

/**
 * Returns a new 2D double-precision float vector with coordinates ([x], [y]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
fun Vec2(x: Double, y: Double): Vec2<Double> = Vec2D(x, y)

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
