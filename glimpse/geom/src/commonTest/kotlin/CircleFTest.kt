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
import graphics.glimpse.types.Vec2
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CircleFTest {

    @Test
    fun `GIVEN a circle, WHEN boundingBox, THEN return boundingBox of this circle`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)

        val result = circle.boundingBox

        assertEqualsWithDelta(Rectangle(left = 0f, bottom = 0f, right = 2f, top = 2f), result)
    }

    @Test
    fun `GIVEN a circle, WHEN midpoint, THEN return center point of this circle`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)

        val result = circle.midpoint

        assertEqualsWithDelta(Vec2(x = 1f, y = 1f), result)
    }

    @Test
    fun `GIVEN a circle and a point outside of circle, WHEN contains, THEN return false`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val point = Vec2(x = 0f, y = 0f)

        val result = point in circle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a circle and a point inside the circle, WHEN contains, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val point = Vec2(x = 0.5f, y = 0.5f)

        val result = point in circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a point on circle perimeter, WHEN contains, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val point = Vec2(x = 0f, y = 1f)

        val result = point in circle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a point outside of circle, WHEN distanceTo, THEN return distance from point to circle`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val point = Vec2(x = 0f, y = 0f)

        val result = circle.distanceTo(point)

        assertEqualsWithDelta(0.41421357f, result)
    }

    @Test
    fun `GIVEN a circle and a point inside the circle, WHEN distanceTo, THEN return 0`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val point = Vec2(x = 0.5f, y = 0.5f)

        val result = circle.distanceTo(point)

        assertEqualsWithDelta(0f, result)
    }

    @Test
    fun `GIVEN a circle and a point on circle perimeter, WHEN distanceTo, THEN return 0`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val point = Vec2(x = 0f, y = 1f)

        val result = circle.distanceTo(point)

        assertEqualsWithDelta(0f, result)
    }

    @Test
    fun `GIVEN a circle and a section outside of circle, WHEN intersects, THEN return false`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val section = Section2(start = Vec2(x = 2f, y = 2f), end = Vec2(x = 4f, y = 4f))

        val result = circle intersects section

        assertFalse(result)
    }

    @Test
    fun `GIVEN a circle and a section tangent to the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val section = Section2(start = Vec2(x = 0f, y = 0f), end = Vec2(x = 0f, y = 4f))

        val result = circle intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a section inside the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val section = Section2(start = Vec2(x = 0.5f, y = 0.5f), end = Vec2(x = 1.5f, y = 1.5f))

        val result = circle intersects section

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a rectangle outside of circle, WHEN intersects, THEN return false`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val rectangle = Rectangle(left = 3f, bottom = 0f, right = 5f, top = 2f)

        val result = circle intersects rectangle

        assertFalse(result)
    }

    @Test
    fun `GIVEN a circle and a rectangle touching the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val rectangle = Rectangle(left = 2f, bottom = 0f, right = 5f, top = 2f)

        val result = circle intersects rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a rectangle inside the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val rectangle = Rectangle(left = 0.5f, bottom = 0.5f, right = 1.5f, top = 1.5f)

        val result = circle intersects rectangle

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a polygon outside of circle, WHEN intersects, THEN return false`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = -1f, y = 3f),
                Vec2(x = -1f, y = 0f)
            )
        )

        val result = circle intersects polygon

        assertFalse(result)
    }

    @Test
    fun `GIVEN a circle and a polygon touching the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0f, y = 0f),
                Vec2(x = 0f, y = 3f),
                Vec2(x = -1f, y = 0f)
            )
        )

        val result = circle intersects polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle and a polygon inside the circle, WHEN intersects, THEN return true`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val polygon = Polygon(
            vertices = listOf(
                Vec2(x = 0.5f, y = 0.5f),
                Vec2(x = 1.5f, y = 0.5f),
                Vec2(x = 1f, y = 1.5f)
            )
        )

        val result = circle intersects polygon

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 disjoint circles, WHEN intersects, THEN return false`() {
        val circle1 = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val circle2 = Circle(center = Vec2(x = -1f, y = -1f), radius = 1f)

        val result = circle1 intersects circle2

        assertFalse(result)
    }

    @Test
    fun `GIVEN 2 touching circles, WHEN intersects, THEN return true`() {
        val circle1 = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val circle2 = Circle(center = Vec2(x = -1f, y = 1f), radius = 1f)

        val result = circle1 intersects circle2

        assertTrue(result)
    }

    @Test
    fun `GIVEN 2 conjoint circles, WHEN intersects, THEN return true`() {
        val circle1 = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)
        val circle2 = Circle(center = Vec2(x = 0f, y = 0f), radius = 1f)

        val result = circle1 intersects circle2

        assertTrue(result)
    }

    @Test
    fun `GIVEN a circle, WHEN translate, THEN return translated circle`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)

        val result = circle.translate(Vec2(x = 1f, y = 1f))

        assertEqualsWithDelta(Circle(center = Vec2(x = 2f, y = 2f), radius = 1f), result)
    }

    @Test
    fun `GIVEN a circle, WHEN rotate, THEN return circle rotated around origin point`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)

        val result = circle.rotate(angle = Angle.rightAngle())

        assertEqualsWithDelta(Circle(center = Vec2(x = -1f, y = 1f), radius = 1f), result)
    }

    @Test
    fun `GIVEN a circle, WHEN rotate around center, THEN return the same circle`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)

        val result = circle.rotate(angle = Angle.rightAngle(), pivot = Vec2(x = 1f, y = 1f))

        assertEqualsWithDelta(circle, result)
    }

    @Test
    fun `GIVEN a circle, WHEN scale, THEN return scaled circle`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)

        val result = circle.scale(scale = 2f)

        assertEqualsWithDelta(Circle(center = Vec2(x = 2f, y = 2f), radius = 2f), result)
    }

    @Test
    fun `GIVEN a circle, WHEN scale relative to center, THEN return scaled circle`() {
        val circle = Circle(center = Vec2(x = 1f, y = 1f), radius = 1f)

        val result = circle.scale(scale = 2f, center = Vec2(x = 1f, y = 1f))

        assertEqualsWithDelta(Circle(center = Vec2(x = 1f, y = 1f), radius = 2f), result)
    }
}
