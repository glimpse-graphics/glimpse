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

package graphics.glimpse.ui

import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.awt.GLJPanel
import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.GlimpseCallback

/**
 * An implementation of [GLJPanel] that uses [Glimpse OpenGL adapter][GlimpseAdapter]
 * and [callback interface][GlimpseCallback] for rendering.
 *
 * @param fixedScale If `true`, sets surface scale fixed at 1.
 */
class GlimpsePanel(fixedScale: Boolean = false) : GLJPanel(GLCapabilitiesFactory.create()), GlimpseComponent {

    private lateinit var eventListener: GlimpseEventListener

    init {
        if (fixedScale) {
            setSurfaceScale(floatArrayOf(1f, 1f))
        }
    }

    /**
     * Sets [callback] to be used for rendering.
     */
    override fun setCallback(callback: GlimpseCallback) {
        check(!::eventListener.isInitialized) { "GlimpseEventListener already initialized" }
        eventListener = GlimpseEventListener(callback)
        addGLEventListener(eventListener)
        animator?.start()
    }

    /**
     * An implementation of [GLEventListener] using a given [callback] for rendering.
     */
    class GlimpseEventListener(
        private val callback: GlimpseCallback
    ) : GLEventListener {

        private lateinit var glimpseAdapter: GlimpseAdapter

        /**
         * Calls [GlimpseCallback.onCreate].
         */
        override fun init(drawable: GLAutoDrawable?) {
            requireNotNull(drawable) { "GLAutoDrawable is null." }
            require(drawable.gl.isGL2ES2) { "OpenGL does not conform to GL2ES2 profile." }
            glimpseAdapter = GlimpseAdapter(drawable.gl.gL2ES2)
            callback.onCreate(glimpseAdapter)
        }

        /**
         * Calls [GlimpseCallback.onResize].
         */
        override fun reshape(drawable: GLAutoDrawable?, x: Int, y: Int, width: Int, height: Int) {
            callback.onResize(glimpseAdapter, width = width, height = height)
        }

        /**
         * Calls [GlimpseCallback.onRender].
         */
        override fun display(drawable: GLAutoDrawable?) {
            callback.onRender(glimpseAdapter)
        }

        /**
         * Calls [GlimpseCallback.onDestroy].
         */
        override fun dispose(drawable: GLAutoDrawable?) {
            callback.onDestroy(glimpseAdapter)
        }
    }
}
