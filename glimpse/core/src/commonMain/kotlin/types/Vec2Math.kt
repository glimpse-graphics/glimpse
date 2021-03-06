/*
 * Copyright 2020-2021 Slawomir Czerwinski
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
 *
 */

package graphics.glimpse.types

import kotlin.math.sqrt

/**
 * Returns the magnitude of the given [vector].
 */
fun magnitude(vector: Vec2): Float =
    sqrt((vector.x * vector.x + vector.y * vector.y))

/**
 * Returns a unit vector in the direction of the given [vector].
 */
fun normalize(vector: Vec2): Vec2 = vector / magnitude(vector)
