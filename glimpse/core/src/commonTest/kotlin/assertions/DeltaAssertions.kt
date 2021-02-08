/*
 * Copyright 2020 Slawomir Czerwinski
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

package graphics.glimpse.assertions

import graphics.glimpse.types.Mat3
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4
import kotlin.math.abs
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 */
fun assertEqualsWithDelta(
    expected: Float,
    actual: Float,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertTrue(
        actual = abs(x = actual - expected) < delta.delta,
        message = "${messagePrefix}Expected <$expected \u00B1 ${delta.delta}>, actual <$actual>."
    )
}

/**
 * Asserts that the [actual] values are equal to the [expected] values plus/minus [delta],
 * with an optional [message].
 */
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
 * Asserts that the [actual] vector is equal to the [expected] vector plus/minus [delta]
 * (compared per coordinate), with an optional [message].
 */
fun assertEqualsWithDelta(
    expected: Vec3,
    actual: Vec3,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.toFloatArray().toList(),
        actual = actual.toFloatArray().toList(),
        delta = delta,
        message = "${messagePrefix}Vectors are different"
    )
}

/**
 * Asserts that the [actual] vector is equal to the [expected] vector plus/minus [delta]
 * (compared per coordinate), with an optional [message].
 */
fun assertEqualsWithDelta(
    expected: Vec4,
    actual: Vec4,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.toFloatArray().toList(),
        actual = actual.toFloatArray().toList(),
        delta = delta,
        message = "${messagePrefix}Vectors are different"
    )
}

/**
 * Asserts that the [actual] matrix is equal to the [expected] matrix plus/minus [delta]
 * (compared per element), with an optional [message].
 */
fun assertEqualsWithDelta(
    expected: Mat3,
    actual: Mat3,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.toFloatArray().toList(),
        actual = actual.toFloatArray().toList(),
        delta = delta,
        message = "${messagePrefix}Matrices are different"
    )
}

/**
 * Asserts that the [actual] matrix is equal to the [expected] matrix plus/minus [delta]
 * (compared per element), with an optional [message].
 */
fun assertEqualsWithDelta(
    expected: Mat4,
    actual: Mat4,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.toFloatArray().toList(),
        actual = actual.toFloatArray().toList(),
        delta = delta,
        message = "${messagePrefix}Matrices are different"
    )
}

private fun messagePrefix(message: String?) = if (message == null) "" else "$message. "
