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

import graphics.glimpse.geom.Circle
import graphics.glimpse.testing.internal.messagePrefix

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 *
 * @since v1.3.0
 */
@JvmName("assertFloatCircleEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Circle<Float>,
    actual: Circle<Float>,
    delta: Delta = Delta.MEDIUM,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.center,
        actual = actual.center,
        delta = delta,
        message = "${messagePrefix}Center points are different"
    )
    assertEqualsWithDelta(
        expected = expected.radius,
        actual = actual.radius,
        delta = delta,
        message = "${messagePrefix}Radii are different"
    )
}

/**
 * Asserts that the [actual] value is equal to the [expected] value plus/minus [delta],
 * with an optional [message].
 *
 * @since v1.3.0
 */
@JvmName("assertDoubleCircleEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Circle<Double>,
    actual: Circle<Double>,
    delta: Delta = Delta.MEDIUM,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.center,
        actual = actual.center,
        delta = delta,
        message = "${messagePrefix}Center points are different"
    )
    assertEqualsWithDelta(
        expected = expected.radius,
        actual = actual.radius,
        delta = delta,
        message = "${messagePrefix}Radii are different"
    )
}
