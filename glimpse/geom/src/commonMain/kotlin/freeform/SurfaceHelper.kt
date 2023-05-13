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

package graphics.glimpse.geom.freeform

import graphics.glimpse.geom.interpolation.Interpolator
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3

internal interface SurfaceHelper<T> where T : Number, T : Comparable<T> {

    fun getPositions(parameterValueU: T): Interpolator<T, Vec3<T>>

    fun getTextureCoordinates(parameterValueU: T): Interpolator<T, Vec2<T>>

    fun getNormals(parameterValueU: T): Interpolator<T, Vec3<T>>
}
