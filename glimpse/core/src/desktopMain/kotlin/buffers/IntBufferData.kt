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

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.IntBuffer
import kotlin.math.min

/**
 * Data to be stored in a buffer of integer numbers.
 */
actual class IntBufferData(
    internal val nioBuffer: IntBuffer
) {

    /**
     * Size of the data in bytes.
     */
    actual val sizeInBytes: Int get() = nioBuffer.capacity() * ELEMENT_SIZE

    /**
     * Returns `true` if the two specified buffers contain the same data,
     * i.e. contain the same number of the same elements in the same order.
     */
    actual fun contentEquals(other: IntBufferData): Boolean =
        this.nioBuffer == other.nioBuffer

    /**
     * Returns a hash code based on the contents of this buffer data.
     */
    actual fun contentHashCode(): Int = nioBuffer.hashCode()

    override fun toString(): String {
        nioBuffer.rewind()
        val capacity = nioBuffer.capacity()
        val ints = IntArray(size = min(capacity, MAX_ELEMENTS_TO_STRING))
        nioBuffer.get(ints)
        nioBuffer.rewind()
        return ints.joinToString(
            prefix = "IntBufferData(sizeInBytes=$sizeInBytes, [",
            postfix = if (capacity > MAX_ELEMENTS_TO_STRING) ", ... ])" else "])"
        )
    }

    actual companion object {

        private const val ELEMENT_SIZE = Int.SIZE_BYTES

        private const val MAX_ELEMENTS_TO_STRING = 32

        /**
         * Creates buffer data from an array of integer numbers.
         */
        actual fun fromIntArray(intArray: IntArray): IntBufferData =
            ByteBuffer.allocateDirect(intArray.size * ELEMENT_SIZE)
                .apply { order(ByteOrder.nativeOrder()) }
                .asIntBuffer()
                .put(intArray)
                .apply { rewind() }
                .let(::IntBufferData)
    }
}
