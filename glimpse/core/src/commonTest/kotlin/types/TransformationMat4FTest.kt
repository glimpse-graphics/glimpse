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

package graphics.glimpse.types

import graphics.glimpse.testing.assertEqualsWithDelta
import kotlin.test.Test
import kotlin.test.assertEquals

class TransformationMat4FTest {

    @Test
    fun `GIVEN a vector, WHEN translation, THEN should generate translation matrix`() {
        val vector = Vec3(x = 7f, y = 11f, z = 13f)

        val result = translation(vector)

        assertEquals(
            Mat4(
                listOf(
                    1f, 0f, 0f, 0f,
                    0f, 1f, 0f, 0f,
                    0f, 0f, 1f, 0f,
                    7f, 11f, 13f, 1f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a vector and an angle, WHEN rotation, THEN should generate rotation matrix`() {
        val vector = Vec3(x = 1f, y = 1f, z = 1f)
        val angle = Angle.fromDeg(deg = 120f)

        val result = rotation(vector, angle)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0f, 1f, 0f, 0f,
                    0f, 0f, 1f, 0f,
                    1f, 0f, 0f, 0f,
                    0f, 0f, 0f, 1f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a an angle, WHEN rotationX, THEN should generate rotation matrix around X axis`() {
        val angle = Angle.fromDeg(deg = 30f)

        val result = rotationX(angle)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    1f, 0f, 0f, 0f,
                    0f, 0.8660254f, 0.5f, 0f,
                    0f, -0.5f, 0.8660254f, 0f,
                    0f, 0f, 0f, 1f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a an angle, WHEN rotationY, THEN should generate rotation matrix around Y axis`() {
        val angle = Angle.fromDeg(deg = 30f)

        val result = rotationY(angle)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0.8660254f, 0f, -0.5f, 0f,
                    0f, 1f, 0f, 0f,
                    0.5f, 0f, 0.8660254f, 0f,
                    0f, 0f, 0f, 1f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a an angle, WHEN rotationZ, THEN should generate rotation matrix around Z axis`() {
        val angle = Angle.fromDeg(deg = 30f)

        val result = rotationZ(angle)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0.8660254f, 0.5f, 0f, 0f,
                    -0.5f, 0.8660254f, 0f, 0f,
                    0f, 0f, 1f, 0f,
                    0f, 0f, 0f, 1f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a number, WHEN scale, THEN should generate scale matrix`() {
        val number = 7f

        val result = scale(number)

        assertEquals(
            Mat4(
                listOf(
                    7f, 0f, 0f, 0f,
                    0f, 7f, 0f, 0f,
                    0f, 0f, 7f, 0f,
                    0f, 0f, 0f, 1f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN 3 numbers, WHEN scale, THEN should generate scale matrix`() {
        val x = 3f
        val y = 5f
        val z = 7f

        val result = scale(x, y, z)

        assertEquals(
            Mat4(
                listOf(
                    3f, 0f, 0f, 0f,
                    0f, 5f, 0f, 0f,
                    0f, 0f, 7f, 0f,
                    0f, 0f, 0f, 1f
                )
            ),
            result
        )
    }

    @Test
    fun `GIVEN a normal vector and a location, WHEN mirror, THEN should generate mirror matrix`() {
        val normalVector = Vec3(x = 1f, y = 1f, z = 1f)
        val location = Vec3(x = 0f, y = 0f, z = 0f)

        val result = mirror(normalVector, location)

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    0.33333334f, -0.6666667f, -0.6666667f, 0f,
                    -0.6666667f, 0.33333334f, -0.6666667f, 0f,
                    -0.6666667f, -0.6666667f, 0.33333334f, 0f,
                    0f, 0f, 0f, 1f
                )
            ),
            result
        )
    }
}
