/*
 * Copyright 2020 Slawomir Czerwinski
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

package graphics.glimpse.examples.triangle

import graphics.glimpse.shaders.annotations.Attribute
import graphics.glimpse.shaders.annotations.AttributeRole
import graphics.glimpse.shaders.annotations.ShaderParams
import graphics.glimpse.shaders.annotations.Uniform
import graphics.glimpse.types.Mat4

@ShaderParams(
    attributes = [
        Attribute(name = "aPos", role = AttributeRole.POSITIONS, vectorSize = 3),
        Attribute(name = "aTexCoords", role = AttributeRole.TEX_COORDS, vectorSize = 2),
    ]
)
data class TriangleShader(

    @Uniform(name = "uProjMatrix")
    val projectionMatrix: Mat4,

    @Uniform(name = "uViewMatrix")
    val viewMatrix: Mat4,

    @Uniform(name = "uModelMatrix")
    val modelMatrix: Mat4,
)
