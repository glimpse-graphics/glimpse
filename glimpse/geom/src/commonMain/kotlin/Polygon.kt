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
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.minus
import graphics.glimpse.types.one
import graphics.glimpse.types.plus
import graphics.glimpse.types.rotationZ
import graphics.glimpse.types.translation
import graphics.glimpse.types.zero
import kotlin.reflect.KClass

/**
 * 2D polygon with given [vertices].
 *
 * @since v1.3.0
 */
data class Polygon<T>(

    /**
     * Vertices of this polygon.
     */
    val vertices: List<Vec2<T>>,

    /**
     * Type of coordinates in this polygon.
     */
    override val type: KClass<T>

) : Shape<T> where T : Number, T : Comparable<T> {

    /**
     * Bounding box of this polygon.
     */
    override val boundingBox: Rectangle<T> by lazy {
        if (vertices.isEmpty()) {
            Rectangle(
                left = zero(this.type),
                bottom = zero(this.type),
                right = zero(this.type),
                top = zero(this.type),
                type = this.type
            )
        }
        else {
            Rectangle(
                left = vertices.minOf { it.x },
                bottom = vertices.minOf { it.y },
                right = vertices.maxOf { it.x },
                top = vertices.maxOf { it.y },
                type = this.type
            )
        }
    }

    /**
     * Midpoint of this polygon.
     */
    override val midpoint: Vec2<T> by lazy {
        if (vertices.isEmpty()) Vec2(x = zero(this.type), y = zero(this.type), type = this.type)
        else boundingBox.midpoint
    }

    /**
     * Edges of this polygon.
     */
    val edges: List<Section2<T>> by lazy {
        if (vertices.isEmpty()) emptyList()
        else (vertices + vertices.first()).zipWithNext { a, b -> Section2(a, b) }
    }

    /**
     * Returns `true` if given [point] is inside this polygon.
     *
     * If a point is located on the edge of the polygon, it is considered inside.
     */
    override operator fun contains(point: Vec2<T>): Boolean {
        if (point !in boundingBox) return false
        if (edges.any { point in it }) return true

        val lineToPlusInfinity = Section2(point, Vec2(x = boundingBox.right + 1f, y = point.y, type = this.type))
        val lineToMinusInfinity = Section2(point, Vec2(x = boundingBox.left - 1f, y = point.y, type = this.type))
        val rightIntersectionsCount = edges
            .mapNotNull { edge -> edge.intersectionWith(lineToPlusInfinity) }
            .distinct()
            .count()
        val leftIntersectionsCount = edges
            .mapNotNull { edge -> edge.intersectionWith(lineToMinusInfinity) }
            .distinct()
            .count()

        return (rightIntersectionsCount % 2 == 1) && (leftIntersectionsCount % 2 == 1)
    }

    /**
     * Returns the distance between this polygon and the given [point].
     */
    override fun distanceTo(point: Vec2<T>): T =
        if (point in this) zero(this.type)
        else edges.minOf { edge -> edge.distanceTo(point) }

    /**
     * Returns `true` if given [section] intersects this polygon.
     */
    override infix fun intersects(section: Section2<T>): Boolean =
        section.start in this || section.end in this || edges.any { edge -> edge.intersectionWith(section) != null }

    /**
     * Returns `true` if given [other] shape intersects this polygon.
     */
    override fun intersects(other: Shape<T>): Boolean = other.intersects(polygon = this)

    /**
     * Returns `true` if given [polygon] intersects this polygon.
     */
    override infix fun intersects(polygon: Polygon<T>): Boolean =
        polygon.edges.any { edge -> intersects(edge) } || vertices.any { vertex -> vertex in polygon }

    /**
     * Returns `true` if given [circle] intersects this circle.
     */
    override infix fun intersects(circle: Circle<T>): Boolean =
        circle.center in this || edges.any { edge -> circle intersects edge }

    /**
     * Returns this polygon translated by given [vector].
     */
    override fun translate(vector: Vec2<T>): Polygon<T> =
        copy(vertices = vertices.map { vertex -> vertex + vector })

    /**
     * Returns this polygon rotated by given [angle] around origin point.
     */
    override fun rotate(angle: Angle<T>): Polygon<T> =
        transform(matrix = rotationZ(angle, this.type))

    /**
     * Returns this polygon rotated by given [angle] around given [pivot] point.
     */
    override fun rotate(angle: Angle<T>, pivot: Vec2<T>): Polygon<T> =
        transform(matrix = translation(pivot.toVec3()) * rotationZ(angle, this.type) * translation(-pivot.toVec3()))

    /**
     * Returns this polygon scaled by given [scale] value relative to origin point.
     */
    override fun scale(scale: T): Polygon<T> =
        copy(vertices = vertices.map { vertex -> vertex * scale })

    /**
     * Returns this polygon scaled by given [scale] value relative to given [center] point.
     */
    override fun scale(scale: T, center: Vec2<T>): Polygon<T> =
        copy(vertices = vertices.map { vertex -> center + (vertex - center) * scale })

    /**
     * Returns this polygon transformed using given [matrix].
     */
    fun transform(matrix: Mat4<T>): Polygon<T> =
        copy(vertices = vertices.map { vertex -> (matrix * vertex.toVec4(w = one(this.type))).toVec2() })

    /**
     * Returns a polygon composed of all vertices of this polygon
     * and a given [vertex] added at the end.
     */
    operator fun plus(vertex: Vec2<T>): Polygon<T> =
        Polygon(vertices = vertices + vertex, type = type)

    /**
     * Returns a polygon composed of all vertices of this polygon except a given [vertex].
     */
    operator fun minus(vertex: Vec2<T>): Polygon<T> =
        Polygon(vertices = vertices - vertex, type = type)

    /**
     * Returns the same polygon, but with vertices in reversed order.
     */
    fun reversed(): Polygon<T> =
        copy(vertices = vertices.reversed())
}

/**
 * Returns a new [Polygon] with given [vertices].
 */
@Suppress("FunctionNaming")
inline fun <reified T> Polygon(
    vertices: List<Vec2<T>>,
): Polygon<T> where T : Number, T : Comparable<T> =
    Polygon(
        vertices = vertices,
        type = T::class
    )
