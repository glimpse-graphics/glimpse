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

package graphics.glimpse.hud

import graphics.glimpse.textures.Texture
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec4
import graphics.glimpse.types.rotationZ
import graphics.glimpse.types.scale
import graphics.glimpse.types.translation

/**
 * A single quad.
 *
 * A quad is the most atomic element of HUD.
 */
class Quad(

    /**
     * Texture of this quad.
     */
    override val texture: Texture,

    /**
     * Position of this quad.
     *
     * The position is measured towards the [origin].
     */
    position: Vec2<Float> = Vec2.nullVector(),

    /**
     * Origin point of this quad.
     *
     * The origin is relative to the texture, e.g. point `(0.5, 0.5)` is the middle of the quad.
     */
    private val origin: Vec2<Float> = Vec2(x = 0.5f, y = 0.5f)
) : BaseHudElement(position), HudAtom {

    /**
     * Width of this quad.
     */
    var width: Float = intrinsicWidth

    /**
     * Height of this quad.
     */
    var height: Float = intrinsicHeight

    /**
     * Rotation angle of this quad.
     *
     * The pivot point for this rotation is the [origin].
     */
    var rotation: Angle<Float> = Angle.nullAngle()

    /**
     * Output model matrix for this quad.
     */
    override val modelMatrix: Mat4<Float>
        get() = listOf(
            translation(position.toVec3()),
            rotationZ(rotation),
            scale(x = width, y = height),
            translation(-origin.toVec3())
        ).reduce(Mat4<Float>::times)

    /**
     * Bounding box of this quad.
     */
    override val boundingBox: BoundingBox
        get() {
            val originSpaceMatrix = rotationZ(rotation) * scale(x = width, y = height) * translation(-origin.toVec3())
            val transformedCorners = corners.map { corner -> originSpaceMatrix * corner.toVec4(w = 1f) }
            return BoundingBox(
                left = transformedCorners.minOf(Vec4<Float>::x),
                right = transformedCorners.maxOf(Vec4<Float>::x),
                top = transformedCorners.minOf(Vec4<Float>::y),
                bottom = transformedCorners.maxOf(Vec4<Float>::y)
            )
        }

    /**
     * A singleton list consisting of this quad.
     */
    override val atoms: Iterable<HudAtom> get() = if (isVisible) listOf(this) else emptyList()

    companion object {
        private val corners = listOf(
            Vec2(x = 0f, y = 0f), Vec2(x = 0f, y = 1f), Vec2(x = 1f, y = 1f), Vec2(x = 1f, y = 0f)
        )
    }
}
