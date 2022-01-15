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

package graphics.glimpse.gradle.obj

/**
 * Base class for visitors of [ObjFile].
 */
open class ObjFileVisitor {

    /**
     * Handles new vertex position.
     */
    open fun visitVertexPosition(coordinates: List<Float>) = Unit

    /**
     * Handles new texture coordinates position.
     */
    open fun visitTextureCoordinates(coordinates: List<Float>) = Unit

    /**
     * Handles new vertex normal.
     */
    open fun visitVertexNormal(coordinates: List<Float>) = Unit

    /**
     * Handles new face.
     */
    open fun visitFace(indices: List<Triple<Int?, Int?, Int?>>) {
        val firstVertex = indices.first()
        indices.drop(n = 1)
            .windowed(size = 2)
            .forEach { list ->
                visitTriangle(indices = listOf(firstVertex) + list)
            }
    }

    /**
     * Handles new triangle.
     */
    open fun visitTriangle(indices: List<Triple<Int?, Int?, Int?>>) = Unit

    /**
     * Called after last element has been visited.
     */
    open fun afterLast() = Unit
}
