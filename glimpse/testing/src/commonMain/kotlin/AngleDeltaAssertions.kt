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

package graphics.glimpse.testing

import graphics.glimpse.testing.internal.messagePrefix
import graphics.glimpse.types.Angle
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 *
 * @since v1.3.0
 */
@JvmName("assertFloatAngleEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Angle<Float>,
    actual: Angle<Float>,
    delta: Delta = Delta.MEDIUM,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    val difference = actual - expected
    assertTrue(
        actual = abs(x = difference.deg) < delta.floatValue,
        message = "${messagePrefix}Expected <$expected \u00B1 ${delta.floatValue}>, actual <$actual>"
    )
    assertTrue(
        actual = abs(x = difference.rad) < delta.floatValue,
        message = "${messagePrefix}Expected <$expected \u00B1 ${delta.floatValue}>, actual <$actual>"
    )
}

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 *
 * @since v1.3.0
 */
@JvmName("assertDoubleAngleEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Angle<Double>,
    actual: Angle<Double>,
    delta: Delta = Delta.MEDIUM,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    val difference = actual - expected
    assertTrue(
        actual = abs(x = difference.deg) < delta.doubleValue,
        message = "${messagePrefix}Expected <$expected \u00B1 ${delta.doubleValue}>, actual <$actual>"
    )
    assertTrue(
        actual = abs(x = difference.rad) < delta.doubleValue,
        message = "${messagePrefix}Expected <$expected \u00B1 ${delta.doubleValue}>, actual <$actual>"
    )
}

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 *
 * @since v1.3.0
 */
@JvmName("assertFloatAngleListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Angle<Float>>,
    actual: List<Angle<Float>>,
    delta: Delta = Delta.MEDIUM,
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
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 *
 * @since v1.3.0
 */
@JvmName("assertDoubleAngleListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Angle<Double>>,
    actual: List<Angle<Double>>,
    delta: Delta = Delta.MEDIUM,
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
