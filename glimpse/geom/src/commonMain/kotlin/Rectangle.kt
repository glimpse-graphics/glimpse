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

package graphics.glimpse.geom

import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.div
import graphics.glimpse.types.minus
import graphics.glimpse.types.plus
import graphics.glimpse.types.times
import kotlin.reflect.KClass

/**
 * 2D rectangle.
 *
 * @since v1.3.0
 */
data class Rectangle<T>(

    /**
     * X coordinate of the left side of this rectangle.
     */
    val left: T,

    /**
     * Y coordinate of the bottom side of this rectangle.
     */
    val bottom: T,

    /**
     * X coordinate of the right side of this rectangle.
     */
    val right: T,

    /**
     * Y coordinate of the top side of this rectangle.
     */
    val top: T,

    /**
     * Type of coordinates in this rectangle.
     */
    override val type: KClass<T>

) : Shape<T> where T : Number, T : Comparable<T> {

    /**
     * Bounding box of this rectangle.
     */
    override val boundingBox: Rectangle<T> get() = this

    /**
     * Midpoint of this rectangle.
     */
    override val midpoint: Vec2<T> by lazy {
        Vec2(x = (right + left) / 2, y = (top + bottom) / 2, type = this.type)
    }

    /**
     * Width of this rectangle.
     */
    val width: T get() = right - left

    /**
     * Height of this rectangle.
     */
    val height: T get() = top - bottom

    /**
     * Polygon representation of this rectangle.
     */
    val asPolygon: Polygon<T> by lazy {
        Polygon(
            vertices = listOf(
                Vec2(x = left, y = bottom, type = this.type),
                Vec2(x = right, y = bottom, type = this.type),
                Vec2(x = right, y = top, type = this.type),
                Vec2(x = left, y = top, type = this.type)
            ),
            type = this.type
        )
    }

    /**
     * Returns `true` if given [point] is inside this rectangle.
     */
    override operator fun contains(point: Vec2<T>): Boolean =
        point.x in left..right && point.y in bottom..top

    /**
     * Returns the distance between this rectangle and the given [point].
     */
    override fun distanceTo(point: Vec2<T>): T = asPolygon.distanceTo(point)

    /**
     * Returns `true` if given [section] intersects this rectangle.
     */
    override infix fun intersects(section: Section2<T>): Boolean = asPolygon intersects section

    /**
     * Returns `true` if given [other] shape intersects this rectangle.
     */
    override fun intersects(other: Shape<T>): Boolean = other.intersects(polygon = this.asPolygon)

    /**
     * Returns `true` if given [polygon] intersects this rectangle.
     */
    override infix fun intersects(polygon: Polygon<T>): Boolean = asPolygon intersects polygon

    /**
     * Returns `true` if given [circle] intersects this rectangle.
     */
    override infix fun intersects(circle: Circle<T>): Boolean = asPolygon intersects circle

    /**
     * Returns this rectangle translated by given [vector].
     */
    override fun translate(vector: Vec2<T>): Rectangle<T> =
        copy(
            left = left + vector.x,
            bottom = bottom + vector.y,
            right = right + vector.x,
            top = top + vector.y
        )

    /**
     * Returns this rectangle as polygon rotated by given [angle] around origin point.
     */
    override fun rotate(angle: Angle<T>): Polygon<T> =
        asPolygon.rotate(angle)

    /**
     * Returns this rectangle as polygon rotated by given [angle] around given [pivot] point.
     */
    override fun rotate(angle: Angle<T>, pivot: Vec2<T>): Polygon<T> =
        asPolygon.rotate(angle, pivot)

    /**
     * Returns this rectangle scaled by given [scale] value relative to origin point.
     */
    override fun scale(scale: T): Rectangle<T> =
        Rectangle(
            left = this.left * scale,
            bottom = this.bottom * scale,
            right = this.right * scale,
            top = this.top * scale,
            type = this.type
        )

    /**
     * Returns this rectangle scaled by given [scale] value relative to given [center] point.
     */
    override fun scale(scale: T, center: Vec2<T>): Rectangle<T> =
        Rectangle(
            left = center.x + (this.left - center.x) * scale,
            bottom = center.y + (this.bottom - center.y) * scale,
            right = center.x + (this.right - center.x) * scale,
            top = center.y + (this.top - center.y) * scale,
            type = this.type
        )

    /**
     * Returns this rectangle as polygon transformed using given [matrix].
     */
    fun transform(matrix: Mat4<T>): Polygon<T> =
        asPolygon.transform(matrix)
}

/**
 * Returns a new [Rectangle] with given [left], [bottom], [right] and [top] side coordinates.
 */
@Suppress("FunctionNaming")
inline fun <reified T> Rectangle(
    left: T,
    bottom: T,
    right: T,
    top: T
): Rectangle<T> where T : Number, T : Comparable<T> =
    Rectangle(
        left = left,
        bottom = bottom,
        right = right,
        top = top,
        type = T::class
    )
