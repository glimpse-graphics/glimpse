/*
 * Copyright 2020-2021 Slawomir Czerwinski
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
 *
 */

package graphics.glimpse.types

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class Vec3MathTest {

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_magnitude_THEN_return_vectors_magnitude")
    fun `GIVEN a vector, WHEN magnitude, THEN return vector's magnitude`() {
        val vector = Vec3(x = 3f, y = 4f, z = 12f)

        val result = magnitude(vector)

        assertEquals(13f, result)
    }

    @Test
    @JsName(name = "GIVEN_a_vector_WHEN_normalize_THEN_return_a_unit_vector_in_the_same_direction")
    fun `GIVEN a vector, WHEN normalize, THEN return a unit vector in the same direction`() {
        val vector = Vec3(x = 3f, y = 4f, z = 12f)

        val result = normalize(vector)

        assertEquals(Vec3(x = 0.2307692308f, y = 0.3076923077f, z = 0.9230769231f), result)
    }
}
