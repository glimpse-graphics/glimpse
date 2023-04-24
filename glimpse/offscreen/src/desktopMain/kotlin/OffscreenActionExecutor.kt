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

package graphics.glimpse.offscreen

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.offscreen.gl.GLCapabilitiesSpec
import graphics.glimpse.offscreen.gl.GLProfileWrapper

/**
 * Executor of offscreen rendering actions.
 */
actual class OffscreenActionExecutor(private val width: Int, private val height: Int) {

    /**
     * Executes a given [action] offscreen.
     */
    actual fun execute(action: OffscreenAction) {
        GLProfileWrapper.getInstance()
            .createOffscreenAutoDrawable(capabilitiesSpec, width, height)
            .use { drawable ->
                drawable.initialize()
                action.execute(GlimpseAdapter(drawable.gl))
            }
    }

    actual companion object {

        private const val COLOR_CHANNEL_BITS = 8
        private const val DEPTH_BITS = 16
        private const val STENCIL_BITS = 8

        private val capabilitiesSpec = GLCapabilitiesSpec(
            colorChannelBits = COLOR_CHANNEL_BITS,
            depthBits = DEPTH_BITS,
            stencilBits = STENCIL_BITS
        )

        /**
         * Returns a new instance of [OffscreenActionExecutor] using frame buffer with a given
         * [width] and [height].
         */
        actual fun getInstance(width: Int, height: Int): OffscreenActionExecutor =
            OffscreenActionExecutor(width, height)
    }
}
