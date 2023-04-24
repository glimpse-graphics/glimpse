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

package graphics.glimpse.hud.shaders

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.meshes.Mesh
import graphics.glimpse.shaders.BaseProgramExecutor
import graphics.glimpse.shaders.Program

internal class HudProgramExecutor(
    program: Program,
) : BaseProgramExecutor<Hud>(program) {

    override fun applyParams(gl: GlimpseAdapter, shaderParams: Hud) {
        glUniform(gl, NAME_MVP_MATRIX, shaderParams.mvpMatrix)

        shaderParams.texture.useAtIndex(gl, textureIndex = 0)
        glUniform(gl, NAME_TEXTURE, value = 0)
    }

    override fun drawMesh(gl: GlimpseAdapter, mesh: Mesh) {
        val aPosLocation = getAttributeLocation(gl, NAME_POSITIONS)
        mesh.useBuffer(gl, graphics.glimpse.shaders.annotations.AttributeRole.POSITIONS.ordinal)
        gl.glEnableVertexAttribArray(aPosLocation)
        glVertexAttribPointer(gl, aPosLocation, vectorSize = 2)

        val aTexCoordsLocation = getAttributeLocation(gl, NAME_TEX_COORDS)
        mesh.useBuffer(gl, graphics.glimpse.shaders.annotations.AttributeRole.TEX_COORDS.ordinal)
        gl.glEnableVertexAttribArray(aTexCoordsLocation)
        glVertexAttribPointer(gl, aTexCoordsLocation, vectorSize = 2)

        mesh.draw(gl)

        gl.glDisableVertexAttribArray(aPosLocation)
        gl.glDisableVertexAttribArray(aTexCoordsLocation)
    }

    companion object {
        private const val NAME_MVP_MATRIX = "uMVPMat"
        private const val NAME_TEXTURE = "uTex"
        private const val NAME_POSITIONS = "aPos"
        private const val NAME_TEX_COORDS = "aTexCoords"
    }
}
