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

import kotlin.math.sqrt

internal data class Vec3F(
    override val x: Float,
    override val y: Float,
    override val z: Float
) : Vec3<Float> {

    override operator fun unaryPlus(): Vec3F = this

    override operator fun unaryMinus(): Vec3F = Vec3F(x = -this.x, y = -this.y, z = -this.z)

    override operator fun plus(other: Vec3<Float>): Vec3F = Vec3F(
        x = this.x + other.x,
        y = this.y + other.y,
        z = this.z + other.z
    )

    override operator fun minus(other: Vec3<Float>): Vec3F = Vec3F(
        x = this.x - other.x,
        y = this.y - other.y,
        z = this.z - other.z
    )

    override operator fun times(number: Float): Vec3F = Vec3F(
        x = this.x * number,
        y = this.y * number,
        z = this.z * number
    )

    override operator fun div(number: Float): Vec3F = Vec3F(
        x = this.x / number,
        y = this.y / number,
        z = this.z / number
    )

    override infix fun dot(other: Vec3<Float>): Float =
        (this.toList() zip other.toList()).map { (a, b) -> a * b }.sum()

    override infix fun cross(other: Vec3<Float>): Vec3F = Vec3F(
        x = this.y * other.z - this.z * other.y,
        y = this.z * other.x - this.x * other.z,
        z = this.x * other.y - this.y * other.x
    )

    override fun magnitude(): Float = sqrt((this.x * this.x + this.y * this.y + this.z * this.z))

    override fun normalize(): Vec3<Float> = this / this.magnitude()

    override fun toVec2(): Vec2<Float> = Vec2(x = this.x, y = this.y)

    override fun toVec4(): Vec4<Float> = toVec4(w = 0f)
    override fun toVec4(w: Float): Vec4<Float> = Vec4(x = this.x, y = this.y, z = this.z, w = w)

    override fun toFloatVector(): Vec3<Float> = this
    override fun toDoubleVector(): Vec3<Double> =
        Vec3(x = this.x.toDouble(), y = this.y.toDouble(), z = this.z.toDouble())

    override fun toList(): List<Float> = listOf(x, y, z)
}
