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

internal abstract class BaseSurface<T>(
    protected val freeformType: FreeformType,
    override val degree: Vec2<Int>,
    protected val knotsU: List<T>,
    protected val knotsV: List<T>,
    override val type: KClass<T>
) : Surface<T> where T : Number, T : Comparable<T> {

    override val gridSize: Vec2<Int>
        get() = when (freeformType) {
            FreeformType.BEZIER -> Vec2(x = degree.u + 1, y = degree.v + 1)
            FreeformType.B_SPLINE -> Vec2(x = knotsU.size - degree.u - 1, y = knotsV.size - degree.v - 1)
        }

    internal abstract val helper: SurfaceHelper<T>
}
