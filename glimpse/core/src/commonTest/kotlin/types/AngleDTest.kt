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

package graphics.glimpse.types

import graphics.glimpse.testing.assertEqualsWithDelta
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame

class AngleDTest {

    @Test
    fun `GIVEN an Angle, WHEN unaryPlus, THEN return the same object`() {
        val angle = Angle.fromRad(rad = 1.0)

        val result = +angle

        assertSame(angle, result)
    }

    @Test
    fun `GIVEN an Angle, WHEN unaryMinus, THEN return an opposite angle`() {
        val angle = Angle.fromRad(rad = 1.0)

        val result = -angle

        assertEqualsWithDelta(Angle.fromRad(rad = -1.0), result)
    }

    @Test
    fun `GIVEN two Angles, WHEN plus, THEN return sum of the angles`() {
        val angle1 = Angle.fromRad(rad = 2.0)
        val angle2 = Angle.fromRad(rad = 1.5)

        val result = angle1 + angle2

        assertEqualsWithDelta(Angle.fromRad(rad = 3.5), result)
    }

    @Test
    fun `GIVEN two Angles, WHEN minus, THEN return difference between the angles`() {
        val angle1 = Angle.fromRad(rad = 2.0)
        val angle2 = Angle.fromRad(rad = 1.5)

        val result = angle1 - angle2

        assertEqualsWithDelta(Angle.fromRad(rad = 0.5), result)
    }

    @Test
    fun `GIVEN an Angle and a number, WHEN times, THEN return product of the angle and the number`() {
        val angle = Angle.fromRad(rad = 2.0)
        val number = 3.0

        val result = angle * number

        assertEqualsWithDelta(Angle.fromRad(rad = 6.0), result)
    }

    @Test
    fun `GIVEN an Angle and a number, WHEN div, THEN return quotient of the angle and the number`() {
        val angle = Angle.fromRad(rad = 3.0)
        val number = 2.0

        val result = angle / number

        assertEqualsWithDelta(Angle.fromRad(rad = 1.5), result)
    }

    @Test
    fun `GIVEN two Angles, WHEN div, THEN return quotient of the angles`() {
        val angle1 = Angle.fromRad(rad = 3.0)
        val angle2 = Angle.fromRad(rad = 1.5)

        val result = angle1 / angle2

        assertEqualsWithDelta(2.0, result)
    }

    @Test
    fun `GIVEN two Angles, WHEN rem, THEN return remainder from dividing the angles`() {
        val angle1 = Angle.fromRad(rad = 7.0)
        val angle2 = Angle.fromRad(rad = 1.5)

        val result = angle1 % angle2

        assertEqualsWithDelta(Angle.fromRad(rad = 1.0), result)
    }

    @Test
    fun `GIVEN two large Angles, WHEN rem, THEN return remainder from dividing the angles`() {
        val angle1 = Angle.fromDeg(deg = 540.0)
        val angle2 = Angle.fullAngle<Double>()

        val result = angle1 % angle2

        assertEqualsWithDelta(Angle.straightAngle(), result)
    }

    @Test
    fun `GIVEN an Angle, WHEN coerceIn, THEN return angle within range`() {
        val angle1 = Angle.fromDeg(deg = 75.0)
        val angle2 = Angle.fromDeg(deg = 105.0)
        val angle3 = Angle.fromDeg(deg = 185.0)

        val result1 = angle1.coerceIn(Angle.rightAngle<Double>(), Angle.straightAngle<Double>())
        val result2 = angle2.coerceIn(Angle.rightAngle<Double>(), Angle.straightAngle<Double>())
        val result3 = angle3.coerceIn(Angle.rightAngle<Double>(), Angle.straightAngle<Double>())

        assertEqualsWithDelta(Angle.rightAngle<Double>(), result1)
        assertEqualsWithDelta(angle2, result2)
        assertEqualsWithDelta(Angle.straightAngle<Double>(), result3)
    }

    @Test
    fun `GIVEN two Angles, WHEN rangeTo, THEN return AngleRange between the angles`() {
        val angle1 = Angle.fromRad(rad = 1.0)
        val angle2 = Angle.fromRad(rad = 3.0)

        val result = angle1..angle2

        assertEquals(AngleRange(angle1, angle2), result)
    }

    @Test
    fun `GIVEN two Angles, WHEN compareTo, THEN return result of comparison of the angles`() {
        val angle1 = Angle.fromRad(rad = 1.0)
        val angle2 = Angle.fromRad(rad = 3.0)

        assertFalse { angle1 > angle2 }
    }

    @Test
    fun `GIVEN null Angle, WHEN sin, THEN return 0`() {
        val result = sin(Angle.nullAngle<Double>())

        assertEqualsWithDelta(0.0, result)
    }

    @Test
    fun `GIVEN null Angle, WHEN cos, THEN return 1`() {
        val result = cos(Angle.nullAngle<Double>())

        assertEqualsWithDelta(1.0, result)
    }

    @Test
    fun `GIVEN null Angle, WHEN tan, THEN return 0`() {
        val result = tan(Angle.nullAngle<Double>())

        assertEqualsWithDelta(0.0, result)
    }
}
