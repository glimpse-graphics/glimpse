package graphics.glimpse.types

/**
 * A range of values of type [Angle].
 */
data class AngleRange(
    override val start: Angle,
    override val endInclusive: Angle
) : ClosedRange<Angle>
