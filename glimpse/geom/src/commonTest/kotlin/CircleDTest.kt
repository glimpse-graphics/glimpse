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
import graphics.glimpse.types.Vec2
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CircleDTest {

    @Test
    fun `GIVEN a circle, WHEN boundingBox, THEN return boundingBox of this circle`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)

        val result = circle.boundingBox

        assertEqualsWithDelta(Rectangle(left = 0.0, bottom = 0.0, right = 2.0, top = 2.0), result)
    }

    @Test
    fun `GIVEN a circle, WHEN midpoint, THEN return center point of this circle`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)

        val result = circle.midpoint

        assertEqualsWithDelta(Vec2(x = 1.0, y = 1.0), result)
    }

    @Test
    fun `GIVEN a circle and a point outside of circle, WHEN contains, THEN return false`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val point = Vec2(x = 0.0, y = 0.0)

        val result = point in circle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a circle and a point inside the circle, WHEN contains, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val point = Vec2(x = 0.5, y = 0.5)

        val result = point in circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a point on circle perimeter, WHEN contains, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val point = Vec2(x = 0.0, y = 1.0)

        val result = point in circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a point outside of circle, WHEN distanceTo, THEN return distance from point to circle`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val point = Vec2(x = 0.0, y = 0.0)

        val result = circle.distanceTo(point)

        assertEqualsWithDelta(0.414213562373095, result)
    }

    @Test
    fun `GIVEN a circle and a point inside the circle, WHEN distanceTo, THEN return 0`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val point = Vec2(x = 0.5, y = 0.5)

        val result = circle.distanceTo(point)

        assertEqualsWithDelta(0.0, result)
    }

    @Test
    fun `GIVEN a circle and a point on circle perimeter, WHEN distanceTo, THEN return 0`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val point = Vec2(x = 0.0, y = 1.0)

        val result = circle.distanceTo(point)

        assertEqualsWithDelta(0.0, result)
    }

    @Test
    fun `GIVEN a circle and a section outside of circle, WHEN intersects, THEN return false`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val section = Section2(start = Vec2(x = 2.0, y = 2.0), end = Vec2(x = 4.0, y = 4.0))

        val result = circle intersects section

        assertFalse(result)
    }

    @Test
    fun `GIVEN a circle and a section tangent to the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val section = Section2(start = Vec2(x = 0.0, y = 0.0), end = Vec2(x = 0.0, y = 4.0))

        val result = circle intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a section inside the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val section = Section2(start = Vec2(x = 0.0, y = 0.5), end = Vec2(x = 1.5, y = 1.5))

        val result = circle intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a rectangle outside of circle, WHEN intersects, THEN return false`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val rectangle = Rectangle(left = 3.0, bottom = 0.0, right = 5.0, top = 2.0)

        val result = circle intersects rectangle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a circle and a rectangle touching the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val rectangle = Rectangle(left = 2.0, bottom = 0.0, right = 5.0, top = 2.0)

        val result = circle intersects rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a rectangle inside the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val rectangle = Rectangle(left = 0.5, bottom = 0.5, right = 1.5, top = 1.5)

        val result = circle intersects rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a polygon outside of circle, WHEN intersects, THEN return false`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = -1.0, y = 3.0),
                Vec2(x = -1.0, y = 0.0)
            )
        )

        val result = circle intersects polygon

        assertFalse(result)
    }

    @Test
    fun `GIVEN a circle and a polygon touching the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.0, y = 0.0),
                Vec2(x = 0.0, y = 3.0),
                Vec2(x = -1.0, y = 0.0)
            )
        )

        val result = circle intersects polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a polygon inside the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.5, y = 0.5),
                Vec2(x = 1.5, y = 0.5),
                Vec2(x = 1.0, y = 1.5)
            )
        )

        val result = circle intersects polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 disjoint circles, WHEN intersects, THEN return false`() {
        val circle1 = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val circle2 = Circle(center = Vec2(x = -1.0, y = -1.0), radius = 1.0)

        val result = circle1 intersects circle2

        assertFalse(result)
    }

    @Test
    fun `GIVEN 2 touching circles, WHEN intersects, THEN return true`() {
        val circle1 = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val circle2 = Circle(center = Vec2(x = -1.0, y = 1.0), radius = 1.0)

        val result = circle1 intersects circle2

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 conjoint circles, WHEN intersects, THEN return true`() {
        val circle1 = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)
        val circle2 = Circle(center = Vec2(x = 0.0, y = 0.0), radius = 1.0)

        val result = circle1 intersects circle2

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle, WHEN translate, THEN return translated circle`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)

        val result = circle.translate(Vec2(x = 1.0, y = 1.0))

        assertEqualsWithDelta(Circle(center = Vec2(x = 2.0, y = 2.0), radius = 1.0), result)
    }

    @Test
    fun `GIVEN a circle, WHEN rotate, THEN return circle rotated around origin point`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)

        val result = circle.rotate(angle = Angle.rightAngle())

        assertEqualsWithDelta(Circle(center = Vec2(x = -1.0, y = 1.0), radius = 1.0), result)
    }

    @Test
    fun `GIVEN a circle, WHEN rotate around center, THEN return the same circle`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)

        val result = circle.rotate(angle = Angle.rightAngle(), pivot = Vec2(x = 1.0, y = 1.0))

        assertEqualsWithDelta(circle, result)
    }

    @Test
    fun `GIVEN a circle, WHEN scale, THEN return scaled circle`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)

        val result = circle.scale(scale = 2.0)

        assertEqualsWithDelta(Circle(center = Vec2(x = 2.0, y = 2.0), radius = 2.0), result)
    }

    @Test
    fun `GIVEN a circle, WHEN scale relative to center, THEN return scaled circle`() {
        val circle = Circle(center = Vec2(x = 1.0, y = 1.0), radius = 1.0)

        val result = circle.scale(scale = 2.0, center = Vec2(x = 1.0, y = 1.0))

        assertEqualsWithDelta(Circle(center = Vec2(x = 1.0, y = 1.0), radius = 2.0), result)
    }
}
