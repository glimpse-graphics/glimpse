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

package graphics.glimpse.buffers

/**
 * Type of uniform buffer data element.
 *
 * @since v2.0.0
 */
enum class UniformBufferDataElementType(val size: Int) {

    /**
     * A single number.
     */
    NUMBER(size = 1),

    /**
     * A 2D vector.
     */
    VEC2(size = 2),

    /**
     * A 3D vector.
     */
    VEC3(size = 3),

    /**
     * A 4D vector.
     */
    VEC4(size = 4),

    /**
     * A 2×2 matrix.
     */
    MAT2(size = 4),

    /**
     * A 3×3 matrix.
     */
    MAT3(size = 9),

    /**
     * A 4×4 matrix.
     */
    MAT4(size = 16)
}
