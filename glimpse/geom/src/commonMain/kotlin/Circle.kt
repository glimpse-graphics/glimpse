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
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.minus
import graphics.glimpse.types.one
import graphics.glimpse.types.plus
import graphics.glimpse.types.rotationZ
import graphics.glimpse.types.times
import graphics.glimpse.types.translation
import graphics.glimpse.types.zero
import kotlin.reflect.KClass

/**
 * 2D circle with given [center] point and [radius].
 *
 * @since v1.3.0
 */
data class Circle<T>(

    /**
     * Center of this circle.
     */
    val center: Vec2<T>,

    /**
     * Radius of this circle.
     */
    val radius: T

) : Shape<T> where T : Number, T : Comparable<T> {

    /**
     * Type of coordinates in this circle.
     */
    override val type: KClass<T> get() = center.type

    /**
     * Bounding box of this circle.
     */
    override val boundingBox: Rectangle<T> by lazy {
        Rectangle(
            left = center.x - radius,
            bottom = center.y - radius,
            right = center.x + radius,
            top = center.y + radius,
            type = type
        )
    }

    /**
     * Midpoint of this circle.
     */
    override val midpoint: Vec2<T> get() = center

    /**
     * Returns `true` if given [point] is inside this circle.
     *
     * If a point is located on the circle, it is considered inside.
     */
    override operator fun contains(point: Vec2<T>): Boolean =
        (point - center).magnitude() <= radius

    /**
     * Returns the distance between this circle and the given [point].
     */
    override fun distanceTo(point: Vec2<T>): T =
        ((point - center).magnitude() - radius).coerceAtLeast(minimumValue = zero(type))

    /**
     * Returns `true` if given [section] intersects this circle.
     */
    override infix fun intersects(section: Section2<T>): Boolean =
        section.distanceTo(center) <= radius

    /**
     * Returns `true` if given [other] shape intersects this circle.
     */
    override fun intersects(other: Shape<T>): Boolean = other.intersects(circle = this)

    /**
     * Returns `true` if given [polygon] intersects this circle.
     */
    override infix fun intersects(polygon: Polygon<T>): Boolean =
        center in polygon || polygon.edges.any { edge -> intersects(edge) }

    /**
     * Returns `true` if given [circle] intersects this circle.
     */
    override infix fun intersects(circle: Circle<T>): Boolean =
        (circle.center - this.center).magnitude() <= circle.radius + this.radius

    /**
     * Returns this circle translated by given [vector].
     */
    override fun translate(vector: Vec2<T>): Circle<T> =
        copy(center = center + vector)

    /**
     * Returns this circle rotated by given [angle] around origin point.
     */
    override fun rotate(angle: Angle<T>): Circle<T> =
        rotate(angle, Vec2.nullVector(this.type))

    /**
     * Returns this circle rotated by given [angle] around given [pivot] point.
     */
    override fun rotate(angle: Angle<T>, pivot: Vec2<T>): Circle<T> =
        Circle(
            center = (translation(pivot.toVec3()) *
                rotationZ(angle, this.type) *
                translation(-pivot.toVec3()) *
                this.center.toVec4(w = one(this.type))).toVec2(),
            radius = this.radius
        )

    /**
     * Returns this circle scaled by given [scale] value relative to origin point.
     */
    override fun scale(scale: T): Circle<T> =
        Circle(
            center = this.center * scale,
            radius = this.radius * scale
        )

    /**
     * Returns this circle scaled by given [scale] value relative to given [center] point.
     */
    override fun scale(scale: T, center: Vec2<T>): Circle<T> =
        Circle(
            center = center + (this.center - center) * scale,
            radius = this.radius * scale
        )
}
