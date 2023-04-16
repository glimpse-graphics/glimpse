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

package graphics.glimpse.ui.compose.types

import androidx.compose.ui.graphics.Color
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4

/**
 * Returns a [Vec3] representation of a given [color].
 *
 * @since v1.1.0
 */
@Suppress("FunctionNaming")
fun Vec3(color: Color): Vec3<Float> {
    return Vec3(color.red, color.green, color.blue)
}

/**
 * Returns a [Vec4] representation of a given [color].
 *
 * @since v1.1.0
 */
@Suppress("FunctionNaming")
fun Vec4(color: Color): Vec4<Float> {
    return Vec4(color.red, color.green, color.blue, color.alpha)
}
