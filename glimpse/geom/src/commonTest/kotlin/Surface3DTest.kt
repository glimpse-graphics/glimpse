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

class Surface3DTest {

    @Test
    @Suppress("LongMethod")
    fun `GIVEN a Bezier eighth sphere surface, WHEN get, THEN return point on the sphere`() {
        val surface = Surface3.Builder.getInstance<Double>()
            .ofType(CurveType.BEZIER)
            .withDegree(Vec2(x = 3, y = 3))
            .withControlVertices(
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 0.0, z = 1.0),
                    textureCoordinates = Vec2(x = 0.0, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 0.0, z = 1.0),
                    textureCoordinates = Vec2(x = 0.33333, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 0.0, z = 1.0),
                    textureCoordinates = Vec2(x = 0.66667, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 0.0, z = 1.0),
                    textureCoordinates = Vec2(x = 1.0, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 0.5522847498307935, z = 1.0),
                    textureCoordinates = Vec2(x = 0.0, y = 0.33333),
                    normal = Vec3(x = 0.0, y = 0.5, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.3050184448956622, y = 0.5522847498307935, z = 1.0),
                    textureCoordinates = Vec2(x = 0.33333, y = 0.33333),
                    normal = Vec3(x = 0.25, y = 0.5, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522847498307935, y = 0.3050184448956622, z = 1.0),
                    textureCoordinates = Vec2(x = 0.66667, y = 0.33333),
                    normal = Vec3(x = 0.5, y = 0.25, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522847498307935, y = 0.0, z = 1.0),
                    textureCoordinates = Vec2(x = 1.0, y = 0.33333),
                    normal = Vec3(x = 0.5, y = 0.0, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 1.0, z = 0.5522847498307935),
                    textureCoordinates = Vec2(x = 0.0, y = 0.66667),
                    normal = Vec3(x = 0.0, y = 1.0, z = 0.5)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522847498307935, y = 1.0, z = 0.5522847498307935),
                    textureCoordinates = Vec2(x = 0.33333, y = 0.66667),
                    normal = Vec3(x = 0.5, y = 1.0, z = 0.5)
                ),
                ControlVertex3(
                    controlPoint = Vec3(1.0, y = 0.5522847498307935, z = 0.5522847498307935),
                    textureCoordinates = Vec2(x = 0.66667, y = 0.66667),
                    normal = Vec3(x = 1.0, y = 0.5, z = 0.5)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 1.0, y = 0.0, z = 0.5522847498307935),
                    textureCoordinates = Vec2(x = 1.0, y = 0.66667),
                    normal = Vec3(x = 1.0, y = 0.0, z = 0.5)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 1.0, z = 0.0),
                    textureCoordinates = Vec2(x = 0.0, y = 1.0),
                    normal = Vec3(x = 0.0, y = 1.0, z = 0.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522847498307935, y = 1.0, z = 0.0),
                    textureCoordinates = Vec2(x = 0.33333, y = 1.0),
                    normal = Vec3(x = 0.5, y = 1.0, z = 0.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(1.0, y = 0.5522847498307935, z = 0.0),
                    textureCoordinates = Vec2(x = 0.66667, y = 1.0),
                    normal = Vec3(x = 1.0, y = 0.5, z = 0.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 1.0, y = 0.0, z = 0.0),
                    textureCoordinates = Vec2(x = 1.0, y = 1.0),
                    normal = Vec3(x = 1.0, y = 0.0, z = 0.0)
                )
            )
            .build()
        val parametersValues = Vec2(x = 0.5, y = 0.5)

        val result = surface[parametersValues]

        assertEqualsWithDelta(
            Vec3(x = 0.5, y = 0.5, z = 0.7071067811865475),
            result
        )
    }

    @Test
    @Suppress("LongMethod")
    fun `GIVEN a B-spline eighth sphere surface, WHEN get, THEN return point on the sphere`() {
        val surface = Surface3.Builder.getInstance<Double>()
            .ofType(CurveType.B_SPLINE)
            .withDegree(Vec2(x = 3, y = 3))
            .withControlVertices(
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 0.0, z = 1.0),
                    textureCoordinates = Vec2(x = 0.0, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 0.0, z = 1.0),
                    textureCoordinates = Vec2(x = 0.33333, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 0.0, z = 1.0),
                    textureCoordinates = Vec2(x = 0.66667, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 0.0, z = 1.0),
                    textureCoordinates = Vec2(x = 1.0, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 0.5522847498307935, z = 1.0),
                    textureCoordinates = Vec2(x = 0.0, y = 0.33333),
                    normal = Vec3(x = 0.0, y = 0.5, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.3050184448956622, y = 0.5522847498307935, z = 1.0),
                    textureCoordinates = Vec2(x = 0.33333, y = 0.33333),
                    normal = Vec3(x = 0.25, y = 0.5, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522847498307935, y = 0.3050184448956622, z = 1.0),
                    textureCoordinates = Vec2(x = 0.66667, y = 0.33333),
                    normal = Vec3(x = 0.5, y = 0.25, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522847498307935, y = 0.0, z = 1.0),
                    textureCoordinates = Vec2(x = 1.0, y = 0.33333),
                    normal = Vec3(x = 0.5, y = 0.0, z = 1.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 1.0, z = 0.5522847498307935),
                    textureCoordinates = Vec2(x = 0.0, y = 0.66667),
                    normal = Vec3(x = 0.0, y = 1.0, z = 0.5)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522847498307935, y = 1.0, z = 0.5522847498307935),
                    textureCoordinates = Vec2(x = 0.33333, y = 0.66667),
                    normal = Vec3(x = 0.5, y = 1.0, z = 0.5)
                ),
                ControlVertex3(
                    controlPoint = Vec3(1.0, y = 0.5522847498307935, z = 0.5522847498307935),
                    textureCoordinates = Vec2(x = 0.66667, y = 0.66667),
                    normal = Vec3(x = 1.0, y = 0.5, z = 0.5)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 1.0, y = 0.0, z = 0.5522847498307935),
                    textureCoordinates = Vec2(x = 1.0, y = 0.66667),
                    normal = Vec3(x = 1.0, y = 0.0, z = 0.5)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.0, y = 1.0, z = 0.0),
                    textureCoordinates = Vec2(x = 0.0, y = 1.0),
                    normal = Vec3(x = 0.0, y = 1.0, z = 0.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 0.5522847498307935, y = 1.0, z = 0.0),
                    textureCoordinates = Vec2(x = 0.33333, y = 1.0),
                    normal = Vec3(x = 0.5, y = 1.0, z = 0.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(1.0, y = 0.5522847498307935, z = 0.0),
                    textureCoordinates = Vec2(x = 0.66667, y = 1.0),
                    normal = Vec3(x = 1.0, y = 0.5, z = 0.0)
                ),
                ControlVertex3(
                    controlPoint = Vec3(x = 1.0, y = 0.0, z = 0.0),
                    textureCoordinates = Vec2(x = 1.0, y = 1.0),
                    normal = Vec3(x = 1.0, y = 0.0, z = 0.0)
                )
            )
            .withKnotsU(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0)
            .withKnotsV(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0)
            .build()
        val parametersValues = Vec2(x = 0.5, y = 0.5)

        val result = surface[parametersValues]

        assertEqualsWithDelta(
            Vec3(x = 0.5, y = 0.5, z = 0.7071067811865475),
            result
        )
    }
}
