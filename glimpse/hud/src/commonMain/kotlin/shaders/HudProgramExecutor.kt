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
