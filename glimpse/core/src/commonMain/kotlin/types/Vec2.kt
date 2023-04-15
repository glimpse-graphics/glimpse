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
 * Can be used to specify texture coordinates in UV mapping.
 */
data class Vec2(val x: Float, val y: Float) : Vec {

    /**
     * U coordinate for UV mapping.
     */
    val u: Float get() = x

    /**
     * V coordinate for UV mapping.
     */
    val v: Float get() = y

    /**
     * Returns this vector.
     */
    operator fun unaryPlus(): Vec2 = this

    /**
     * Returns a vector opposite to this vector.
     */
    operator fun unaryMinus(): Vec2 = Vec2(x = -this.x, y = -this.y)

    /**
     * Adds the [other] vector to this vector.
     */
    operator fun plus(other: Vec2): Vec2 = Vec2(x = this.x + other.x, y = this.y + other.y)

    /**
     * Subtracts the [other] vector from this vector.
     */
    operator fun minus(other: Vec2): Vec2 = Vec2(x = this.x - other.x, y = this.y - other.y)

    /**
     * Multiplies this vector by the specified [number].
     */
    operator fun times(number: Float): Vec2 = Vec2(x = this.x * number, y = this.y * number)

    /**
     * Divides this vector by the specified [number].
     */
    operator fun div(number: Float): Vec2 = Vec2(x = this.x / number, y = this.y / number)

    /**
     * Calculates dot product of this vector and the [other] vector.
     *
     * @since v1.1.0
     */
    infix fun dot(other: Vec2): Float =
        (this.toList() zip other.toList()).map { (a, b) -> a * b }.sum()

    /**
     * Returns the arc tangent of value [y]/[x] for this vector.
     *
     * @since v1.1.0
     */
    fun atan(): Angle<Float> = Angle.atan2(y, x)

    /**
     * Returns a 3D vector with `x` and `y` coordinates of this vector and the given [z] coordinate.
     */
    fun toVec3(z: Float = 0f) = Vec3(x = this.x, y = this.y, z = z)

    /**
     * Returns a 4D vector with `x` and `y` coordinates of this vector and the given [z] and [w]
     * coordinates.
     */
    fun toVec4(z: Float = 0f, w: Float = 0f) = Vec4(x = this.x, y = this.y, z = z, w = w)

    /**
     * Returns a list of coordinates of this vector.
     */
    override fun toList(): List<Float> = listOf(x, y)

    /**
     * Returns an array of coordinates of this vector.
     */
    override fun toFloatArray(): FloatArray = floatArrayOf(x, y)

    companion object {

        private const val SIZE = 2

        /**
         * A null vector.
         *
         * @since v1.1.0
         */
        val nullVector: Vec2 = Vec2(x = 0f, y = 0f)

        /**
         * A standard unit vector in the direction of X axis.
         *
         * @since v1.1.0
         */
        val unitX: Vec2 = Vec2(x = 1f, y = 0f)

        /**
         * A standard unit vector in the direction of Y axis.
         *
         * @since v1.1.0
         */
        val unitY: Vec2 = Vec2(x = 0f, y = 1f)

        /**
         * Returns an instance of [Vec2] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different than 2, [IllegalArgumentException]
         * is thrown.
         */
        fun fromList(list: List<Float>): Vec2 {
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
        ): Vec2 = Vec2(
            x = distance * cos(angle),
            y = distance * sin(angle)
        )
    }
}
