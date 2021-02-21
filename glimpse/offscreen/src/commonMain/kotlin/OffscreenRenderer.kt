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

package graphics.glimpse.offscreen

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.PixelFormat

/**
 * Offscreen renderer. Implement this class to render an image without displaying it on screen.
 */
abstract class OffscreenRenderer {

    /**
     * Implement this property to define width of the rendered image.
     */
    abstract val width: Int

    /**
     * Implement this property to define height of the rendered image.
     */
    abstract val height: Int

    /**
     * Renders the image offscreen.
     */
    fun render() {
        OffscreenActionExecutor.getInstance(width, height)
            .execute(this::doRender)
    }

    /**
     * Implement this method to render an image.
     */
    protected abstract fun doRender(gl: GlimpseAdapter)

    /**
     * Returns pixels for rendered image.
     */
    protected fun readPixels(
        gl: GlimpseAdapter,
        format: PixelFormat = PixelFormat.RGBA
    ): ByteArray =
        gl.glReadPixels(x = 0, y = 0, width, height, format)
}
