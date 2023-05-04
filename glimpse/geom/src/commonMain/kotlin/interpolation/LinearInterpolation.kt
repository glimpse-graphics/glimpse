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

package graphics.glimpse.geom.interpolation

import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.minus
import graphics.glimpse.types.one
import graphics.glimpse.types.plus
import graphics.glimpse.types.times
import kotlin.reflect.KClass

/**
 * Returns linear interpolation of values [a] and [b] at given [parameterValue].
 */
internal inline fun <reified T> linearInterpolation(
    a: T,
    b: T,
    parameterValue: T
): T where T : Number, T : Comparable<T> =
    a * (one<T>() - parameterValue) + b * parameterValue

/**
 * Returns linear interpolation of values [a] and [b] at given [parameterValue].
 */
internal fun <T> linearInterpolation(
    a: T,
    b: T,
    parameterValue: T,
    type: KClass<T>
): T where T : Number, T : Comparable<T> =
    a * (one(type) - parameterValue) + b * parameterValue

/**
 * Returns linear interpolation of vectors [v1] and [v2] at given [parameterValue].
 */
internal inline fun <reified T> linearInterpolation(
    v1: Vec2<T>,
    v2: Vec2<T>,
    parameterValue: T
): Vec2<T> where T : Number, T : Comparable<T> =
    v1 * (one<T>() - parameterValue) + v2 * parameterValue

/**
 * Returns linear interpolation of vectors [v1] and [v2] at given [parameterValue].
 */
internal fun <T> linearInterpolation(
    v1: Vec2<T>,
    v2: Vec2<T>,
    parameterValue: T,
    type: KClass<T>
): Vec2<T> where T : Number, T : Comparable<T> =
    v1 * (one(type) - parameterValue) + v2 * parameterValue

/**
 * Returns linear interpolation of vectors [v1] and [v2] at given [parameterValue].
 */
internal inline fun <reified T> linearInterpolation(
    v1: Vec3<T>,
    v2: Vec3<T>,
    parameterValue: T
): Vec3<T> where T : Number, T : Comparable<T> =
    v1 * (one<T>() - parameterValue) + v2 * parameterValue

/**
 * Returns linear interpolation of vectors [v1] and [v2] at given [parameterValue].
 */
internal fun <T> linearInterpolation(
    v1: Vec3<T>,
    v2: Vec3<T>,
    parameterValue: T,
    type: KClass<T>
): Vec3<T> where T : Number, T : Comparable<T> =
    v1 * (one(type) - parameterValue) + v2 * parameterValue
