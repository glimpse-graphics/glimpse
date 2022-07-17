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

@file:Suppress("FunctionNaming")

package graphics.glimpse.buffers

import graphics.glimpse.differentFloats
import graphics.glimpse.floats
import graphics.glimpse.FLOATS_SIZE_IN_BYTES
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FloatBufferDataTest {

    @Test
    fun `GIVEN float array, WHEN create FloatBufferData, THEN data should have correct size in bytes`() {
        val bufferData = FloatBufferData.fromFloatArray(floats)

        assertEquals(FLOATS_SIZE_IN_BYTES, bufferData.sizeInBytes)
    }

    @Test
    fun `GIVEN 2 FloatBufferData objects with the same elements, WHEN contentEquals, THEN return true`() {
        val bufferData1 = FloatBufferData.fromFloatArray(floats)
        val bufferData2 = FloatBufferData.fromFloatArray(floats)

        assertTrue(bufferData1.contentEquals(bufferData2))
    }

    @Test
    fun `GIVEN 2 FloatBufferData objects with different elements, WHEN contentEquals, THEN return false`() {
        val bufferData1 = FloatBufferData.fromFloatArray(floats)
        val bufferData2 = FloatBufferData.fromFloatArray(differentFloats)

        assertFalse(bufferData1.contentEquals(bufferData2))
    }
}
