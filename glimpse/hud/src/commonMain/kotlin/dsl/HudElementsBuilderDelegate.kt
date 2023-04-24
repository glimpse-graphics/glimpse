/*
 * Copyright 2020-2023 Glimpse Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.hud.dsl

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.GlimpseDisposableContainer
import graphics.glimpse.hud.HudElement
import graphics.glimpse.hud.Quad
import graphics.glimpse.hud.Space
import graphics.glimpse.hud.TransformationWrapper
import graphics.glimpse.hud.VisibilityWrapper
import graphics.glimpse.hud.layouts.Alignment
import graphics.glimpse.hud.layouts.ColumnLayout
import graphics.glimpse.hud.layouts.HorizontalAlignment
import graphics.glimpse.hud.layouts.RowLayout
import graphics.glimpse.hud.layouts.StackLayout
import graphics.glimpse.hud.layouts.TableLayout
import graphics.glimpse.hud.layouts.VerticalAlignment
import graphics.glimpse.hud.text.Font
import graphics.glimpse.hud.text.TextTextureImageSourceBuilder
import graphics.glimpse.textures.Texture
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec4

/**
 * Concrete builder of HUD elements to be used as a delegate by other builders.
 */
class HudElementsBuilderDelegate(

    /**
     * Glimpse adapter associated with this builder.
     */
    override val gl: GlimpseAdapter,

    /**
     * Container for disposable Glimpse objects created by this builder.
     */
    override val disposables: GlimpseDisposableContainer
) : HudElementsBuilder {

    /**
     * Creates a new HUD elements builder delegate from given [parent] HUD elements builder.
     */
    constructor(parent: HudElementsBuilder) : this(parent.gl, parent.disposables)

    private val textureBuilder = Texture.Builder.getInstance(gl)

    /**
     * Elements added to this builder.
     */
    val elements = mutableListOf<HudElement>()

    /**
     * Adds a custom element.
     */
    override fun <T : HudElement> element(element: T): T {
        elements += element
        return element
    }

    /**
     * Creates a quad.
     */
    override fun quad(
        texture: Texture,
        position: Vec2<Float>,
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

    /**
     * Creates a quad containing text.
     */
    override fun text(
        text: String,
        font: Font,
        color: Vec4<Float>,
        padding: Padding,
        width: Int,
        height: Int,
        position: Vec2<Float>,
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
            .single()
            .also(disposables::add),
        position = position,
        onInputEvent = onInputEvent,
        init = init
    )

    /**
     * Creates empty space.
     */
    override fun space(
        width: Number,
        height: Number
    ): Space = element(
        Space(width.toFloat(), height.toFloat())
    )

    /**
     * Creates a column layout.
     */
    override fun column(
        position: Vec2<Float>,
        alignment: HorizontalAlignment,
        spacing: Float,
        onInputEvent: ((event: Any?) -> Boolean)?,
        init: HudElementsBuilder.() -> Unit
    ): ColumnLayout = element(
        ColumnLayoutBuilder(this, position, alignment, spacing)
            .apply(init)
            .build()
            .applyInputEventListener(onInputEvent)
    )

    /**
     * Creates a row layout.
     */
    override fun row(
        position: Vec2<Float>,
        alignment: VerticalAlignment,
        spacing: Float,
        onInputEvent: ((event: Any?) -> Boolean)?,
        init: HudElementsBuilder.() -> Unit
    ): RowLayout = element(
        RowLayoutBuilder(this, position, alignment, spacing)
            .apply(init)
            .build()
            .applyInputEventListener(onInputEvent)
    )

    /**
     * Creates a stack layout.
     */
    override fun stack(
        position: Vec2<Float>,
        alignment: Alignment,
        onInputEvent: ((event: Any?) -> Boolean)?,
        init: HudElementsBuilder.() -> Unit
    ): StackLayout = element(
        StackLayoutBuilder(this, position, alignment)
            .apply(init)
            .build()
            .applyInputEventListener(onInputEvent)
    )

    /**
     * Creates a table layout
     */
    override fun table(
        position: Vec2<Float>,
        columns: List<TableLayout.Column>,
        columnsSpacing: Float,
        rowsSpacing: Float,
        onInputEvent: ((event: Any?) -> Boolean)?,
        init: HudElementsBuilder.() -> Unit
    ): TableLayout = element(
        TableLayoutBuilder(this, position, columns, columnsSpacing, rowsSpacing)
            .apply(init)
            .build()
            .applyInputEventListener(onInputEvent)
    )

    /**
     * Sets [visibility] of an element defined in the [init] block.
     */
    override fun withVisibility(
        visibility: () -> Boolean,
        init: HudElementsBuilder.() -> Unit
    ): VisibilityWrapper = element(
        VisibilityWrapperBuilder(this, visibility)
            .apply(init)
            .build()
    )

    /**
     * Transforms an element defined in the [init] block.
     */
    override fun withTransformation(
        translation: () -> Vec2<Float>,
        rotation: () -> Angle<Float>,
        scale: () -> Vec2<Float>,
        init: HudElementsBuilder.() -> Unit
    ): TransformationWrapper = element(
        TransformationWrapperBuilder(this, translation, rotation, scale)
            .apply(init)
            .build()
    )
}
