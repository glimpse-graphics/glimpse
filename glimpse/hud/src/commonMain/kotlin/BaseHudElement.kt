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
 * Base implementation of [HudElement].
 */
abstract class BaseHudElement(

    /**
     * Position of this element.
     */
    override var position: Vec2
) : HudElement {

    /**
     * `true` if this element is visible.
     */
    override var isVisible: Boolean = true

    private val inputEventListeners = mutableListOf<HudElement.InputEventListener>()

    /**
     * Adds given input events [listener] to this element.
     */
    override fun addInputEventListener(listener: HudElement.InputEventListener) {
        inputEventListeners += listener
    }

    /**
     * Removes given input events [listener] from this element.
     */
    override fun removeInputEventListener(listener: HudElement.InputEventListener) {
        inputEventListeners -= listener
    }

    /**
     * Handles given input [event] at given [position].
     *
     * @return `true` if the event has been consumed by this element.
     */
    override fun handleInputEvent(position: Vec2, event: Any?): Boolean {
        val relativePosition = position - this.position
        for (listener in inputEventListeners) {
            if (listener.onInputEvent(relativePosition, event)) return true
        }
        return false
    }
}
