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

/**
 * Offscreen multi-renderer. Implement this class to render multiplse images without displaying
 * them on screen.
 */
expect abstract class OffscreenMultiRenderer : OffscreenRenderer {

    /**
     * Implement this property to define number of images to render.
     */
    protected abstract val imagesCount: Int

    /**
     * Render all images in a loop.
     */
    final override fun doRender(gl: GlimpseAdapter)

    /**
     * Implement this method to initialize [OpenGL adapter][gl] before rendering first image.
     */
    protected abstract fun onCreate(gl: GlimpseAdapter)

    /**
     * Implement this method to render an image at a given [index].
     */
    protected abstract fun onRender(gl: GlimpseAdapter, index: Int)

    /**
     * Implement this method to process image [pixels] at a given [index].
     */
    protected abstract fun onImage(pixels: ByteArray, index: Int)

    /**
     * Implement this method to dispose [OpenGL adapter][gl] after rendering last image.
     */
    protected abstract fun onDestroy(gl: GlimpseAdapter)
}
