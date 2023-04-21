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

import graphics.glimpse.types.Mat2
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.div
import graphics.glimpse.types.max
import graphics.glimpse.types.min
import graphics.glimpse.types.minus
import graphics.glimpse.types.one
import graphics.glimpse.types.zero
import kotlin.reflect.KClass

/**
 * 2D line section with given [start] and [end] points.
 *
 * @since v1.3.0
 */
data class Section2<T>(

    /**
     * Start point of this section.
     */
    val start: Vec2<T>,

    /**
     * End point of this section.
     */
    val end: Vec2<T>

) where T : Number, T : Comparable<T> {

    private val type: KClass<T> get() = start.type

    /**
     * This section as a vector from start point to end point.
     */
    val asVector: Vec2<T> by lazy { end - start }

    /**
     * Length of this section.
     */
    val length: T by lazy { asVector.magnitude() }

    /**
     * Direction unit vector for this section.
     */
    val direction: Vec2<T> by lazy {
        if (length != zero(this.type)) asVector.normalize()
        else Vec2.nullVector(this.type)
    }

    /**
     * Bounding box of this section.
     */
    val boundingBox: Rectangle<T> by lazy {
        Rectangle(
            left = min(start.x, end.x),
            bottom = min(start.y, end.y),
            right = max(start.x, end.x),
            top = max(start.y, end.y),
            type = this.type
        )
    }

    /**
     * Returns `true` if given [point] is on this section.
     */
    operator fun contains(point: Vec2<T>): Boolean =
        if (projectionOf(point) != point) {
            false
        } else {
            (point - start).magnitude() <= length && (point - end).magnitude() <= length
        }

    /**
     * Returns distance between this section and given [point] to this section.
     */
    fun distanceTo(point: Vec2<T>): T {
        val projection = projectionOf(point)
        return if ((projection - start).magnitude() <= length && (projection - end).magnitude() <= length) {
            (point - projection).magnitude()
        } else {
            min((point - start).magnitude(), (point - end).magnitude())
        }
    }

    /**
     * Returns projection of given [point] onto this section.
     */
    fun projectionOf(point: Vec2<T>): Vec2<T> =
        start + direction * ((point - start) dot direction)

    /**
     * Calculates intersection point of this section and given [other] section.
     *
     * If the two sections are parallel to each other or do not intersect, `null` is returned instead.
     */
    fun intersectionWith(other: Section2<T>): Vec2<T>? {
        val dividendT = Mat2(
            elements = listOf(
                this.start.x - other.start.x,
                this.start.y - other.start.y,
                other.start.x - other.end.x,
                other.start.y - other.end.y
            ),
            type = type
        ).det()
        val dividendU = Mat2(
            elements = listOf(
                this.start.x - other.start.x,
                this.start.y - other.start.y,
                this.start.x - this.end.x,
                this.start.y - this.end.y
            ),
            type = type
        ).det()
        val divisor = Mat2(
            elements = listOf(
                this.start.x - this.end.x,
                this.start.y - this.end.y,
                other.start.x - other.end.x,
                other.start.y - other.end.y
            ),
            type = type
        ).det()

        if (divisor.compareTo(zero(type)) == 0) return null

        val t = dividendT / divisor
        val u = dividendU / divisor

        val validParameterRange = zero(type)..one(type)

        return if (t !in validParameterRange || u !in validParameterRange) null else this.start + this.asVector * t
    }
}
