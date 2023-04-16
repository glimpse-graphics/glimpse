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
 */
@Deprecated(message = "Use Vec2.magnitude() instead", replaceWith = ReplaceWith("vector.magnitude()"))
fun <T : Number> magnitude(vector: Vec2<T>): T = vector.magnitude()

/**
 * Returns a unit vector in the direction of the given [vector].
 */
@Deprecated(message = "Use Vec2.normalize() instead", replaceWith = ReplaceWith("vector.normalize()"))
fun <T : Number> normalize(vector: Vec2<T>): Vec2<T> = vector / vector.magnitude()
