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

import graphics.glimpse.testing.assertEqualsWithDelta
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec2
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PolygonDTest {

    @Test
    fun `GIVEN a polygon, WHEN boundingBox, THEN return boundingBox of this polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )

        val result = polygon.boundingBox

        assertEqualsWithDelta(Rectangle(left = 0.0, bottom = 0.0, right = 2.0, top = 2.0), result)
    }

    @Test
    fun `GIVEN a polygon, WHEN midpoint, THEN return midpoint of this polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )

        val result = polygon.midpoint

        assertEqualsWithDelta(Vec2(x = 1.0, y = 1.0), result)
    }

    @Test
    fun `GIVEN a polygon, WHEN edges, THEN return edges of this polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )

        val result = polygon.edges

        assertEqualsWithDelta(
            listOf(
                Section2(start = Vec2(x = 0.0, y = 0.0), end = Vec2(x = 2.0, y = 0.0)),
                Section2(start = Vec2(x = 2.0, y = 0.0), end = Vec2(x = 2.0, y = 1.0)),
                Section2(start = Vec2(x = 2.0, y = 1.0), end = Vec2(x = 1.0, y = 2.0)),
                Section2(start = Vec2(x = 1.0, y = 2.0), end = Vec2(x = 0.0, y = 1.0)),
                Section2(start = Vec2(x = 0.0, y = 1.0), end = Vec2(x = 0.0, y = 0.0)),
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon and a point outside of polygon, WHEN contains, THEN return false`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val point = Vec2(x = 0.0, y = 2.0)

        val result = point in polygon

        assertFalse(result)
    }

    @Test
    fun `GIVEN a polygon and a point inside the polygon, WHEN contains, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val point = Vec2(x = 0.5, y = 0.5)

        val result = point in polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a point on polygon perimeter, WHEN contains, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val point = Vec2(x = 0.0, y = 0.0)

        val result = point in polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a point outside polygon, WHEN distanceTo, THEN return distance from point to polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val point = Vec2(x = 0.0, y = 2.0)

        val result = polygon.distanceTo(point)

        assertEqualsWithDelta(0.7071067811865475, result)
    }

    @Test
    fun `GIVEN a polygon and a point inside the polygon, WHEN distanceTo, THEN return 0`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val point = Vec2(x = 0.5, y = 0.5)

        val result = polygon.distanceTo(point)

        assertEqualsWithDelta(0.0, result)
    }

    @Test
    fun `GIVEN a polygon and a point on polygon perimeter, WHEN distanceTo, THEN return 0`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val point = Vec2(x = 0.0, y = 0.0)

        val result = polygon.distanceTo(point)

        assertEqualsWithDelta(0.0, result)
    }

    @Test
    fun `GIVEN a polygon and a section outside of polygon, WHEN intersects, THEN return false`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val section = Section2(start = Vec2(x = 0.0, y = 2.0), end = Vec2(x = 1.0, y = 3.0))

        val result = polygon intersects section

        assertFalse(result)
    }

    @Test
    fun `GIVEN a polygon and a section touching the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val section = Section2(start = Vec2(x = -1.0, y = 1.0), end = Vec2(x = 1.0, y = -1.0))

        val result = polygon intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a section inside the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val section = Section2(start = Vec2(x = 0.5, y = 0.5), end = Vec2(x = 1.5, y = 1.5))

        val result = polygon intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a rectangle outside of polygon, WHEN intersects, THEN return false`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val rectangle = Rectangle(left = 3.0, bottom = 0.0, right = 5.0, top = 2.0)

        val result = polygon intersects rectangle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a polygon and a rectangle touching the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val rectangle = Rectangle(left = 2.0, bottom = 0.0, right = 5.0, top = 2.0)

        val result = polygon intersects rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a rectangle inside the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val rectangle = Rectangle(left = 0.5, bottom = 0.5, right = 1.5, top = 1.5)

        val result = polygon intersects rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 disjoint polygons, WHEN intersects, THEN return false`() {
        val polygon1 = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val polygon2 = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 2.0),
                Vec2(x = 1.0, y = 3.0),
                Vec2(x = 0.0, y = 3.0)
            )
        )

        val result = polygon1 intersects polygon2

        assertFalse(result)
    }

    @Test
    fun `GIVEN 2 touching polygons, WHEN intersects, THEN return true`() {
        val polygon1 = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val polygon2 = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 2.0)
            )
        )

        val result = polygon1 intersects polygon2

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 conjoint polygons, WHEN intersects, THEN return true`() {
        val polygon1 = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val polygon2 = Polygon(
            vertices = listOf(
                Vec2(x = 0.5, y = 0.5),
                Vec2(x = 1.5, y = 0.5),
                Vec2(x = 1.0, y = 1.5)
            )
        )

        val result = polygon1 intersects polygon2

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a circle outside of polygon, WHEN intersects, THEN return false`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val circle = Circle(center = Vec2(x = -1.0, y = -1.0), radius = 1.0)

        val result = polygon intersects circle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a polygon and a circle touching the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val circle = Circle(center = Vec2(x = -1.0, y = 1.0), radius = 1.0)

        val result = polygon intersects circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a circle inside the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val circle = Circle(center = Vec2(x = 0.0, y = 0.0), radius = 1.0)

        val result = polygon intersects circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon, WHEN translate, THEN return translated polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )

        val result = polygon.translate(Vec2(x = 1.0, y = 1.0))

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 1.0, y = 1.0),
                    Vec2(x = 3.0, y = 1.0),
                    Vec2(x = 3.0, y = 2.0),
                    Vec2(x = 2.0, y = 3.0),
                    Vec2(x = 1.0, y = 2.0)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN rotate, THEN return polygon rotated around origin point`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )

        val result = polygon.rotate(angle = Angle.rightAngle())

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 0.0, y = 0.0),
                    Vec2(x = 0.0, y = 2.0),
                    Vec2(x = -1.0, y = 2.0),
                    Vec2(x = -2.0, y = 1.0),
                    Vec2(x = -1.0, y = 0.0)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN rotate around pivot, THEN return polygon rotated around pivot`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )

        val result = polygon.rotate(angle = Angle.rightAngle(), pivot = Vec2(x = 1.0, y = 1.0))

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 2.0, y = 0.0),
                    Vec2(x = 2.0, y = 2.0),
                    Vec2(x = 1.0, y = 2.0),
                    Vec2(x = 0.0, y = 1.0),
                    Vec2(x = 1.0, y = 0.0)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN scale, THEN return scaled polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )

        val result = polygon.scale(scale = 2.0)

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 0.0, y = 0.0),
                    Vec2(x = 4.0, y = 0.0),
                    Vec2(x = 4.0, y = 2.0),
                    Vec2(x = 2.0, y = 4.0),
                    Vec2(x = 0.0, y = 2.0)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN scale relative to point, THEN return scaled polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )

        val result = polygon.scale(scale = 2.0, center = Vec2(x = 1.0, y = 1.0))

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = -1.0, y = -1.0),
                    Vec2(x = 3.0, y = -1.0),
                    Vec2(x = 3.0, y = 1.0),
                    Vec2(x = 1.0, y = 3.0),
                    Vec2(x = -1.0, y = 1.0)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN transform, THEN return transformed polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )

        val result = polygon.transform(
            matrix = Mat4(
                listOf(
                    1.0, 5.0, 9.0, 13.0,
                    2.0, 6.0, 10.0, 14.0,
                    3.0, 7.0, 11.0, 15.0,
                    4.0, 8.0, 12.0, 16.0
                )
            )
        )

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 4.0, y = 8.0),
                    Vec2(x = 6.0, y = 18.0),
                    Vec2(x = 8.0, y = 24.0),
                    Vec2(x = 9.0, y = 25.0),
                    Vec2(x = 6.0, y = 14.0)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon and a vector, WHEN plus, THEN return polygon with additional vertex`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val vector = Vec2(x = -1.0, y = -1.0)

        val result = polygon + vector

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 0.0, y = 0.0),
                    Vec2(x = 2.0, y = 0.0),
                    Vec2(x = 2.0, y = 1.0),
                    Vec2(x = 1.0, y = 2.0),
                    Vec2(x = 0.0, y = 1.0),
                    Vec2(x = -1.0, y = -1.0)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon and a vector, WHEN minus, THEN return polygon without removed vertex`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )
        val vector = Vec2(x = 2.0, y = 1.0)

        val result = polygon - vector

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 0.0, y = 0.0),
                    Vec2(x = 2.0, y = 0.0),
                    Vec2(x = 1.0, y = 2.0),
                    Vec2(x = 0.0, y = 1.0)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN reversed, THEN return polygon with vertices in reversed order`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 2.0, y = 1.0),
                Vec2(x = 1.0, y = 2.0),
                Vec2(x = 0.0, y = 1.0)
            )
        )

        val result = polygon.reversed()

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 0.0, y = 1.0),
                    Vec2(x = 1.0, y = 2.0),
                    Vec2(x = 2.0, y = 1.0),
                    Vec2(x = 2.0, y = 0.0),
                    Vec2(x = 0.0, y = 0.0)
                )
            ),
            result
        )
    }
}
