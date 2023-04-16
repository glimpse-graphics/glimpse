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

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.math.roundToInt

private const val COLOR_CHANNEL_MAX = 255f

/**
 * Returns a [Vec3] representation of a given [color].
 *
 * @deprecated Use [toVec3] instead.
 */
@Suppress("FunctionNaming")
@Deprecated(
    message = "Use Int.toVec3() instead",
    replaceWith = ReplaceWith("color.toVec3()", "graphics.glimpse.types.toVec3")
)
fun Vec3(@ColorInt color: Int): Vec3<Float> = Vec3(
    x = Color.red(color) / COLOR_CHANNEL_MAX,
    y = Color.green(color) / COLOR_CHANNEL_MAX,
    z = Color.blue(color) / COLOR_CHANNEL_MAX
)

/**
 * Returns a [Vec4] representation of a given [color].
 *
 * @deprecated Use [toVec4] instead.
 */
@Suppress("FunctionNaming")
@Deprecated(
    message = "Use Int.toVec4() instead",
    replaceWith = ReplaceWith("color.toVec4()", "graphics.glimpse.types.toVec4")
)
fun Vec4(@ColorInt color: Int): Vec4<Float> = Vec4(
    x = Color.red(color) / COLOR_CHANNEL_MAX,
    y = Color.green(color) / COLOR_CHANNEL_MAX,
    z = Color.blue(color) / COLOR_CHANNEL_MAX,
    w = Color.alpha(color) / COLOR_CHANNEL_MAX
)

/**
 * Returns a [Vec3] representation of this color int.
 *
 * @since v1.3.0
 */
fun @receiver:ColorInt Int.toVec3(): Vec3<Float> =
    Vec3(
        x = Color.red(this) / COLOR_CHANNEL_MAX,
        y = Color.green(this) / COLOR_CHANNEL_MAX,
        z = Color.blue(this) / COLOR_CHANNEL_MAX
    )

/**
 * Returns a [Vec4] representation of this color int.
 *
 * @since v1.3.0
 */
fun @receiver:ColorInt Int.toVec4(): Vec4<Float> =
    Vec4(
        x = Color.red(this) / COLOR_CHANNEL_MAX,
        y = Color.green(this) / COLOR_CHANNEL_MAX,
        z = Color.blue(this) / COLOR_CHANNEL_MAX,
        w = Color.alpha(this) / COLOR_CHANNEL_MAX
    )

/**
 * Returns a color int representation of this [Vec3].
 *
 * @since v1.3.0
 */
@ColorInt
fun Vec3<Float>.toColorInt(): Int =
    Color.rgb(
        (this.r * COLOR_CHANNEL_MAX).roundToInt(),
        (this.g * COLOR_CHANNEL_MAX).roundToInt(),
        (this.b * COLOR_CHANNEL_MAX).roundToInt()
    )

/**
 * Returns a color int representation of this [Vec4].
 *
 * @since v1.3.0
 */
@ColorInt
fun Vec4<Float>.toColorInt(): Int =
    Color.argb(
        (this.a * COLOR_CHANNEL_MAX).roundToInt(),
        (this.r * COLOR_CHANNEL_MAX).roundToInt(),
        (this.g * COLOR_CHANNEL_MAX).roundToInt(),
        (this.b * COLOR_CHANNEL_MAX).roundToInt()
    )
