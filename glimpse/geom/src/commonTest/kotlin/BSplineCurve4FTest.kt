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

class BSplineCurve4FTest {

    @Test
    fun `GIVEN a curve builder, WHEN build, THEN return a new curve`() {
        val result = Curve4.Builder.getInstance<Float>()
            .ofType(CurveType.B_SPLINE)
            .withControlPoints(
                Vec4(x = 1.0f, y = 2.0f, z = 3.0f, w = 1.0f),
                Vec4(x = 3.0f, y = 4.0f, z = 5.0f, w = 1.0f)
            )
            .withKnots(0.0f, 0.0f, 1.0f, 1.0f)
            .build()

        assertEquals(
            BSplineCurve4(
                controlPoints = listOf(
                    Vec4(x = 1.0f, y = 2.0f, z = 3.0f, w = 1.0f),
                    Vec4(x = 3.0f, y = 4.0f, z = 5.0f, w = 1.0f)
                ),
                knots = listOf(0.0f, 0.0f, 1.0f, 1.0f)
            ),
            result
        )
    }

    @Test
    fun `GIVEN a linear B-spline curve, WHEN get, THEN return linear interpolation of points P0 and P1`() {
        val curve = BSplineCurve4(
            controlPoints = listOf(
                Vec4(x = 1.0f, y = 2.0f, z = 3.0f, w = 1.0f),
                Vec4(x = 3.0f, y = 4.0f, z = 5.0f, w = 1.0f)
            ),
            knots = listOf(0.0f, 0.0f, 1.0f, 1.0f)
        )
        val parameterValue = 0.38472f

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec4(x = 1.76944f, y = 2.76944f, z = 3.76944f, w = 1.0f), result)
    }

    @Test
    fun `GIVEN a quadratic B-spline curve, WHEN get, THEN return point on the curve`() {
        val curve = BSplineCurve4(
            controlPoints = listOf(
                Vec4(x = 0.0f, y = 0.0f, z = 0.0f, w = 1.0f),
                Vec4(x = 1.0f, y = 2.0f, z = 1.0f, w = 1.0f),
                Vec4(x = 2.0f, y = 0.0f, z = 0.0f, w = 1.0f)
            ),
            knots = listOf(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
        )
        val parameterValue = 0.38472f

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec4(x = 0.76944f, y = 0.9468421f, z = 0.47342104f, w = 1.0f), result)
    }

    @Test
    fun `GIVEN a cubic B-spline curve, WHEN get, THEN return point on the curve`() {
        val curve = BSplineCurve4(
            controlPoints = listOf(
                Vec4(x = 0.0f, y = 0.0f, z = 0.0f, w = 1.0f),
                Vec4(x = 0.0f, y = 1.0f, z = 2.0f, w = 1.0f),
                Vec4(x = 1.0f, y = 1.0f, z = 2.0f, w = 1.0f),
                Vec4(x = 1.0f, y = 0.0f, z = 0.0f, w = 1.0f)
            ),
            knots = listOf(0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f)
        )
        val parameterValue = 0.38472f

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec4(x = 0.33014402f, y = 0.7101316f, z = 1.4202632f, w = 1.0f), result)
    }

    @Test
    fun `GIVEN a B-spline circle, WHEN toPolygonalChain, THEN return polygonal chain with vertices on the circle`() {
        val curve = BSplineCurve4(
            controlPoints = listOf(
                Vec4(x = 1.0f, y = 0.0f, z = 0.0f, w = 1.0f),
                Vec4(x = 1.0f, y = 1.0f, z = 0.0f, w = 0.70710677f),
                Vec4(x = 0.0f, y = 1.0f, z = 0.0f, w = 1.0f),
                Vec4(x = -1.0f, y = 1.0f, z = 0.0f, w = 0.70710677f),
                Vec4(x = -1.0f, y = 0.0f, z = 0.0f, w = 1.0f),
                Vec4(x = -1.0f, y = -1.0f, z = 0.0f, w = 0.70710677f),
                Vec4(x = 0.0f, y = -1.0f, z = 0.0f, w = 1.0f),
                Vec4(x = 1.0f, y = -1.0f, z = 0.0f, w = 0.70710677f),
                Vec4(x = 1.0f, y = 0.0f, z = 0.0f, w = 1.0f),
            ),
            knots = listOf(0.0f, 0.0f, 0.0f, 0.25f, 0.25f, 0.5f, 0.5f, 0.75f, 0.75f, 1.0f, 1.0f, 1.0f)
        )
        val parameterValues = (0..8).asSequence().map { it / 8.0f }

        val result = parameterValues.map { curve[it] }.map { it.toVec2() / it.w }.toList()

        assertEqualsWithDelta(
            listOf(
                Vec2(x = 1.0f, y = 0.0f),
                Vec2(x = 0.70710677f, y = 0.70710677f),
                Vec2(x = 0.0f, y = 1.0f),
                Vec2(x = -0.70710677f, y = 0.70710677f),
                Vec2(x = -1.0f, y = 0.0f),
                Vec2(x = -0.70710677f, y = -0.70710677f),
                Vec2(x = 0.0f, y = -1.0f),
                Vec2(x = 0.70710677f, y = -0.70710677f),
                Vec2(x = 1.0f, y = 0.0f)
            ),
            result
        )
    }
}
