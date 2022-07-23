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
 * Base implementation of HUD element wrappers.
 */
abstract class BaseHudElementWrapper(

    /**
     * Element wrapped by this wrapper.
     */
    private val element: HudElement
) : HudElement {

    /**
     * Position of the wrapped element.
     */
    override var position: Vec2
        get() = element.position
        set(value) {
            element.position = value
        }

    /**
     * Bounding box of the wrapped element.
     */
    override val boundingBox: BoundingBox
        get() = element.boundingBox

    /**
     * `true` the wrapped element is visible.
     */
    override var isVisible: Boolean
        get() = element.isVisible
        set(value) {
            element.isVisible = value
        }

    /**
     * Atoms making up the wrapped element.
     */
    override val atoms: Iterable<HudAtom>
        get() = if (isVisible) element.atoms else emptyList()

    /**
     * Adds given input events [listener] to the wrapped element.
     */
    override fun addInputEventListener(listener: HudElement.InputEventListener) {
        element.addInputEventListener(listener)
    }

    /**
     * Removes given input events [listener] from the wrapped element.
     */
    override fun removeInputEventListener(listener: HudElement.InputEventListener) {
        element.removeInputEventListener(listener)
    }

    /**
     * Handles given input [event] at given [position].
     *
     * @return `true` if the event has been consumed by the wrapped element.
     */
    override fun handleInputEvent(position: Vec2, event: Any?): Boolean =
        element.handleInputEvent(position, event)
}
