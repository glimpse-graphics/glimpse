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

internal data class Vec2L(
    override val x: Long,
    override val y: Long
) : Vec2<Long>() {

    override val ring: Ring<Long> get() = LongRing

    override fun create(x: Long, y: Long): Vec2<Long> = Vec2(x = x, y = y)

    override fun atan(): Angle<Long> = throw UnsupportedOperationException("Cannot calculate atan for integer vector")

    override fun magnitude(): Long = throw UnsupportedOperationException("Cannot calculate magnitude of integer vector")

    override fun toVec3(z: Long): Vec3<Long> = Vec3(x = this.x, y = this.y, z = z)
    override fun toVec4(z: Long, w: Long): Vec4<Long> = Vec4(x = this.x, y = this.y, z = z, w = w)

    override fun toIntVector(): Vec2<Int> = Vec2(x = this.x.toInt(), y = this.y.toInt())
    override fun toLongVector(): Vec2<Long> = this
    override fun toFloatVector(): Vec2<Float> = Vec2(x = this.x.toFloat(), y = this.y.toFloat())
    override fun toDoubleVector(): Vec2<Double> = Vec2(x = this.x.toDouble(), y = this.y.toDouble())

    override fun toString(): String = toString(className = "Vec2")
}
