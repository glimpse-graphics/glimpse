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

internal data class Vec2I(
    override val x: Int,
    override val y: Int
) : Vec2<Int>() {

    override val ring: Ring<Int> get() = IntRing

    override fun create(x: Int, y: Int): Vec2<Int> = Vec2(x = x, y = y)

    override fun atan(): Angle<Int> = throw UnsupportedOperationException("Cannot calculate atan for integer vector")

    override fun magnitude(): Int = throw UnsupportedOperationException("Cannot calculate magnitude of integer vector")

    override fun toVec3(z: Int): Vec3<Int> = Vec3(x = this.x, y = this.y, z = z)
    override fun toVec4(z: Int, w: Int): Vec4<Int> = Vec4(x = this.x, y = this.y, z = z, w = w)

    override fun toIntVector(): Vec2<Int> = this
    override fun toLongVector(): Vec2<Long> = Vec2(x = this.x.toLong(), y = this.y.toLong())
    override fun toFloatVector(): Vec2<Float> = Vec2(x = this.x.toFloat(), y = this.y.toFloat())
    override fun toDoubleVector(): Vec2<Double> = Vec2(x = this.x.toDouble(), y = this.y.toDouble())

    override fun toString(): String = toString(className = "Vec2")
}
