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
 * Empty space element of HUD.
 */
class Space(

    /**
     * Space width.
     */
    var width: Float = 0f,

    /**
     * Space height.
     */
    var height: Float = 0f
) : BaseHudElement(Vec2.nullVector()) {

    /**
     * Bounding box of this space.
     */
    override val boundingBox: BoundingBox
        get() = BoundingBox(
            left = 0f,
            right = width,
            top = 0f,
            bottom = height
        )

    /**
     * Empty list of atoms.
     */
    override val atoms: Iterable<HudAtom> =
        emptyList()
}
