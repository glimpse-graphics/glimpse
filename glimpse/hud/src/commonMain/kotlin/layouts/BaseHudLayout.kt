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

import graphics.glimpse.hud.BaseHudElement
import graphics.glimpse.hud.BoundingBox
import graphics.glimpse.hud.HudAtom
import graphics.glimpse.hud.HudElement
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.translation

/**
 * Base implementation of a [HudLayout].
 */
abstract class BaseHudLayout(

    /**
     * Position of this layout.
     */
    position: Vec2 = Vec2.nullVector
) : BaseHudElement(position), HudLayout {

    protected val elements = mutableListOf<HudElement>()

    /**
     * Width of this layout.
     */
    override val width: Float get() = boundingBox.width

    /**
     * Height of this layout.
     */
    override val height: Float get() = boundingBox.height

    /**
     * Bounding box of this layout.
     */
    override var boundingBox: BoundingBox = BoundingBox(left = 0f, right = 0f, top = 0f, bottom = 0f)
        protected set

    /**
     * Atoms making up this layout.
     */
    override val atoms: Iterable<HudAtom>
        get() = if (isVisible) {
            elements.filter(HudElement::isVisible)
                .flatMap { element -> element.atoms.map { atom -> LayoutAtom(atom) } }
        } else {
            emptyList()
        }

    /**
     * Adds given [element] to this layout.
     */
    override fun addElement(element: HudElement) {
        elements += element
        layoutElements()
    }

    /**
     * Removes given [element] from this layout.
     */
    override fun removeElement(element: HudElement) {
        elements -= element
        layoutElements()
    }

    /**
     * Removes an element at the given [index] from this layout.
     */
    override fun removeElementAt(index: Int) {
        elements.removeAt(index)
        layoutElements()
    }

    /**
     * Handles given input [event] at given [position].
     *
     * @return `true` if the event has been consumed by this layout.
     */
    override fun handleInputEvent(position: Vec2, event: Any?): Boolean {
        if (super.handleInputEvent(position, event)) return true

        val relativePosition = position - this.position
        for (element in elements.reversed()) {
            if (relativePosition - element.position in element.boundingBox) {
                if (element.handleInputEvent(relativePosition, event)) return true
            }
        }

        return false
    }

    private inner class LayoutAtom(private val atom: HudAtom) : HudAtom by atom {

        override val modelMatrix: Mat4
            get() = translation(position.toVec3()) * atom.modelMatrix
    }
}
