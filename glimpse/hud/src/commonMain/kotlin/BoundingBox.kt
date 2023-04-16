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

package graphics.glimpse.hud

import graphics.glimpse.types.Vec2

/**
 * Bounding box of an element of HUD.
 */
data class BoundingBox(

    /**
     * X coordinate of the left side of this bounding box.
     */
    val left: Float,

    /**
     * X coordinate of the right side of this bounding box.
     */
    val right: Float,

    /**
     * Y coordinate of the top side of this bounding box.
     */
    val top: Float,

    /**
     * Y coordinate of the bottom side of this bounding box.
     */
    val bottom: Float
) {

    /**
     * Width of this bounding box.
     */
    val width = right - left

    /**
     * Height of this bounding box.
     */
    val height = bottom - top

    /**
     * Midpoint of this bounding box.
     */
    val midpoint: Vec2<Float> = Vec2(x = (left + right) / 2f, y = (top + bottom) / 2f)

    /**
     * Returns `true` if given [point] is inside this bounding box.
     */
    operator fun contains(point: Vec2<Float>): Boolean =
        point.x in left..right && point.y in top..bottom
}
