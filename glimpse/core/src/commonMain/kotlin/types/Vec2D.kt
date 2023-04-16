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

import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.math.sqrt

internal data class Vec2D(
    override val x: Double,
    override val y: Double
) : Vec2<Double>() {

    override val ring: Ring<Double> get() = DoubleRing

    override fun create(x: Double, y: Double): Vec2<Double> = Vec2(x = x, y = y)

    override fun atan(): Angle<Double> = Angle.atan2(y, x)

    override fun magnitude(): Double = sqrt((this.x * this.x + this.y * this.y))

    override fun toVec3(z: Double): Vec3<Double> = Vec3(x = this.x, y = this.y, z = z)
    override fun toVec4(z: Double, w: Double): Vec4<Double>  = Vec4(x = this.x, y = this.y, z = z, w = w)

    override fun toIntVector(): Vec2<Int> = Vec2(x = this.x.roundToInt(), y = this.y.roundToInt())
    override fun toLongVector(): Vec2<Long> = Vec2(x = this.x.roundToLong(), y = this.y.roundToLong())
    override fun toFloatVector(): Vec2<Float> = Vec2(x = this.x.toFloat(), y = this.y.toFloat())
    override fun toDoubleVector(): Vec2<Double> = this

    override fun toString(): String = toString(className = "Vec2")
}
