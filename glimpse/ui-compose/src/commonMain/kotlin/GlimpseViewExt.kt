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

package graphics.glimpse.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.GlimpseCallback

/**
 * A `GlimpseView` displays content rendered using [Glimpse OpenGL adapter][GlimpseAdapter].
 */
@Composable
fun GlimpseView(
    onCreate: GlimpseAdapter.() -> Unit,
    onResize: GlimpseAdapter.(Offset, Size) -> Unit,
    onRender: GlimpseAdapter.() -> Unit,
    onDestroy: GlimpseAdapter.() -> Unit,
    modifier: Modifier = Modifier,
    update: GlimpseViewScope.() -> Unit = NoOpUpdate
) {
    val callback = object : GlimpseCallback {

        override fun onCreate(gl: GlimpseAdapter) {
            gl.onCreate()
        }

        override fun onResize(gl: GlimpseAdapter, x: Int, y: Int, width: Int, height: Int) {
            gl.onResize(Offset(x.toFloat(), y.toFloat()), Size(width.toFloat(), height.toFloat()))
        }

        override fun onRender(gl: GlimpseAdapter) {
            gl.onRender()
        }

        override fun onDestroy(gl: GlimpseAdapter) {
            gl.onDestroy()
        }
    }

    GlimpseView(
        callback = callback,
        modifier = modifier,
        update = update
    )
}
