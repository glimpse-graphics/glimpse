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

import graphics.glimpse.differentFloats
import graphics.glimpse.floats
import graphics.glimpse.FLOATS_SIZE_IN_BYTES
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FloatBufferDataTest {

    @Test
    fun GIVEN_float_array_WHEN_create_FloatBufferData_THEN_data_should_have_correct_size_in_bytes() {
        val bufferData = FloatBufferData.fromFloatArray(floats)

        assertEquals(FLOATS_SIZE_IN_BYTES, bufferData.sizeInBytes)
    }

    @Test
    fun GIVEN_2_FloatBufferData_objects_with_the_same_elements_WHEN_contentEquals_THEN_return_true() {
        val bufferData1 = FloatBufferData.fromFloatArray(floats)
        val bufferData2 = FloatBufferData.fromFloatArray(floats)

        assertTrue(bufferData1.contentEquals(bufferData2))
    }

    @Test
    fun GIVEN_2_FloatBufferData_objects_with_different_elements_WHEN_contentEquals_THEN_return_false() {
        val bufferData1 = FloatBufferData.fromFloatArray(floats)
        val bufferData2 = FloatBufferData.fromFloatArray(differentFloats)

        assertFalse(bufferData1.contentEquals(bufferData2))
    }
}
