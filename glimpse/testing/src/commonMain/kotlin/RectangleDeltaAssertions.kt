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

import graphics.glimpse.geom.Rectangle
import graphics.glimpse.testing.internal.messagePrefix

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 *
 * @since v1.3.0
 */
@JvmName("assertFloatRectangleEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Rectangle<Float>,
    actual: Rectangle<Float>,
    delta: Delta = Delta.MEDIUM,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.left,
        actual = actual.left,
        delta = delta,
        message = "${messagePrefix}Left sides are different"
    )
    assertEqualsWithDelta(
        expected = expected.bottom,
        actual = actual.bottom,
        delta = delta,
        message = "${messagePrefix}Bottom sides are different"
    )
    assertEqualsWithDelta(
        expected = expected.right,
        actual = actual.right,
        delta = delta,
        message = "${messagePrefix}Right sides are different"
    )
    assertEqualsWithDelta(
        expected = expected.top,
        actual = actual.top,
        delta = delta,
        message = "${messagePrefix}Top sides are different"
    )
}

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 *
 * @since v1.3.0
 */
@JvmName("assertDoubleRectangleEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Rectangle<Double>,
    actual: Rectangle<Double>,
    delta: Delta = Delta.MEDIUM,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.left,
        actual = actual.left,
        delta = delta,
        message = "${messagePrefix}Left sides are different"
    )
    assertEqualsWithDelta(
        expected = expected.bottom,
        actual = actual.bottom,
        delta = delta,
        message = "${messagePrefix}Bottom sides are different"
    )
    assertEqualsWithDelta(
        expected = expected.right,
        actual = actual.right,
        delta = delta,
        message = "${messagePrefix}Right sides are different"
    )
    assertEqualsWithDelta(
        expected = expected.top,
        actual = actual.top,
        delta = delta,
        message = "${messagePrefix}Top sides are different"
    )
}
