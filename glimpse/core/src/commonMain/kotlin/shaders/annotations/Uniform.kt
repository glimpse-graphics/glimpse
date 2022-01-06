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
 * Marks a uniform variable property in [ShaderParams]-annotated class.
 *
 * Supports properties of types:
 * - numbers: `Boolean` (since v1.1.0), `Int`, `Float`,
 * - vectors: `Vec2`, `Vec3`, `Vec4`,
 * - matrices: `Mat2`, `Mat3`, `Mat4`,
 * - arrays of all above types,
 * - textures: `Texture`.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Uniform(

    /**
     * Uniform variable name, as defined in the shader.
     */
    val name: String
)
