package graphics.glimpse.types

import java.awt.Color

private const val VEC3_SIZE = 3
private const val VEC4_SIZE = 4

/**
 * Returns a [Vec3] representation of a given [color].
 */
fun Vec3(color: Color): Vec3 {
    val output = FloatArray(size = VEC3_SIZE)
    color.getRGBColorComponents(output)
    return Vec3.fromList(output.toList())
}

/**
 * Returns a [Vec4] representation of a given [color].
 */
fun Vec4(color: Color): Vec4 {
    val output = FloatArray(size = VEC4_SIZE)
    color.getRGBComponents(output)
    return Vec4.fromList(output.toList())
}
