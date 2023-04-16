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
 * Creates a view matrix defined by an [eye] position, a [target] point, and an [upVector].
 */
fun lookAt(eye: Vec3<Double>, target: Vec3<Double>, upVector: Vec3<Double>): Mat4<Double> {
    val forward = (target - eye).normalize()
    val right = (forward cross upVector).normalize()
    val up = right cross forward
    return Mat4(
        listOf(
            right.x, up.x, -forward.x, 0.0,
            right.y, up.y, -forward.y, 0.0,
            right.z, up.z, -forward.z, 0.0,
            0.0, 0.0, 0.0, 1.0
        )
    ) * translation(-eye)
}
