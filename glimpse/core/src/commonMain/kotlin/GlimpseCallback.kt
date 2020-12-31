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

package graphics.glimpse

/**
 * Callback used by various Glimpse components for rendering.
 */
interface GlimpseCallback {

    /**
     * Called when the OpenGL context is obtained.
     */
    fun onCreate(gl: GlimpseAdapter)

    /**
     * Called when the rendering target is resized.
     */
    fun onResize(gl: GlimpseAdapter, x: Int = 0, y: Int = 0, width: Int, height: Int)

    /**
     * Called when the rendering happens.
     */
    fun onRender(gl: GlimpseAdapter)

    /**
     * Called when the OpenGL context is released.
     */
    fun onDestroy(gl: GlimpseAdapter)
}
