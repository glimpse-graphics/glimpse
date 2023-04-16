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

internal data class Vec2F(
    override val x: Float,
    override val y: Float
) : Vec2<Float>() {

    override val ring: Ring<Float> get() = FloatRing

    override fun create(x: Float, y: Float): Vec2<Float> = Vec2(x = x, y = y)

    override fun cross(other: Vec2<Float>): Vec3<Float> =
        Vec3(x = 0f, y = 0f, z = this.x * other.y - this.y * other.x)

    override fun atan(): Angle<Float> = Angle.atan2(y, x)

    override fun magnitude(): Float = sqrt((this.x * this.x + this.y * this.y))

    override fun toVec3(z: Float): Vec3<Float> = Vec3(x = this.x, y = this.y, z = z)
    override fun toVec4(z: Float, w: Float): Vec4<Float> = Vec4(x = this.x, y = this.y, z = z, w = w)

    override fun toIntVector(): Vec2<Int> = Vec2(x = this.x.roundToInt(), y = this.y.roundToInt())
    override fun toLongVector(): Vec2<Long> = Vec2(x = this.x.roundToLong(), y = this.y.roundToLong())
    override fun toFloatVector(): Vec2<Float> = this
    override fun toDoubleVector(): Vec2<Double> = Vec2(x = this.x.toDouble(), y = this.y.toDouble())

    override fun toString(): String = toString(className = "Vec2")
}
