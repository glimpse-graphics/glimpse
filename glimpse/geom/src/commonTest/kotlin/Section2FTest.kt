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
import graphics.glimpse.types.Vec2
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class Section2FTest {

    @Test
    fun `GIVEN a section, WHEN asVector, THEN return vector along this section`() {
        val section = Section2(start = Vec2(x = 1f, y = 2f), end = Vec2(x = 3f, y = 4f))

        val result = section.asVector

        assertEqualsWithDelta(Vec2(x = 2f, y = 2f), result)
    }

    @Test
    fun `GIVEN a section, WHEN length, THEN return length of this section`() {
        val section = Section2(start = Vec2(x = 1f, y = 2f), end = Vec2(x = 4f, y = 6f))

        val result = section.length

        assertEqualsWithDelta(5f, result)
    }

    @Test
    fun `GIVEN a section, WHEN direction, THEN return direction of this section`() {
        val section = Section2(start = Vec2(x = 1f, y = 2f), end = Vec2(x = 3f, y = 4f))

        val result = section.direction

        assertEqualsWithDelta(Vec2(x = 0.70710677f, y = 0.70710677f), result)
    }

    @Test
    fun `GIVEN a section, WHEN boundingBox, THEN return boundingBox of this section`() {
        val section = Section2(start = Vec2(x = 1f, y = 2f), end = Vec2(x = 3f, y = 4f))

        val result = section.boundingBox

        assertEqualsWithDelta(Rectangle(left = 1f, bottom = 2f, right = 3f, top = 4f), result)
    }

    @Test
    fun `GIVEN a sections and a point in the section, WHEN contains, THEN return true`() {
        val section = Section2(start = Vec2(x = 1f, y = 2f), end = Vec2(x = 3f, y = 4f))
        val point = Vec2(x = 1.5f, y = 2.5f)

        val result = point in section

        assertTrue(result)
    }

    @Test
    fun `GIVEN a sections and a point outside of the section, WHEN contains, THEN return false`() {
        val section = Section2(start = Vec2(x = 1f, y = 2f), end = Vec2(x = 3f, y = 4f))
        val point = Vec2(x = 1.6f, y = 2.5f)

        val result = point in section

        assertFalse(result)
    }

    @Test
    fun `GIVEN a two sections, WHEN intersectionWith, THEN return intersection point of both sections`() {
        val section1 = Section2(start = Vec2(x = 1f, y = 1f), end = Vec2(x = 2f, y = 2f))
        val section2 = Section2(start = Vec2(x = 1f, y = 2f), end = Vec2(x = 2f, y = 1f))

        val result = section1.intersectionWith(section2)

        assertNotNull(result)
        assertEqualsWithDelta(Vec2(x = 1.5f, y = 1.5f), result)
    }

    @Test
    fun `GIVEN a two parallel sections, WHEN intersectionWith, THEN return null`() {
        val section1 = Section2(start = Vec2(x = 1f, y = 1f), end = Vec2(x = 2f, y = 2f))
        val section2 = Section2(start = Vec2(x = 1f, y = 2f), end = Vec2(x = 2f, y = 3f))

        val result = section1.intersectionWith(section2)

        assertNull(result)
    }

    @Test
    fun `GIVEN a two disjoint sections, WHEN intersectionWith, THEN return null`() {
        val section1 = Section2(start = Vec2(x = -1f, y = 0f), end = Vec2(x = 1f, y = 0f))
        val section2 = Section2(start = Vec2(x = 2f, y = 1f), end = Vec2(x = 2f, y = -1f))

        val result = section1.intersectionWith(section2)

        assertNull(result)
    }

    @Test
    fun `GIVEN a section and a point, WHEN projectionOf, THEN return projection of the point onto the section`() {
        val section = Section2(start = Vec2(x = 1f, y = 1f), end = Vec2(x = 3f, y = 3f))
        val point = Vec2(x = 1f, y = 3f)

        val result = section.projectionOf(point)

        assertEqualsWithDelta(Vec2(x = 2f, y = 2f), result)
    }

    @Test
    fun `GIVEN a section and a point with a projection, WHEN distanceTo, THEN return distance from point to section`() {
        val section = Section2(start = Vec2(x = 1f, y = 1f), end = Vec2(x = 3f, y = 3f))
        val point = Vec2(x = 1f, y = 3f)

        val result = section.distanceTo(point)

        assertEqualsWithDelta(1.4142135f, result)
    }

    @Test
    fun `GIVEN a section and a point, WHEN distanceTo, THEN return distance from point to section end`() {
        val section = Section2(start = Vec2(x = 1f, y = 1f), end = Vec2(x = 3f, y = 3f))
        val point = Vec2(x = -1f, y = 1f)

        val result = section.distanceTo(point)

        assertEqualsWithDelta(2f, result)
    }
}
