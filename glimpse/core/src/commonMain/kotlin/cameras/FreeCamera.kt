/*
 * Copyright 2020 Slawomir Czerwinski
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

package graphics.glimpse.cameras

import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.rotationX
import graphics.glimpse.types.rotationY
import graphics.glimpse.types.rotationZ
import graphics.glimpse.types.translation

/**
 * A freely transformed camera, defined by its [eye] position, and its rotations:
 * [roll], [pitch], [yaw].
 */
data class FreeCamera(

    /**
     * Position of the camera eye.
     */
    override val eye: Vec3,

    /**
     * Camera roll angle (clockwise/anticlockwise rotation of the camera eye).
     */
    val roll: Angle,

    /**
     * Camera pitch angle (point upwards/downwards).
     */
    val pitch: Angle,

    /**
     * Camera yaw angle (camera heading rotation)
     */
    val yaw: Angle
) : Camera {

    /**
     * View matrix defined by the camera.
     */
    override val viewMatrix: Mat4 = listOf(
        initialMatrix,
        rotationX(-roll),
        rotationY(pitch),
        rotationZ(-yaw),
        translation(-eye),
    ).reduce { mat, transformation -> mat * transformation }

    companion object {
        private val initialMatrix = Mat4(
            listOf(
                0f, 0f, -1f, 0f,
                -1f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f
            )
        )
    }
}
