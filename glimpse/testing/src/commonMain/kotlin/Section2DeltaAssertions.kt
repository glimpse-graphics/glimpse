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

import graphics.glimpse.geom.Section2
import graphics.glimpse.testing.internal.messagePrefix
import kotlin.test.assertEquals

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 */
@JvmName("assertFloatSection2EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Section2<Float>,
    actual: Section2<Float>,
    delta: Delta = Delta.MEDIUM,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.start,
        actual = actual.start,
        delta = delta,
        message = "${messagePrefix}Start points are different"
    )
    assertEqualsWithDelta(
        expected = expected.end,
        actual = actual.end,
        delta = delta,
        message = "${messagePrefix}End points are different"
    )
}

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 */
@JvmName("assertDoubleSection2EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Section2<Double>,
    actual: Section2<Double>,
    delta: Delta = Delta.MEDIUM,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.start,
        actual = actual.start,
        delta = delta,
        message = "${messagePrefix}Start points are different"
    )
    assertEqualsWithDelta(
        expected = expected.end,
        actual = actual.end,
        delta = delta,
        message = "${messagePrefix}End points are different"
    )
}

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 */
@JvmName("assertFloatSection2ListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Section2<Float>>,
    actual: List<Section2<Float>>,
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
 */
@JvmName("assertDoubleSection2ListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Section2<Double>>,
    actual: List<Section2<Double>>,
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
