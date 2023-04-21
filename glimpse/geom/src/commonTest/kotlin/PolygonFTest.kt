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

class PolygonFTest {

    @Test
    fun `GIVEN a polygon, WHEN boundingBox, THEN return boundingBox of this polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )

        val result = polygon.boundingBox

        assertEqualsWithDelta(Rectangle(left = 0f, bottom = 0f, right = 2f, top = 2f), result)
    }

    @Test
    fun `GIVEN a polygon, WHEN midpoint, THEN return midpoint of this polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )

        val result = polygon.midpoint

        assertEqualsWithDelta(Vec2(x = 1f, y = 1f), result)
    }

    @Test
    fun `GIVEN a polygon, WHEN edges, THEN return edges of this polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )

        val result = polygon.edges

        assertEqualsWithDelta(
            listOf(
                Section2(start = Vec2(x = 0f, y = 0f), end = Vec2(x = 2f, y = 0f)),
                Section2(start = Vec2(x = 2f, y = 0f), end = Vec2(x = 2f, y = 1f)),
                Section2(start = Vec2(x = 2f, y = 1f), end = Vec2(x = 1f, y = 2f)),
                Section2(start = Vec2(x = 1f, y = 2f), end = Vec2(x = 0f, y = 1f)),
                Section2(start = Vec2(x = 0f, y = 1f), end = Vec2(x = 0f, y = 0f)),
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon and a point outside of polygon, WHEN contains, THEN return false`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val point = Vec2(x = 0f, y = 2f)

        val result = point in polygon

        assertFalse(result)
    }

    @Test
    fun `GIVEN a polygon and a point inside the polygon, WHEN contains, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val point = Vec2(x = 0.5f, y = 0.5f)

        val result = point in polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a point on polygon perimeter, WHEN contains, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val point = Vec2(x = 0f, y = 0f)

        val result = point in polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a point outside polygon, WHEN distanceTo, THEN return distance from point to polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val point = Vec2(x = 0f, y = 2f)

        val result = polygon.distanceTo(point)

        assertEqualsWithDelta(0.70710677f, result)
    }

    @Test
    fun `GIVEN a polygon and a point inside the polygon, WHEN distanceTo, THEN return 0`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val point = Vec2(x = 0.5f, y = 0.5f)

        val result = polygon.distanceTo(point)

        assertEqualsWithDelta(0f, result)
    }

    @Test
    fun `GIVEN a polygon and a point on polygon perimeter, WHEN distanceTo, THEN return 0`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val point = Vec2(x = 0f, y = 0f)

        val result = polygon.distanceTo(point)

        assertEqualsWithDelta(0f, result)
    }

    @Test
    fun `GIVEN a polygon and a section outside of polygon, WHEN intersects, THEN return false`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val section = Section2(start = Vec2(x = 0f, y = 2f), end = Vec2(x = 1f, y = 3f))

        val result = polygon intersects section

        assertFalse(result)
    }

    @Test
    fun `GIVEN a polygon and a section touching the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val section = Section2(start = Vec2(x = -1f, y = 1f), end = Vec2(x = 1f, y = -1f))

        val result = polygon intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a section inside the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val section = Section2(start = Vec2(x = 0.5f, y = 0.5f), end = Vec2(x = 1.5f, y = 1.5f))

        val result = polygon intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a rectangle outside of polygon, WHEN intersects, THEN return false`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val rectangle = Rectangle(left = 3f, bottom = 0f, right = 5f, top = 2f)

        val result = polygon intersects rectangle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a polygon and a rectangle touching the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val rectangle = Rectangle(left = 2f, bottom = 0f, right = 5f, top = 2f)

        val result = polygon intersects rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a rectangle inside the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val rectangle = Rectangle(left = 0.5f, bottom = 0.5f, right = 1.5f, top = 1.5f)

        val result = polygon intersects rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 disjoint polygons, WHEN intersects, THEN return false`() {
        val polygon1 = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val polygon2 = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 2f),
                Vec2(x = 1f, y = 3f),
                Vec2(x = 0f, y = 3f)
            )
        )

        val result = polygon1 intersects polygon2

        assertFalse(result)
    }

    @Test
    fun `GIVEN 2 touching polygons, WHEN intersects, THEN return true`() {
        val polygon1 = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val polygon2 = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 2f)
            )
        )

        val result = polygon1 intersects polygon2

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 conjoint polygons, WHEN intersects, THEN return true`() {
        val polygon1 = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val polygon2 = Polygon(
            vertices = listOf(
                Vec2(x = 0.5f, y = 0.5f),
                Vec2(x = 1.5f, y = 0.5f),
                Vec2(x = 1f, y = 1.5f)
            )
        )

        val result = polygon1 intersects polygon2

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a circle outside of polygon, WHEN intersects, THEN return false`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val circle = Circle(center = Vec2(x = -1f, y = -1f), radius = 1f)

        val result = polygon intersects circle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a polygon and a circle touching the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val circle = Circle(center = Vec2(x = -1f, y = 1f), radius = 1f)

        val result = polygon intersects circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon and a circle inside the polygon, WHEN intersects, THEN return true`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val circle = Circle(center = Vec2(x = 0f, y = 0f), radius = 1f)

        val result = polygon intersects circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a polygon, WHEN translate, THEN return translated polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )

        val result = polygon.translate(Vec2(x = 1f, y = 1f))

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 1f, y = 1f),
                    Vec2(x = 3f, y = 1f),
                    Vec2(x = 3f, y = 2f),
                    Vec2(x = 2f, y = 3f),
                    Vec2(x = 1f, y = 2f)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN rotate, THEN return polygon rotated around origin point`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )

        val result = polygon.rotate(angle = Angle.rightAngle())

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 0f, y = 0f),
                    Vec2(x = 0f, y = 2f),
                    Vec2(x = -1f, y = 2f),
                    Vec2(x = -2f, y = 1f),
                    Vec2(x = -1f, y = 0f)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN rotate around pivot, THEN return polygon rotated around pivot`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )

        val result = polygon.rotate(angle = Angle.rightAngle(), pivot = Vec2(x = 1f, y = 1f))

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 2f, y = 0f),
                    Vec2(x = 2f, y = 2f),
                    Vec2(x = 1f, y = 2f),
                    Vec2(x = 0f, y = 1f),
                    Vec2(x = 1f, y = 0f)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN scale, THEN return scaled polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )

        val result = polygon.scale(scale = 2f)

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 0f, y = 0f),
                    Vec2(x = 4f, y = 0f),
                    Vec2(x = 4f, y = 2f),
                    Vec2(x = 2f, y = 4f),
                    Vec2(x = 0f, y = 2f)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN scale relative to point, THEN return scaled polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )

        val result = polygon.scale(scale = 2f, center = Vec2(x = 1f, y = 1f))

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = -1f, y = -1f),
                    Vec2(x = 3f, y = -1f),
                    Vec2(x = 3f, y = 1f),
                    Vec2(x = 1f, y = 3f),
                    Vec2(x = -1f, y = 1f)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN transform, THEN return transformed polygon`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )

        val result = polygon.transform(
            matrix = Mat4(
                listOf(
                    1f, 5f, 9f, 13f,
                    2f, 6f, 10f, 14f,
                    3f, 7f, 11f, 15f,
                    4f, 8f, 12f, 16f
                )
            )
        )

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 4f, y = 8f),
                    Vec2(x = 6f, y = 18f),
                    Vec2(x = 8f, y = 24f),
                    Vec2(x = 9f, y = 25f),
                    Vec2(x = 6f, y = 14f)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon and a vector, WHEN plus, THEN return polygon with additional vertex`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val vector = Vec2(x = -1f, y = -1f)

        val result = polygon + vector

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 0f, y = 0f),
                    Vec2(x = 2f, y = 0f),
                    Vec2(x = 2f, y = 1f),
                    Vec2(x = 1f, y = 2f),
                    Vec2(x = 0f, y = 1f),
                    Vec2(x = -1f, y = -1f)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon and a vector, WHEN minus, THEN return polygon without removed vertex`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )
        val vector = Vec2(x = 2f, y = 1f)

        val result = polygon - vector

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 0f, y = 0f),
                    Vec2(x = 2f, y = 0f),
                    Vec2(x = 1f, y = 2f),
                    Vec2(x = 0f, y = 1f)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a polygon, WHEN reversed, THEN return polygon with vertices in reversed order`() {
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 2f, y = 1f),
                Vec2(x = 1f, y = 2f),
                Vec2(x = 0f, y = 1f)
            )
        )

        val result = polygon.reversed()

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 0f, y = 1f),
                    Vec2(x = 1f, y = 2f),
                    Vec2(x = 2f, y = 1f),
                    Vec2(x = 2f, y = 0f),
                    Vec2(x = 0f, y = 0f)
                )
            ),
            result
        )
    }
}
