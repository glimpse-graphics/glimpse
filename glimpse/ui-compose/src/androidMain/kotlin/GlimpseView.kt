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

package graphics.glimpse.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.GlimpseCallback
import graphics.glimpse.ui.GlimpseSurfaceView

/**
 * A `GlimpseView` displays content rendered using [Glimpse OpenGL adapter][GlimpseAdapter]
 * and [callback interface][GlimpseCallback].
 *
 * @param callback The rendering [callback interface][GlimpseCallback].
 * @param modifier The modifier to be applied to the layout.
 * @param update The callback to be invoked after the layout is inflated.
 */
@Composable
actual fun GlimpseView(
    callback: GlimpseCallback,
    modifier: Modifier,
    update: GlimpseViewScope.() -> Unit
) {
    val context = LocalContext.current
    val glimpseViewScope = remember {
        val view = GlimpseSurfaceView(context)
        GlimpseViewScope(view).apply {
            this.zOrderOnTop = false
        }
    }

    AndroidView(
        factory = { glimpseViewScope.view },
        modifier = modifier
    ) { view ->
        view.setCallback(callback)
        glimpseViewScope.update()
    }
}
