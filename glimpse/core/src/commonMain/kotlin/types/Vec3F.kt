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

internal data class Vec3F(
    override val x: Float,
    override val y: Float,
    override val z: Float
) : Vec3<Float>() {

    override val ring: Ring<Float> get() = FloatRing

    override fun create(x: Float, y: Float, z: Float): Vec3<Float> = Vec3(x = x, y = y, z = z)

    override fun magnitude(): Float = sqrt((this.x * this.x + this.y * this.y + this.z * this.z))

    override fun toVec2(): Vec2<Float> = Vec2(x = this.x, y = this.y)
    override fun toVec4(w: Float): Vec4<Float> = Vec4(x = this.x, y = this.y, z = this.z, w = w)

    override fun toIntVector(): Vec3<Int> =
        Vec3(x = this.x.roundToInt(), y = this.y.roundToInt(), z = this.z.roundToInt())
    override fun toLongVector(): Vec3<Long> =
        Vec3(x = this.x.roundToLong(), y = this.y.roundToLong(), z = this.z.roundToLong())
    override fun toFloatVector(): Vec3<Float> = this
    override fun toDoubleVector(): Vec3<Double> =
        Vec3(x = this.x.toDouble(), y = this.y.toDouble(), z = this.z.toDouble())

    override fun toList(): List<Float> = listOf(x, y, z)

    override fun toString(): String = toString(className = "Vec3")
}
