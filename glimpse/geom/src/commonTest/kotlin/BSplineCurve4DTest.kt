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
import graphics.glimpse.types.Vec4
import kotlin.test.Test
import kotlin.test.assertEquals

class BSplineCurve4DTest {

    @Test
    fun `GIVEN a curve builder, WHEN build, THEN return a new curve`() {
        val result = Curve4.Builder.getInstance<Double>()
            .ofType(CurveType.B_SPLINE)
            .withControlPoints(
                Vec4(x = 1.0, y = 2.0, z = 3.0, w = 1.0),
                Vec4(x = 3.0, y = 4.0, z = 5.0, w = 1.0)
            )
            .withKnots(0.0, 0.0, 1.0, 1.0)
            .build()

        assertEquals(
            BSplineCurve4(
                controlPoints = listOf(
                    Vec4(x = 1.0, y = 2.0, z = 3.0, w = 1.0),
                    Vec4(x = 3.0, y = 4.0, z = 5.0, w = 1.0)
                ),
                knots = listOf(0.0, 0.0, 1.0, 1.0)
            ),
            result
        )
    }

    @Test
    fun `GIVEN a linear B-spline curve, WHEN get, THEN return linear interpolation of points P0 and P1`() {
        val curve = BSplineCurve4(
            controlPoints = listOf(
                Vec4(x = 1.0, y = 2.0, z = 3.0, w = 1.0),
                Vec4(x = 3.0, y = 4.0, z = 5.0, w = 1.0)
            ),
            knots = listOf(0.0, 0.0, 1.0, 1.0)
        )
        val parameterValue = 0.38472

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec4(x = 1.76944, y = 2.76944, z = 3.76944, w = 1.0), result)
    }

    @Test
    fun `GIVEN a quadratic B-spline curve, WHEN get, THEN return point on the curve`() {
        val curve = BSplineCurve4(
            controlPoints = listOf(
                Vec4(x = 0.0, y = 0.0, z = 0.0, w = 1.0),
                Vec4(x = 1.0, y = 2.0, z = 1.0, w = 1.0),
                Vec4(x = 2.0, y = 0.0, z = 0.0, w = 1.0)
            ),
            knots = listOf(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
        )
        val parameterValue = 0.38472

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec4(x = 0.76944, y = 0.9468420864, z = 0.4734210432, w = 1.0), result)
    }

    @Test
    fun `GIVEN a cubic B-spline curve, WHEN get, THEN return point on the curve`() {
        val curve = BSplineCurve4(
            controlPoints = listOf(
                Vec4(x = 0.0, y = 0.0, z = 0.0, w = 1.0),
                Vec4(x = 0.0, y = 1.0, z = 2.0, w = 1.0),
                Vec4(x = 1.0, y = 1.0, z = 2.0, w = 1.0),
                Vec4(x = 1.0, y = 0.0, z = 0.0, w = 1.0)
            ),
            knots = listOf(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0)
        )
        val parameterValue = 0.38472

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec4(x = 0.3301440221, y = 0.7101315648, z = 1.4202631296, w = 1.0), result)
    }

    @Test
    fun `GIVEN a B-spline circle, WHEN toPolygonalChain, THEN return polygonal chain with vertices on the circle`() {
        val curve = BSplineCurve4(
            controlPoints = listOf(
                Vec4(x = 1.0, y = 0.0, z = 0.0, w = 1.0),
                Vec4(x = 1.0, y = 1.0, z = 0.0, w = 0.7071067811865475),
                Vec4(x = 0.0, y = 1.0, z = 0.0, w = 1.0),
                Vec4(x = -1.0, y = 1.0, z = 0.0, w = 0.7071067811865475),
                Vec4(x = -1.0, y = 0.0, z = 0.0, w = 1.0),
                Vec4(x = -1.0, y = -1.0, z = 0.0, w = 0.7071067811865475),
                Vec4(x = 0.0, y = -1.0, z = 0.0, w = 1.0),
                Vec4(x = 1.0, y = -1.0, z = 0.0, w = 0.7071067811865475),
                Vec4(x = 1.0, y = 0.0, z = 0.0, w = 1.0),
            ),
            knots = listOf(0.0, 0.0, 0.0, 0.25, 0.25, 0.5, 0.5, 0.75, 0.75, 1.0, 1.0, 1.0)
        )
        val parameterValues = (0..8).asSequence().map { it / 8.0 }

        val result = parameterValues.map { curve[it] }.map { it.toVec2() / it.w }.toList()

        assertEqualsWithDelta(
            listOf(
                Vec2(x = 1.0, y = 0.0),
                Vec2(x = 0.7071067811865475, y = 0.7071067811865475),
                Vec2(x = 0.0, y = 1.0),
                Vec2(x = -0.7071067811865475, y = 0.7071067811865475),
                Vec2(x = -1.0, y = 0.0),
                Vec2(x = -0.7071067811865475, y = -0.7071067811865475),
                Vec2(x = 0.0, y = -1.0),
                Vec2(x = 0.7071067811865475, y = -0.7071067811865475),
                Vec2(x = 1.0, y = 0.0)
            ),
            result
        )
    }
}
