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
import graphics.glimpse.types.Vec4
import kotlin.test.Test

class BezierCurve4DTest {

    @Test
    fun `GIVEN a Bezier curve of degree 0, WHEN get, THEN return point P0`() {
        val curve = BezierCurve4(Vec4(x = 1.0, y = 2.0, z = 3.0, w = 1.0))
        val parameterValue = 0.38472

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec4(x = 1.0, y = 2.0, z = 3.0, w = 1.0), result)
    }

    @Test
    fun `GIVEN a linear Bezier curve, WHEN get, THEN return linear interpolation of points P0 and P1`() {
        val curve = BezierCurve4(Vec4(x = 1.0, y = 2.0, z = 3.0, w = 1.0), Vec4(x = 3.0, y = 4.0, z = 5.0, w = 1.0))
        val parameterValue = 0.38472

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec4(x = 1.76944, y = 2.76944, z = 3.76944, w = 1.0), result)
    }

    @Test
    fun `GIVEN a quadratic Bezier curve, WHEN get, THEN return point on the curve`() {
        val curve = BezierCurve4(
            Vec4(x = 0.0, y = 0.0, z = 0.0, w = 1.0),
            Vec4(x = 1.0, y = 2.0, z = 1.0, w = 1.0),
            Vec4(x = 2.0, y = 0.0, z = 0.0, w = 1.0)
        )
        val parameterValue = 0.38472

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec4(x = 0.76944, y = 0.9468420864, z = 0.4734210432, w = 1.0), result)
    }

    @Test
    fun `GIVEN a cubic Bezier curve, WHEN get, THEN return point on the curve`() {
        val curve = BezierCurve4(
            Vec4(x = 0.0, y = 0.0, z = 0.0, w = 1.0),
            Vec4(x = 0.0, y = 1.0, z = 2.0, w = 1.0),
            Vec4(x = 1.0, y = 1.0, z = 2.0, w = 1.0),
            Vec4(x = 1.0, y = 0.0, z = 0.0, w = 1.0)
        )
        val parameterValue = 0.38472

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec4(x = 0.3301440221, y = 0.7101315648, z = 1.4202631296, w = 1.0), result)
    }

    @Test
    fun `GIVEN a quadratic Bezier curve arc, WHEN get, THEN return point on the arc`() {
        val curve = BezierCurve4(
            Vec4(x = 0.0, y = 1.0, z = 0.0, w = 1.0),
            Vec4(x = 1.0, y = 1.0, z = 0.0, w = 0.7071067811865475),
            Vec4(x = 1.0, y = 0.0, z = 0.0, w = 1.0)
        )
        val parameterValue = 0.5

        val result = curve[parameterValue]

        assertEqualsWithDelta(Vec4(x = 0.7071067811865475, y = 0.7071067811865475, z = 0.0, w = 1.0), result)
    }
}
