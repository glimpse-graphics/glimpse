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

/**
 * Computes the sine of the specified [angle].
 */
fun sin(angle: Angle<Float>): Float = kotlin.math.sin(angle.rad)

/**
 * Computes the sine of the specified [angle].
 */
fun sin(angle: Angle<Double>): Double = kotlin.math.sin(angle.rad)

/**
 * Computes the cosine of the specified [angle].
 */
fun cos(angle: Angle<Float>): Float = kotlin.math.cos(angle.rad)

/**
 * Computes the cosine of the specified [angle].
 */
fun cos(angle: Angle<Double>): Double = kotlin.math.cos(angle.rad)

/**
 * Computes the tangent of the specified [angle].
 */
fun tan(angle: Angle<Float>): Float = kotlin.math.tan(angle.rad)

/**
 * Computes the tangent of the specified [angle].
 */
fun tan(angle: Angle<Double>): Double = kotlin.math.tan(angle.rad)
