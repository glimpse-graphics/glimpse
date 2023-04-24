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

package graphics.glimpse.offscreen.egl

import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLDisplay

/**
 * A wrapper for EGL display.
 */
class EGLDisplayWrapper private constructor(
    private val egl: EGL10,
    private val eglDisplay: EGLDisplay
) {

    /**
     * Initializes EGL display and returns EGL version.
     */
    fun initialize(): String {
        val eglVersion = IntArray(size = 2)
        check(value = egl.eglInitialize(eglDisplay, eglVersion)) { "Failed to initialize EGL" }
        return eglVersion.joinToString(separator = VERSION_SEPARATOR)
    }

    /**
     * Returns a wrapper for EGL config matching a given [specification][configSpec].
     *
     * If no matching EGL config was found, throws [IllegalStateException].
     */
    fun findConfig(configSpec: EGLConfigSpec): EGLConfigWrapper {
        val numConfigs = IntArray(size = 1)
        egl.eglChooseConfig(eglDisplay, configSpec.attributes, null, 0, numConfigs)
        val configsCount = numConfigs.first()
        check(value = configsCount > 0) { "No EGL configs found" }
        val eglConfigs = Array<EGLConfig?>(size = configsCount) { null }
        egl.eglChooseConfig(eglDisplay, configSpec.attributes, eglConfigs, configsCount, numConfigs)
        return configSpec.findBestMatch(
            eglConfigs.filterNotNull().map { eglConfig ->
                EGLConfigWrapper.create(egl, eglDisplay, eglConfig)
            }
        )
    }

    companion object {

        private const val VERSION_SEPARATOR = "."

        internal fun getDisplay(
            egl: EGL10,
            nativeDisplay: Any? = EGL10.EGL_DEFAULT_DISPLAY
        ): EGLDisplayWrapper {
            val eglDisplay = egl.eglGetDisplay(nativeDisplay)
            check(value = eglDisplay !== EGL10.EGL_NO_DISPLAY) { "Failed to get EGL display" }
            return EGLDisplayWrapper(egl, eglDisplay)
        }
    }
}
