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

/**
 * Combined angle measure in [degrees][deg] and [radians][rad].
 */
interface Angle<T : Number> : Comparable<Angle<T>> {

    /**
     * Angle measure in degrees.
     */
    val deg: T

    /**
     * Angle measure in radians.
     */
    val rad: T

    /**
     * Returns this angle.
     */
    operator fun unaryPlus(): Angle<T>

    /**
     * Returns the negative of this angle.
     */
    operator fun unaryMinus(): Angle<T>

    /**
     * Adds the [other] angle to this angle.
     */
    operator fun plus(other: Angle<T>): Angle<T>

    /**
     * Subtracts the [other] angle from this angle.
     */
    operator fun minus(other: Angle<T>): Angle<T>

    /**
     * Multiplies this angle by the specified [number].
     */
    operator fun times(number: T): Angle<T>

    /**
     * Divides this angle by the specified [number].
     */
    operator fun div(number: T): Angle<T>

    /**
     * Divides this angle by the [other] angle.
     */
    operator fun div(other: Angle<T>): T

    /**
     * Calculates remainder of dividing this angle by the [other] angle.
     */
    operator fun rem(other: Angle<T>): Angle<T>

    /**
     * Ensures that this value lies in the specified range [minimumAngle]..[maximumAngle].
     */
    fun coerceIn(minimumAngle: Angle<T>, maximumAngle: Angle<T>): Angle<T>

    /**
     * Creates a range from this angle to the specified [other] angle.
     */
    operator fun rangeTo(other: Angle<T>): ClosedRange<Angle<T>>

    /**
     * Compares this angle to the specified [other] angle.
     * Returns zero if this angle is equal to the specified other angle,
     * a negative number if it's less than other, or a positive number if it's greater than other.
     */
    override fun compareTo(other: Angle<T>): Int

    /**
     * Returns `Angle<Float>` with the same value as this angle.
     */
    fun toFloatAngle(): Angle<Float>

    /**
     * Returns `Angle<Double>` with the same value as this angle.
     */
    fun toDoubleAngle(): Angle<Double>

    companion object {

        /**
         * Angle measure of 0 degrees.
         */
        val nullAngle: Angle<Float> = fromDeg(deg = 0)

        /**
         * Angle measure of 90 degrees.
         */
        val rightAngle: Angle<Float> = fromDeg(deg = 90)

        /**
         * Angle measure of 180 degrees.
         */
        val straightAngle: Angle<Float> = fromDeg(deg = 180)

        /**
         * Angle measure of 360 degrees.
         */
        val fullAngle: Angle<Float> = fromDeg(deg = 360)

        /**
         * Creates a new angle measure from the given measure in [degrees][deg].
         *
         * @since v1.3.0
         */
        fun fromDeg(deg: Int): Angle<Float> = AngleF.fromDeg(deg)

        /**
         * Creates a new angle measure from the given measure in [degrees][deg].
         *
         * @since v1.3.0
         */
        fun fromDeg(deg: Long): Angle<Double> = AngleD.fromDeg(deg)

        /**
         * Creates a new angle measure from the given measure in [degrees][deg].
         */
        fun fromDeg(deg: Float): Angle<Float> = AngleF.fromDeg(deg)

        /**
         * Creates a new angle measure from the given measure in [degrees][deg].
         *
         * @since v1.3.0
         */
        fun fromDeg(deg: Double): Angle<Double> = AngleD.fromDeg(deg)

        /**
         * Creates a new angle measure from the given measure in [radians][rad].
         */
        fun fromRad(rad: Float): Angle<Float> = AngleF.fromRad(rad)

        /**
         * Creates a new angle measure from the given measure in [radians][rad].
         *
         * @since v1.3.0
         */
        fun fromRad(rad: Double): Angle<Double> = AngleD.fromRad(rad)

        /**
         * Returns the arc tangent of a given [value].
         */
        fun atan(value: Float): Angle<Float> = AngleF.atan(value)

        /**
         * Returns the arc tangent of a given [value].
         *
         * @since v1.3.0
         */
        fun atan(value: Double): Angle<Double> = AngleD.atan(value)

        /**
         * Returns the arc tangent of a given value [y]/[x].
         */
        fun atan2(y: Float, x: Float): Angle<Float> = AngleF.atan2(x, y)

        /**
         * Returns the arc tangent of a given value [y]/[x].
         *
         * @since v1.3.0
         */
        fun atan2(y: Double, x: Double): Angle<Double> = AngleD.atan2(x, y)
    }
}
