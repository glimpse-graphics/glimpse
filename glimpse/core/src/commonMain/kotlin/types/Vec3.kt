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
 * 3D vector with coordinates ([x], [y], [z]).
 *
 * Can also be used to specify RGB color values.
 */
interface Vec3<T : Number> : Vec<T> {

    /**
     * X coordinate of this vector.
     */
    val x: T

    /**
     * Y coordinate of this vector.
     */
    val y: T

    /**
     * Z coordinate of this vector.
     */
    val z: T

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
     * First component of this vector.
     */
    operator fun component1(): T

    /**
     * Second component of this vector.
     */
    operator fun component2(): T

    /**
     * Third component of this vector.
     */
    operator fun component3(): T

    /**
     * Returns this vector.
     */
    operator fun unaryPlus(): Vec3<T>

    /**
     * Returns a vector opposite to this vector.
     */
    operator fun unaryMinus(): Vec3<T>

    /**
     * Adds the [other] vector to this vector.
     */
    operator fun plus(other: Vec3<T>): Vec3<T>

    /**
     * Subtracts the [other] vector from this vector.
     */
    operator fun minus(other: Vec3<T>): Vec3<T>

    /**
     * Multiplies this vector by the specified [number].
     */
    operator fun times(number: T): Vec3<T>

    /**
     * Divides this vector by the specified [number].
     */
    operator fun div(number: T): Vec3<T>

    /**
     * Calculates dot product of this vector and the [other] vector.
     */
    infix fun dot(other: Vec3<T>): T

    /**
     * Calculates cross product of this vector and the [other] vector.
     */
    infix fun cross(other: Vec3<T>): Vec3<T>

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
    fun normalize(): Vec3<T>

    /**
     * Returns a 2D vector with `x` and `y` coordinates of this vector.
     */
    fun toVec2(): Vec2<T>

    /**
     * Returns a 4D vector with `x`, `y` and `z coordinates of this vector and w coordinate equal to zero.
     *
     * @since v1.3.0
     */
    fun toVec4(): Vec4<T>

    /**
     * Returns a 4D vector with `x`, `y` and `z coordinates of this vector and the given [w]
     * coordinate.
     */
    fun toVec4(w: T): Vec4<T>

    /**
     * Returns a 2D float vector equal to this vector.
     *
     * @since v1.3.0
     */
    fun toFloatVector(): Vec3<Float>

    /**
     * Returns a 2D double-precision float vector equal to this vector.
     *
     * @since v1.3.0
     */
    fun toDoubleVector(): Vec3<Double>

    companion object {

        private const val SIZE = 3

        /**
         * A null vector.
         */
        val nullVector: Vec3<Float> = Vec3(x = 0f, y = 0f, z = 0f)

        /**
         * A standard unit vector in the direction of X axis.
         */
        val unitX: Vec3<Float> = Vec3(x = 1f, y = 0f, z = 0f)

        /**
         * A standard unit vector in the direction of Y axis.
         */
        val unitY: Vec3<Float> = Vec3(x = 0f, y = 1f, z = 0f)

        /**
         * A standard unit vector in the direction of Z axis.
         */
        val unitZ: Vec3<Float> = Vec3(x = 0f, y = 0f, z = 1f)

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
 * Returns a new 3D float vector with coordinates ([x], [y], [z]).
 */
@Suppress("FunctionNaming")
fun Vec3(x: Float, y: Float, z: Float): Vec3<Float> = Vec3F(x, y, z)

/**
 * Returns a new 3D double-precision float vector with coordinates ([x], [y], [z]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
fun Vec3(x: Double, y: Double, z: Double): Vec3<Double> = Vec3D(x, y, z)

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
