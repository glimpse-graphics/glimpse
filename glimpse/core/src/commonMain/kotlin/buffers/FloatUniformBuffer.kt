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

package graphics.glimpse.buffers

import graphics.glimpse.types.Mat2
import graphics.glimpse.types.Mat3
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4

/**
 * A buffer containing uniform floating point [data] of given [elementType].
 *
 * Buffers can be more efficient than arrays of numbers, vectors or matrices,
 * in particular when operating on large amounts of constant data.
 *
 * @since v2.0.0
 */
data class FloatUniformBuffer(
    val data: FloatBufferData,
    val elementType: UniformBufferDataElementType
)

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v2.0.0
 */
@JvmName("ofFloats")
fun Iterable<Float>.toUniformBuffer(): FloatUniformBuffer =
    this.toList().toFloatArray().toUniformBuffer()

/**
 * Returns a new uniform buffer containing all elements of this array.
 *
 * @since v2.0.0
 */
@JvmName("ofFloats")
fun FloatArray.toUniformBuffer(): FloatUniformBuffer =
    FloatUniformBuffer(
        data = this.toFloatBufferData(),
        elementType = UniformBufferDataElementType.NUMBER
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v2.0.0
 */
@JvmName("ofPairs")
fun Iterable<Pair<Float, Float>>.toUniformBuffer(): FloatUniformBuffer =
    FloatUniformBuffer(
        data = this.flatMap { pair -> pair.toList() }.toFloatBufferData(),
        elementType = UniformBufferDataElementType.VEC2
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v2.0.0
 */
@JvmName("ofVec2")
fun Iterable<Vec2<Float>>.toUniformBuffer(): FloatUniformBuffer =
    FloatUniformBuffer(
        data = this.flatMap { vector -> vector.toList() }.toFloatBufferData(),
        elementType = UniformBufferDataElementType.VEC2
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v2.0.0
 */
@JvmName("ofTriples")
fun Iterable<Triple<Float, Float, Float>>.toUniformBuffer(): FloatUniformBuffer =
    FloatUniformBuffer(
        data = this.flatMap { triple -> triple.toList() }.toFloatBufferData(),
        elementType = UniformBufferDataElementType.VEC3
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v2.0.0
 */
@JvmName("ofVec3")
fun Iterable<Vec3<Float>>.toUniformBuffer(): FloatUniformBuffer =
    FloatUniformBuffer(
        data = this.flatMap { vector -> vector.toList() }.toFloatBufferData(),
        elementType = UniformBufferDataElementType.VEC3
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v2.0.0
 */
@JvmName("ofVec4")
fun Iterable<Vec4<Float>>.toUniformBuffer(): FloatUniformBuffer =
    FloatUniformBuffer(
        data = this.flatMap { vector -> vector.toList() }.toFloatBufferData(),
        elementType = UniformBufferDataElementType.VEC4
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v2.0.0
 */
@JvmName("ofMat2")
fun Iterable<Mat2<Float>>.toUniformBuffer(): FloatUniformBuffer =
    FloatUniformBuffer(
        data = this.flatMap { matrix -> matrix.toList() }.toFloatBufferData(),
        elementType = UniformBufferDataElementType.MAT2
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v2.0.0
 */
@JvmName("ofMat3")
fun Iterable<Mat3<Float>>.toUniformBuffer(): FloatUniformBuffer =
    FloatUniformBuffer(
        data = this.flatMap { matrix -> matrix.toList() }.toFloatBufferData(),
        elementType = UniformBufferDataElementType.MAT3
    )

/**
 * Returns a new uniform buffer containing all elements of this iterable.
 *
 * @since v2.0.0
 */
@JvmName("ofMat4")
fun Iterable<Mat4<Float>>.toUniformBuffer(): FloatUniformBuffer =
    FloatUniformBuffer(
        data = this.flatMap { matrix -> matrix.toList() }.toFloatBufferData(),
        elementType = UniformBufferDataElementType.MAT4
    )
