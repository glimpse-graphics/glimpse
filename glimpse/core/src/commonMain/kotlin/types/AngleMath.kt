package graphics.glimpse.types

/**
 * Computes the sine of the specified [angle].
 */
fun sin(angle: Angle): Float = kotlin.math.sin(angle.rad)

/**
 * Computes the cosine of the specified [angle].
 */
fun cos(angle: Angle): Float = kotlin.math.cos(angle.rad)

/**
 * Computes the tangent of the specified [angle].
 */
fun tan(angle: Angle): Float = kotlin.math.tan(angle.rad)
