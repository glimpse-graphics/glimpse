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

package graphics.glimpse.geom

import graphics.glimpse.types.Angle
import graphics.glimpse.types.Vec2
import kotlin.reflect.KClass

/**
 * 2D shape.
 *
 * @since v1.3.0
 */
sealed interface Shape<T> where T : Number, T : Comparable<T> {

    /**
     * Type of coordinates in this shape.
     */
    val type: KClass<T>

    /**
     * Bounding box of this shape.
     */
    val boundingBox: Rectangle<T>

    /**
     * Midpoint of this shape.
     */
    val midpoint: Vec2<T>

    /**
     * Returns `true` if given [point] is inside this shape.
     *
     * If a point is located on the edge of this shape, it is considered to be inside.
     */
    operator fun contains(point: Vec2<T>): Boolean

    /**
     * Returns the distance between this shape and the given [point].
     */
    fun distanceTo(point: Vec2<T>): T

    /**
     * Returns `true` if given [section] intersects this shape.
     */
    infix fun intersects(section: Section2<T>): Boolean

    /**
     * Returns `true` if given [other] shape intersects this shape.
     */
    infix fun intersects(other: Shape<T>): Boolean

    /**
     * Returns `true` if given [rectangle] intersects this shape.
     */
    infix fun intersects(rectangle: Rectangle<T>): Boolean =
        this intersects rectangle.asPolygon

    /**
     * Returns `true` if given [polygon] intersects this shape.
     */
    infix fun intersects(polygon: Polygon<T>): Boolean

    /**
     * Returns `true` if given [circle] intersects this shape.
     */
    infix fun intersects(circle: Circle<T>): Boolean

    /**
     * Returns this shape translated by given [vector].
     */
    fun translate(vector: Vec2<T>): Shape<T>

    /**
     * Returns this shape rotated by given [angle] around origin point.
     */
    fun rotate(angle: Angle<T>): Shape<T>

    /**
     * Returns this shape rotated by given [angle] around given [pivot] point.
     */
    fun rotate(angle: Angle<T>, pivot: Vec2<T>): Shape<T>

    /**
     * Returns this shape scaled by given [scale] value relative to origin point.
     */
    fun scale(scale: T): Shape<T>

    /**
     * Returns this shape scaled by given [scale] value relative to given [center] point.
     */
    fun scale(scale: T, center: Vec2<T>): Shape<T>
}
