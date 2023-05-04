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
import graphics.glimpse.types.Vec4
import kotlin.test.Test

class Surface4DTest {

    @Test
    @Suppress("LongMethod")
    fun `GIVEN a Bezier eighth sphere surface, WHEN get, THEN return point on the sphere`() {
        val surface = Surface4.Builder.getInstance<Double>()
            .ofType(FreeformType.BEZIER)
            .withDegree(Vec2(x = 2, y = 2))
            .withControlVertices(
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0, y = 0.0, z = 1.0, w = 1.0),
                    textureCoordinates = Vec2(x = 0.0, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0, y = 0.0, z = 1.0, w = 0.7071067811865475),
                    textureCoordinates = Vec2(x = 0.5, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0, y = 0.0, z = 1.0, w = 1.0),
                    textureCoordinates = Vec2(x = 1.0, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0, y = 1.0, z = 1.0, w = 0.7071067811865475),
                    textureCoordinates = Vec2(x = 0.0, y = 0.5),
                    normal = Vec3(x = 0.0, y = 1.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0, y = 1.0, z = 1.0, w = 0.5),
                    textureCoordinates = Vec2(x = 0.5, y = 0.5),
                    normal = Vec3(x = 1.0, y = 1.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0, y = 0.0, z = 1.0, w = 0.7071067811865475),
                    textureCoordinates = Vec2(x = 1.0, y = 0.5),
                    normal = Vec3(x = 1.0, y = 0.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0, y = 1.0, z = 0.0, w = 1.0),
                    textureCoordinates = Vec2(x = 0.0, y = 1.0),
                    normal = Vec3(x = 0.0, y = 1.0, z = 0.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0, y = 1.0, z = 0.0, w = 0.7071067811865475),
                    textureCoordinates = Vec2(x = 0.5, y = 1.0),
                    normal = Vec3(x = 1.0, y = 1.0, z = 0.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0, y = 0.0, z = 0.0, w = 1.0),
                    textureCoordinates = Vec2(x = 1.0, y = 1.0),
                    normal = Vec3(x = 1.0, y = 0.0, z = 0.0)
                )
            )
            .build()
        val parametersValues = Vec2(x = 0.5, y = 0.5)

        val result = surface[parametersValues]

        assertEqualsWithDelta(
            Vec4(x = 0.5, y = 0.5, z = 0.7071067811865475, w = 1.0),
            result
        )
    }

    @Test
    @Suppress("LongMethod")
    fun `GIVEN a B-spline eighth sphere surface, WHEN get, THEN return point on the sphere`() {
        val surface = Surface4.Builder.getInstance<Double>()
            .ofType(FreeformType.B_SPLINE)
            .withDegree(Vec2(x = 2, y = 2))
            .withControlVertices(
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0, y = 0.0, z = 1.0, w = 1.0),
                    textureCoordinates = Vec2(x = 0.0, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0, y = 0.0, z = 1.0, w = 0.7071067811865475),
                    textureCoordinates = Vec2(x = 0.5, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0, y = 0.0, z = 1.0, w = 1.0),
                    textureCoordinates = Vec2(x = 1.0, y = 0.0),
                    normal = Vec3(x = 0.0, y = 0.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0, y = 1.0, z = 1.0, w = 0.7071067811865475),
                    textureCoordinates = Vec2(x = 0.0, y = 0.5),
                    normal = Vec3(x = 0.0, y = 1.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0, y = 1.0, z = 1.0, w = 0.5),
                    textureCoordinates = Vec2(x = 0.5, y = 0.5),
                    normal = Vec3(x = 1.0, y = 1.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0, y = 0.0, z = 1.0, w = 0.7071067811865475),
                    textureCoordinates = Vec2(x = 1.0, y = 0.5),
                    normal = Vec3(x = 1.0, y = 0.0, z = 1.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 0.0, y = 1.0, z = 0.0, w = 1.0),
                    textureCoordinates = Vec2(x = 0.0, y = 1.0),
                    normal = Vec3(x = 0.0, y = 1.0, z = 0.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0, y = 1.0, z = 0.0, w = 0.7071067811865475),
                    textureCoordinates = Vec2(x = 0.5, y = 1.0),
                    normal = Vec3(x = 1.0, y = 1.0, z = 0.0)
                ),
                ControlVertex4(
                    controlPoint = Vec4(x = 1.0, y = 0.0, z = 0.0, w = 1.0),
                    textureCoordinates = Vec2(x = 1.0, y = 1.0),
                    normal = Vec3(x = 1.0, y = 0.0, z = 0.0)
                )
            )
            .withKnotsU(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
            .withKnotsV(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
            .build()
        val parametersValues = Vec2(x = 0.5, y = 0.5)

        val result = surface[parametersValues]

        assertEqualsWithDelta(
            Vec4(x = 0.5, y = 0.5, z = 0.7071067811865475, w = 1.0),
            result
        )
    }
}
