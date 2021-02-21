/*
 * Copyright 2020-2021 Slawomir Czerwinski
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
 *
 */

package graphics.glimpse.buffers

import graphics.glimpse.differentInts
import graphics.glimpse.ints
import graphics.glimpse.INTS_SIZE_IN_BYTES
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IntBufferDataTest {

    @Test
    fun `GIVEN int array, WHEN create IntBufferData, THEN data should have correct size in bytes`() {
        val bufferData = IntBufferData.fromIntArray(ints)

        assertEquals(INTS_SIZE_IN_BYTES, bufferData.sizeInBytes)
    }

    @Test
    fun `GIVEN 2 IntBufferData objects with the same elements, WHEN contentEquals, THEN return true`() {
        val bufferData1 = IntBufferData.fromIntArray(ints)
        val bufferData2 = IntBufferData.fromIntArray(ints)

        assertTrue(bufferData1.contentEquals(bufferData2))
    }

    @Test
    fun `GIVEN 2 IntBufferData objects with different elements, WHEN contentEquals, THEN return false`() {
        val bufferData1 = IntBufferData.fromIntArray(ints)
        val bufferData2 = IntBufferData.fromIntArray(differentInts)

        assertFalse(bufferData1.contentEquals(bufferData2))
    }
}
