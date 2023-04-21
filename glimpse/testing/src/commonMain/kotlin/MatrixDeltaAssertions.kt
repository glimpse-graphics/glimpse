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
import graphics.glimpse.types.Mat2
import graphics.glimpse.types.Mat3
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.toDoubleArray
import graphics.glimpse.types.toFloatArray

/**
 * Asserts that the [actual] matrix is equal to the [expected] matrix plus/minus [delta]
 * (compared per element), with an optional [message].
 */
@JvmName("assertFloatMat2EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Mat2<Float>,
    actual: Mat2<Float>,
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
@JvmName("assertDoubleMat2EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Mat2<Double>,
    actual: Mat2<Double>,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.toDoubleArray().toList(),
        actual = actual.toDoubleArray().toList(),
        delta = delta,
        message = "${messagePrefix}Matrices are different"
    )
}

/**
 * Asserts that the [actual] matrix is equal to the [expected] matrix plus/minus [delta]
 * (compared per element), with an optional [message].
 */
@JvmName("assertFloatMat3EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Mat3<Float>,
    actual: Mat3<Float>,
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
@JvmName("assertDoubleMat3EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Mat3<Double>,
    actual: Mat3<Double>,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.toDoubleArray().toList(),
        actual = actual.toDoubleArray().toList(),
        delta = delta,
        message = "${messagePrefix}Matrices are different"
    )
}

/**
 * Asserts that the [actual] matrix is equal to the [expected] matrix plus/minus [delta]
 * (compared per element), with an optional [message].
 */
@JvmName("assertFloatMat4EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Mat4<Float>,
    actual: Mat4<Float>,
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
@JvmName("assertDoubleMat4EqualsWithDelta")
fun assertEqualsWithDelta(
    expected: Mat4<Double>,
    actual: Mat4<Double>,
    delta: Delta = Delta.FINE,
    message: String? = null
) {
    val messagePrefix = messagePrefix(message)
    assertEqualsWithDelta(
        expected = expected.toDoubleArray().toList(),
        actual = actual.toDoubleArray().toList(),
        delta = delta,
        message = "${messagePrefix}Matrices are different"
    )
}
