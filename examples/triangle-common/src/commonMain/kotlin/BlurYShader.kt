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

package graphics.glimpse.examples.triangle

import graphics.glimpse.shaders.annotations.Attribute
import graphics.glimpse.shaders.annotations.AttributeRole
import graphics.glimpse.shaders.annotations.Sampler2D
import graphics.glimpse.shaders.annotations.ShaderParams
import graphics.glimpse.shaders.annotations.Uniform
import graphics.glimpse.textures.Texture
import graphics.glimpse.types.Vec2

@ShaderParams(
    attributes = [
        Attribute(name = "aPos", role = AttributeRole.POSITIONS, vectorSize = 3),
        Attribute(name = "aTexCoords", role = AttributeRole.TEX_COORDS, vectorSize = 2),
    ]
)
data class BlurYShader(

    @Uniform(name = "uScreenSize")
    val screenSize: Vec2<Float>,

    @Sampler2D(name = "uImage")
    val image: Texture,

    @Sampler2D(name = "uPosition")
    val position: Texture,
)
