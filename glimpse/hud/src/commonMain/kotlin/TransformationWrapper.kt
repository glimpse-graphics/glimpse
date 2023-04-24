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

import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec4
import graphics.glimpse.types.rotationZ
import graphics.glimpse.types.scale
import graphics.glimpse.types.translation

/**
 * Transformation wrapper for element of HUD.
 */
class TransformationWrapper(

    /**
     * Transformed element.
     */
    element: HudElement,

    /**
     * Function returning current translation of the transformed element.
     */
    private val translationProvider: () -> Vec2<Float>,

    /**
     * Function returning current rotation of the transformed element.
     */
    private val rotationProvider: () -> Angle<Float>,

    /**
     * Function returning current scale of the transformed element.
     */
    private val scaleProvider: () -> Vec2<Float>
) : BaseHudElementWrapper(element) {

    /**
     * Position of the transformed element.
     */
    override var position: Vec2<Float>
        get() = super.position + translationProvider()
        set(value) { super.position = value }

    /**
     * Bounding box of the transformed element.
     */
    override val boundingBox: BoundingBox
        get() {
            val originalBoundingBox = super.boundingBox
            val corners = listOf(
                Vec2(originalBoundingBox.left, originalBoundingBox.top),
                Vec2(originalBoundingBox.left, originalBoundingBox.bottom),
                Vec2(originalBoundingBox.right, originalBoundingBox.bottom),
                Vec2(originalBoundingBox.right, originalBoundingBox.top)
            )
            val scale = scaleProvider()
            val originSpaceMatrix = rotationZ(rotationProvider()) * scale(x = scale.x, scale.y)
            val transformedCorners = corners.map { corner -> originSpaceMatrix * corner.toVec4(w = 1f) }
            return BoundingBox(
                left = transformedCorners.minOf(Vec4<Float>::x),
                right = transformedCorners.maxOf(Vec4<Float>::x),
                top = transformedCorners.minOf(Vec4<Float>::y),
                bottom = transformedCorners.maxOf(Vec4<Float>::y)
            )
        }

    /**
     * Atoms making up the transformed element.
     */
    override val atoms: Iterable<HudAtom>
        get() = super.atoms.map { atom -> TransformationAtom(atom) }

    private val transformationMatrix: Mat4<Float>
        get() {
            val scale = scaleProvider()
            return listOf(
                translation(super.position.toVec3() + translationProvider().toVec3()),
                rotationZ(rotationProvider()),
                scale(x = scale.x, scale.y),
                translation(-super.position.toVec3())
            ).reduce(Mat4<Float>::times)
        }

    /**
     * Handles given input [event] at given [position].
     *
     * @return `true` if the event has been consumed by the transformed element.
     */
    override fun handleInputEvent(position: Vec2<Float>, event: Any?): Boolean =
        super.handleInputEvent(
            (transformationMatrix.inverse() * position.toVec4(w = 1f)).toVec2(),
            event
        )

    private inner class TransformationAtom(private val atom: HudAtom) : HudAtom by atom {

        override val modelMatrix: Mat4<Float>
            get() = transformationMatrix * atom.modelMatrix
    }
}
