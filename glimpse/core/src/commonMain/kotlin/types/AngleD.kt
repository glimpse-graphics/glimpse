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

import kotlin.math.PI

internal class AngleD private constructor(
    override val deg: Double,
    override val rad: Double
) : Angle<Double> {

    override operator fun unaryPlus(): AngleD = this

    override operator fun unaryMinus(): AngleD = fromDeg(deg = -deg)

    override operator fun plus(other: Angle<Double>): AngleD = fromDeg(deg = this.deg + other.deg)

    override operator fun minus(other: Angle<Double>): AngleD = fromDeg(deg = this.deg - other.deg)

    override operator fun times(number: Double): AngleD = fromDeg(deg = deg * number)

    override operator fun div(number: Double): AngleD = fromDeg(deg = deg / number)

    override operator fun div(other: Angle<Double>): Double = this.deg / other.deg

    override operator fun rem(other: Angle<Double>): AngleD = fromDeg(deg = this.deg % other.deg)

    override fun coerceIn(minimumAngle: Angle<Double>, maximumAngle: Angle<Double>): AngleD =
        fromDeg(deg = deg.coerceIn(minimumAngle.deg, maximumAngle.deg))

    override operator fun rangeTo(other: Angle<Double>): AngleRange<Double> =
        AngleRange(start = this, endInclusive = other)

    override fun compareTo(other: Angle<Double>): Int = this.deg.compareTo(other.deg)

    override fun toFloatAngle(): AngleF = AngleF.fromDeg(this.deg.toFloat())

    override fun toDoubleAngle(): AngleD = this

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
        private const val STRAIGHT_ANGLE_DEG = 180L

        internal fun fromDeg(deg: Long): AngleD = AngleD(deg.toDouble(), (deg * PI / STRAIGHT_ANGLE_DEG))
        internal fun fromDeg(deg: Double): AngleD = AngleD(deg, (deg * PI / STRAIGHT_ANGLE_DEG))

        internal fun fromRad(rad: Double): AngleD = AngleD((rad * STRAIGHT_ANGLE_DEG / PI), rad)

        internal fun atan(value: Double): AngleD = fromRad(kotlin.math.atan(value))
        internal fun atan2(y: Double, x: Double): AngleD = fromRad(kotlin.math.atan2(y, x))
    }
}
