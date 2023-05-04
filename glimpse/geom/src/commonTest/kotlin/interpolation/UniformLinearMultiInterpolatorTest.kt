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
import kotlin.test.Test

class UniformLinearMultiInterpolatorTest {

    @Test
    fun `GIVEN an interpolator with 2 keys, WHEN get, THEN return an interpolated value`() {
        val interpolator = UniformLinearMultiInterpolator(2.0, 0.0)
        val parameterValues = listOf(0.0, 0.1, 0.5, 0.7, 1.0)

        val result = parameterValues.map { interpolator[it] }

        assertEqualsWithDelta(listOf(2.0, 1.8, 1.0, 0.6, 0.0), result)
    }

    @Test
    fun `GIVEN an interpolator with 3 keys, WHEN get, THEN return an interpolated value`() {
        val interpolator = UniformLinearMultiInterpolator(2.0, 0.0, 1.0)
        val parameterValues = listOf(0.0, 0.1, 0.5, 0.7, 1.0)

        val result = parameterValues.map { interpolator[it] }

        assertEqualsWithDelta(listOf(2.0, 1.6, 0.0, 0.4, 1.0), result)
    }
}
