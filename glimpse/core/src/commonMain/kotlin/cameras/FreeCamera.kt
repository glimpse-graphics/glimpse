/*
 * Copyright 2020-2023 Glimpse Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.cameras

import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.one
import graphics.glimpse.types.rotationX
import graphics.glimpse.types.rotationY
import graphics.glimpse.types.rotationZ
import graphics.glimpse.types.translation
import graphics.glimpse.types.unaryMinus
import graphics.glimpse.types.zero
import kotlin.reflect.KClass

/**
 * A freely transformed camera, defined by its [eye] position, and its rotations:
 * [roll], [pitch], [yaw].
 */
data class FreeCamera<T>(

    /**
     * Position of the camera eye.
     */
    override val eye: Vec3<T>,

    /**
     * Camera roll angle (clockwise/anticlockwise rotation of the camera eye).
     */
    val roll: Angle<T>,

    /**
     * Camera pitch angle (point upwards/downwards).
     */
    val pitch: Angle<T>,

    /**
     * Camera yaw angle (camera heading rotation)
     */
    val yaw: Angle<T>
) : Camera<T> where T : Number, T : Comparable<T> {

    private val type: KClass<T> get() = eye.type

    /**
     * View matrix defined by the camera.
     */
    override val viewMatrix: Mat4<T> = listOf(
        initialMatrix(type),
        rotationX(-roll, this.type),
        rotationY(pitch, this.type),
        rotationZ(-yaw, this.type),
        translation(-eye),
    ).reduce { mat, transformation -> mat * transformation }

    companion object {
        private fun <T> initialMatrix(type: KClass<T>) where T : Number, T : Comparable<T> = Mat4(
            elements = listOf(
                zero(type), zero(type), -one(type), zero(type),
                -one(type), zero(type), zero(type), zero(type),
                zero(type), one(type), zero(type), zero(type),
                zero(type), zero(type), zero(type), one(type)
            ),
            type = type
        )
    }
}
