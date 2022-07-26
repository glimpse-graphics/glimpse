package graphics.glimpse.hud.layouts

import graphics.glimpse.hud.BoundingBox
import graphics.glimpse.types.Vec2

/**
 * A layout arranging its elements in a table.
 *
 * Each subsequent element is displayed in the next table cell.
 */
class TableLayout(

    /**
     * Position of this layout.
     */
    position: Vec2 = Vec2.nullVector,

    /**
     * Configuration of columns in this table layout.
     */
    columns: List<Column>,

    /**
     * Spacing of columns in this table layout.
     */
    columnsSpacing: Float = 0f,

    /**
     * Spacing of rows in this table layout.
     */
    rowsSpacing: Float = 0f
) : BaseHudLayout(position) {

    /**
     * Configuration of columns in this table layout.
     */
    var columns: List<Column> = columns
        set(value) {
            field = value
            layoutElements()
        }

    /**
     * Spacing of columns in this table layout.
     */
    var columnsSpacing: Float = columnsSpacing
        set(value) {
            field = value
            layoutElements()
        }

    /**
     * Spacing of rows in this table layout.
     */
    var rowsSpacing: Float = rowsSpacing
        set(value) {
            field = value
            layoutElements()
        }

    /**
     * Arranges this layout's elements in a table.
     */
    override fun layoutElements() {
        super.layoutElements()

        val rows = elements.chunked(columns.size)
        val rowsHeights = rows.map { row ->
            row.maxOf { element -> element.boundingBox.height }
        }

        val width = columns.map { column -> column.width }.sum() + columnsSpacing * columns.lastIndex
        val height = rowsHeights.sum() + rowsSpacing * rows.lastIndex

        boundingBox = BoundingBox(
            left = -width / 2f,
            right = width / 2f,
            top = -height / 2f,
            bottom = height / 2f
        )

        var top = boundingBox.top
        for ((rowIndex, row) in rows.withIndex()) {
            var left = boundingBox.left
            val rowHeight = rowsHeights[rowIndex]
            for ((columnIndex, element) in row.withIndex()) {
                val cellWidth = columns[columnIndex].width
                val cellAlignment = columns[columnIndex].alignment
                element.position = Vec2(
                    x = left + cellWidth / 2f +
                            cellAlignment.x * (cellWidth - element.boundingBox.width) / 2f -
                            element.boundingBox.midpoint.x,
                    y = top + rowHeight / 2f +
                            cellAlignment.y * (rowHeight - element.boundingBox.height) / 2f -
                            element.boundingBox.midpoint.y
                )
                left += cellWidth + columnsSpacing
            }
            top += rowHeight + rowsSpacing
        }
    }

    /**
     * Configuration of a column in table layout.
     */
    data class Column(

        /**
         * Column width.
         */
        val width: Float,

        /**
         * Column cells alignment.
         */
        val alignment: Alignment
    )
}
