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

package graphics.glimpse.shaders.annotations

/**
 * Marks a uniform sampler2D variable property in [ShaderParams]-annotated class.
 *
 * Supports properties of types:
 * - textures: `Texture`,
 * - types with `iterator(): Iterator<Texture>` method (e.g. `Array<Texture>`, `Iterable<Texture>`, etc.).
 *
 * @see ShaderParams
 * @see Uniform
 *
 * @since v1.2.0
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Sampler2D(

    /**
     * Uniform variable name, as defined in the shader.
     */
    val name: String,

    /**
     * Number of textures returned by the iterator.
     *
     * Negative value, if the type of the annotated property is `Texture`.
     */
    val size: Int = -1
)
