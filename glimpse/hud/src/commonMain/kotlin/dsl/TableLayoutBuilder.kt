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

import graphics.glimpse.hud.layouts.TableLayout
import graphics.glimpse.types.Vec2

internal class TableLayoutBuilder(
    parent: HudElementsBuilder,
    private val position: Vec2,
    private val columns: List<TableLayout.Column>,
    private val columnsSpacing: Float,
    private val rowsSpacing: Float,
    private val delegate: HudElementsBuilderDelegate = HudElementsBuilderDelegate(parent)
) : HudElementsBuilder by delegate {

    fun build(): TableLayout =
        TableLayout(position, columns, columnsSpacing, rowsSpacing).apply {
            for (element in delegate.elements) {
                addElement(element)
            }
        }
}
