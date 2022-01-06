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
 * Data to be stored in a buffer of floating point numbers.
 */
expect class FloatBufferData {

    /**
     * Size of the data in bytes.
     */
    val sizeInBytes: Int

    /**
     * Returns `true` if the two specified buffers contain the same data,
     * i.e. contain the same number of the same elements in the same order.
     */
    fun contentEquals(other: FloatBufferData): Boolean

    /**
     * Returns a hash code based on the contents of this buffer data.
     */
    fun contentHashCode(): Int

    companion object {

        /**
         * Creates buffer data from an array of floating point numbers.
         */
        fun fromFloatArray(floatArray: FloatArray): FloatBufferData
    }
}
