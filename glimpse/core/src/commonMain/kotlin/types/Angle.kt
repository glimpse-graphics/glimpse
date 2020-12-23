package graphics.glimpse.types

import graphics.glimpse.PI

/**
 * Combined angle measure in [degrees][deg] and [radians][rad].
 */
class Angle private constructor(
    val deg: Float,
    val rad: Float
) : Comparable<Angle> {

    /**
     * Returns this angle.
     */
    operator fun unaryPlus(): Angle = this

    /**
     * Returns the negative of this angle.
     */
    operator fun unaryMinus(): Angle = fromDeg(deg = -deg)

    /**
     * Adds the [other] angle to this angle.
     */
    operator fun plus(other: Angle): Angle = fromDeg(deg = this.deg + other.deg)

    /**
     * Subtracts the [other] angle from this angle.
     */
    operator fun minus(other: Angle): Angle = fromDeg(deg = this.deg - other.deg)

    /**
     * Multiplies this angle by the specified [number].
     */
    operator fun times(number: Float): Angle = fromDeg(deg = deg * number)

    /**
     * Divides this angle by the specified [number].
     */
    operator fun div(number: Float): Angle = fromDeg(deg = deg / number)

    /**
     * Divides this angle by the [other] angle.
     */
    operator fun div(other: Angle): Float = this.deg / other.deg

    /**
     * Calculates remainder of dividing this angle by the [other] angle.
     */
    operator fun rem(other: Angle): Angle = fromDeg(deg = this.deg % other.deg)

    /**
     * Ensures that this value lies in the specified range [minimumAngle]..[maximumAngle].
     */
    fun coerceIn(minimumAngle: Angle, maximumAngle: Angle): Angle =
        fromDeg(deg = deg.coerceIn(minimumAngle.deg, maximumAngle.deg))

    /**
     * Creates a range from this angle to the specified [other] angle.
     */
    operator fun rangeTo(other: Angle): AngleRange = AngleRange(start = this, endInclusive = other)

    /**
     * Compares this angle to the specified [other] angle.
     * Returns zero if this angle is equal to the specified other angle,
     * a negative number if it's less than other, or a positive number if it's greater than other.
     */
    override fun compareTo(other: Angle): Int = this.deg.compareTo(other.deg)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Angle

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
        private const val STRAIGHT_ANGLE_DEG = 180.0f

        /**
         * Angle measure of 0 degrees.
         */
        val nullAngle = fromDeg(deg = 0f)

        /**
         * Angle measure of 90 degrees.
         */
        val rightAngle = fromDeg(deg = 90f)

        /**
         * Angle measure of 180 degrees.
         */
        val straightAngle = fromDeg(deg = STRAIGHT_ANGLE_DEG)

        /**
         * Angle measure of 360 degrees.
         */
        val fullAngle = fromDeg(deg = 360f)

        /**
         * Creates a new angle measure from the given measure in [degrees][deg].
         */
        fun fromDeg(deg: Float): Angle = Angle(deg, (deg * PI / STRAIGHT_ANGLE_DEG))

        /**
         * Creates a new angle measure from the given measure in [radians][rad].
         */
        fun fromRad(rad: Float): Angle = Angle((rad * STRAIGHT_ANGLE_DEG / PI), rad)
    }
}
