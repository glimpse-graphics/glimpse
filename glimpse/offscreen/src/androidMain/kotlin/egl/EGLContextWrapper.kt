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

import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay
import javax.microedition.khronos.egl.EGLSurface
import javax.microedition.khronos.opengles.GL
import javax.microedition.khronos.opengles.GL10

/**
 * A wrapper for EGL context.
 */
class EGLContextWrapper private constructor(
    private val egl: EGL10,
    private val eglDisplay: EGLDisplay,
    private val eglConfig: EGLConfig,
    private val eglContext: EGLContext
) : AutoCloseable {

    /**
     * OpenGL interface using this EGL context.
     */
    val gl: GL = eglContext.gl as GL10

    /**
     * Returns a wrapper for offscreen EGL surface.
     */
    fun createOffscreenSurface(width: Int, height: Int): EGLOffscreenSurfaceWrapper {
        val attributes = intArrayOf(
            EGL10.EGL_WIDTH, width,
            EGL10.EGL_HEIGHT, height,
            EGL10.EGL_NONE
        )
        val eglSurface: EGLSurface? = egl.eglCreatePbufferSurface(eglDisplay, eglConfig, attributes)
        check(value = eglSurface != null && eglSurface != EGL10.EGL_NO_SURFACE) {
            "Could not create EGL surface"
        }
        check(egl.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)) {
            "Could not set EGL surface"
        }
        return EGLOffscreenSurfaceWrapper.create(egl, eglDisplay, eglContext, eglSurface)
    }

    /**
     * Destroys this EGL context.
     */
    override fun close() {
        egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT)
        check(egl.eglDestroyContext(eglDisplay, eglContext)) { "Could not destroy EGL context" }
    }

    companion object {
        internal fun create(
            egl: EGL10,
            eglDisplay: EGLDisplay,
            eglConfig: EGLConfig,
            eglContext: EGLContext
        ): EGLContextWrapper = EGLContextWrapper(egl, eglDisplay, eglConfig, eglContext)
    }
}
