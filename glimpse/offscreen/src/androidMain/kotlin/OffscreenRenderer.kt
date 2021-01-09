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

package graphics.glimpse.offscreen

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.PixelFormat
import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.offscreen.egl.EGLConfigSpec
import graphics.glimpse.offscreen.egl.EGLContextWrapper
import graphics.glimpse.offscreen.egl.EGLWrapper

/**
 * Offscreen renderer. Implement this class to render an image without displaying it on screen.
 */
actual abstract class OffscreenRenderer(private val gl: GlimpseAdapter) {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    /**
     * Implement this property to define width of the rendered image.
     */
    actual abstract val width: Int

    /**
     * Implement this property to define height of the rendered image.
     */
    actual abstract val height: Int

    /**
     * Renders the image offscreen.
     */
    actual fun render() {
        try {
            createContext().use { context ->
                context.createOffscreenSurface(width, height).use {
                    doRender(gl)
                }
            }
        } catch (expected: RuntimeException) {
            logger.error(message = "Offscreen rendering failed", expected)
        }
    }

    private fun createContext(): EGLContextWrapper {
        val display = EGLWrapper.getInstance().getDefaultDisplay()
        display.initialize()
        val config = display.findConfig(configSpec)
        return config.createContext()
    }

    /**
     * Implement this method to render an image.
     */
    protected actual abstract fun doRender(gl: GlimpseAdapter)

    /**
     * Returns pixels for rendered image.
     */
    actual fun readPixels(format: PixelFormat): ByteArray =
        gl.glReadPixels(x = 0, y = 0, width, height, format)

    companion object {
        private const val COLOR_CHANNEL_BITS = 8
        private const val DEPTH_BITS = 16
        private const val STENCIL_BITS = 8

        private val configSpec = EGLConfigSpec(
            colorChannelBits = COLOR_CHANNEL_BITS,
            depthBits = DEPTH_BITS,
            stencilBits = STENCIL_BITS
        )
    }
}
