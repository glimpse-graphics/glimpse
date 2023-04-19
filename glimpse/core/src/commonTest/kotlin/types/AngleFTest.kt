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

package graphics.glimpse.types

import graphics.glimpse.assertions.assertEqualsWithDelta
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame

class AngleFTest {

    @Test
    fun `GIVEN an Angle, WHEN unaryPlus, THEN return the same object`() {
        val angle = Angle.fromRad(rad = 1f)

        val result = +angle

        assertSame(angle, result)
    }

    @Test
    fun `GIVEN an Angle, WHEN unaryMinus, THEN return an opposite angle`() {
        val angle = Angle.fromRad(rad = 1f)

        val result = -angle

        assertEqualsWithDelta(Angle.fromRad(rad = -1f), result)
    }

    @Test
    fun `GIVEN two Angles, WHEN plus, THEN return sum of the angles`() {
        val angle1 = Angle.fromRad(rad = 2f)
        val angle2 = Angle.fromRad(rad = 1.5f)

        val result = angle1 + angle2

        assertEqualsWithDelta(Angle.fromRad(rad = 3.5f), result)
    }

    @Test
    fun `GIVEN two Angles, WHEN minus, THEN return difference between the angles`() {
        val angle1 = Angle.fromRad(rad = 2f)
        val angle2 = Angle.fromRad(rad = 1.5f)

        val result = angle1 - angle2

        assertEqualsWithDelta(Angle.fromRad(rad = 0.5f), result)
    }

    @Test
    fun `GIVEN an Angle and a number, WHEN times, THEN return product of the angle and the number`() {
        val angle = Angle.fromRad(rad = 2f)
        val number = 3f

        val result = angle * number

        assertEqualsWithDelta(Angle.fromRad(rad = 6f), result)
    }

    @Test
    fun `GIVEN an Angle and a number, WHEN div, THEN return quotient of the angle and the number`() {
        val angle = Angle.fromRad(rad = 3f)
        val number = 2f

        val result = angle / number

        assertEqualsWithDelta(Angle.fromRad(rad = 1.5f), result)
    }

    @Test
    fun `GIVEN two Angles, WHEN div, THEN return quotient of the angles`() {
        val angle1 = Angle.fromRad(rad = 3f)
        val angle2 = Angle.fromRad(rad = 1.5f)

        val result = angle1 / angle2

        assertEqualsWithDelta(2f, result)
    }

    @Test
    fun `GIVEN two Angles, WHEN rem, THEN return remainder from dividing the angles`() {
        val angle1 = Angle.fromRad(rad = 7f)
        val angle2 = Angle.fromRad(rad = 1.5f)

        val result = angle1 % angle2

        assertEqualsWithDelta(Angle.fromRad(rad = 1f), result)
    }

    @Test
    fun `GIVEN an Angle, WHEN coerceIn, THEN return angle within range`() {
        val angle1 = Angle.fromDeg(deg = 75f)
        val angle2 = Angle.fromDeg(deg = 105f)
        val angle3 = Angle.fromDeg(deg = 185f)

        val result1 = angle1.coerceIn(Angle.rightAngle<Float>(), Angle.straightAngle<Float>())
        val result2 = angle2.coerceIn(Angle.rightAngle<Float>(), Angle.straightAngle<Float>())
        val result3 = angle3.coerceIn(Angle.rightAngle<Float>(), Angle.straightAngle<Float>())

        assertEqualsWithDelta(Angle.rightAngle<Float>(), result1)
        assertEqualsWithDelta(angle2, result2)
        assertEqualsWithDelta(Angle.straightAngle<Float>(), result3)
    }

    @Test
    fun `GIVEN two Angles, WHEN rangeTo, THEN return AngleRange between the angles`() {
        val angle1 = Angle.fromRad(rad = 1f)
        val angle2 = Angle.fromRad(rad = 3f)

        val result = angle1..angle2

        assertEquals(AngleRange(angle1, angle2), result)
    }

    @Test
    fun `GIVEN two Angles, WHEN compareTo, THEN return result of comparison of the angles`() {
        val angle1 = Angle.fromRad(rad = 1f)
        val angle2 = Angle.fromRad(rad = 3f)

        assertFalse { angle1 > angle2 }
    }

    @Test
    fun `GIVEN null Angle, WHEN sin, THEN return 0`() {
        val result = sin(Angle.nullAngle<Float>())

        assertEqualsWithDelta(0f, result)
    }

    @Test
    fun `GIVEN null Angle, WHEN cos, THEN return 1`() {
        val result = cos(Angle.nullAngle<Float>())

        assertEqualsWithDelta(1f, result)
    }

    @Test
    fun `GIVEN null Angle, WHEN tan, THEN return 0`() {
        val result = tan(Angle.nullAngle<Float>())

        assertEqualsWithDelta(0f, result)
    }
}
