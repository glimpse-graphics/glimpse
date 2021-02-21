/*
 * Copyright 2020-2021 Slawomir Czerwinski
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
 *
 */

package graphics.glimpse.lenses

import graphics.glimpse.types.Mat4
import graphics.glimpse.types.orthographic

/**
 * A lens for an orthographic (parallel) projection defined by a given set
 * of clipping planes: [left], [right], [bottom], [top], [near] and [far].
 */
data class OrthographicLens(
    val left: Float,
    val right: Float = -left,
    val bottom: Float,
    val top: Float = -bottom,
    val near: Float,
    val far: Float
) : Lens {

    /**
     * Projection matrix defined by the lens.
     */
    override val projectionMatrix: Mat4 = orthographic(left, right, bottom, top, near, far)
}
