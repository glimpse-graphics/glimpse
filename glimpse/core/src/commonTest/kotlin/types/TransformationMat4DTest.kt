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

package graphics.glimpse.types

import graphics.glimpse.assertions.assertEqualsWithDelta
import kotlin.test.Test
import kotlin.test.assertEquals

class TransformationMat4DTest {

    @Test
    fun `GIVEN a vector, WHEN translation, THEN should generate translation matrix`() {
        val vector = Vec3(x = 7.0, y = 11.0, z = 13.0)

        val result = translation(vector)

        assertEquals(
            Mat4(
                listOf(
                    1.0, 0.0, 0.0, 0.0,
                    0.0, 1.0, 0.0, 0.0,
                    0.0, 0.0, 1.0, 0.0,
                    7.0, 11.0, 13.0, 1.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a vector and an angle, WHEN rotation, THEN should generate rotation matrix`() {
        val vector = Vec3(x = 1.0, y = 1.0, z = 1.0)
        val angle = Angle.fromDeg(deg = 120.0)

        val result = rotation(vector, angle)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0.0, 1.0, 0.0, 0.0,
                    0.0, 0.0, 1.0, 0.0,
                    1.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 1.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a an angle, WHEN rotationX, THEN should generate rotation matrix around X axis`() {
        val angle = Angle.fromDeg(deg = 30.0)

        val result = rotationX(angle)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    1.0, 0.0, 0.0, 0.0,
                    0.0, 0.8660254037844386, 0.5, 0.0,
                    0.0, -0.5, 0.8660254037844386, 0.0,
                    0.0, 0.0, 0.0, 1.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a an angle, WHEN rotationY, THEN should generate rotation matrix around Y axis`() {
        val angle = Angle.fromDeg(deg = 30.0)

        val result = rotationY(angle)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0.8660254037844386, 0.0, -0.5, 0.0,
                    0.0, 1.0, 0.0, 0.0,
                    0.5, 0.0, 0.8660254037844386, 0.0,
                    0.0, 0.0, 0.0, 1.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a an angle, WHEN rotationZ, THEN should generate rotation matrix around Z axis`() {
        val angle = Angle.fromDeg(deg = 30.0)

        val result = rotationZ(angle)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0.8660254037844386, 0.5, 0.0, 0.0,
                    -0.5, 0.8660254037844386, 0.0, 0.0,
                    0.0, 0.0, 1.0, 0.0,
                    0.0, 0.0, 0.0, 1.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a number, WHEN scale, THEN should generate scale matrix`() {
        val number = 7.0

        val result = scale(number)

        assertEquals(
            Mat4(
                listOf(
                    7.0, 0.0, 0.0, 0.0,
                    0.0, 7.0, 0.0, 0.0,
                    0.0, 0.0, 7.0, 0.0,
                    0.0, 0.0, 0.0, 1.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN 3 numbers, WHEN scale, THEN should generate scale matrix`() {
        val x = 3.0
        val y = 5.0
        val z = 7.0

        val result = scale(x, y, z)

        assertEquals(
            Mat4(
                listOf(
                    3.0, 0.0, 0.0, 0.0,
                    0.0, 5.0, 0.0, 0.0,
                    0.0, 0.0, 7.0, 0.0,
                    0.0, 0.0, 0.0, 1.0
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a normal vector and a location, WHEN mirror, THEN should generate mirror matrix`() {
        val normalVector = Vec3(x = 1.0, y = 1.0, z = 1.0)
        val location = Vec3(x = 0.0, y = 0.0, z = 0.0)

        val result = mirror(normalVector, location)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0.33333333333333, -0.66666666666666, -0.66666666666666, 0.0,
                    -0.66666666666666, 0.33333333333333, -0.66666666666666, 0.0,
                    -0.66666666666666, -0.66666666666666, 0.33333333333333, 0.0,
                    0.0, 0.0, 0.0, 1.0
                )
            ),
            result
        )
    }
}
