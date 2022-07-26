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

package graphics.glimpse.hud.dsl

import graphics.glimpse.hud.HudElement
import graphics.glimpse.hud.TransformationWrapper
import graphics.glimpse.hud.Quad
import graphics.glimpse.hud.Space
import graphics.glimpse.hud.VisibilityWrapper
import graphics.glimpse.hud.layouts.Alignment
import graphics.glimpse.hud.layouts.ColumnLayout
import graphics.glimpse.hud.layouts.HorizontalAlignment
import graphics.glimpse.hud.layouts.RowLayout
import graphics.glimpse.hud.layouts.StackLayout
import graphics.glimpse.hud.layouts.VerticalAlignment
import graphics.glimpse.hud.text.Font
import graphics.glimpse.textures.Texture
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec4

/**
 * Builder of HUD elements.
 */
interface HudElementsBuilder {

    /**
     * Adds a custom element.
     */
    fun <T : HudElement> element(
        element: T
    ): T

    /**
     * Creates a quad.
     */
    fun quad(
        texture: Texture,
        position: Vec2 = Vec2.nullVector,
        onInputEvent: ((event: Any?) -> Boolean)? = null,
        init: QuadBuilder.() -> Unit = {}
    ): Quad

    /**
     * Creates a quad containing text.
     */
    fun text(
        text: String,
        font: Font = Font.DEFAULT,
        color: Vec4 = Vec4(x = 1f, y = 1f, z = 1f, w = 1f),
        padding: Padding = Padding(),
        width: Int = 0,
        height: Int = 0,
        position: Vec2 = Vec2.nullVector,
        onInputEvent: ((event: Any?) -> Boolean)? = null,
        init: QuadBuilder.() -> Unit = {}
    ): Quad

    /**
     * Creates empty space.
     */
    fun space(
        width: Number = 0,
        height: Number = 0
    ): Space

    /**
     * Creates a column layout.
     */
    fun column(
        position: Vec2 = Vec2.nullVector,
        alignment: HorizontalAlignment = HorizontalAlignment.Center,
        spacing: Float = 0f,
        onInputEvent: ((event: Any?) -> Boolean)? = null,
        init: HudElementsBuilder.() -> Unit
    ): ColumnLayout

    /**
     * Creates a row layout.
     */
    fun row(
        position: Vec2 = Vec2.nullVector,
        alignment: VerticalAlignment = VerticalAlignment.Center,
        spacing: Float = 0f,
        onInputEvent: ((event: Any?) -> Boolean)? = null,
        init: HudElementsBuilder.() -> Unit
    ): RowLayout

    /**
     * Creates a stack layout.
     */
    fun stack(
        position: Vec2 = Vec2.nullVector,
        alignment: Alignment = Alignment.Center,
        onInputEvent: ((event: Any?) -> Boolean)? = null,
        init: HudElementsBuilder.() -> Unit
    ): StackLayout

    /**
     * Sets [visibility] of an element defined in the [init] block.
     */
    fun withVisibility(
        visibility: () -> Boolean,
        init: HudElementsBuilder.() -> Unit
    ): VisibilityWrapper

    /**
     * Transforms an element defined in the [init] block.
     */
    fun withTransformation(
        translation: () -> Vec2 = { Vec2.nullVector },
        rotation: () -> Angle = { Angle.nullAngle },
        scale: () -> Vec2 = { Vec2(x = 1f, y = 1f) },
        init: HudElementsBuilder.() -> Unit
    ): TransformationWrapper
}
