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

/**
 * Returns floating point buffer data containing all of the elements of this array.
 */
fun FloatArray.toFloatBufferData(): FloatBufferData =
    FloatBufferData.fromFloatArray(floatArray = this)

/**
 * Returns integer buffer data containing all of the elements of this array.
 */
fun IntArray.toIntBufferData(): IntBufferData =
    IntBufferData.fromIntArray(intArray = this)

/**
 * Returns floating point buffer data containing all of the elements of this collection.
 */
fun Collection<Float>.toFloatBufferData(): FloatBufferData =
    toFloatArray().toFloatBufferData()

/**
 * Returns integer buffer data containing all of the elements of this collection.
 */
fun Collection<Int>.toIntBufferData(): IntBufferData =
    toIntArray().toIntBufferData()

/**
 * Returns floating point buffer data containing the the specified [elements].
 */
fun floatBufferDataOf(vararg elements: Float): FloatBufferData =
    floatArrayOf(*elements).toFloatBufferData()

/**
 * Returns integer buffer data containing the the specified [elements].
 */
fun intBufferDataOf(vararg elements: Int): IntBufferData =
    intArrayOf(*elements).toIntBufferData()
