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

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame

class AngleDTest {

    @Test
    @JsName(name = "GIVEN_an_Angle_WHEN_unaryPlus_THEN_return_the_same_object")
    fun `GIVEN an Angle, WHEN unaryPlus, THEN return the same object`() {
        val angle = Angle.fromRad(rad = 1.0)

        val result = +angle

        assertSame(angle, result)
    }

    @Test
    @JsName(name = "GIVEN_an_Angle_WHEN_unaryMinus_THEN_return_an_opposite_angle")
    fun `GIVEN an Angle, WHEN unaryMinus, THEN return an opposite angle`() {
        val angle = Angle.fromRad(rad = 1.0)

        val result = -angle

        assertEquals(Angle.fromRad(rad = -1.0), result)
    }

    @Test
    @JsName(name = "GIVEN_two_Angles_WHEN_plus_THEN_return_sum_of_the_angles")
    fun `GIVEN two Angles, WHEN plus, THEN return sum of the angles`() {
        val angle1 = Angle.fromRad(rad = 2.0)
        val angle2 = Angle.fromRad(rad = 1.5)

        val result = angle1 + angle2

        assertEquals(Angle.fromRad(rad = 3.5), result)
    }

    @Test
    @JsName(name = "GIVEN_two_Angles_WHEN_minus_THEN_return_difference_between_the_angles")
    fun `GIVEN two Angles, WHEN minus, THEN return difference between the angles`() {
        val angle1 = Angle.fromRad(rad = 2.0)
        val angle2 = Angle.fromRad(rad = 1.5)

        val result = angle1 - angle2

        assertEquals(Angle.fromRad(rad = 0.5), result)
    }

    @Test
    @JsName(name = "GIVEN_an_Angle_and_a_number_WHEN_times_THEN_return_product_of_the_angle_and_the_number")
    fun `GIVEN an Angle and a number, WHEN times, THEN return product of the angle and the number`() {
        val angle = Angle.fromRad(rad = 2.0)
        val number = 3.0

        val result = angle * number

        assertEquals(Angle.fromRad(rad = 6.0), result)
    }

    @Test
    @JsName(name = "GIVEN_an_Angle_and_a_number_WHEN_div_THEN_return_quotient_of_the_angle_and_the_number")
    fun `GIVEN an Angle and a number, WHEN div, THEN return quotient of the angle and the number`() {
        val angle = Angle.fromRad(rad = 3.0)
        val number = 2.0

        val result = angle / number

        assertEquals(Angle.fromRad(rad = 1.5), result)
    }

    @Test
    @JsName(name = "GIVEN_two_Angles_WHEN_div_THEN_return_quotient_of_the_angles")
    fun `GIVEN two Angles, WHEN div, THEN return quotient of the angles`() {
        val angle1 = Angle.fromRad(rad = 3.0)
        val angle2 = Angle.fromRad(rad = 1.5)

        val result = angle1 / angle2

        assertEquals(2.0, result)
    }

    @Test
    @JsName(name = "GIVEN_two_Angles_WHEN_rem_THEN_return_remainder_from_dividing_the_angles")
    fun `GIVEN two Angles, WHEN rem, THEN return remainder from dividing the angles`() {
        val angle1 = Angle.fromRad(rad = 7.0)
        val angle2 = Angle.fromRad(rad = 1.5)

        val result = angle1 % angle2

        assertEquals(Angle.fromRad(rad = 1.0), result)
    }

    @Test
    @JsName(name = "GIVEN_an_Angle_WHEN_coerceIn_THEN_return_angle_within_range")
    fun `GIVEN an Angle, WHEN coerceIn, THEN return angle within range`() {
        val angle1 = Angle.fromDeg(deg = 75.0)
        val angle2 = Angle.fromDeg(deg = 105.0)
        val angle3 = Angle.fromDeg(deg = 185.0)

        val result1 = angle1.coerceIn(Angle.rightAngle.toDoubleAngle(), Angle.straightAngle.toDoubleAngle())
        val result2 = angle2.coerceIn(Angle.rightAngle.toDoubleAngle(), Angle.straightAngle.toDoubleAngle())
        val result3 = angle3.coerceIn(Angle.rightAngle.toDoubleAngle(), Angle.straightAngle.toDoubleAngle())

        assertEquals(Angle.rightAngle.toDoubleAngle(), result1)
        assertEquals(angle2, result2)
        assertEquals(Angle.straightAngle.toDoubleAngle(), result3)
    }

    @Test
    @JsName(name = "GIVEN_two_Angles_WHEN_rangeTo_THEN_return_AngleRange_between_the_angles")
    fun `GIVEN two Angles, WHEN rangeTo, THEN return AngleRange between the angles`() {
        val angle1 = Angle.fromRad(rad = 1.0)
        val angle2 = Angle.fromRad(rad = 3.0)

        val result = angle1..angle2

        assertEquals(AngleRange(angle1, angle2), result)
    }

    @Test
    @JsName(name = "GIVEN_two_Angles_WHEN_compareTo_THEN_return_result_of_comparison_of_the_angles")
    fun `GIVEN two Angles, WHEN compareTo, THEN return result of comparison of the angles`() {
        val angle1 = Angle.fromRad(rad = 1.0)
        val angle2 = Angle.fromRad(rad = 3.0)

        assertFalse { angle1 > angle2 }
    }

    @Test
    @JsName(name = "GIVEN_null_Angle_WHEN_sin_THEN_return_0")
    fun `GIVEN null Angle, WHEN sin, THEN return 0`() {
        val result = sin(Angle.nullAngle.toDoubleAngle())

        assertEquals(0.0, result)
    }

    @Test
    @JsName(name = "GIVEN_null_Angle_WHEN_cos_THEN_return_1")
    fun `GIVEN null Angle, WHEN cos, THEN return 1`() {
        val result = cos(Angle.nullAngle.toDoubleAngle())

        assertEquals(1.0, result)
    }

    @Test
    @JsName(name = "GIVEN_null_Angle_WHEN_tan_THEN_return_0")
    fun `GIVEN null Angle, WHEN tan, THEN return 0`() {
        val result = tan(Angle.nullAngle.toDoubleAngle())

        assertEquals(0.0, result)
    }
}
