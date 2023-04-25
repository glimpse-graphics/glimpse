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

import graphics.glimpse.BlendingFactorFunction
import graphics.glimpse.ClearableBufferType
import graphics.glimpse.DepthTestFunction
import graphics.glimpse.FaceCullingMode
import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.GlimpseCallback
import graphics.glimpse.VSync
import graphics.glimpse.cameras.Camera
import graphics.glimpse.cameras.FreeCamera
import graphics.glimpse.framebuffers.Framebuffer
import graphics.glimpse.framebuffers.FramebufferAttachmentType
import graphics.glimpse.framebuffers.withFramebuffer
import graphics.glimpse.lenses.Lens
import graphics.glimpse.lenses.PerspectiveLens
import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.meshes.Mesh
import graphics.glimpse.shaders.Program
import graphics.glimpse.shaders.ProgramExecutor
import graphics.glimpse.textures.Texture
import graphics.glimpse.textures.TexturePresets
import graphics.glimpse.textures.TextureWrap
import graphics.glimpse.types.Angle
import graphics.glimpse.types.Vec2
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.rotationY
import graphics.glimpse.ui.compose.types.toVec3

/**
 * A [callback][GlimpseCallback] rendering rotating triangle.
 */
class TriangleCallback(
    private val resources: AppResources
) : GlimpseCallback {

    private val logger = GlimpseLogger.create(this)

    private lateinit var mesh: Mesh
    private lateinit var texture: Texture
    private lateinit var normalMap: Texture
    private lateinit var program: Program
    private lateinit var programExecutor: ProgramExecutor<TriangleShader>

    private lateinit var blurMesh: Mesh
    private lateinit var blurXProgram: Program
    private lateinit var blurXProgramExecutor: ProgramExecutor<BlurXShader>
    private lateinit var blurYProgram: Program
    private lateinit var blurYProgramExecutor: ProgramExecutor<BlurYShader>

    private var triangleFramebuffer: Framebuffer? = null
    private var blurXFramebuffer: Framebuffer? = null

    private var size = Vec2(x = 1f, y = 1f)

    private var lens: Lens<Float> = PerspectiveLens(fovY, aspect = 1f, NEAR, FAR)

    private val camera: Camera<Float> = FreeCamera(
        eye = Vec3(x = 0f, y = 0f, z = 2f),
        roll = Angle.nullAngle(),
        pitch = -Angle.rightAngle<Float>(),
        yaw = Angle.rightAngle()
    )

    /**
     * Initializes basic rendering parameters, mesh and program.
     */
    override fun onCreate(gl: GlimpseAdapter) {
        logger.debug(message = "onCreate")

        gl.glClearColor(resources.getThemeColors().background.toVec3())
        gl.glClearDepth(depth = 1f)
        gl.glDepthTest(DepthTestFunction.LESS_OR_EQUAL)
        gl.glCullFace(FaceCullingMode.DISABLED)
        gl.glEnableBlending()
        gl.glBlendingFunction(BlendingFactorFunction.SOURCE_ALPHA, BlendingFactorFunction.ONE_MINUS_SOURCE_ALPHA)
        gl.glVSync(VSync.ON)

        mesh = Mesh.Factory.newInstance(gl).createMesh(resources.getMeshData())
        val textures = Texture.Builder.getInstance(gl)
            .addTexture(resources.getTextureSource())
            .addTexture(resources.getNormalMapSource())
            .setTextureWrap(TextureWrap.CLAMP_TO_EDGE)
            .generateMipmaps()
            .build()
        texture = textures[0]
        normalMap = textures[1]
        program = TriangleProgramFactory(resources).createProgram(gl)
        programExecutor = program.createTriangleShaderProgramExecutor()

        blurMesh = Mesh.Factory.newInstance(gl).createMesh(resources.getBlurMeshData())
        blurXProgram = BlurProgramFactory(resources).createBlurXProgram(gl)
        blurXProgramExecutor = blurXProgram.createBlurXShaderProgramExecutor()
        blurYProgram = BlurProgramFactory(resources).createBlurYProgram(gl)
        blurYProgramExecutor = blurYProgram.createBlurYShaderProgramExecutor()

        triangleFramebuffer = createTriangleFramebuffer(gl)
        blurXFramebuffer = createBlurXFramebuffer(gl)
    }

    private fun createTriangleFramebuffer(gl: GlimpseAdapter): Framebuffer? = try {
        val width = size.x.toInt()
        val height = size.y.toInt()
        Framebuffer.Builder.getInstance(gl)
            .attachTexture(
                FramebufferAttachmentType.Color[0],
                Texture.createEmpty(gl, width, height, TexturePresets.rgba)
            )
            .attachTexture(
                FramebufferAttachmentType.Color[1],
                Texture.createEmpty(gl, width, height, TexturePresets.rgba)
            )
            .build()
    } catch (exception: IllegalStateException) {
        logger.error(message = "Error creating framebuffer", exception = exception)
        null
    }

    private fun createBlurXFramebuffer(gl: GlimpseAdapter): Framebuffer? = try {
        val width = size.x.toInt()
        val height = size.y.toInt()
        Framebuffer.Builder.getInstance(gl)
            .attachTexture(
                FramebufferAttachmentType.Color[0],
                Texture.createEmpty(gl, width, height, TexturePresets.rgba)
            )
            .build()
    } catch (exception: IllegalStateException) {
        logger.error(message = "Error creating framebuffer", exception = exception)
        null
    }

    /**
     * Updates viewport and lens.
     */
    override fun onResize(gl: GlimpseAdapter, x: Int, y: Int, width: Int, height: Int) {
        logger.debug(message = "onResize: ${width}x$height")

        gl.glViewport(width = width, height = height)
        lens = PerspectiveLens(fovY, aspect = width.toFloat() / height.toFloat(), NEAR, FAR)

        size = Vec2(x = width.toFloat(), y = height.toFloat())

        triangleFramebuffer?.dispose(gl)
        triangleFramebuffer = createTriangleFramebuffer(gl)
        blurXFramebuffer?.dispose(gl)
        blurXFramebuffer = createBlurXFramebuffer(gl)
    }

    /**
     * Renders the triangle.
     */
    override fun onRender(gl: GlimpseAdapter) {
        if (
            !::programExecutor.isInitialized ||
            !::blurXProgramExecutor.isInitialized ||
            !::blurYProgramExecutor.isInitialized
        ) {
            return
        }

        val imageFramebuffer = triangleFramebuffer
        val blurFramebuffer = blurXFramebuffer
        if (imageFramebuffer != null && blurFramebuffer != null) {
            gl.withFramebuffer(imageFramebuffer) {
                renderTriangle(this)
            }
            gl.withFramebuffer(blurFramebuffer) {
                applyBlurX(gl, imageFramebuffer)
            }
            applyBlurY(gl, imageFramebuffer, blurFramebuffer)
        } else {
            renderTriangle(gl)
        }
    }

    private fun renderTriangle(gl: GlimpseAdapter) {
        gl.glClear(ClearableBufferType.COLOR_BUFFER, ClearableBufferType.DEPTH_BUFFER)

        program.use(gl)

        val modelMatrix = rotationY(calculateRotationAngle())
        val params = TriangleShader(
            projectionMatrix = lens.projectionMatrix,
            viewMatrix = camera.viewMatrix,
            modelMatrix = modelMatrix,
            normalMatrix = modelMatrix.toMat3().inverse().transpose(),
            texture = texture,
            normalMap = normalMap
        )

        programExecutor.applyParams(gl, params)
        programExecutor.drawMesh(gl, mesh)
    }

    private fun calculateRotationAngle() =
        Angle.fromDeg(((getSystemTimeInMillis() / ROTATION_SPEED_FACTOR) % FULL_ANGLE_DEG).toFloat())

    private fun applyBlurX(gl: GlimpseAdapter, triangleFramebuffer: Framebuffer) {
        gl.glClear(ClearableBufferType.COLOR_BUFFER, ClearableBufferType.DEPTH_BUFFER)

        blurXProgram.use(gl)

        val blurXParams = BlurXShader(
            screenSize = size,
            image = requireNotNull(triangleFramebuffer.textures[FramebufferAttachmentType.Color[0]]),
            position = requireNotNull(triangleFramebuffer.textures[FramebufferAttachmentType.Color[1]])
        )

        blurXProgramExecutor.applyParams(gl, blurXParams)
        blurXProgramExecutor.drawMesh(gl, blurMesh)
    }

    private fun applyBlurY(gl: GlimpseAdapter, triangleFramebuffer: Framebuffer, blurXFramebuffer: Framebuffer) {
        gl.glClear(ClearableBufferType.COLOR_BUFFER, ClearableBufferType.DEPTH_BUFFER)

        blurYProgram.use(gl)

        val blurYParams = BlurYShader(
            screenSize = size,
            image = requireNotNull(blurXFramebuffer.textures[FramebufferAttachmentType.Color[0]]),
            position = requireNotNull(triangleFramebuffer.textures[FramebufferAttachmentType.Color[1]])
        )

        blurYProgramExecutor.applyParams(gl, blurYParams)
        blurYProgramExecutor.drawMesh(gl, blurMesh)
    }

    /**
     * Disposes the mesh and the program.
     */
    override fun onDestroy(gl: GlimpseAdapter) {
        logger.debug(message = "onDestroy")

        try {
            triangleFramebuffer?.dispose(gl)
            if (::mesh.isInitialized) mesh.dispose(gl)
            if (::programExecutor.isInitialized) programExecutor.dispose(gl)
            else if (::program.isInitialized) program.dispose(gl)
            if (::blurXProgramExecutor.isInitialized) blurXProgramExecutor.dispose(gl)
            else if (::blurXProgram.isInitialized) blurXProgram.dispose(gl)
            if (::blurYProgramExecutor.isInitialized) blurYProgramExecutor.dispose(gl)
            else if (::blurYProgram.isInitialized) blurYProgram.dispose(gl)
        } catch (expected: Exception) {
            logger.error(message = "Error disposing Glimpse", exception = expected)
        }
    }

    companion object {
        private val fovY = Angle.rightAngle<Float>()
        private const val NEAR = .1f
        private const val FAR = 10f

        private const val FULL_ANGLE_DEG = 360L
        private const val ROTATION_SPEED_FACTOR = 40L
    }
}
