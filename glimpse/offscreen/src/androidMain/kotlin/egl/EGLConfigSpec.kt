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

package graphics.glimpse.offscreen.egl

import android.opengl.EGL14
import javax.microedition.khronos.egl.EGL10

/**
 * EGL config specification.
 */
data class EGLConfigSpec(

    /**
     * Number of bits per color channel, i.e. each of the following: red, green, blue, alpha.
     */
    val colorChannelBits: Int,

    /**
     * Number of depth bits.
     */
    val depthBits: Int,

    /**
     * Number of stencil bits.
     */
    val stencilBits: Int
) {

    internal val attributes: IntArray by lazy {
        intArrayOf(
            EGL10.EGL_RED_SIZE, colorChannelBits,
            EGL10.EGL_GREEN_SIZE, colorChannelBits,
            EGL10.EGL_BLUE_SIZE, colorChannelBits,
            EGL10.EGL_ALPHA_SIZE, colorChannelBits,
            EGL10.EGL_DEPTH_SIZE, depthBits,
            EGL10.EGL_STENCIL_SIZE, stencilBits,
            EGL10.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
            EGL10.EGL_NONE
        )
    }

    internal fun findBestMatch(configs: List<EGLConfigWrapper>): EGLConfigWrapper =
        configs.filter { config ->
            config[EGL10.EGL_RED_SIZE] == colorChannelBits &&
                config[EGL10.EGL_GREEN_SIZE] == colorChannelBits &&
                config[EGL10.EGL_BLUE_SIZE] == colorChannelBits &&
                config[EGL10.EGL_ALPHA_SIZE] == colorChannelBits &&
                config[EGL10.EGL_DEPTH_SIZE] >= depthBits &&
                config[EGL10.EGL_STENCIL_SIZE] >= stencilBits
        }.maxByOrNull { config ->
            config[EGL10.EGL_DEPTH_SIZE]
        }.let { config ->
            checkNotNull(config) { "Could not find any matching EGL config" }
        }
}
