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

import java.awt.Color

private const val VEC3_SIZE = 3
private const val VEC4_SIZE = 4

/**
 * Returns a [Vec3] representation of a given [color].
 *
 * @deprecated Use [toVec3] instead.
 */
@Suppress("FunctionNaming")
@Deprecated(
    message = "Use Color.toVec3() instead",
    replaceWith = ReplaceWith("color.toVec3()", "graphics.glimpse.types.toVec3")
)
fun Vec3(color: Color): Vec3<Float> {
    val output = FloatArray(size = VEC3_SIZE)
    color.getRGBColorComponents(output)
    return Vec3.fromList(output.toList())
}

/**
 * Returns a [Vec4] representation of a given [color].
 *
 * @deprecated Use [toVec4] instead.
 */
@Suppress("FunctionNaming")
@Deprecated(
    message = "Use Color.toVec4() instead",
    replaceWith = ReplaceWith("color.toVec4()", "graphics.glimpse.types.toVec4")
)
fun Vec4(color: Color): Vec4<Float> {
    val output = FloatArray(size = VEC4_SIZE)
    color.getRGBComponents(output)
    return Vec4.fromList(output.toList())
}

/**
 * Returns a [Vec3] representation of this [Color].
 *
 * @since v1.3.0
 */
fun Color.toVec3(): Vec3<Float> {
    val output = FloatArray(size = VEC3_SIZE)
    getRGBColorComponents(output)
    return Vec3.fromList(output.toList())
}

/**
 * Returns a [Vec4] representation of this [Color].
 *
 * @since v1.3.0
 */
fun Color.toVec4(): Vec4<Float> {
    val output = FloatArray(size = VEC4_SIZE)
    getRGBComponents(output)
    return Vec4.fromList(output.toList())
}

/**
 * Returns a Compose [Color] representation of this [Vec3].
 *
 * @since v1.3.0
 */
fun Vec3<Float>.toColor(): Color =
    Color(this.r, this.g, this.b)

/**
 * Returns a Compose [Color] representation of this [Vec4].
 *
 * @since v1.3.0
 */
fun Vec4<Float>.toColor(): Color =
    Color(this.r, this.g, this.b, this.a)
