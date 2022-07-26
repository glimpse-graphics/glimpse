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

import graphics.glimpse.GlimpseAdapter
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
import graphics.glimpse.hud.text.TextTextureImageSourceBuilder
import graphics.glimpse.textures.Texture
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec4

class HudElementsBuilderDelegate(
    val gl: GlimpseAdapter
) : HudElementsBuilder {

    private val textureBuilder = Texture.Builder.getInstance(gl)

    val elements = mutableListOf<HudElement>()

    override fun <T : HudElement> element(element: T): T {
        elements += element
        return element
    }

    override fun quad(
        texture: Texture,
        position: Vec2,
        onInputEvent: ((event: Any?) -> Boolean)?,
        init: QuadBuilder.() -> Unit
    ): Quad = element(
        QuadBuilder(texture, position)
            .apply(init)
            .build()
            .applyInputEventListener(onInputEvent)
    )

    private fun <T : HudElement> T.applyInputEventListener(
        onInputEvent: ((event: Any?) -> Boolean)?
    ): T {
        if (onInputEvent != null) {
            addInputEventListener { _, event -> onInputEvent(event) }
        }
        return this
    }

    override fun text(
        text: String,
        font: Font,
        color: Vec4,
        padding: Padding,
        width: Int,
        height: Int,
        position: Vec2,
        onInputEvent: ((event: Any?) -> Boolean)?,
        init: QuadBuilder.() -> Unit
    ): Quad = quad(
        texture = textureBuilder
            .addTexture(
                TextTextureImageSourceBuilder()
                    .fromText(text)
                    .withFont(font)
                    .withColor(color)
                    .withPadding(padding.left, padding.top, padding.right, padding.bottom)
                    .withSize(width, height)
                    .build()
            )
            .build()
            .single(),
        position = position,
        onInputEvent = onInputEvent,
        init = init
    )

    override fun space(
        width: Number,
        height: Number
    ): Space = element(
        Space(width.toFloat(), height.toFloat())
    )

    override fun column(
        position: Vec2,
        alignment: HorizontalAlignment,
        onInputEvent: ((event: Any?) -> Boolean)?,
        init: HudElementsBuilder.() -> Unit
    ): ColumnLayout = element(
        ColumnLayoutBuilder(gl, position, alignment)
            .apply(init)
            .build()
            .applyInputEventListener(onInputEvent)
    )

    override fun row(
        position: Vec2,
        alignment: VerticalAlignment,
        onInputEvent: ((event: Any?) -> Boolean)?,
        init: HudElementsBuilder.() -> Unit
    ): RowLayout = element(
        RowLayoutBuilder(gl, position, alignment)
            .apply(init)
            .build()
            .applyInputEventListener(onInputEvent)
    )

    override fun stack(
        position: Vec2,
        alignment: Alignment,
        onInputEvent: ((event: Any?) -> Boolean)?,
        init: HudElementsBuilder.() -> Unit
    ): StackLayout = element(
        StackLayoutBuilder(gl, position, alignment)
            .apply(init)
            .build()
            .applyInputEventListener(onInputEvent)
    )

    override fun withVisibility(
        visibility: () -> Boolean,
        init: HudElementsBuilder.() -> Unit
    ): VisibilityWrapper = element(
        VisibilityWrapperBuilder(gl, visibility)
            .apply(init)
            .build()
    )

    override fun withTransformation(
        translation: () -> Vec2,
        rotation: () -> Angle,
        scale: () -> Vec2,
        init: HudElementsBuilder.() -> Unit
    ): TransformationWrapper = element(
        TransformationWrapperBuilder(gl, translation, rotation, scale)
            .apply(init)
            .build()
    )
}
