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

class BSplineCurve4DTest {

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
