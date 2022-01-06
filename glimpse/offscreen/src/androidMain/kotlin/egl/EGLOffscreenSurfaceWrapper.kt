/*
 * Copyright 2020-2022 Slawomir Czerwinski
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
 */

package graphics.glimpse.offscreen.egl

import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay
import javax.microedition.khronos.egl.EGLSurface

/**
 * A wrapper for offscreen EGL surface.
 */
class EGLOffscreenSurfaceWrapper(
    private val egl: EGL10,
    private val eglDisplay: EGLDisplay,
    private val eglContext: EGLContext,
    private val eglSurface: EGLSurface
) : AutoCloseable {

    /**
     * Destroys this EGL surface.
     */
    override fun close() {
        egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, eglContext)
        check(egl.eglDestroySurface(eglDisplay, eglSurface)) { "Could not destroy EGL surface" }
    }

    companion object {
        internal fun create(
            egl: EGL10,
            eglDisplay: EGLDisplay,
            eglContext: EGLContext,
            eglSurface: EGLSurface
        ): EGLOffscreenSurfaceWrapper =
            EGLOffscreenSurfaceWrapper(egl, eglDisplay, eglContext, eglSurface)
    }
}
