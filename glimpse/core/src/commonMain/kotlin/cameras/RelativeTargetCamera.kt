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

import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.lookAt
import kotlin.reflect.KClass

/**
 * A camera pointed at the given [target], and located at the given [distance] from it,
 * with the given [longitude] and [latitude] angles, while preserving the defined [upVector].
 */
data class RelativeTargetCamera<T>(

    /**
     * Position of the camera target.
     */
    val target: Vec3<T>,

    /**
     * Distance from the target.
     */
    val distance: T,

    /**
     * Camera longitude relative to the target.
     */
    val longitude: Angle<T>,

    /**
     * Camera latitude relative to the target.
     */
    val latitude: Angle<T>,

    /**
     * Up-vector preserved for the camera.
     */
    val upVector: Vec3<T> = Vec3.unitZ(target.type)
) : Camera<T> where T : Number, T : Comparable<T> {

    private val type: KClass<T> get() = target.type

    /**
     * Position of the camera eye.
     */
    override val eye = target + Vec3.fromSphericalCoordinates(distance, longitude, latitude, this.type)

    /**
     * View matrix defined by the camera.
     */
    override val viewMatrix: Mat4<T> = lookAt(eye, target, upVector, this.type)
}
