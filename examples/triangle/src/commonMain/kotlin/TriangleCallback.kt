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

import graphics.glimpse.ClearableBufferType
import graphics.glimpse.DepthTestFunction
import graphics.glimpse.DrawingMode
import graphics.glimpse.FaceCullingMode
import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.GlimpseCallback
import graphics.glimpse.cameras.Camera
import graphics.glimpse.cameras.FreeCamera
import graphics.glimpse.lenses.Lens
import graphics.glimpse.lenses.PerspectiveLens
import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.meshes.Mesh
import graphics.glimpse.shaders.Program
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.rotationY

/**
 * A [callback][GlimpseCallback] rendering rotating triangle.
 */
class TriangleCallback(
    private val meshFactory: TriangleMeshFactory,
    private val programFactory: TriangleProgramFactory
) : GlimpseCallback {

    private val logger = GlimpseLogger.create(this)

    private lateinit var mesh: Mesh
    private lateinit var program: Program

    private var lens: Lens = PerspectiveLens(fovY, aspect = 1f, NEAR, FAR)

    private val camera: Camera = FreeCamera(
        eye = Vec3(x = 0f, y = 0f, z = 2f),
        roll = Angle.nullAngle,
        pitch = -Angle.rightAngle,
        yaw = Angle.rightAngle
    )

    /**
     * Initializes basic rendering parameters, mesh and program.
     */
    override fun onCreate(gl: GlimpseAdapter) {
        logger.debug(message = "onCreate")

        gl.glClearColor(Vec3(x = 0.9f, y = 0.9f, z = 0.9f))
        gl.glClearDepth(depth = 1f)
        gl.glDepthTest(DepthTestFunction.LESS_OR_EQUAL)
        gl.glCullFace(FaceCullingMode.DISABLED)

        mesh = meshFactory.createTriangleMesh(gl)
        program = programFactory.createProgram(gl)
    }

    /**
     * Updates viewport and lens.
     */
    override fun onResize(gl: GlimpseAdapter, x: Int, y: Int, width: Int, height: Int) {
        logger.debug(message = "onResize: $width×$height")

        gl.glViewport(width = width, height = height)
        lens = PerspectiveLens(fovY, aspect = width.toFloat() / height.toFloat(), NEAR, FAR)
    }

    /**
     * Renders the triangle.
     */
    override fun onRender(gl: GlimpseAdapter) {
        gl.glClear(ClearableBufferType.COLOR_BUFFER, ClearableBufferType.DEPTH_BUFFER)

        if (!::program.isInitialized) return

        program.use(gl)

        val modelMatrixLocation = gl.glGetUniformLocation(program.handle, UNIFORM_MODEL_MATRIX)
        val viewMatrixLocation = gl.glGetUniformLocation(program.handle, UNIFORM_VIEW_MATRIX)
        val projMatrixLocation = gl.glGetUniformLocation(program.handle, UNIFORM_PROJECTION_MATRIX)
        val aPosLocation = gl.glGetAttributeLocation(program.handle, ATTRIBUTE_POSITION)
        val aTexCoordsLocation = gl.glGetAttributeLocation(program.handle, ATTRIBUTE_TEX_COORDS)

        gl.glUniform(modelMatrixLocation, rotationY(calculateRotationAngle()))
        gl.glUniform(viewMatrixLocation, camera.viewMatrix)
        gl.glUniform(projMatrixLocation, lens.projectionMatrix)

        mesh.useBuffer(gl = gl, bufferIndex = 0)
        gl.glEnableVertexAttribArray(aPosLocation)
        gl.glVertexAttribPointer(aPosLocation, vectorSize = 3)

        mesh.useBuffer(gl = gl, bufferIndex = 1)
        gl.glEnableVertexAttribArray(aTexCoordsLocation)
        gl.glVertexAttribPointer(aTexCoordsLocation, vectorSize = 2)

        gl.glDrawArrays(mode = DrawingMode.TRIANGLES, count = mesh.vertexCount)

        gl.glDisableVertexAttribArray(location = aPosLocation)
        gl.glDisableVertexAttribArray(location = aTexCoordsLocation)
    }

    private fun calculateRotationAngle() =
        Angle.fromDeg(((getSystemTimeInMillis() / ROTATION_SPEED_FACTOR) % FULL_ANGLE_DEG).toFloat())

    /**
     * Disposes the mesh and the program.
     */
    override fun onDestroy(gl: GlimpseAdapter) {
        logger.debug(message = "onDestroy")

        try {
            if (::mesh.isInitialized) mesh.dispose(gl)
            if (::program.isInitialized) program.dispose(gl)
        } catch (expected: Exception) {
            logger.error(message = "Error disposing Glimpse", exception = expected)
        }
    }

    companion object {
        private val fovY = Angle.rightAngle
        private const val NEAR = .1f
        private const val FAR = 10f

        private const val FULL_ANGLE_DEG = 360L
        private const val ROTATION_SPEED_FACTOR = 5L

        private const val UNIFORM_MODEL_MATRIX = "uModelMatrix"
        private const val UNIFORM_VIEW_MATRIX = "uViewMatrix"
        private const val UNIFORM_PROJECTION_MATRIX = "uProjMatrix"

        private const val ATTRIBUTE_POSITION = "aPos"
        private const val ATTRIBUTE_TEX_COORDS = "aTexCoords"
    }
}
