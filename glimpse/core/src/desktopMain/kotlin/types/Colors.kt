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
 */
@Suppress("FunctionNaming")
fun Vec3(color: Color): Vec3<Float> {
    val output = FloatArray(size = VEC3_SIZE)
    color.getRGBColorComponents(output)
    return Vec3.fromList(output.toList())
}

/**
 * Returns a [Vec4] representation of a given [color].
 */
@Suppress("FunctionNaming")
fun Vec4(color: Color): Vec4<Float> {
    val output = FloatArray(size = VEC4_SIZE)
    color.getRGBComponents(output)
    return Vec4.fromList(output.toList())
}
