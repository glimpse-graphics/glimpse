package graphics.glimpse.hud.dsl

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.hud.layouts.TableLayout
import graphics.glimpse.types.Vec2

internal class TableLayoutBuilder(
    gl: GlimpseAdapter,
    private val position: Vec2,
    private val columns: List<TableLayout.Column>,
    private val columnsSpacing: Float,
    private val rowsSpacing: Float,
    private val delegate: HudElementsBuilderDelegate = HudElementsBuilderDelegate(gl)
) : HudElementsBuilder by delegate {

    fun build(): TableLayout =
        TableLayout(position, columns, columnsSpacing, rowsSpacing).apply {
            for (element in delegate.elements) {
                addElement(element)
            }
        }
}
