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
abstract class Vec2<T : Number> : BaseVec<T>() {

    /**
     * X coordinate of this vector.
     */
    abstract val x: T

    /**
     * Y coordinate of this vector.
     */
    abstract val y: T

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
    abstract operator fun component1(): T

    /**
     * Second component of this vector.
     */
    abstract operator fun component2(): T

    /**
     * Implement this function to provide a way to create a new vector.
     *
     * @since v1.3.0
     */
    protected abstract fun create(x: T, y: T): Vec2<T>

    /**
     * Returns this vector.
     */
    operator fun unaryPlus(): Vec2<T> = this

    /**
     * Returns a vector opposite to this vector.
     */
    operator fun unaryMinus(): Vec2<T> =
        create(
            x = ring.additiveInverse(this.x),
            y = ring.additiveInverse(this.y)
        )

    /**
     * Adds the [other] vector to this vector.
     */
    operator fun plus(other: Vec2<T>): Vec2<T> =
        create(
            x = ring.add(this.x, other.x),
            y = ring.add(this.y, other.y)
        )

    /**
     * Subtracts the [other] vector from this vector.
     */
    operator fun minus(other: Vec2<T>): Vec2<T> =
        create(
            x = ring.subtract(this.x, other.x),
            y = ring.subtract(this.y, other.y)
        )

    /**
     * Multiplies this vector by the specified [number].
     */
    operator fun times(number: T): Vec2<T> =
        create(
            x = ring.multiply(this.x, number),
            y = ring.multiply(this.y, number)
        )

    /**
     * Divides this vector by the specified [number].
     */
    operator fun div(number: T): Vec2<T> =
        (ring as? Field<T>)?.let { field ->
            create(
                x = field.divide(this.x, number),
                y = field.divide(this.y, number)
            )
        } ?: throw UnsupportedOperationException("${ring::class.simpleName} is not a field")

    /**
     * Calculates dot product of this vector and the [other] vector.
     *
     * @since v1.1.0
     */
    infix fun dot(other: Vec2<T>): T =
        (this.toList() zip other.toList()).map { (a, b) -> ring.multiply(a, b) }.let { ring.sum(it) }

    /**
     * Calculates cross product of this vector and the [other] vector.
     *
     * Cross product of two 2D vectors is always a 3D vector in the direction of Z axis.
     *
     * @since v1.3.0
     */
    abstract infix fun cross(other: Vec2<T>): Vec3<T>

    /**
     * Returns the arc tangent of value [y]/[x] for this vector.
     *
     * @since v1.1.0
     */
    abstract fun atan(): Angle<T>

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
    fun normalize(): Vec2<T> = this / this.magnitude()

    /**
     * Returns a 3D vector with `x` and `y` coordinates of this vector and the given [z] coordinate.
     */
    abstract fun toVec3(z: T = ring.additiveIdentity): Vec3<T>

    /**
     * Returns a 4D vector with `x` and `y` coordinates of this vector and the given [z] and [w]
     * coordinates.
     */
    abstract fun toVec4(z: T = ring.additiveIdentity, w: T = ring.additiveIdentity): Vec4<T>

    /**
     * Returns a 2D integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    abstract fun toIntVector(): Vec2<Int>

    /**
     * Returns a 2D long integer vector equal to this vector.
     *
     * Floating point values will be rounded.
     *
     * @since v1.3.0
     */
    abstract fun toLongVector(): Vec2<Long>

    /**
     * Returns a 2D float vector equal to this vector.
     *
     * @since v1.3.0
     */
    abstract fun toFloatVector(): Vec2<Float>

    /**
     * Returns a 2D double-precision float vector equal to this vector.
     *
     * @since v1.3.0
     */
    abstract fun toDoubleVector(): Vec2<Double>

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
 * Returns a new 2D integer vector with coordinates ([x], [y]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
fun Vec2(x: Int, y: Int): Vec2<Int> = Vec2I(x, y)

/**
 * Returns a new 2D long integer vector with coordinates ([x], [y]).
 *
 * @since v1.3.0
 */
@Suppress("FunctionNaming")
fun Vec2(x: Long, y: Long): Vec2<Long> = Vec2L(x, y)

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
fun Vec2<Int>.toIntArray(): IntArray = intArrayOf(x, y)

/**
 * Returns an array of coordinates of this vector.
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
