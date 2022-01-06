/*
 * Copyright 2020-2022 Slawomir Czerwinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.cameras

import graphics.glimpse.assertions.assertEqualsWithDelta
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec3
import kotlin.js.JsName
import kotlin.test.Test

class RelativeTargetCameraTest {

    @Test
    @JsName(name = "GIVEN_a_RelativeTargetCamera_WHEN_eye_THEN_return_correct_eye_position")
    fun `GIVEN a RelativeTargetCamera, WHEN eye, THEN return correct eye position`() {
        val target = Vec3.nullVector
        val distance = 1.0f
        val longitude = Angle.fromDeg(deg = 45f)
        val latitude = Angle.fromDeg(deg = 45f)
        val camera = RelativeTargetCamera(target, distance, longitude, latitude)

        val result = camera.eye

        assertEqualsWithDelta(Vec3(x = 0.5f, y = 0.5f, z = 0.7071067812f), result)
    }

    @Test
    @JsName(name = "GIVEN_a_RelativeTargetCamera_WHEN_viewMatrix_THEN_return_correct_matrix")
    fun `GIVEN a RelativeTargetCamera, WHEN viewMatrix, THEN return correct matrix`() {
        val target = Vec3.nullVector
        val distance = 1.0f
        val longitude = Angle.fromDeg(deg = 45f)
        val latitude = Angle.fromDeg(deg = 45f)
        val camera = RelativeTargetCamera(target, distance, longitude, latitude)

        val result = camera.viewMatrix

        assertEqualsWithDelta(
            Mat4(
                listOf(
                    -0.7071067812f, -0.5f, 0.5f, 0f,
                    0.7071067812f, -0.5f, 0.5f, 0f,
                    0f, 0.7071067812f, 0.7071067812f, 0f,
                    0f, 0f, -1f, 1f
                )
            ),
            result
        )
    }
}
