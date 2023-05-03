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
import graphics.glimpse.types.Vec3
import kotlin.test.Test
import kotlin.test.assertEquals

class BSplineCurve3FTest {

    @Test
    fun `GIVEN a linear B-spline curve, WHEN get, THEN return linear interpolation of points P0 and P1`() {
        val curve = BSplineCurve3(
            controlPoints = listOf(Vec3(x = 1.0f, y = 2.0f, z = 3.0f), Vec3(x = 3.0f, y = 4.0f, z = 5.0f)),
            knots = listOf(0.0f, 0.0f, 1.0f, 1.0f)
        )
        val parameterValue = 0.38472f

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec3(x = 1.76944f, y = 2.76944f, z = 3.76944f), result)
    }

    @Test
    fun `GIVEN a quadratic B-spline curve, WHEN get, THEN return point on the curve`() {
        val curve = BSplineCurve3(
            controlPoints = listOf(
                Vec3(x = 0.0f, y = 0.0f, z = 0.0f),
                Vec3(x = 1.0f, y = 2.0f, z = 1.0f),
                Vec3(x = 2.0f, y = 0.0f, z = 0.0f)
            ),
            knots = listOf(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
        )
        val parameterValue = 0.38472f

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec3(x = 0.76944f, y = 0.9468421f, z = 0.47342104f), result)
    }

    @Test
    fun `GIVEN a cubic B-spline curve, WHEN get, THEN return point on the curve`() {
        val curve = BSplineCurve3(
            controlPoints = listOf(
                Vec3(x = 0.0f, y = 0.0f, z = 0.0f),
                Vec3(x = 0.0f, y = 1.0f, z = 2.0f),
                Vec3(x = 1.0f, y = 1.0f, z = 2.0f),
                Vec3(x = 1.0f, y = 0.0f, z = 0.0f)
            ),
            knots = listOf(0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f)
        )
        val parameterValue = 0.38472f

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec3(x = 0.33014402f, y = 0.7101316f, z = 1.4202632f), result)
    }

    @Test
    fun `GIVEN a quadratic combined B-spline curve, WHEN get, THEN return point on the curve`() {
        val curve = BSplineCurve3(
            controlPoints = listOf(
                Vec3(x = 0.0f, y = 0.0f, z = 0.0f),
                Vec3(x = 1.0f, y = 2.0f, z = 1.0f),
                Vec3(x = 2.0f, y = 0.0f, z = 0.0f),
                Vec3(x = 1.0f, y = 2.0f, z = 1.0f),
                Vec3(x = 0.0f, y = 0.0f, z = 0.0f)
            ),
            knots = listOf(0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f)
        )
        val parameterValue = 0.80764f

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec3(x = 0.76944f, y = 0.9468421f, z = 0.47342104f), result)
    }

    @Test
    fun `GIVEN a 2D B-spline curve, WHEN toCurve3, THEN return 3D Bezier curve`() {
        val curve = BSplineCurve3(
            controlPoints = listOf(
                Vec3(x = 0.0f, y = 0.0f, z = 0.0f),
                Vec3(x = 1.0f, y = 2.0f, z = 1.0f),
                Vec3(x = 2.0f, y = 0.0f, z = 0.0f)
            ),
            knots = listOf(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
        )

        val result = curve.toCurve2()

        assertEquals(
            BSplineCurve2(
                controlPoints = listOf(Vec2(x = 0.0f, y = 0.0f), Vec2(x = 1.0f, y = 2.0f), Vec2(x = 2.0f, y = 0.0f)),
                knots = listOf(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
            ),
            result
        )
    }
}
