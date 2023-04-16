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

internal data class Vec2D(
    override val x: Double,
    override val y: Double
) : Vec2<Double> {

    override operator fun unaryPlus(): Vec2D = this

    override operator fun unaryMinus(): Vec2D = Vec2D(x = -this.x, y = -this.y)

    override operator fun plus(other: Vec2<Double>): Vec2D = Vec2D(x = this.x + other.x, y = this.y + other.y)

    override operator fun minus(other: Vec2<Double>): Vec2D = Vec2D(x = this.x - other.x, y = this.y - other.y)

    override operator fun times(number: Double): Vec2D = Vec2D(x = this.x * number, y = this.y * number)

    override operator fun div(number: Double): Vec2D = Vec2D(x = this.x / number, y = this.y / number)

    override infix fun dot(other: Vec2<Double>): Double =
        (this.toList() zip other.toList()).sumOf { (a, b) -> a * b }

    override fun atan(): Angle<Double> = Angle.atan2(y, x)

    override fun magnitude(): Double = sqrt((this.x * this.x + this.y * this.y))

    override fun normalize(): Vec2<Double> = this / this.magnitude()

    override fun toVec3(): Vec3<Double> = toVec3(z = 0.0)
    override fun toVec3(z: Double): Vec3<Double> = Vec3(x = this.x, y = this.y, z = z)

    override fun toVec4(): Vec4<Double> = toVec4(w = 0.0)
    override fun toVec4(w: Double): Vec4<Double> = toVec4(z = 0.0, w = w)
    override fun toVec4(z: Double, w: Double): Vec4<Double>  = Vec4(x = this.x, y = this.y, z = z, w = w)

    override fun toFloatVector(): Vec2<Float> = Vec2(x = this.x.toFloat(), y = this.y.toFloat())
    override fun toDoubleVector(): Vec2<Double> = this

    override fun toList(): List<Double> = listOf(x, y)
}
