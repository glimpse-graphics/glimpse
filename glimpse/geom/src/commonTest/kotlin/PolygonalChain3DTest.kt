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

class PolygonalChain3DTest {

    @Test
    fun `GIVEN a polygonal chain, WHEN toRationalForm, THEN return the same chain in rational form`() {
        val polygonalChain = PolygonalChain3(
            vertices = listOf(
                Vec3(x = 0.0, y = 0.0, z = 1.0),
                Vec3(x = 2.0, y = 0.0, z = 2.0),
                Vec3(x = 2.0, y = 1.0, z = 3.0),
                Vec3(x = 1.0, y = 2.0, z = 2.0),
                Vec3(x = 0.0, y = 1.0, z = 1.0)
            )
        )

        val result = polygonalChain.toRationalForm()

        assertEqualsWithDelta(
            listOf(
                Vec4(x = 0.0, y = 0.0, z = 1.0, w = 1.0),
                Vec4(x = 2.0, y = 0.0, z = 2.0, w = 1.0),
                Vec4(x = 2.0, y = 1.0, z = 3.0, w = 1.0),
                Vec4(x = 1.0, y = 2.0, z = 2.0, w = 1.0),
                Vec4(x = 0.0, y = 1.0, z = 1.0, w = 1.0)
            ),
            result.vertices
        )
    }
}
