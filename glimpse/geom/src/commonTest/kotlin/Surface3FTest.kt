/*
 * Copyright 2020-2023 Glimpse Open Source Project
 *
 * Licensed under the Apache License, Version 2.0f (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0f
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

class Surface3FTest {

    @Test
    @Suppress("LongMethod")
    fun `GIVEN a Bezier eighth sphere surface, WHEN get, THEN return point on the sphere`() {
        val surface = Surface3.Builder.getInstance<Float>()
            .ofType(CurveType.BEZIER)
            .withDegree(Vec2(x = 3, y = 3))
            .withControlVertices(
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 0.0f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.0f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 0.0f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.33333f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 0.0f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.66667f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 0.0f, z = 1.0f),
                    textureCoordinates = Vec2(x = 1.0f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 0.5522848f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.0f, y = 0.33333f),
                    normal = Vec3(x = 0.0f, y = 0.5f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.30501845f, y = 0.5522848f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.33333f, y = 0.33333f),
                    normal = Vec3(x = 0.25f, y = 0.5f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522848f, y = 0.30501845f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.66667f, y = 0.33333f),
                    normal = Vec3(x = 0.5f, y = 0.25f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522848f, y = 0.0f, z = 1.0f),
                    textureCoordinates = Vec2(x = 1.0f, y = 0.33333f),
                    normal = Vec3(x = 0.5f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 1.0f, z = 0.5522848f),
                    textureCoordinates = Vec2(x = 0.0f, y = 0.66667f),
                    normal = Vec3(x = 0.0f, y = 1.0f, z = 0.5f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522848f, y = 1.0f, z = 0.5522848f),
                    textureCoordinates = Vec2(x = 0.33333f, y = 0.66667f),
                    normal = Vec3(x = 0.5f, y = 1.0f, z = 0.5f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(1.0f, y = 0.5522848f, z = 0.5522848f),
                    textureCoordinates = Vec2(x = 0.66667f, y = 0.66667f),
                    normal = Vec3(x = 1.0f, y = 0.5f, z = 0.5f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 1.0f, y = 0.0f, z = 0.5522848f),
                    textureCoordinates = Vec2(x = 1.0f, y = 0.66667f),
                    normal = Vec3(x = 1.0f, y = 0.0f, z = 0.5f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 1.0f, z = 0.0f),
                    textureCoordinates = Vec2(x = 0.0f, y = 1.0f),
                    normal = Vec3(x = 0.0f, y = 1.0f, z = 0.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522848f, y = 1.0f, z = 0.0f),
                    textureCoordinates = Vec2(x = 0.33333f, y = 1.0f),
                    normal = Vec3(x = 0.5f, y = 1.0f, z = 0.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(1.0f, y = 0.5522848f, z = 0.0f),
                    textureCoordinates = Vec2(x = 0.66667f, y = 1.0f),
                    normal = Vec3(x = 1.0f, y = 0.5f, z = 0.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 1.0f, y = 0.0f, z = 0.0f),
                    textureCoordinates = Vec2(x = 1.0f, y = 1.0f),
                    normal = Vec3(x = 1.0f, y = 0.0f, z = 0.0f)
                ),
            )
            .build()
        val parametersValues = Vec2(x = 0.5f, y = 0.5f)

        val result = surface[parametersValues]

        assertEqualsWithDelta(
            Vec3(x = 0.5f, y = 0.5f, z = 0.70710677f),
            result
        )
    }

    @Test
    @Suppress("LongMethod")
    fun `GIVEN a B-spline eighth sphere surface, WHEN get, THEN return point on the sphere`() {
        val surface = Surface3.Builder.getInstance<Float>()
            .ofType(CurveType.BEZIER)
            .withDegree(Vec2(x = 3, y = 3))
            .withControlVertices(
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 0.0f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.0f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 0.0f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.33333f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 0.0f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.66667f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 0.0f, z = 1.0f),
                    textureCoordinates = Vec2(x = 1.0f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 0.5522848f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.0f, y = 0.33333f),
                    normal = Vec3(x = 0.0f, y = 0.5f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.30501845f, y = 0.5522848f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.33333f, y = 0.33333f),
                    normal = Vec3(x = 0.25f, y = 0.5f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522848f, y = 0.30501845f, z = 1.0f),
                    textureCoordinates = Vec2(x = 0.66667f, y = 0.33333f),
                    normal = Vec3(x = 0.5f, y = 0.25f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522848f, y = 0.0f, z = 1.0f),
                    textureCoordinates = Vec2(x = 1.0f, y = 0.33333f),
                    normal = Vec3(x = 0.5f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 1.0f, z = 0.5522848f),
                    textureCoordinates = Vec2(x = 0.0f, y = 0.66667f),
                    normal = Vec3(x = 0.0f, y = 1.0f, z = 0.5f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522848f, y = 1.0f, z = 0.5522848f),
                    textureCoordinates = Vec2(x = 0.33333f, y = 0.66667f),
                    normal = Vec3(x = 0.5f, y = 1.0f, z = 0.5f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(1.0f, y = 0.5522848f, z = 0.5522848f),
                    textureCoordinates = Vec2(x = 0.66667f, y = 0.66667f),
                    normal = Vec3(x = 1.0f, y = 0.5f, z = 0.5f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 1.0f, y = 0.0f, z = 0.5522848f),
                    textureCoordinates = Vec2(x = 1.0f, y = 0.66667f),
                    normal = Vec3(x = 1.0f, y = 0.0f, z = 0.5f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0f, y = 1.0f, z = 0.0f),
                    textureCoordinates = Vec2(x = 0.0f, y = 1.0f),
                    normal = Vec3(x = 0.0f, y = 1.0f, z = 0.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522848f, y = 1.0f, z = 0.0f),
                    textureCoordinates = Vec2(x = 0.33333f, y = 1.0f),
                    normal = Vec3(x = 0.5f, y = 1.0f, z = 0.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(1.0f, y = 0.5522848f, z = 0.0f),
                    textureCoordinates = Vec2(x = 0.66667f, y = 1.0f),
                    normal = Vec3(x = 1.0f, y = 0.5f, z = 0.0f)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 1.0f, y = 0.0f, z = 0.0f),
                    textureCoordinates = Vec2(x = 1.0f, y = 1.0f),
                    normal = Vec3(x = 1.0f, y = 0.0f, z = 0.0f)
                ),
            )
            .build()
        val parametersValues = Vec2(x = 0.5f, y = 0.5f)

        val result = surface[parametersValues]

        assertEqualsWithDelta(
            Vec3(x = 0.5f, y = 0.5f, z = 0.70710677f),
            result
        )
    }
}
