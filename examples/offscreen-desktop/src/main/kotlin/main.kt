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

package graphics.glimpse.examples.offscreen

import java.awt.Image
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.imageio.ImageIO
import javax.swing.JFrame
import kotlin.system.exitProcess
import javax.swing.ImageIcon
import javax.swing.JLabel
import graphics.glimpse.createImage
import javax.swing.SwingUtilities.invokeLater

val resources = AppResources()

const val FRAME_WIDTH = 800
const val FRAME_HEIGHT = 600

/**
 * Runs _Offscreen_ example desktop application.
 */
fun main() {
    val frame = JFrame(resources.getAppName())
    frame.apply {
        iconImage = ImageIO.read(object {}.javaClass.getResource("/icon.png"))
        setSize(FRAME_WIDTH, FRAME_HEIGHT)
        setLocationRelativeTo(null)
        addWindowListener(object : WindowAdapter() {

            override fun windowOpened(e: WindowEvent?) {
                invokeLater {
                    QuadRenderer(resources) { gl ->
                        val image: Image = gl.createImage(
                            width = QuadRenderer.IMAGE_SIZE,
                            height = QuadRenderer.IMAGE_SIZE
                        )
                        val icon = JLabel(ImageIcon(image))
                        add(icon)
                        pack()
                        setLocationRelativeTo(null)
                    }.render()
                }
            }

            override fun windowClosing(event: WindowEvent?) {
                exitProcess(0)
            }
        })
        isVisible = true
    }
}
