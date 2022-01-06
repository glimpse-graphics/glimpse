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

package graphics.glimpse.ui

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.GlimpseCallback
import graphics.glimpse.logging.GlimpseLogger
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * An implementation of [GLSurfaceView] that uses [Glimpse OpenGL adapter][GlimpseAdapter]
 * and [callback interface][GlimpseCallback] for rendering.
 */
open class GlimpseSurfaceView(
    context: Context,
    attrs: AttributeSet?
) : GLSurfaceView(context, attrs), GlimpseComponent {

    constructor(context: Context) : this(context, null)

    private val logger by lazy { GlimpseLogger.create(this) }

    private val glimpseAdapter by lazy { GlimpseAdapter() }

    private var glimpseRenderer: GlimpseRenderer? = null

    init {
        initEGLSettings()
    }

    private fun initEGLSettings() {
        setEGLContextClientVersion(GLES_VERSION)
        setEGLConfigChooser(
            COLOR_CHANNEL_BITS,
            COLOR_CHANNEL_BITS,
            COLOR_CHANNEL_BITS,
            COLOR_CHANNEL_BITS,
            DEPTH_BITS,
            STENCIL_BITS
        )
    }

    /**
     * Sets [callback] to be used for rendering.
     */
    override fun setCallback(callback: GlimpseCallback) {
        setGlimpseRenderer(GlimpseRenderer(callback))
    }

    private fun setGlimpseRenderer(renderer: GlimpseRenderer?) {
        glimpseRenderer = renderer
        super.setRenderer(glimpseRenderer)
    }

    @Deprecated(message = "Use setCallback() instead")
    override fun setRenderer(renderer: Renderer?) {
        if (renderer != null && renderer !is GlimpseRenderer) {
            logger.warn(message = "Not a GlimpseRenderer. Setting renderer to null.")
        }
        setGlimpseRenderer(renderer as? GlimpseRenderer)
    }

    /**
     * Calls [GlimpseCallback.onDestroy] to clean up Glimpse.
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        glimpseRenderer?.onDestroy(glimpseAdapter)
    }

    /**
     * An implementation of renderer using a given [callback] for rendering.
     */
    inner class GlimpseRenderer(
        private val callback: GlimpseCallback
    ) : Renderer,
        GlimpseCallback by callback {

        /**
         * Calls [GlimpseCallback.onCreate].
         */
        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            onCreate(glimpseAdapter)
        }

        /**
         * Calls [GlimpseCallback.onResize].
         */
        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            onResize(glimpseAdapter, width = width, height = height)
        }

        /**
         * Calls [GlimpseCallback.onRender].
         */
        override fun onDrawFrame(gl: GL10?) {
            onRender(glimpseAdapter)
        }
    }

    companion object {

        private const val GLES_VERSION = 2

        private const val COLOR_CHANNEL_BITS = 8
        private const val DEPTH_BITS = 16
        private const val STENCIL_BITS = 8
    }
}
