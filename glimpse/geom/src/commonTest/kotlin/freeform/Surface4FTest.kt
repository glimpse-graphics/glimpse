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

package graphics.glimpse.geom.freeform

import graphics.glimpse.testing.assertEqualsWithDelta
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4
import kotlin.test.Test

class Surface4FTest {

    @Test
    @Suppress("LongMethod")
    fun `GIVEN a Bezier eighth sphere surface, WHEN get, THEN return point on the sphere`() {
        val surface = Surface4.Builder.getInstance<Float>()
            .ofType(FreeformType.BEZIER)
            .withDegree(Vec2(x = 2, y = 2))
            .withControlVertices(
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0f, y = 0.0f, z = 1.0f, w = 1.0f),
                    textureCoordinates = Vec2(x = 0.0f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0f, y = 0.0f, z = 1.0f, w = 0.70710677f),
                    textureCoordinates = Vec2(x = 0.5f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0f, y = 0.0f, z = 1.0f, w = 1.0f),
                    textureCoordinates = Vec2(x = 1.0f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0f, y = 1.0f, z = 1.0f, w = 0.70710677f),
                    textureCoordinates = Vec2(x = 0.0f, y = 0.5f),
                    normal = Vec3(x = 0.0f, y = 1.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0f, y = 1.0f, z = 1.0f, w = 0.5f),
                    textureCoordinates = Vec2(x = 0.5f, y = 0.5f),
                    normal = Vec3(x = 1.0f, y = 1.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0f, y = 0.0f, z = 1.0f, w = 0.70710677f),
                    textureCoordinates = Vec2(x = 1.0f, y = 0.5f),
                    normal = Vec3(x = 1.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0f, y = 1.0f, z = 0.0f, w = 1.0f),
                    textureCoordinates = Vec2(x = 0.0f, y = 1.0f),
                    normal = Vec3(x = 0.0f, y = 1.0f, z = 0.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0f, y = 1.0f, z = 0.0f, w = 0.70710677f),
                    textureCoordinates = Vec2(x = 0.5f, y = 1.0f),
                    normal = Vec3(x = 1.0f, y = 1.0f, z = 0.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0f, y = 0.0f, z = 0.0f, w = 1.0f),
                    textureCoordinates = Vec2(x = 1.0f, y = 1.0f),
                    normal = Vec3(x = 1.0f, y = 0.0f, z = 0.0f)
                )
            )
            .build()
        val parametersValues = Vec2(x = 0.5f, y = 0.5f)

        val result = surface[parametersValues]

        assertEqualsWithDelta(
            Vec4(x = 0.5f, y = 0.5f, z = 0.70710677f, w = 1.0f),
            result
        )
    }

    @Test
    @Suppress("LongMethod")
    fun `GIVEN a B-spline eighth sphere surface, WHEN get, THEN return point on the sphere`() {
        val surface = Surface4.Builder.getInstance<Float>()
            .ofType(FreeformType.B_SPLINE)
            .withDegree(Vec2(x = 2, y = 2))
            .withControlVertices(
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0f, y = 0.0f, z = 1.0f, w = 1.0f),
                    textureCoordinates = Vec2(x = 0.0f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0f, y = 0.0f, z = 1.0f, w = 0.70710677f),
                    textureCoordinates = Vec2(x = 0.5f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0f, y = 0.0f, z = 1.0f, w = 1.0f),
                    textureCoordinates = Vec2(x = 1.0f, y = 0.0f),
                    normal = Vec3(x = 0.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0f, y = 1.0f, z = 1.0f, w = 0.70710677f),
                    textureCoordinates = Vec2(x = 0.0f, y = 0.5f),
                    normal = Vec3(x = 0.0f, y = 1.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0f, y = 1.0f, z = 1.0f, w = 0.5f),
                    textureCoordinates = Vec2(x = 0.5f, y = 0.5f),
                    normal = Vec3(x = 1.0f, y = 1.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0f, y = 0.0f, z = 1.0f, w = 0.70710677f),
                    textureCoordinates = Vec2(x = 1.0f, y = 0.5f),
                    normal = Vec3(x = 1.0f, y = 0.0f, z = 1.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0f, y = 1.0f, z = 0.0f, w = 1.0f),
                    textureCoordinates = Vec2(x = 0.0f, y = 1.0f),
                    normal = Vec3(x = 0.0f, y = 1.0f, z = 0.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0f, y = 1.0f, z = 0.0f, w = 0.70710677f),
                    textureCoordinates = Vec2(x = 0.5f, y = 1.0f),
                    normal = Vec3(x = 1.0f, y = 1.0f, z = 0.0f)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0f, y = 0.0f, z = 0.0f, w = 1.0f),
                    textureCoordinates = Vec2(x = 1.0f, y = 1.0f),
                    normal = Vec3(x = 1.0f, y = 0.0f, z = 0.0f)
                )
            )
            .withKnotsU(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
            .withKnotsV(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f)
            .build()
        val parametersValues = Vec2(x = 0.5f, y = 0.5f)

        val result = surface[parametersValues]

        assertEqualsWithDelta(
            Vec4(x = 0.5f, y = 0.5f, z = 0.70710677f, w = 1.0f),
            result
        )
    }
}
