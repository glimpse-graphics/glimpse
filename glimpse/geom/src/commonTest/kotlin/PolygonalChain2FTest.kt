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
import org.junit.Test
import kotlin.test.assertTrue

class PolygonalChain2FTest {

    @Test
    fun `GIVEN an empty polygonal chain, WHEN segments, THEN return an empty list`() {
        val polygonalChain = PolygonalChain2<Float>(vertices = emptyList())

        val result = polygonalChain.segments

        assertTrue { result.isEmpty() }
    }

    @Test
    fun `GIVEN a polygonal chain with 1 vertex, WHEN segments, THEN return an empty list`() {
        val polygonalChain = PolygonalChain2(vertices = listOf(Vec2(x = 0.0f, y = 0.0f)))

        val result = polygonalChain.segments

        assertTrue { result.isEmpty() }
    }

    @Test
    fun `GIVEN a polygonal chain with multiple vertices, WHEN segments, THEN return a list of segments`() {
        val polygonalChain = PolygonalChain2(
            vertices = listOf(
                Vec2(x = 0.0f, y = 0.0f),
                Vec2(x = 2.0f, y = 0.0f),
                Vec2(x = 2.0f, y = 1.0f),
                Vec2(x = 1.0f, y = 2.0f),
                Vec2(x = 0.0f, y = 1.0f)
            )
        )

        val result = polygonalChain.segments

        assertEqualsWithDelta(
            listOf(
                Section2(start = Vec2(x = 0.0f, y = 0.0f), end = Vec2(x = 2.0f, y = 0.0f)),
                Section2(start = Vec2(x = 2.0f, y = 0.0f), end = Vec2(x = 2.0f, y = 1.0f)),
                Section2(start = Vec2(x = 2.0f, y = 1.0f), end = Vec2(x = 1.0f, y = 2.0f)),
                Section2(start = Vec2(x = 1.0f, y = 2.0f), end = Vec2(x = 0.0f, y = 1.0f)),
            ),
            result
        )
    }
}
