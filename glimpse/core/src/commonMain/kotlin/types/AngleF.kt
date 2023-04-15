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

import graphics.glimpse.PI

internal class AngleF private constructor(
    override val deg: Float,
    override val rad: Float
) : Angle<Float> {

    override operator fun unaryPlus(): AngleF = this

    override operator fun unaryMinus(): AngleF = fromDeg(deg = -deg)

    override operator fun plus(other: Angle<Float>): AngleF = fromDeg(deg = this.deg + other.deg)

    override operator fun minus(other: Angle<Float>): AngleF = fromDeg(deg = this.deg - other.deg)

    override operator fun times(number: Float): AngleF = fromDeg(deg = deg * number)

    override operator fun div(number: Float): AngleF = fromDeg(deg = deg / number)

    override operator fun div(other: Angle<Float>): Float = this.deg / other.deg

    override operator fun rem(other: Angle<Float>): AngleF = fromDeg(deg = this.deg % other.deg)

    override fun coerceIn(minimumAngle: Angle<Float>, maximumAngle: Angle<Float>): AngleF =
        fromDeg(deg = deg.coerceIn(minimumAngle.deg, maximumAngle.deg))

    override operator fun rangeTo(other: Angle<Float>): AngleRange<Float> =
        AngleRange(start = this, endInclusive = other)

    override fun compareTo(other: Angle<Float>): Int = this.deg.compareTo(other.deg)

    override fun toFloatAngle(): AngleF = this

    override fun toDoubleAngle(): AngleD = AngleD.fromDeg(this.deg.toDouble())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Angle<*>) return false

        if (deg != other.deg) return false
        if (rad != other.rad) return false

        return true
    }

    override fun hashCode(): Int {
        var result = deg.hashCode()
        result = 31 * result + rad.hashCode()
        return result
    }

    override fun toString(): String = "Angle(deg=$deg, rad=$rad)"

    companion object {
        private const val STRAIGHT_ANGLE_DEG = 180

        internal fun fromDeg(deg: Int): AngleF = AngleF(deg.toFloat(), (deg * PI / STRAIGHT_ANGLE_DEG))
        internal fun fromDeg(deg: Float): AngleF = AngleF(deg, (deg * PI / STRAIGHT_ANGLE_DEG))

        internal fun fromRad(rad: Float): AngleF = AngleF((rad * STRAIGHT_ANGLE_DEG / PI), rad)

        internal fun atan(value: Float): AngleF = fromRad(kotlin.math.atan(value))
        internal fun atan2(y: Float, x: Float): AngleF = fromRad(kotlin.math.atan2(y, x))
    }
}
