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

import graphics.glimpse.types.Vec2
import kotlin.reflect.KClass

/**
 * A surface.
 *
 * @since v2.0.0
 */
interface Surface<T> where T : Number, T : Comparable<T> {

    /**
     * Degree of this surface in both directions.
     */
    val degree: Vec2<Int>

    /**
     * Size of the control points grid of this surface.
     */
    val gridSize: Vec2<Int>

    /**
     * Type of coordinates in this surface.
     */
    val type: KClass<T>

    /**
     * Returns a basic triangulation for this surface.
     *
     * Points of the triangulation will be determined by given parameter values
     * in the directions [U][parameterValuesU] and [V][parameterValuesV].
     */
    fun createTriangulation(parameterValuesU: List<T>, parameterValuesV: List<T>): SurfaceTriangulation<T>
}
