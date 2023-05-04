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

package graphics.glimpse.geom.interpolation

import graphics.glimpse.testing.assertEqualsWithDelta
import graphics.glimpse.types.Vec3
import kotlin.test.Test

class UniformLinearVec3MultiInterpolatorTest {

    @Test
    fun `GIVEN an interpolator with 2 keys, WHEN get, THEN return an interpolated value`() {
        val interpolator = UniformLinearVec3MultiInterpolator(
            Vec3(x = 1.0, y = 2.0, z = 0.0),
            Vec3(x = -1.0, y = 1.0, z = 1.0)
        )
        val parameterValues = listOf(0.0, 0.1, 0.5, 0.7, 1.0)

        val result = parameterValues.map { interpolator[it] }

        assertEqualsWithDelta(
            listOf(
                Vec3(x = 1.0, y = 2.0, z = 0.0),
                Vec3(x = 0.8, y = 1.9, z = 0.1),
                Vec3(x = 0.0, y = 1.5, z = 0.5),
                Vec3(x = -0.4, y = 1.3, z = 0.7),
                Vec3(x = -1.0, y = 1.0, z = 1.0)
            ),
            result
        )
    }

    @Test
    fun `GIVEN an interpolator with 3 keys, WHEN get, THEN return an interpolated value`() {
        val interpolator = UniformLinearVec3MultiInterpolator(
            Vec3(x = 1.0, y = 2.0, z = 0.0),
            Vec3(x = -1.0, y = 1.0, z = 1.0),
            Vec3(x = 0.0, y = 0.0, z = 0.0)
        )
        val parameterValues = listOf(0.0, 0.1, 0.5, 0.7, 1.0)

        val result = parameterValues.map { interpolator[it] }

        assertEqualsWithDelta(
            listOf(
                Vec3(x = 1.0, y = 2.0, z = 0.0),
                Vec3(x = 0.6, y = 1.8, z = 0.2),
                Vec3(x = -1.0, y = 1.0, z = 1.0),
                Vec3(x = -0.6, y = 0.6, z = 0.6),
                Vec3(x = 0.0, y = 0.0, z = 0.0)
            ),
            result
        )
    }
}