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

internal data class Vec4I(
    override val x: Int,
    override val y: Int,
    override val z: Int,
    override val w: Int
) : Vec4<Int>() {

    override val ring: Ring<Int> get() = IntRing

    override fun toVec2(): Vec2<Int> = Vec2(x = this.x, y = this.y)
    override fun toVec3(): Vec3<Int> = Vec3(x = this.x, y = this.y, z = this.z)

    override fun toIntVector(): Vec4<Int> = this
    override fun toLongVector(): Vec4<Long> =
        Vec4(x = this.x.toLong(), y = this.y.toLong(), z = this.z.toLong(), w = this.w.toLong())
    override fun toFloatVector(): Vec4<Float> =
        Vec4(x = this.x.toFloat(), y = this.y.toFloat(), z = this.z.toFloat(), w = this.w.toFloat())
    override fun toDoubleVector(): Vec4<Double> =
        Vec4(x = this.x.toDouble(), y = this.y.toDouble(), z = this.z.toDouble(), w = this.w.toDouble())

    override fun toList(): List<Int> = listOf(x, y, z, w)

    override fun toString(): String = toString(className = "Vec4")
}
