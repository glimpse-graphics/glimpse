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
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLDisplay

/**
 * A wrapper for EGL config.
 */
class EGLConfigWrapper private constructor(
    private val egl: EGL10,
    private val eglDisplay: EGLDisplay,
    private val eglConfig: EGLConfig
) {

    @Suppress("NOTHING_TO_INLINE")
    internal inline operator fun get(attribute: Int): Int = getAttribute(attribute)

    internal fun getAttribute(attribute: Int): Int {
        check(egl.eglGetConfigAttrib(eglDisplay, eglConfig, attribute, attributeValue)) {
            "Failed to get EGL config attribute: $attribute"
        }
        return attributeValue.first()
    }

    /**
     * Returns a wrapper for EGL context created using this EGL config.
     */
    fun createContext(): EGLContextWrapper {
        val attributes = intArrayOf(
            EGL14.EGL_CONTEXT_CLIENT_VERSION, EGL_CONTEXT_CLIENT_VERSION,
            EGL10.EGL_NONE
        )
        val eglContext = egl.eglCreateContext(eglDisplay, eglConfig, EGL10.EGL_NO_CONTEXT, attributes)
        check(value = eglContext != null && eglContext !== EGL10.EGL_NO_CONTEXT) {
            "Could not create EGL context"
        }
        return EGLContextWrapper.create(egl, eglDisplay, eglConfig, eglContext)
    }

    override fun toString(): String = """
        |EGLConfigWrapper(
        |   EGL_RED_SIZE=${this[EGL10.EGL_RED_SIZE]}
        |   EGL_GREEN_SIZE=${this[EGL10.EGL_GREEN_SIZE]}
        |   EGL_BLUE_SIZE=${this[EGL10.EGL_BLUE_SIZE]}
        |   EGL_ALPHA_SIZE=${this[EGL10.EGL_ALPHA_SIZE]}
        |   EGL_DEPTH_SIZE=${this[EGL10.EGL_DEPTH_SIZE]}
        |   EGL_STENCIL_SIZE=${this[EGL10.EGL_STENCIL_SIZE]}
        |   EGL_RENDERABLE_TYPE=${this[EGL10.EGL_RENDERABLE_TYPE].toString(radix = 2)}
        |)
        |""".trimMargin()

    companion object {
        private const val EGL_CONTEXT_CLIENT_VERSION: Int = 2

        private val attributeValue = IntArray(size = 1)

        internal fun create(
            egl: EGL10,
            eglDisplay: EGLDisplay,
            eglConfig: EGLConfig
        ): EGLConfigWrapper = EGLConfigWrapper(egl, eglDisplay, eglConfig)
    }
}
