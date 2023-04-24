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

import graphics.glimpse.testing.assertEqualsWithDelta
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec2
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RectangleFTest {

    @Test
    fun `GIVEN a rectangle, WHEN boundingBox, THEN return the same rectangle`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)

        val result = rectangle.boundingBox

        assertEqualsWithDelta(rectangle, result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN midpoint, THEN return midpoint of this rectangle`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)

        val result = rectangle.midpoint

        assertEqualsWithDelta(Vec2(x = 2f, y = 3f), result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN width, THEN return width of this rectangle`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)

        val result = rectangle.width

        assertEqualsWithDelta(2f, result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN height, THEN return height of this rectangle`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)

        val result = rectangle.height

        assertEqualsWithDelta(2f, result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN asPolygon, THEN return height of this rectangle`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)

        val result = rectangle.asPolygon

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 1f, y = 2f),
                    Vec2(x = 3f, y = 2f),
                    Vec2(x = 3f, y = 4f),
                    Vec2(x = 1f, y = 4f)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a rectangle and a point outside of rectangle, WHEN contains, THEN return false`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val point = Vec2(x = 0f, y = 2f)

        val result = point in rectangle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a rectangle and a point inside the rectangle, WHEN contains, THEN return true`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val point = Vec2(x = 1.5f, y = 2.5f)

        val result = point in rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a point on rectangle perimeter, WHEN contains, THEN return true`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val point = Vec2(x = 1f, y = 3f)

        val result = point in rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and point outside rectangle, WHEN distanceTo, THEN return distance from point to polygon`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val point = Vec2(x = 0f, y = 2f)

        val result = rectangle.distanceTo(point)

        assertEqualsWithDelta(1f, result)
    }

    @Test
    fun `GIVEN a rectangle and a point inside the rectangle, WHEN distanceTo, THEN return 0`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val point = Vec2(x = 1.5f, y = 2.5f)

        val result = rectangle.distanceTo(point)

        assertEqualsWithDelta(0f, result)
    }

    @Test
    fun `GIVEN a rectangle and a point on rectangle perimeter, WHEN distanceTo, THEN return 0`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val point = Vec2(x = 1f, y = 3f)

        val result = rectangle.distanceTo(point)

        assertEqualsWithDelta(0f, result)
    }

    @Test
    fun `GIVEN a rectangle and a section outside of rectangle, WHEN intersects, THEN return false`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val section = Section2(start = Vec2(x = 0f, y = 2f), end = Vec2(x = 1f, y = 5f))

        val result = rectangle intersects section

        assertFalse(result)
    }

    @Test
    fun `GIVEN a rectangle and a section touching the rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val section = Section2(start = Vec2(x = 0f, y = 3f), end = Vec2(x = 2f, y = 1f))

        val result = rectangle intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a section inside the rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val section = Section2(start = Vec2(x = 1.5f, y = 2.5f), end = Vec2(x = 2.5f, y = 3.5f))

        val result = rectangle intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 disjoint rectangles, WHEN intersects, THEN return false`() {
        val rectangle1 = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val rectangle2 = Rectangle(left = -1f, bottom = -1f, right = 0f, top = 2f)

        val result = rectangle1 intersects rectangle2

        assertFalse(result)
    }

    @Test
    fun `GIVEN 2 touching rectangles, WHEN intersects, THEN return true`() {
        val rectangle1 = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val rectangle2 = Rectangle(left = -1f, bottom = -1f, right = 1f, top = 3f)

        val result = rectangle1 intersects rectangle2

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 conjoint rectangles, WHEN intersects, THEN return true`() {
        val rectangle1 = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val rectangle2 = Rectangle(left = 1.5f, bottom = 2.5f, right = 2.5f, top = 3.5f)

        val result = rectangle1 intersects rectangle2

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a polygon outside of rectangle, WHEN intersects, THEN return false`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 0f, y = 3f)
            )
        )

        val result = rectangle intersects polygon

        assertFalse(result)
    }

    @Test
    fun `GIVEN a rectangle and a polygon touching the rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 2f, y = 0f),
                Vec2(x = 0f, y = 4f)
            )
        )

        val result = rectangle intersects polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a polygon inside of rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 1.5f, y = 2.5f),
                Vec2(x = 2.5f, y = 2.5f),
                Vec2(x = 2.5f, y = 3.5f)
            )
        )

        val result = rectangle intersects polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a circle outside of rectangle, WHEN intersects, THEN return false`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val circle = Circle(center = Vec2(x = 0f, y = 1f), radius = 1f)

        val result = rectangle intersects circle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a rectangle and a circle touching the rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)

        val result = rectangle intersects circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a circle inside the rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)
        val circle = Circle(center = Vec2(x = 2f, y = 3f), radius = 1f)

        val result = rectangle intersects circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN translate, THEN return translated rectangle`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)

        val result = rectangle.translate(Vec2(x = -1f, y = -2f))

        assertEqualsWithDelta(Rectangle(left = 0f, bottom = 0f, right = 2f, top = 2f), result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN rotate, THEN return rectangle as polygon rotated around origin point`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)

        val result = rectangle.rotate(angle = Angle.rightAngle())

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = -2f, y = 1f),
                    Vec2(x = -2f, y = 3f),
                    Vec2(x = -4f, y = 3f),
                    Vec2(x = -4f, y = 1f)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a rectangle, WHEN rotate around pivot, THEN return rectangle as polygon rotated around pivot`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)

        val result = rectangle.rotate(angle = Angle.rightAngle(), pivot = Vec2(x = 2f, y = 3f))

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 3f, y = 2f),
                    Vec2(x = 3f, y = 4f),
                    Vec2(x = 1f, y = 4f),
                    Vec2(x = 1f, y = 2f)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a rectangle, WHEN scale, THEN return scaled rectangle`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)

        val result = rectangle.scale(scale = 2f)

        assertEqualsWithDelta(Rectangle(left = 2f, bottom = 4f, right = 6f, top = 8f), result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN scale relative to point, THEN return scaled rectangle`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)

        val result = rectangle.scale(scale = 2f, center = Vec2(x = 2f, y = 3f))

        assertEqualsWithDelta(Rectangle(left = 0f, bottom = 1f, right = 4f, top = 5f), result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN transform, THEN return transformed rectangle as polygon`() {
        val rectangle = Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f)

        val result = rectangle.transform(
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
                    Vec2(x = 9f, y = 25f),
                    Vec2(x = 11f, y = 35f),
                    Vec2(x = 15f, y = 47f),
                    Vec2(x = 13f, y = 37f)
                )
            ),
            result
        )
    }
}
