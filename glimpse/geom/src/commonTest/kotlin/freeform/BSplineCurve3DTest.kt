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

package graphics.glimpse.geom.freeform

import graphics.glimpse.testing.assertEqualsWithDelta
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import kotlin.test.Test
import kotlin.test.assertEquals

class BSplineCurve3DTest {

    @Test
    fun `GIVEN a curve builder, WHEN build, THEN return a new curve`() {
        val result = Curve3.Builder.getInstance<Double>()
            .ofType(FreeformType.B_SPLINE)
            .withControlPoints(Vec3(x = 1.0, y = 2.0, z = 3.0), Vec3(x = 3.0, y = 4.0, z = 5.0))
            .withKnots(0.0, 0.0, 1.0, 1.0)
            .build()

        assertEquals(
            BSplineCurve3(
                controlPoints = listOf(Vec3(x = 1.0, y = 2.0, z = 3.0), Vec3(x = 3.0, y = 4.0, z = 5.0)),
                knots = listOf(0.0, 0.0, 1.0, 1.0)
            ),
            result
        )
    }

    @Test
    fun `GIVEN a linear B-spline curve, WHEN get, THEN return linear interpolation of points P0 and P1`() {
        val curve = BSplineCurve3(
            controlPoints = listOf(Vec3(x = 1.0, y = 2.0, z = 3.0), Vec3(x = 3.0, y = 4.0, z = 5.0)),
            knots = listOf(0.0, 0.0, 1.0, 1.0)
        )
        val parameterValue = 0.38472

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec3(x = 1.76944, y = 2.76944, z = 3.76944), result)
    }

    @Test
    fun `GIVEN a quadratic B-spline curve, WHEN get, THEN return point on the curve`() {
        val curve = BSplineCurve3(
            controlPoints = listOf(
                Vec3(x = 0.0, y = 0.0, z = 0.0),
                Vec3(x = 1.0, y = 2.0, z = 1.0),
                Vec3(x = 2.0, y = 0.0, z = 0.0)
            ),
            knots = listOf(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
        )
        val parameterValue = 0.38472

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec3(x = 0.76944, y = 0.9468420864, z = 0.4734210432), result)
    }

    @Test
    fun `GIVEN a cubic B-spline curve, WHEN get, THEN return point on the curve`() {
        val curve = BSplineCurve3(
            controlPoints = listOf(
                Vec3(x = 0.0, y = 0.0, z = 0.0),
                Vec3(x = 0.0, y = 1.0, z = 2.0),
                Vec3(x = 1.0, y = 1.0, z = 2.0),
                Vec3(x = 1.0, y = 0.0, z = 0.0)
            ),
            knots = listOf(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0)
        )
        val parameterValue = 0.38472

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec3(x = 0.3301440221, y = 0.7101315648, z = 1.4202631296), result)
    }

    @Test
    fun `GIVEN a quadratic combined B-spline curve, WHEN get, THEN return point on the curve`() {
        val curve = BSplineCurve3(
            controlPoints = listOf(
                Vec3(x = 0.0, y = 0.0, z = 0.0),
                Vec3(x = 1.0, y = 2.0, z = 1.0),
                Vec3(x = 2.0, y = 0.0, z = 0.0),
                Vec3(x = 1.0, y = 2.0, z = 1.0),
                Vec3(x = 0.0, y = 0.0, z = 0.0)
            ),
            knots = listOf(0.0, 0.0, 0.0, 0.5, 0.5, 1.0, 1.0, 1.0)
        )
        val parameterValue = 0.80764

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec3(x = 0.76944, y = 0.9468420864, z = 0.4734210432), result)
    }

    @Test
    fun `GIVEN a 2D B-spline curve, WHEN toCurve3, THEN return 3D Bezier curve`() {
        val curve = BSplineCurve3(
            controlPoints = listOf(
                Vec3(x = 0.0, y = 0.0, z = 0.0),
                Vec3(x = 1.0, y = 2.0, z = 1.0),
                Vec3(x = 2.0, y = 0.0, z = 0.0)
            ),
            knots = listOf(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
        )

        val result = curve.toCurve2()

        assertEquals(
            BSplineCurve2(
                controlPoints = listOf(Vec2(x = 0.0, y = 0.0), Vec2(x = 1.0, y = 2.0), Vec2(x = 2.0, y = 0.0)),
                knots = listOf(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
            ),
            result
        )
    }
}
