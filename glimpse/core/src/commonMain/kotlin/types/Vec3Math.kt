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
 * Returns the magnitude of the given [vector].
 *
 * @deprecated Use [Vec2.magnitude] instead.
 */
@Deprecated(
    message = "Use Vec3.magnitude() instead",
    replaceWith = ReplaceWith("vector.magnitude()")
)
fun <T> magnitude(vector: Vec3<T>): T where T : Number, T : Comparable<T> = vector.magnitude()

/**
 * Returns a unit vector in the direction of the given [vector].
 *
 * @deprecated Use [Vec3.normalize] instead.
 */
@Deprecated(
    message = "Use Vec3.normalize() instead",
    replaceWith = ReplaceWith("vector.normalize()")
)
fun <T> normalize(vector: Vec3<T>): Vec3<T> where T : Number, T : Comparable<T> = vector / vector.magnitude()
