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

internal data class Vec3D(
    override val x: Double,
    override val y: Double,
    override val z: Double
) : Vec3<Double> {

    override operator fun unaryPlus(): Vec3D = this

    override operator fun unaryMinus(): Vec3D = Vec3D(x = -this.x, y = -this.y, z = -this.z)

    override operator fun plus(other: Vec3<Double>): Vec3D = Vec3D(
        x = this.x + other.x,
        y = this.y + other.y,
        z = this.z + other.z
    )

    override operator fun minus(other: Vec3<Double>): Vec3D = Vec3D(
        x = this.x - other.x,
        y = this.y - other.y,
        z = this.z - other.z
    )

    override operator fun times(number: Double): Vec3D = Vec3D(
        x = this.x * number,
        y = this.y * number,
        z = this.z * number
    )

    override operator fun div(number: Double): Vec3D = Vec3D(
        x = this.x / number,
        y = this.y / number,
        z = this.z / number
    )

    override infix fun dot(other: Vec3<Double>): Double =
        (this.toList() zip other.toList()).sumOf { (a, b) -> a * b }

    override infix fun cross(other: Vec3<Double>): Vec3D = Vec3D(
        x = this.y * other.z - this.z * other.y,
        y = this.z * other.x - this.x * other.z,
        z = this.x * other.y - this.y * other.x
    )

    override fun magnitude(): Double = sqrt((this.x * this.x + this.y * this.y + this.z * this.z))

    override fun normalize(): Vec3<Double> = this / this.magnitude()

    override fun toVec2(): Vec2<Double> = Vec2(x = this.x, y = this.y)

    override fun toVec4(): Vec4<Double> = toVec4(w = 0.0)
    override fun toVec4(w: Double): Vec4<Double> = Vec4(x = this.x, y = this.y, z = this.z, w = w)

    override fun toFloatVector(): Vec3<Float> =
        Vec3(x = this.x.toFloat(), y = this.y.toFloat(), z = this.z.toFloat())
    override fun toDoubleVector(): Vec3<Double> = this

    override fun toList(): List<Double> = listOf(x, y, z)
}
