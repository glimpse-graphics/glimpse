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
 * Interface for an element of HUD.
 */
interface HudElement {

    /**
     * Position of this element.
     */
    var position: Vec2

    /**
     * Bounding box of this element.
     *
     * The coordinates of the bounding box are relative to this element's position.
     */
    val boundingBox: BoundingBox

    /**
     * `true` if this element is visible.
     */
    var isVisible: Boolean

    /**
     * Atoms making up this element.
     */
    val atoms: Iterable<HudAtom>

    /**
     * Adds given input events [listener] to this element.
     */
    fun addInputEventListener(listener: InputEventListener)

    /**
     * Removes given input events [listener] from this element.
     */
    fun removeInputEventListener(listener: InputEventListener)

    /**
     * Handles given input [event] at given [position].
     *
     * @return `true` if the event has been consumed by this element.
     */
    fun handleInputEvent(position: Vec2, event: Any?): Boolean

    /**
     * Input events listener for HUD elements.
     */
    fun interface InputEventListener {

        /**
         * Called when given input [event] occurs at given [position].
         *
         * @return `true` if the event has been consumed.
         */
        fun onInputEvent(position: Vec2, event: Any?): Boolean
    }
}
