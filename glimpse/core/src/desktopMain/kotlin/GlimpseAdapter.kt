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

package graphics.glimpse

import com.jogamp.opengl.GLES2
import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.logging.GlimpseLoggerFactory
import graphics.glimpse.types.Vec3
import graphics.glimpse.types.Vec4

/**
 * Glimpse OpenGL adapter for the given [GL ES 2.0][gles].
 */
actual class GlimpseAdapter(private val gles: GLES2) {

    /**
     * Glimpse logger.
     */
    actual val logger: GlimpseLogger = GlimpseLogger.create(this)

    /**
     * Returns a boolean value for the given integer [value].
     */
    actual fun booleanOf(value: Int): Boolean = value != GLES2.GL_FALSE

    /**
     * Sets clear values for color buffers to [color] with alpha channel set to fully opaque.
     */
    actual fun glClearColor(color: Vec3) {
        gles.glClearColor(color.r, color.g, color.b, 1f)
    }

    /**
     * Sets clear values for color buffers to [color].
     */
    actual fun glClearColor(color: Vec4) {
        gles.glClearColor(color.r, color.g, color.b, color.a)
    }

    /**
     * Sets clear value for the depth buffer to [depth].
     */
    actual fun glClearDepth(depth: Float) {
        gles.glClearDepthf(depth)
    }

    /**
     * Sets the given [depthTestFunction].
     */
    actual fun glDepthTest(depthTestFunction: DepthTestFunction) {
        if (depthTestFunction.isDepthTestEnabled) {
            gles.glEnable(GLES2.GL_DEPTH_TEST)
        } else {
            gles.glDisable(GLES2.GL_DEPTH_TEST)
        }
        depthTestFunction.toInt()?.let { depthFunc -> gles.glDepthFunc(depthFunc) }
    }

    private fun DepthTestFunction.toInt(): Int? = when (this) {
        DepthTestFunction.DISABLED -> null
        DepthTestFunction.NEVER -> GLES2.GL_NEVER
        DepthTestFunction.LESS -> GLES2.GL_LESS
        DepthTestFunction.EQUAL -> GLES2.GL_EQUAL
        DepthTestFunction.LESS_OR_EQUAL -> GLES2.GL_LEQUAL
        DepthTestFunction.GREATER -> GLES2.GL_GREATER
        DepthTestFunction.NOT_EQUAL -> GLES2.GL_NOTEQUAL
        DepthTestFunction.GREATER_OR_EQUAL -> GLES2.GL_GEQUAL
        DepthTestFunction.ALWAYS -> GLES2.GL_ALWAYS
    }

    /**
     * Sets facets to be culled to [faceCullingMode].
     */
    actual fun glCullFace(faceCullingMode: FaceCullingMode) {
        if (faceCullingMode.isFaceCullingEnabled) {
            gles.glEnable(GLES2.GL_CULL_FACE)
        } else {
            gles.glDisable(GLES2.GL_CULL_FACE)
        }
        faceCullingMode.toInt()?.let { depthFunc -> gles.glDepthFunc(depthFunc) }
    }

    private fun FaceCullingMode.toInt(): Int? = when (this) {
        FaceCullingMode.DISABLED -> null
        FaceCullingMode.FRONT -> GLES2.GL_FRONT
        FaceCullingMode.BACK -> GLES2.GL_BACK
        FaceCullingMode.FRONT_AND_BACK -> GLES2.GL_FRONT_AND_BACK
    }

    /**
     * Sets the viewport to have its bottom-left corner at ([x], [y]),
     * and a given [width] and [height].
     */
    actual fun glViewport(x: Int, y: Int, width: Int, height: Int) {
        gles.glViewport(x, y, width, height)
    }

    /**
     * Clears given buffers to the predefined clear values.
     */
    actual fun glClear(vararg buffers: ClearableBufferType) {
        if (buffers.isEmpty()) return
        gles.glClear(
            buffers.map { clearTarget -> clearTarget.toInt() }
                .reduce { a, b -> a or b }
        )
    }

    private fun ClearableBufferType.toInt(): Int = when (this) {
        ClearableBufferType.COLOR_BUFFER -> GLES2.GL_COLOR_BUFFER_BIT
        ClearableBufferType.DEPTH_BUFFER -> GLES2.GL_DEPTH_BUFFER_BIT
        ClearableBufferType.STENCIL_BUFFER -> GLES2.GL_STENCIL_BUFFER_BIT
    }
}
