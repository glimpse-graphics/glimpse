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

class BSplineCurve4FTest {

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
