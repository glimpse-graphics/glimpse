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
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4
import kotlin.test.assertEquals

/**
 * Asserts that the [actual] vector is equal to the [expected] vector plus/minus [delta]
 * (compared per coordinate), with an optional [message].
 *
 * @since v2.0.0
 */
@JvmName("assertFloatVec2EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Vec2<Float>,
    actual: Vec2<Float>,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.x,
        actual = actual.x,
        delta = delta,
        message = "${messagePrefix}Vectors X coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.y,
        actual = actual.y,
        delta = delta,
        message = "${messagePrefix}Vectors Y coordinates are different"
    )
}

/**
 * Asserts that the [actual] vector is equal to the [expected] vector plus/minus [delta]
 * (compared per coordinate), with an optional [message].
 *
 * @since v2.0.0
 */
@JvmName("assertDoubleVec2EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Vec2<Double>,
    actual: Vec2<Double>,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.x,
        actual = actual.x,
        delta = delta,
        message = "${messagePrefix}Vectors X coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.y,
        actual = actual.y,
        delta = delta,
        message = "${messagePrefix}Vectors Y coordinates are different"
    )
}

/**
 * Asserts that the [actual] vector is equal to the [expected] vector plus/minus [delta]
 * (compared per coordinate), with an optional [message].
 *
 * @since v2.0.0
 */
@JvmName("assertFloatVec2ListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Vec2<Float>>,
    actual: List<Vec2<Float>>,
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
 *
 * @since v2.0.0
 */
@JvmName("assertDoubleVec2ListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Vec2<Double>>,
    actual: List<Vec2<Double>>,
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
 *
 * @since v2.0.0
 */
@JvmName("assertFloatVec3EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Vec3<Float>,
    actual: Vec3<Float>,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.x,
        actual = actual.x,
        delta = delta,
        message = "${messagePrefix}Vectors X coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.y,
        actual = actual.y,
        delta = delta,
        message = "${messagePrefix}Vectors Y coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.z,
        actual = actual.z,
        delta = delta,
        message = "${messagePrefix}Vectors Z coordinates are different"
    )
}

/**
 * Asserts that the [actual] vector is equal to the [expected] vector plus/minus [delta]
 * (compared per coordinate), with an optional [message].
 *
 * @since v2.0.0
 */
@JvmName("assertDoubleVec3EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Vec3<Double>,
    actual: Vec3<Double>,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.x,
        actual = actual.x,
        delta = delta,
        message = "${messagePrefix}Vectors X coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.y,
        actual = actual.y,
        delta = delta,
        message = "${messagePrefix}Vectors Y coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.z,
        actual = actual.z,
        delta = delta,
        message = "${messagePrefix}Vectors Z coordinates are different"
    )
}

/**
 * Asserts that the [actual] vector is equal to the [expected] vector plus/minus [delta]
 * (compared per coordinate), with an optional [message].
 *
 * @since v2.0.0
 */
@JvmName("assertFloatVec3ListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Vec3<Float>>,
    actual: List<Vec3<Float>>,
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
 *
 * @since v2.0.0
 */
@JvmName("assertDoubleVec3ListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Vec3<Double>>,
    actual: List<Vec3<Double>>,
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
 *
 * @since v2.0.0
 */
@JvmName("assertFloatVec4EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Vec4<Float>,
    actual: Vec4<Float>,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.x,
        actual = actual.x,
        delta = delta,
        message = "${messagePrefix}Vectors X coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.y,
        actual = actual.y,
        delta = delta,
        message = "${messagePrefix}Vectors Y coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.z,
        actual = actual.z,
        delta = delta,
        message = "${messagePrefix}Vectors Z coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.w,
        actual = actual.w,
        delta = delta,
        message = "${messagePrefix}Vectors W coordinates are different"
    )
}

/**
 * Asserts that the [actual] vector is equal to the [expected] vector plus/minus [delta]
 * (compared per coordinate), with an optional [message].
 *
 * @since v2.0.0
 */
@JvmName("assertDoubleVec4EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Vec4<Double>,
    actual: Vec4<Double>,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.x,
        actual = actual.x,
        delta = delta,
        message = "${messagePrefix}Vectors X coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.y,
        actual = actual.y,
        delta = delta,
        message = "${messagePrefix}Vectors Y coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.z,
        actual = actual.z,
        delta = delta,
        message = "${messagePrefix}Vectors Z coordinates are different"
    )
    assertEqualsWithDelta(
        expected = expected.w,
        actual = actual.w,
        delta = delta,
        message = "${messagePrefix}Vectors W coordinates are different"
    )
}

/**
 * Asserts that the [actual] vector is equal to the [expected] vector plus/minus [delta]
 * (compared per coordinate), with an optional [message].
 *
 * @since v2.0.0
 */
@JvmName("assertFloatVec4ListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Vec4<Float>>,
    actual: List<Vec4<Float>>,
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
 *
 * @since v2.0.0
 */
@JvmName("assertDoubleVec4ListEqualsWithDelta")
fun assertEqualsWithDelta(
    expected: List<Vec4<Double>>,
    actual: List<Vec4<Double>>,
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
