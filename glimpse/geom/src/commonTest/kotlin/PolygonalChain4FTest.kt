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
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4
import org.junit.Test

class PolygonalChain4FTest {

    @Test
    fun `GIVEN a polygonal chain, WHEN toNonRationalForm, THEN return the same chain in non-rational form`() {
        val polygonalChain = PolygonalChain4(
            vertices = listOf(
                Vec4(x = 0.0f, y = 0.0f, z = 1.0f, w = 1.0f),
                Vec4(x = 2.0f, y = 0.0f, z = 2.0f, w = 2.0f),
                Vec4(x = 2.0f, y = 1.0f, z = 3.0f, w = 1.0f),
                Vec4(x = 1.0f, y = 2.0f, z = 2.0f, w = 0.5f),
                Vec4(x = 0.0f, y = 1.0f, z = 1.0f, w = 1.0f)
            )
        )

        val result = polygonalChain.toNonRationalForm()

        assertEqualsWithDelta(
            listOf(
                Vec3(x = 0.0f, y = 0.0f, z = 1.0f),
                Vec3(x = 1.0f, y = 0.0f, z = 1.0f),
                Vec3(x = 2.0f, y = 1.0f, z = 3.0f),
                Vec3(x = 2.0f, y = 4.0f, z = 4.0f),
                Vec3(x = 0.0f, y = 1.0f, z = 1.0f)
            ),
            result.vertices
        )
    }
}
