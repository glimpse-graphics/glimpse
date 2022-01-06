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

package graphics.glimpse.cameras

import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.lookAt

/**
 * A camera located in the [eye] position and pointed at the given [target],
 * while preserving the defined [upVector].
 */
data class TargetCamera(

    /**
     * Position of the camera eye.
     */
    override val eye: Vec3,

    /**
     * Position of the camera target.
     */
    val target: Vec3,

    /**
     * Up-vector preserved for the camera.
     */
    val upVector: Vec3 = Vec3.unitZ
) : Camera {

    /**
     * View matrix defined by the camera.
     */
    override val viewMatrix: Mat4 = lookAt(eye, target, upVector)
}
