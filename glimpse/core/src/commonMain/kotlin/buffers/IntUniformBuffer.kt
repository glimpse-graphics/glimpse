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

package graphics.glimpse.buffers

import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4

/**
 * A buffer containing uniform integer [data] of given [elementType] wrapper.
 *
 * Buffers can be more efficient than arrays of numbers, vectors or matrices,
 * in particular when operating on large amounts of constant data.
 *
 * @since v1.3.0
 */
data class IntUniformBuffer(
    val data: IntBufferData,
    val elementType: UniformBufferDataElementType
)

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v1.3.0
 */
@JvmName("ofIntegers")
fun Iterable<Int>.toUniformBuffer(): IntUniformBuffer =
    this.toList().toIntArray().toUniformBuffer()

/**
 * Returns a new uniform buffer containing all elements of this array.
 *
 * @since v1.3.0
 */
@JvmName("ofIntegers")
fun IntArray.toUniformBuffer(): IntUniformBuffer =
    IntUniformBuffer(
        data = this.toIntBufferData(),
        elementType = UniformBufferDataElementType.NUMBER
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v1.3.0
 */
@JvmName("ofPairs")
fun Iterable<Pair<Int, Int>>.toUniformBuffer(): IntUniformBuffer =
    IntUniformBuffer(
        data = this.flatMap { pair -> pair.toList() }.toIntBufferData(),
        elementType = UniformBufferDataElementType.VEC2
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v1.3.0
 */
@JvmName("ofVec2")
fun Iterable<Vec2<Int>>.toUniformBuffer(): IntUniformBuffer =
    IntUniformBuffer(
        data = this.flatMap { vector -> vector.toList() }.toIntBufferData(),
        elementType = UniformBufferDataElementType.VEC2
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v1.3.0
 */
@JvmName("ofTriples")
fun Iterable<Triple<Int, Int, Int>>.toUniformBuffer(): IntUniformBuffer =
    IntUniformBuffer(
        data = this.flatMap { triple -> triple.toList() }.toIntBufferData(),
        elementType = UniformBufferDataElementType.VEC3
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v1.3.0
 */
@JvmName("ofVec3")
fun Iterable<Vec3<Int>>.toUniformBuffer(): IntUniformBuffer =
    IntUniformBuffer(
        data = this.flatMap { vector -> vector.toList() }.toIntBufferData(),
        elementType = UniformBufferDataElementType.VEC3
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v1.3.0
 */
@JvmName("ofVec4")
fun Iterable<Vec4<Int>>.toUniformBuffer(): IntUniformBuffer =
    IntUniformBuffer(
        data = this.flatMap { vector -> vector.toList() }.toIntBufferData(),
        elementType = UniformBufferDataElementType.VEC4
    )
