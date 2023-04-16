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
abstract class Vec3<T : Number> : BaseVec<T>() {

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
     * Implement this function to provide a way to create a new vector.
     *
     * @since v1.3.0
     */
    protected abstract fun create(x: T, y: T, z: T): Vec3<T>

    /**
     * Returns this vector.
     */
    operator fun unaryPlus(): Vec3<T> = this

    /**
     * Returns a vector opposite to this vector.
     */
    operator fun unaryMinus(): Vec3<T> =
        create(
            x = ring.additiveInverse(this.x),
            y = ring.additiveInverse(this.y),
            z = ring.additiveInverse(this.z)
        )

    /**
     * Adds the [other] vector to this vector.
     */
    operator fun plus(other: Vec3<T>): Vec3<T> =
        create(
            x = ring.add(this.x, other.x),
            y = ring.add(this.y, other.y),
            z = ring.add(this.z, other.z)
        )

    /**
     * Subtracts the [other] vector from this vector.
     */
    operator fun minus(other: Vec3<T>): Vec3<T> =
        create(
            x = ring.subtract(this.x, other.x),
            y = ring.subtract(this.y, other.y),
            z = ring.subtract(this.z, other.z)
        )

    /**
     * Multiplies this vector by the specified [number].
     */
    operator fun times(number: T): Vec3<T> =
        create(
            x = ring.multiply(this.x, number),
            y = ring.multiply(this.y, number),
            z = ring.multiply(this.z, number)
        )

    /**
     * Divides this vector by the specified [number].
     */
    operator fun div(number: T): Vec3<T> =
        (ring as? Field<T>)?.let { field ->
            create(
                x = field.divide(this.x, number),
                y = field.divide(this.y, number),
                z = field.divide(this.z, number)
            )
        } ?: throw UnsupportedOperationException("${ring::class.simpleName} is not a field")

    /**
     * Calculates dot product of this vector and the [other] vector.
     */
    infix fun dot(other: Vec3<T>): T =
        (this.toList() zip other.toList()).map { (a, b) -> ring.multiply(a, b) }.let { ring.sum(it) }

    /**
     * Calculates cross product of this vector and the [other] vector.
     */
    infix fun cross(other: Vec3<T>): Vec3<T> =
        create(
            x = ring.subtract(ring.multiply(this.y, other.z), ring.multiply(this.z, other.y)),
            y = ring.subtract(ring.multiply(this.z, other.x), ring.multiply(this.x, other.z)),
            z = ring.subtract(ring.multiply(this.x, other.y), ring.multiply(this.y, other.x))
        )

    /**
     * Returns the magnitude of this vector.
     *
     * @since v1.3.0
     */
    abstract fun magnitude(): T

    /**
     * Returns a unit vector in the direction of this vector.
     *
     * @since v1.3.0
     */
    fun normalize(): Vec3<T> = this / this.magnitude()

    /**
     * Returns a 2D vector with `x` and `y` coordinates of this vector.
     */
    abstract fun toVec2(): Vec2<T>

    /**
     * Returns a 4D vector with `x`, `y` and `z coordinates of this vector and the given [w]
     * coordinate.
     */
    abstract fun toVec4(w: T = ring.additiveIdentity): Vec4<T>

    /**
     * Returns a 3D integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    abstract fun toIntVector(): Vec3<Int>

    /**
     * Returns a 3D long integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    abstract fun toLongVector(): Vec3<Long>

    /**
     * Returns a 3D float vector equal to this vector.
     *
     * @since v1.3.0
     */
    abstract fun toFloatVector(): Vec3<Float>

    /**
     * Returns a 3D double-precision float vector equal to this vector.
     *
     * @since v1.3.0
     */
    abstract fun toDoubleVector(): Vec3<Double>

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
 * Returns a new 3D integer vector with coordinates ([x], [y], [z]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
fun Vec3(x: Int, y: Int, z: Int): Vec3<Int> = Vec3I(x, y, z)

/**
 * Returns a new 3D long integer vector with coordinates ([x], [y], [z]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
fun Vec3(x: Long, y: Long, z: Long): Vec3<Long> = Vec3L(x, y, z)

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
