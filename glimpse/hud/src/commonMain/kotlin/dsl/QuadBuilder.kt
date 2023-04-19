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

package graphics.glimpse.hud.dsl

import graphics.glimpse.hud.Quad
import graphics.glimpse.textures.Texture
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Vec2

/**
 * Builder of a quad element of HUD.
 */
class QuadBuilder internal constructor(
    private val texture: Texture,
    private val position: Vec2<Float>
) {

    /**
     * Origin point of the quad.
     *
     * The origin is relative to the texture, e.g. point `(0.5, 0.5)` is the middle of the quad.
     */
    var origin = Vec2(x = 0.5f, y = 0.5f)

    /**
     * Width of the quad.
     */
    var width: Number = texture.width

    /**
     * Height of the quad.
     */
    var height: Number = texture.height

    /**
     * Rotation of the quad.
     */
    var rotation: Angle<Float> = Angle.nullAngle()

    internal fun build(): Quad =
        Quad(texture, position, origin).apply {
            width = this@QuadBuilder.width.toFloat()
            height = this@QuadBuilder.height.toFloat()
            rotation = this@QuadBuilder.rotation
        }
}
