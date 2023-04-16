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

internal data class Vec4D(
    override val x: Double,
    override val y: Double,
    override val z: Double,
    override val w: Double
) : Vec4<Double>() {

    override val ring: Ring<Double> get() = DoubleRing

    override fun toVec2(): Vec2<Double> = Vec2(x = this.x, y = this.y)
    override fun toVec3(): Vec3<Double> = Vec3(x = this.x, y = this.y, z = this.z)

    override fun toIntVector(): Vec4<Int> =
        Vec4(x = this.x.roundToInt(), y = this.y.roundToInt(), z = this.z.roundToInt(), w = this.w.roundToInt())
    override fun toLongVector(): Vec4<Long> =
        Vec4(x = this.x.roundToLong(), y = this.y.roundToLong(), z = this.z.roundToLong(), w = this.w.roundToLong())
    override fun toFloatVector(): Vec4<Float> =
        Vec4(x = this.x.toFloat(), y = this.y.toFloat(), z = this.z.toFloat(), w = this.w.toFloat())
    override fun toDoubleVector(): Vec4<Double> = this

    override fun toList(): List<Double> = listOf(x, y, z, w)

    override fun toString(): String = toString(className = "Vec4")
}
