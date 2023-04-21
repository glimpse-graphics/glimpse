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

package graphics.glimpse.testing

import graphics.glimpse.testing.internal.messagePrefix
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 */
@JvmName("assertFloatEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Float,
    actual: Float,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertTrue(
        actual = abs(x = actual - expected) < delta.floatValue,
        message = "${messagePrefix}Expected <$expected \u00B1 ${delta.floatValue}>, actual <$actual>"
    )
}

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 */
@JvmName("assertDoubleEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Double,
    actual: Double,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertTrue(
        actual = abs(x = actual - expected) < delta.doubleValue,
        message = "${messagePrefix}Expected <$expected \u00B1 ${delta.doubleValue}>, actual <$actual>"
    )
}

/**
 * Asserts that the [actual] values are equal to the [expected] values plus/minus [delta],
 * with an optional [message].
 */
@JvmName("assertFloatListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Float>,
    actual: List<Float>,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEquals(
        expected = expected.size,
        actual = actual.size,
        message = "${messagePrefix}Different sizes"
    )
    for (index in expected.indices) {
        assertEqualsWithDelta(
            expected = expected[index],
            actual = actual[index],
            delta = delta,
            message = "${messagePrefix}Different values at index <$index>"
        )
    }
}

/**
 * Asserts that the [actual] values are equal to the [expected] values plus/minus [delta],
 * with an optional [message].
 */
@JvmName("assertDoubleListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Double>,
    actual: List<Double>,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEquals(
        expected = expected.size,
        actual = actual.size,
        message = "${messagePrefix}Different sizes"
    )
    for (index in expected.indices) {
        assertEqualsWithDelta(
            expected = expected[index],
            actual = actual[index],
            delta = delta,
            message = "${messagePrefix}Different values at index <$index>"
        )
    }
}
