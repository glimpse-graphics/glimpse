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

package graphics.glimpse.geom

import graphics.glimpse.types.Vec3
import kotlin.reflect.KClass

/**
 * 3D polygonal chain with given [vertices].
 *
 * @since v2.0.0
 */
data class PolygonalChain3<T>(

    /**
     * Vertices of this polygonal chain.
     */
    val vertices: List<Vec3<T>>,

    /**
     * Type of coordinates in this polygonal chain.
     */
    val type: KClass<T>

) where T : Number, T : Comparable<T>

/**
 * Returns a new [PolygonalChain3] with given [vertices].
 *
 * @since v2.0.0
 */
@Suppress("FunctionNaming")
inline fun <reified T> PolygonalChain3(
    vertices: List<Vec3<T>>,
): PolygonalChain3<T> where T : Number, T : Comparable<T> =
    PolygonalChain3(
        vertices = vertices,
        type = T::class
    )
