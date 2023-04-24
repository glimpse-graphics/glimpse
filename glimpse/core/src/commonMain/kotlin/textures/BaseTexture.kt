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

package graphics.glimpse.textures

import graphics.glimpse.GlimpseAdapter
import java.util.concurrent.atomic.AtomicBoolean

internal abstract class BaseTexture : Texture {

    protected abstract val type: TextureType

    private val disposed = AtomicBoolean(false)
    override val isDisposed: Boolean get() = disposed.get()

    override fun useAtIndex(gl: GlimpseAdapter, textureIndex: Int) {
        gl.glActiveTexture(textureIndex)
        gl.glBindTexture(type, handle)
    }

    override fun dispose(gl: GlimpseAdapter) {
        check(disposed.compareAndSet(false, true)) { "Texture is already disposed" }
        val handles = intArrayOf(handle)
        gl.glDeleteTextures(handles)
    }
}
