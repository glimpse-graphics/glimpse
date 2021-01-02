/*
 * Copyright 2020 Slawomir Czerwinski
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

package graphics.glimpse.ui.compose

import androidx.compose.desktop.SwingPanel
import androidx.compose.runtime.Composable
import com.jogamp.opengl.util.Animator
import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.GlimpseCallback
import graphics.glimpse.ui.GlimpsePanel

/**
 * A `GlimpseView` displays content rendered using [Glimpse OpenGL adapter][GlimpseAdapter]
 * and [callback interface][GlimpseCallback].
 */
@Composable
actual fun GlimpseView(callback: GlimpseCallback, zOrderOnTop: Boolean) {
    val glimpsePanel = GlimpsePanel()
    SwingPanel(component = glimpsePanel)
    glimpsePanel.setCallback(callback)
    Animator(glimpsePanel).start()
}