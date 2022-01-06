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
 * Can be used to specify RGBA color values.
 */
data class Vec4(val x: Float, val y: Float, val z: Float, val w: Float) : Vec {

    /**
     * Red channel of RGBA color.
     */
    val r: Float get() = x

    /**
     * Green channel of RGBA color.
     */
    val g: Float get() = y

    /**
     * Blue channel of RGBA color.
     */
    val b: Float get() = z

    /**
     * Alpha channel of RGBA color.
     */
    val a: Float get() = w

    /**
     * Returns a 2D vector with `x` and `y` coordinates of this vector.
     */
    fun toVec2() = Vec2(x = this.x, y = this.y)

    /**
     * Returns a 3D vector with `x`, `y` and `z` coordinates of this vector.
     */
    fun toVec3() = Vec3(x = this.x, y = this.y, z = this.z)

    /**
     * Returns a list of coordinates of this vector.
     */
    override fun toList(): List<Float> = listOf(x, y, z, w)

    /**
     * Returns an array of coordinates of this vector.
     */
    override fun toFloatArray(): FloatArray = floatArrayOf(x, y, z, w)

    companion object {

        private const val SIZE = 4

        /**
         * Returns an instance of [Vec4] with the given [list] of coordinates.
         *
         * If the size of the list of coordinates is different than 4, [IllegalArgumentException]
         * is thrown.
         */
        fun fromList(list: List<Float>): Vec4 {
            require(list.size == SIZE)
            val (x, y, z, w) = list
            return Vec4(x, y, z, w)
        }
    }
}
