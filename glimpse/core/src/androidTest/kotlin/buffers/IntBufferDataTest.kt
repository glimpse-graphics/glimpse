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
    fun GIVEN_int_array_WHEN_create_IntBufferData_THEN_data_should_have_correct_size_in_bytes() {
        val bufferData = IntBufferData.fromIntArray(ints)

        assertEquals(INTS_SIZE_IN_BYTES, bufferData.sizeInBytes)
    }

    @Test
    fun GIVEN_2_IntBufferData_objects_with_the_same_elements_WHEN_contentEquals_THEN_return_true() {
        val bufferData1 = IntBufferData.fromIntArray(ints)
        val bufferData2 = IntBufferData.fromIntArray(ints)

        assertTrue(bufferData1.contentEquals(bufferData2))
    }

    @Test
    fun GIVEN_2_IntBufferData_objects_with_different_elements_WHEN_contentEquals_THEN_return_false() {
        val bufferData1 = IntBufferData.fromIntArray(ints)
        val bufferData2 = IntBufferData.fromIntArray(differentInts)

        assertFalse(bufferData1.contentEquals(bufferData2))
    }
}
