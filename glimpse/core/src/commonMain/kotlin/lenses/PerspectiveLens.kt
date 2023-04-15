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

package graphics.glimpse.lenses

import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.perspective

/**
 * A lens for a perspective projection defined by a given frustum.
 *
 * The frustum is defined by its [near] and [far] depth clipping planes,
 * and its [field of view angle in the Y direction][fovY] and [aspect] ratio between X and Y
 * field of view.
 */
data class PerspectiveLens(
    val fovY: Angle<Float>,
    val aspect: Float,
    val near: Float,
    val far: Float
) : Lens {

    /**
     * Projection matrix defined by the lens.
     */
    override val projectionMatrix: Mat4 = perspective(fovY, aspect, near, far)
}
