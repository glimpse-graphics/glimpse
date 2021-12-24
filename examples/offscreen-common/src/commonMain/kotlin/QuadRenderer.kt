/*
 * Copyright 2020-2021 Slawomir Czerwinski
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

package graphics.glimpse.examples.offscreen

import graphics.glimpse.ClearableBufferType
import graphics.glimpse.DepthTestFunction
import graphics.glimpse.FaceCullingMode
import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.cameras.Camera
import graphics.glimpse.cameras.FreeCamera
import graphics.glimpse.lenses.Lens
import graphics.glimpse.lenses.PerspectiveLens
import graphics.glimpse.meshes.Mesh
import graphics.glimpse.offscreen.OffscreenRenderer
import graphics.glimpse.shaders.Program
import graphics.glimpse.shaders.Shader
import graphics.glimpse.shaders.ShaderType
import graphics.glimpse.textures.Texture
import graphics.glimpse.textures.TextureMagFilter
import graphics.glimpse.textures.TextureMinFilter
import graphics.glimpse.textures.TextureType
import graphics.glimpse.textures.TextureWrap
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Mat3
import graphics.glimpse.types.Mat4
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4

/**
 * Offscreen renderer for a quad.
 */
class QuadRenderer(
    private val resources: AppResources,
    private val onRenderedListener: OnRenderedListener
) : OffscreenRenderer() {

    override val width: Int = IMAGE_SIZE
    override val height: Int = IMAGE_SIZE

    private val lens: Lens = PerspectiveLens(fovY, aspect = 1f, NEAR, FAR)

    private val camera: Camera = FreeCamera(
        eye = Vec3(x = 0f, y = 0f, z = 2f),
        roll = Angle.nullAngle,
        pitch = -Angle.rightAngle,
        yaw = Angle.rightAngle
    )

    /**
     * Renders a quad.
     */
    override fun doRender(gl: GlimpseAdapter) {
        gl.glClearColor(Vec4(x = 0f, y = 0f, z = 0f, w = 0f))
        gl.glClearDepth(depth = 1f)
        gl.glDepthTest(DepthTestFunction.LESS_OR_EQUAL)
        gl.glCullFace(FaceCullingMode.BACK)

        gl.glTexParameterWrap(TextureType.TEXTURE_2D, TextureWrap.REPEAT, TextureWrap.REPEAT)
        gl.glTexParameterFilter(
            TextureType.TEXTURE_2D,
            TextureMinFilter.LINEAR_MIPMAP_LINEAR,
            TextureMagFilter.LINEAR
        )

        gl.glClear(ClearableBufferType.COLOR_BUFFER, ClearableBufferType.DEPTH_BUFFER)

        val mesh = Mesh.Factory.newInstance(gl).createMesh(resources.getMeshData())

        val textures = Texture.Builder.getInstance(gl)
            .addTexture(resources.getTextureSource())
            .addTexture(resources.getNormalMapSource())
            .generateMipmaps()
            .build()

        val shaderFactory = Shader.Factory.newInstance(gl)
        val vertexShader = shaderFactory.createShader(
            type = ShaderType.VERTEX_SHADER,
            source = resources.getShaderSource(ShaderType.VERTEX_SHADER)
        )
        val fragmentShader = shaderFactory.createShader(
            type = ShaderType.FRAGMENT_SHADER,
            source = resources.getShaderSource(ShaderType.FRAGMENT_SHADER)
        )
        val program = Program.Builder.newInstance(gl)
            .withVertexShader(vertexShader)
            .withFragmentShader(fragmentShader)
            .build()
        val programExecutor = program.createQuadShaderProgramExecutor()

        program.use(gl)

        val params = QuadShader(
            projectionMatrix = lens.projectionMatrix,
            viewMatrix = camera.viewMatrix,
            modelMatrix = Mat4.identity,
            normalMatrix = Mat3.identity,
            texture = textures[0],
            normalMap = textures[1]
        )

        programExecutor.applyParams(gl, params)
        programExecutor.drawMesh(gl, mesh)

        onRenderedListener.onRendered(gl)

        mesh.dispose(gl)
        programExecutor.dispose(gl)
    }

    /**
     * A listener for quad rendered event.
     */
    fun interface OnRenderedListener {

        /**
         * Implement this method to handle the result of quad rendering.
         */
        fun onRendered(gl: GlimpseAdapter)
    }

    companion object {

        /**
         * Rendered quad image size in pixels.
         */
        const val IMAGE_SIZE = 600

        private val fovY = Angle.rightAngle
        private const val NEAR = .1f
        private const val FAR = 10f
    }
}
