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

class RectangleDTest {

    @Test
    fun `GIVEN a rectangle, WHEN boundingBox, THEN return the same rectangle`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)

        val result = rectangle.boundingBox

        assertEqualsWithDelta(rectangle, result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN midpoint, THEN return midpoint of this rectangle`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)

        val result = rectangle.midpoint

        assertEqualsWithDelta(Vec2(x = 2.0, y = 3.0), result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN width, THEN return width of this rectangle`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)

        val result = rectangle.width

        assertEqualsWithDelta(2.0, result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN height, THEN return height of this rectangle`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)

        val result = rectangle.height

        assertEqualsWithDelta(2.0, result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN asPolygon, THEN return height of this rectangle`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)

        val result = rectangle.asPolygon

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 1.0, y = 2.0),
                    Vec2(x = 3.0, y = 2.0),
                    Vec2(x = 3.0, y = 4.0),
                    Vec2(x = 1.0, y = 4.0)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a rectangle and a point outside of rectangle, WHEN contains, THEN return false`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val point = Vec2(x = 0.0, y = 2.0)

        val result = point in rectangle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a rectangle and a point inside the rectangle, WHEN contains, THEN return true`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val point = Vec2(x = 1.5, y = 2.5)

        val result = point in rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a point on rectangle perimeter, WHEN contains, THEN return true`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val point = Vec2(x = 1.0, y = 3.0)

        val result = point in rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and point outside rectangle, WHEN distanceTo, THEN return distance from point to polygon`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val point = Vec2(x = 0.0, y = 2.0)

        val result = rectangle.distanceTo(point)

        assertEqualsWithDelta(1.0, result)
    }

    @Test
    fun `GIVEN a rectangle and a point inside the rectangle, WHEN distanceTo, THEN return 0`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val point = Vec2(x = 1.5, y = 2.5)

        val result = rectangle.distanceTo(point)

        assertEqualsWithDelta(0.0, result)
    }

    @Test
    fun `GIVEN a rectangle and a point on rectangle perimeter, WHEN distanceTo, THEN return 0`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val point = Vec2(x = 1.0, y = 3.0)

        val result = rectangle.distanceTo(point)

        assertEqualsWithDelta(0.0, result)
    }

    @Test
    fun `GIVEN a rectangle and a section outside of rectangle, WHEN intersects, THEN return false`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val section = Section2(start = Vec2(x = 0.0, y = 2.0), end = Vec2(x = 1.0, y = 5.0))

        val result = rectangle intersects section

        assertFalse(result)
    }

    @Test
    fun `GIVEN a rectangle and a section touching the rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val section = Section2(start = Vec2(x = 0.0, y = 3.0), end = Vec2(x = 2.0, y = 1.0))

        val result = rectangle intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a section inside the rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val section = Section2(start = Vec2(x = 1.5, y = 2.5), end = Vec2(x = 2.5, y = 3.5))

        val result = rectangle intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 disjoint rectangles, WHEN intersects, THEN return false`() {
        val rectangle1 = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val rectangle2 = Rectangle(left = -1.0, bottom = -1.0, right = 0.0, top = 2.0)

        val result = rectangle1 intersects rectangle2

        assertFalse(result)
    }

    @Test
    fun `GIVEN 2 touching rectangles, WHEN intersects, THEN return true`() {
        val rectangle1 = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val rectangle2 = Rectangle(left = -1.0, bottom = -1.0, right = 1.0, top = 3.0)

        val result = rectangle1 intersects rectangle2

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 conjoint rectangles, WHEN intersects, THEN return true`() {
        val rectangle1 = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val rectangle2 = Rectangle(left = 1.5, bottom = 2.5, right = 2.5, top = 3.5)

        val result = rectangle1 intersects rectangle2

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a polygon outside of rectangle, WHEN intersects, THEN return false`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 0.0, y = 3.0)
            )
        )

        val result = rectangle intersects polygon

        assertFalse(result)
    }

    @Test
    fun `GIVEN a rectangle and a polygon touching the rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 2.0, y = 0.0),
                Vec2(x = 0.0, y = 4.0)
            )
        )

        val result = rectangle intersects polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a polygon inside of rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 1.5, y = 2.5),
                Vec2(x = 2.5, y = 2.5),
                Vec2(x = 2.5, y = 3.5)
            )
        )

        val result = rectangle intersects polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a circle outside of rectangle, WHEN intersects, THEN return false`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val circle = Circle(center = Vec2(x = 0.0, y = 1.0), radius = 1.0)

        val result = rectangle intersects circle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a rectangle and a circle touching the rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)

        val result = rectangle intersects circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle and a circle inside the rectangle, WHEN intersects, THEN return true`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)
        val circle = Circle(center = Vec2(x = 2.0, y = 3.0), radius = 1.0)

        val result = rectangle intersects circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN translate, THEN return translated rectangle`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)

        val result = rectangle.translate(Vec2(x = -1.0, y = -2.0))

        assertEqualsWithDelta(Rectangle(left = 0.0, bottom = 0.0, right = 2.0, top = 2.0), result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN rotate, THEN return rectangle as polygon rotated around origin point`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)

        val result = rectangle.rotate(angle = Angle.rightAngle())

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = -2.0, y = 1.0),
                    Vec2(x = -2.0, y = 3.0),
                    Vec2(x = -4.0, y = 3.0),
                    Vec2(x = -4.0, y = 1.0)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a rectangle, WHEN rotate around pivot, THEN return rectangle as polygon rotated around pivot`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)

        val result = rectangle.rotate(angle = Angle.rightAngle(), pivot = Vec2(x = 2.0, y = 3.0))

        assertEqualsWithDelta(
            Polygon(
                vertices = listOf(
                    Vec2(x = 3.0, y = 2.0),
                    Vec2(x = 3.0, y = 4.0),
                    Vec2(x = 1.0, y = 4.0),
                    Vec2(x = 1.0, y = 2.0)
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a rectangle, WHEN scale, THEN return scaled rectangle`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)

        val result = rectangle.scale(scale = 2.0)

        assertEqualsWithDelta(Rectangle(left = 2.0, bottom = 4.0, right = 6.0, top = 8.0), result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN scale relative to point, THEN return scaled rectangle`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)

        val result = rectangle.scale(scale = 2.0, center = Vec2(x = 2.0, y = 3.0))

        assertEqualsWithDelta(Rectangle(left = 0.0, bottom = 1.0, right = 4.0, top = 5.0), result)
    }

    @Test
    fun `GIVEN a rectangle, WHEN transform, THEN return transformed rectangle as polygon`() {
        val rectangle = Rectangle(left = 1.0, bottom = 2.0, right = 3.0, top = 4.0)

        val result = rectangle.transform(
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
                    Vec2(x = 9.0, y = 25.0),
                    Vec2(x = 11.0, y = 35.0),
                    Vec2(x = 15.0, y = 47.0),
                    Vec2(x = 13.0, y = 37.0)
                )
            ),
            result
        )
    }
}
