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

import graphics.glimpse.hud.HudElement

/**
 * A layout for elements of HUD.
 *
 * A layout arranges positions and possibly sizes of its elements.
 */
interface HudLayout : HudElement {

    /**
     * Width of this layout.
     */
    val width: Float

    /**
     * Height of this layout.
     */
    val height: Float

    /**
     * Adds given [element] to this layout.
     */
    fun addElement(element: HudElement)

    /**
     * Removes given [element] from this layout.
     */
    fun removeElement(element: HudElement)

    /**
     * Removes an element at the given [index] from this layout.
     */
    fun removeElementAt(index: Int)

    /**
     * Arranges this layout's elements.
     */
    fun layoutElements()
}
