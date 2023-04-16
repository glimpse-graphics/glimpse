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

internal data class Vec2F(
    override val x: Float,
    override val y: Float
) : Vec2<Float> {

    override operator fun unaryPlus(): Vec2F = this

    override operator fun unaryMinus(): Vec2F = Vec2F(x = -this.x, y = -this.y)

    override operator fun plus(other: Vec2<Float>): Vec2F = Vec2F(x = this.x + other.x, y = this.y + other.y)

    override operator fun minus(other: Vec2<Float>): Vec2F = Vec2F(x = this.x - other.x, y = this.y - other.y)

    override operator fun times(number: Float): Vec2F = Vec2F(x = this.x * number, y = this.y * number)

    override operator fun div(number: Float): Vec2F = Vec2F(x = this.x / number, y = this.y / number)

    override infix fun dot(other: Vec2<Float>): Float =
        (this.toList() zip other.toList()).map { (a, b) -> a * b }.sum()

    override fun atan(): Angle<Float> = Angle.atan2(y, x)

    override fun magnitude(): Float = sqrt((this.x * this.x + this.y * this.y))

    override fun normalize(): Vec2<Float> = this / this.magnitude()

    override fun toVec3(): Vec3<Float> = toVec3(z = 0f)
    override fun toVec3(z: Float): Vec3<Float> = Vec3(x = this.x, y = this.y, z = z)

    override fun toVec4(): Vec4<Float> = toVec4(w = 0f)
    override fun toVec4(w: Float): Vec4<Float> = toVec4(z = 0f, w = w)
    override fun toVec4(z: Float, w: Float): Vec4<Float> = Vec4(x = this.x, y = this.y, z = z, w = w)

    override fun toFloatVector(): Vec2<Float> = this
    override fun toDoubleVector(): Vec2<Double> = Vec2(x = this.x.toDouble(), y = this.y.toDouble())

    override fun toList(): List<Float> = listOf(x, y)
}
