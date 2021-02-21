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

package graphics.glimpse.offscreen.gl

import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLProfile

/**
 * OpenGL capabilities specification.
 */
data class GLCapabilitiesSpec(

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

    internal fun createOffscreenCapabilities(profile: GLProfile): GLCapabilities {
        val capabilities = GLCapabilities(profile)
        capabilities.hardwareAccelerated = true
        capabilities.doubleBuffered = false
        capabilities.alphaBits = colorChannelBits
        capabilities.redBits = colorChannelBits
        capabilities.blueBits = colorChannelBits
        capabilities.greenBits = colorChannelBits
        capabilities.depthBits = depthBits
        capabilities.stencilBits = stencilBits
        capabilities.isOnscreen = false
        return capabilities
    }
}
