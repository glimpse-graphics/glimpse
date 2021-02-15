/*
 * Copyright 2020 Slawomir Czerwinski
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
 *
 */

package graphics.glimpse.types

/**
 * 3D vector with coordinates ([x], [y], [z]).
 *
 * Can be used to specify RGB color values.
 */
data class Vec3(val x: Float, val y: Float, val z: Float) : Vec {

    /**
     * Red channel of RGB color.
     */
    val r: Float get() = x

    /**
     * Green channel of RGB color.
     */
    val g: Float get() = y

    /**
     * Blue channel of RGB color.
     */
    val b: Float get() = z

    /**
     * Returns this vector.
     */
    operator fun unaryPlus(): Vec3 = this

    /**
     * Returns a vector opposite to this vector.
     */
    operator fun unaryMinus(): Vec3 = Vec3(x = -this.x, y = -this.y, z = -this.z)

    /**
     * Adds the [other] vector to this vector.
     */
    operator fun plus(other: Vec3): Vec3 = Vec3(
        x = this.x + other.x,
        y = this.y + other.y,
        z = this.z + other.z
    )

    /**
     * Subtracts the [other] vector from this vector.
     */
    operator fun minus(other: Vec3): Vec3 = Vec3(
        x = this.x - other.x,
        y = this.y - other.y,
        z = this.z - other.z
    )

    /**
     * Multiplies this vector by the specified [number].
     */
    operator fun times(number: Float): Vec3 = Vec3(
        x = this.x * number,
        y = this.y * number,
        z = this.z * number
    )

    /**
     * Divides this vector by the specified [number].
     */
    operator fun div(number: Float): Vec3 = Vec3(
        x = this.x / number,
        y = this.y / number,
        z = this.z / number
    )

    /**
     * Calculates dot product of this vector and the [other] vector.
     */
    infix fun dot(other: Vec3): Float =
        (this.toList() zip other.toList()).map { (a, b) -> a * b }.sum()

    /**
     * Calculates cross product of this vector and the [other] vector.
     */
    infix fun cross(other: Vec3): Vec3 = Vec3(
        x = this.y * other.z - this.z * other.y,
        y = this.z * other.x - this.x * other.z,
        z = this.x * other.y - this.y * other.x
    )

    /**
     * Returns a 2D vector with `x` and `y` coordinates of this vector.
     */
    fun toVec2() = Vec2(x = this.x, y = this.y)

    /**
     * Returns a 4D vector with `x`, `y` and `z coordinates of this vector and the given [w]
     * coordinate.
     */
    fun toVec4(w: Float = 0f) = Vec4(x = this.x, y = this.y, z = this.z, w = w)

    /**
     * Returns a list of coordinates of this vector.
     */
    override fun toList(): List<Float> = listOf(x, y, z)

    /**
     * Returns an array of coordinates of this vector.
     */
    override fun toFloatArray(): FloatArray = floatArrayOf(x, y, z)

    companion object {

        private const val SIZE = 3

        /**
         * A null vector.
         */
        val nullVector: Vec3 = Vec3(x = 0f, y = 0f, z = 0f)

        /**
         * A standard unit vector in the direction of X axis.
         */
        val unitX: Vec3 = Vec3(x = 1f, y = 0f, z = 0f)

        /**
         * A standard unit vector in the direction of Y axis.
         */
        val unitY: Vec3 = Vec3(x = 0f, y = 1f, z = 0f)

        /**
         * A standard unit vector in the direction of Z axis.
         */
        val unitZ: Vec3 = Vec3(x = 0f, y = 0f, z = 1f)

        /**
         * Returns an instance of [Vec3] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different than 3, [IllegalArgumentException]
         * is thrown.
         */
        fun fromList(list: List<Float>): Vec3 {
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
            longitude: Angle,
            latitude: Angle
        ): Vec3 = Vec3(
            x = distance * cos(longitude) * cos(latitude),
            y = distance * sin(longitude) * cos(latitude),
            z = distance * sin(latitude)
        )
    }
}
