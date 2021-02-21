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

import com.jogamp.opengl.DefaultGLCapabilitiesChooser
import com.jogamp.opengl.GL2ES2
import com.jogamp.opengl.GLDrawableFactory
import com.jogamp.opengl.GLOffscreenAutoDrawable

/**
 * Offscreen OpenGL drawable wrapper.
 */
class GLOffscreenAutoDrawableWrapper private constructor(
    private val drawable: GLOffscreenAutoDrawable
) : AutoCloseable {

    /**
     * OpenGL interface for this drawable wrapper.
     */
    val gl: GL2ES2 by lazy {
        if (drawable.gl.isGL2ES2) drawable.gl.gL2ES2
        else throw IllegalStateException("OpenGL does not conform to GL2ES2 profile.")
    }

    /**
     * Initializes drawable contained in this wrapper.
     */
    fun initialize() {
        drawable.display()
        drawable.context.makeCurrent()
    }

    /**
     * Destroys drawable contained in this wrapper.
     */
    override fun close() {
        drawable.context.destroy()
        drawable.destroy()
    }

    companion object {
        internal fun create(
            profile: GLProfileWrapper,
            capabilitiesSpec: GLCapabilitiesSpec,
            width: Int,
            height: Int
        ): GLOffscreenAutoDrawableWrapper {
            val capabilities = capabilitiesSpec.createOffscreenCapabilities(profile.profile)
            val factory = GLDrawableFactory.getFactory(profile.profile)

            val drawable: GLOffscreenAutoDrawable = factory.createOffscreenAutoDrawable(
                factory.defaultDevice,
                capabilities,
                DefaultGLCapabilitiesChooser(),
                width,
                height
            )

            return GLOffscreenAutoDrawableWrapper(drawable)
        }
    }
}
