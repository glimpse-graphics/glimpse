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

import com.jogamp.opengl.util.Animator
import com.jogamp.opengl.util.AnimatorBase
import com.jogamp.opengl.util.FPSAnimator
import graphics.glimpse.GlimpseCallback
import graphics.glimpse.logging.GlimpseLogger
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import kotlin.system.exitProcess

/**
 * An implementation of [JFrame] that contains only [GlimpsePanel].
 */
class GlimpseFrame(
    title: String = DEFAULT_TITLE,
    width: Int = DEFAULT_WIDTH,
    height: Int = DEFAULT_HEIGHT,
    fpsLimit: Int? = null
) : JFrame(title), GlimpseComponent {

    private val logger by lazy { GlimpseLogger.create(this) }

    private val panel = GlimpsePanel()
    private val animator: AnimatorBase =
        fpsLimit?.let { FPSAnimator(panel, fpsLimit) }
            ?: Animator(panel)

    init {
        setSize(width, height)
        setLocationRelativeTo(null)
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(event: WindowEvent?) {
                try {
                    animator.stop()
                    dispose()
                } catch (expected: Exception) {
                    logger.error(message = "Error closing frame", exception = expected)
                }
                exitProcess(0)
            }
        })
    }

    /**
     * Sets [callback] to be used for rendering.
     */
    override fun setCallback(callback: GlimpseCallback) {
        panel.setCallback(callback)
        contentPane.add(panel)
        isVisible = true
        animator.start()
    }

    companion object {

        private const val DEFAULT_TITLE = "GlimpseFrame"

        private const val DEFAULT_WIDTH = 800
        private const val DEFAULT_HEIGHT = 600
    }
}
