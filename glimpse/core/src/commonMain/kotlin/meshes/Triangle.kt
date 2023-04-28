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

package graphics.glimpse.meshes

import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3

internal data class Triangle(
    val position1: Vec3<Float>,
    val position2: Vec3<Float>,
    val position3: Vec3<Float>,
    val texCoord1: Vec2<Float>,
    val texCoord2: Vec2<Float>,
    val texCoord3: Vec2<Float>,
    val normal1: Vec3<Float>,
    val normal2: Vec3<Float>,
    val normal3: Vec3<Float>
) {

    val tangent: Vec3<Float>
    val bitangent: Vec3<Float>

    val positions: List<Vec3<Float>>
        get() = listOf(position1, position2, position3)

    val textureCoordinates: List<Vec2<Float>>
        get() = listOf(texCoord1, texCoord2, texCoord3)

    val normals: List<Vec3<Float>>
        get() = listOf(normal1, normal2, normal3)

    val tangents: List<Vec3<Float>>
        get() = listOf(tangent, tangent, tangent)

    val bitangents: List<Vec3<Float>>
        get() = listOf(bitangent, bitangent, bitangent)

    init {
        val edge1 = position2 - position1
        val edge2 = position3 - position1

        val deltaUV1 = texCoord2 - texCoord1
        val deltaUV2 = texCoord3 - texCoord1

        val factor = 1f / (deltaUV1.x * deltaUV2.y - deltaUV2.x * deltaUV1.y)

        tangent = (edge1 * deltaUV2.y - edge2 * deltaUV1.y) * factor
        bitangent = (edge2 * deltaUV1.x - edge1 * deltaUV2.x) * factor
    }
}
