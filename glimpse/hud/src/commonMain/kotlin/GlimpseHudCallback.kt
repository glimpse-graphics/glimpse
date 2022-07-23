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

package graphics.glimpse.hud

import graphics.glimpse.BlendingFactorFunction
import graphics.glimpse.DepthTestFunction
import graphics.glimpse.DrawingMode
import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.GlimpseCallback
import graphics.glimpse.buffers.Buffer
import graphics.glimpse.buffers.toFloatBufferData
import graphics.glimpse.cameras.Camera
import graphics.glimpse.cameras.FreeCamera
import graphics.glimpse.hud.shaders.Hud
import graphics.glimpse.hud.shaders.HudShaderSourcesProvider
import graphics.glimpse.hud.shaders.HudProgramExecutor
import graphics.glimpse.lenses.OrthographicLens
import graphics.glimpse.meshes.Mesh
import graphics.glimpse.shaders.Program
import graphics.glimpse.shaders.Shader
import graphics.glimpse.shaders.ShaderType
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3

/**
 * Glimpse callback for HUD.
 *
 * The recommended way of using this callback is delegation.
 */
class GlimpseHudCallback(

    /**
     * Elements of this HUD.
     */
    private val elements: List<HudElement>,

    /**
     * Provider of HUD shader sources.
     */
    private val hudShaderSourcesProvider: HudShaderSourcesProvider = HudShaderSourcesProvider.DEFAULT
) : GlimpseCallback {

    private lateinit var quadMesh: Mesh
    private lateinit var programExecutor: HudProgramExecutor

    private var lens: OrthographicLens =
        OrthographicLens(left = 0f, right = 1f, bottom = 1f, top = 0f, near = -1f, far = 1f)

    /**
     * Initializes this HUD.
     *
     * Disables depth test, configures alpha blending, creates program executor and mesh.
     */
    override fun onCreate(gl: GlimpseAdapter) {
        gl.glDepthTest(DepthTestFunction.DISABLED)

        gl.glEnableBlending()
        gl.glBlendingFunction(BlendingFactorFunction.SOURCE_ALPHA, BlendingFactorFunction.ONE_MINUS_SOURCE_ALPHA)

        createQuadMesh(gl)
        createHudProgramExecutor(gl)
    }

    private fun createQuadMesh(gl: GlimpseAdapter) {
        val buffer = Buffer.Factory.newInstance(gl)
            .createArrayBuffers(quadData.toFloatBufferData())
            .first()

        quadMesh = object : Mesh {

            override val buffers: List<Buffer> = listOf(buffer, buffer)

            override val vertexCount: Int = QUAD_VERTICES

            override fun useBuffer(gl: GlimpseAdapter, bufferIndex: Int) {
                buffers[bufferIndex].use(gl)
            }

            override fun draw(gl: GlimpseAdapter) {
                gl.glDrawArrays(DrawingMode.TRIANGLE_FAN, vertexCount)
            }

            override fun dispose(gl: GlimpseAdapter) {
                buffer.dispose(gl)
            }
        }
    }

    private fun createHudProgramExecutor(gl: GlimpseAdapter) {
        val vertexShaderSource = hudShaderSourcesProvider.getShaderSource(ShaderType.VERTEX_SHADER)
        val fragmentShaderSource = hudShaderSourcesProvider.getShaderSource(ShaderType.FRAGMENT_SHADER)

        val shaderFactory = Shader.Factory.newInstance(gl)
        val program = Program.Builder.newInstance(gl)
            .withVertexShader(shaderFactory.createShader(ShaderType.VERTEX_SHADER, vertexShaderSource))
            .withFragmentShader(shaderFactory.createShader(ShaderType.FRAGMENT_SHADER, fragmentShaderSource))
            .build()

        programExecutor = HudProgramExecutor(program)
    }

    /**
     * Recalculates orthographic lens of this HUD.
     */
    override fun onResize(gl: GlimpseAdapter, x: Int, y: Int, width: Int, height: Int) {
        lens = lens.copy(
            left = x.toFloat(),
            right = width.toFloat(),
            bottom = height.toFloat(),
            top = y.toFloat()
        )
    }

    /**
     * Renders this HUD.
     *
     * Before calling this method, make sure depth test is disabled.
     */
    override fun onRender(gl: GlimpseAdapter) {
        for (atom in elements.flatMap(HudElement::atoms)) {
            val shaderParams = Hud(
                mvpMatrix = lens.projectionMatrix * camera.viewMatrix * atom.modelMatrix,
                texture = atom.texture
            )
            with(programExecutor) {
                useProgram(gl)
                applyParams(gl, shaderParams)
                drawMesh(gl, quadMesh)
            }
        }
    }

    /**
     * Disposes program executor and mesh related to this HUD.
     */
    override fun onDestroy(gl: GlimpseAdapter) {
        if (::programExecutor.isInitialized) {
            programExecutor.dispose(gl)
        }
        if (::quadMesh.isInitialized) {
            quadMesh.dispose(gl)
        }
    }

    /**
     * Dispatches given input [event] at given [position] to this HUD's [elements].
     */
    fun onInputEvent(position: Vec2, event: Any?) {
        for (element in elements.reversed()) {
            if (position - element.position in element.boundingBox) {
                if (element.handleInputEvent(position, event)) break
            }
        }
    }

    companion object {

        private const val QUAD_VERTICES = 4

        private val quadData = listOf(0f, 0f, 0f, 1f, 1f, 1f, 1f, 0f)

        private val camera: Camera =
            FreeCamera(eye = Vec3.nullVector, roll = Angle.nullAngle, pitch = -Angle.rightAngle, yaw = Angle.rightAngle)
    }
}
