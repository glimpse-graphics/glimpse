/*
 * Copyright 2020-2023 Glimpse Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.types

import kotlin.math.PI
import kotlin.reflect.KClass

/**
 * Combined angle measure in [degrees][deg] and [radians][rad].
 */
class Angle<T> private constructor(

    /**
     * Angle measure in degrees.
     */
    val deg: T,

    /**
     * Angle measure in radians.
     */
    val rad: T
) : Comparable<Angle<T>> where T : Number, T : Comparable<T> {

    /**
     * Returns this angle.
     */
    operator fun unaryPlus(): Angle<T> = this

    /**
     * Returns the negative of this angle.
     */
    operator fun unaryMinus(): Angle<T> =
        Angle(deg = -this.deg, rad = -this.rad)

    /**
     * Adds the [other] angle to this angle.
     */
    operator fun plus(other: Angle<T>): Angle<T> =
        Angle(deg = this.deg + other.deg, rad = this.rad + other.rad)

    /**
     * Subtracts the [other] angle from this angle.
     */
    operator fun minus(other: Angle<T>): Angle<T> =
        Angle(deg = this.deg - other.deg, rad = this.rad - other.rad)

    /**
     * Multiplies this angle by the specified [number].
     */
    operator fun times(number: T): Angle<T> =
        Angle(deg = this.deg * number, rad = this.rad * number)

    /**
     * Divides this angle by the specified [number].
     */
    operator fun div(number: T): Angle<T> =
        Angle(deg = this.deg / number, rad = this.rad / number)

    /**
     * Divides this angle by the [other] angle.
     */
    operator fun div(other: Angle<T>): T =
        this.deg / other.deg

    /**
     * Calculates remainder of dividing this angle by the [other] angle.
     */
    operator fun rem(other: Angle<T>): Angle<T> =
        Angle(deg = this.deg % other.deg, rad = this.rad % other.rad)

    /**
     * Ensures that this value lies in the specified range [minimumAngle]..[maximumAngle].
     */
    fun coerceIn(minimumAngle: Angle<T>, maximumAngle: Angle<T>): Angle<T> =
        Angle(
            deg = this.deg.coerceIn(minimumAngle.deg, maximumAngle.deg),
            rad = this.rad.coerceIn(minimumAngle.rad, maximumAngle.rad)
        )

    /**
     * Creates a range from this angle to the specified [other] angle.
     */
    operator fun rangeTo(other: Angle<T>): AngleRange<T> =
        AngleRange(start = this, endInclusive = other)

    /**
     * Compares this angle to the specified [other] angle.
     * Returns zero if this angle is equal to the specified other angle,
     * a negative number if it's less than other, or a positive number if it's greater than other.
     */
    override fun compareTo(other: Angle<T>): Int =
        this.deg.compareTo(other.deg)

    /**
     * Returns `Angle<Float>` with the same value as this angle.
     */
    fun toFloatAngle(): Angle<Float> =
        Angle(deg = this.deg.toFloat(), rad = this.rad.toFloat())

    /**
     * Returns `Angle<Double>` with the same value as this angle.
     */
    fun toDoubleAngle(): Angle<Double> {
        val doubleDeg = this.deg.toDouble()
        return Angle(deg = doubleDeg, rad = (doubleDeg * PI / STRAIGHT_ANGLE_DEG))
    }

    /**
     * Returns string representation of this angle.
     */
    override fun toString(): String = "Angle(deg=$deg, rad=$rad)"

    companion object {
        private const val STRAIGHT_ANGLE_DEG = 180

        /**
         * Angle measure of 0 degrees.
         */
        @Deprecated(
            message = "Use Angle.nullAngle() instead.",
            replaceWith = ReplaceWith(expression = "Angle.nullAngle<Float>()")
        )
        val nullAngle: Angle<Float> =
            fromDeg(deg = 0)

        /**
         * Angle measure of 90 degrees.
         */
        @Deprecated(
            message = "Use Angle.rightAngle() instead.",
            replaceWith = ReplaceWith(expression = "Angle.rightAngle<Float>()")
        )
        val rightAngle: Angle<Float> =
            fromDeg(deg = 90)

        /**
         * Angle measure of 180 degrees.
         */
        @Deprecated(
            message = "Use Angle.straightAngle() instead.",
            replaceWith = ReplaceWith(expression = "Angle.straightAngle<Float>()")
        )
        val straightAngle: Angle<Float> =
            fromDeg(deg = 180)

        /**
         * Angle measure of 360 degrees.
         */
        @Deprecated(
            message = "Use Angle.fullAngle() instead.",
            replaceWith = ReplaceWith(expression = "Angle.fullAngle<Float>()")
        )
        val fullAngle: Angle<Float> =
            fromDeg(deg = 360)

        /**
         * Returns angle measure of 0 degrees.
         *
         * @since v1.3.0
         */
        inline fun <reified T> nullAngle(): Angle<T> where T : Number, T : Comparable<T> =
            nullAngle(T::class)

        /**
         * Returns angle measure of 0 degrees.
         *
         * @since v1.3.0
         */
        fun <T> nullAngle(type: KClass<T>): Angle<T> where T : Number, T : Comparable<T> =
            Angle(deg = zero(type = type), rad = zero(type = type))

        /**
         * Returns angle measure of 90 degrees.
         *
         * @since v1.3.0
         */
        inline fun <reified T> rightAngle(): Angle<T> where T : Number, T : Comparable<T> =
            rightAngle(T::class)

        /**
         * Returns angle measure of 90 degrees.
         *
         * @since v1.3.0
         */
        fun <T> rightAngle(type: KClass<T>): Angle<T> where T : Number, T : Comparable<T> =
            when (type) {
                Float::class -> fromDeg(deg = 90f)
                Double::class -> fromDeg(deg = 90.0)
                else -> throw UnsupportedOperationException("Angles of type ${type.simpleName} are not supported.")
            } as Angle<T>

        /**
         * Returns angle measure of 180 degrees.
         *
         * @since v1.3.0
         */
        inline fun <reified T> straightAngle(): Angle<T> where T : Number, T : Comparable<T> =
            straightAngle(T::class)

        /**
         * Returns angle measure of 180 degrees.
         *
         * @since v1.3.0
         */
        fun <T> straightAngle(type: KClass<T>): Angle<T> where T : Number, T : Comparable<T> =
            when (type) {
                Float::class -> fromDeg(deg = 180f)
                Double::class -> fromDeg(deg = 180.0)
                else -> throw UnsupportedOperationException("Angles of type ${type.simpleName} are not supported.")
            } as Angle<T>

        /**
         * Returns angle measure of 360 degrees.
         *
         * @since v1.3.0
         */
        inline fun <reified T> fullAngle(): Angle<T> where T : Number, T : Comparable<T> =
            fullAngle(T::class)

        /**
         * Returns angle measure of 360 degrees.
         *
         * @since v1.3.0
         */
        fun <T> fullAngle(type: KClass<T>): Angle<T> where T : Number, T : Comparable<T> =
            when (type) {
                Float::class -> fromDeg(deg = 360f)
                Double::class -> fromDeg(deg = 360.0)
                else -> throw UnsupportedOperationException("Angles of type ${type.simpleName} are not supported.")
            } as Angle<T>

        /**
         * Creates a new angle measure from the given measure in [degrees][deg].
         *
         * @since v1.3.0
         */
        fun fromDeg(deg: Int): Angle<Float> =
            fromDeg(deg.toFloat())

        /**
         * Creates a new angle measure from the given measure in [degrees][deg].
         */
        fun fromDeg(deg: Float): Angle<Float> =
            Angle(deg = deg, rad = (deg * PI / STRAIGHT_ANGLE_DEG).toFloat())

        /**
         * Creates a new angle measure from the given measure in [degrees][deg].
         *
         * @since v1.3.0
         */
        fun fromDeg(deg: Long): Angle<Double> =
            fromDeg(deg.toDouble())

        /**
         * Creates a new angle measure from the given measure in [degrees][deg].
         *
         * @since v1.3.0
         */
        fun fromDeg(deg: Double): Angle<Double> =
            Angle(deg = deg, rad = (deg * PI / STRAIGHT_ANGLE_DEG))

        /**
         * Creates a new angle measure from the given measure in [radians][rad].
         */
        fun fromRad(rad: Float): Angle<Float> =
            Angle(deg = (rad * STRAIGHT_ANGLE_DEG / PI).toFloat(), rad = rad)

        /**
         * Creates a new angle measure from the given measure in [radians][rad].
         *
         * @since v1.3.0
         */
        fun fromRad(rad: Double): Angle<Double> =
            Angle(deg = (rad * STRAIGHT_ANGLE_DEG / PI), rad = rad)

        /**
         * Returns the arc tangent of a given [value].
         */
        fun <T> atan(value: T): Angle<T> where T : Number, T : Comparable<T> {
            val rad = atan(x = value)
            val deg = (rad * STRAIGHT_ANGLE_DEG / PI)
            return Angle(deg = deg, rad = rad)
        }

        /**
         * Returns the arc tangent of a given value [y]/[x].
         */
        fun <T> atan2(y: T, x: T): Angle<T> where T : Number, T : Comparable<T> {
            val rad = graphics.glimpse.types.atan2(y = y, x = x)
            val deg = (rad * STRAIGHT_ANGLE_DEG / PI)
            return Angle(deg = deg, rad = rad)
        }
    }
}
