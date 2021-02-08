package graphics.glimpse.types

import android.graphics.Color
import androidx.annotation.ColorInt

private const val COLOR_CHANNEL_MAX = 255f

/**
 * Returns a [Vec3] representation of a given [color].
 */
fun Vec3(@ColorInt color: Int): Vec3 = Vec3(
    x = Color.red(color) / COLOR_CHANNEL_MAX,
    y = Color.green(color) / COLOR_CHANNEL_MAX,
    z = Color.blue(color) / COLOR_CHANNEL_MAX
)

/**
 * Returns a [Vec4] representation of a given [color].
 */
fun Vec4(@ColorInt color: Int): Vec4 = Vec4(
    x = Color.red(color) / COLOR_CHANNEL_MAX,
    y = Color.green(color) / COLOR_CHANNEL_MAX,
    z = Color.blue(color) / COLOR_CHANNEL_MAX,
    w = Color.alpha(color) / COLOR_CHANNEL_MAX
)
