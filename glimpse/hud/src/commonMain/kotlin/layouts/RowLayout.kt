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

package graphics.glimpse.hud.layouts

import graphics.glimpse.hud.BoundingBox
import graphics.glimpse.types.Vec2

/**
 * A layout arranging its elements in a row.
 *
 * Each subsequent element is displayed on the right of the previous one.
 */
class RowLayout(

    /**
     * Position of this layout.
     */
    position: Vec2 = Vec2.nullVector,

    /**
     * Vertical alignment of the elements.
     */
    private val alignment: VerticalAlignment = VerticalAlignment.Center
) : BaseHudLayout(position) {

    /**
     * Arranges this layout's elements in a row.
     */
    override fun layoutElements() {
        val width = elements.map { element -> element.boundingBox.width }.sum()
        val height = elements.maxOf { element -> element.boundingBox.height }

        boundingBox = BoundingBox(
            left = -width / 2f,
            right = width / 2f,
            top = -height / 2f,
            bottom = height / 2f
        )

        var left = boundingBox.left
        for (element in elements) {
            element.position = Vec2(
                x = left - element.boundingBox.left,
                y = alignment.y * (height - element.boundingBox.height) / 2f - element.boundingBox.midpoint.y
            )
            left += element.boundingBox.width
        }
    }
}
