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

import com.jogamp.opengl.GLProfile

/**
 * OpenGL profile wrapper.
 */
class GLProfileWrapper private constructor(internal val profile: GLProfile) {

    /**
     * Creates a wrapper with an offscreen OpenGL drawable.
     */
    fun createOffscreenAutoDrawable(
        capabilitiesSpec: GLCapabilitiesSpec,
        width: Int,
        height: Int
    ): GLOffscreenAutoDrawableWrapper =
        GLOffscreenAutoDrawableWrapper.create(profile = this, capabilitiesSpec, width, height)

    companion object {

        /**
         * Returns an instance of OpenGL profile wrapper containing GL2ES2 profile implementation.
         */
        fun getInstance(): GLProfileWrapper = GLProfileWrapper(GLProfile.getGL2ES2())
    }
}
