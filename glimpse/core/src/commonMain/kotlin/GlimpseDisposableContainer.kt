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

package graphics.glimpse

/**
 * A disposable container for disposable Glimpse objects.
 *
 * After disposed, this container becomes empty and ready to be filled again.
 * Thus, [isDisposed] is always `false` for this object.
 *
 * @since v1.2.0
 */
class GlimpseDisposableContainer : GlimpseDisposable {

    private val disposables = mutableSetOf<GlimpseDisposable>()

    /**
     * Always `false`.
     */
    override val isDisposed: Boolean = false

    /**
     * Adds given [disposable] to this container.
     *
     * Returns `true` if the disposable was added to this container.
     * If the disposable was already in the container before, returns `false`.
     */
    fun add(disposable: GlimpseDisposable): Boolean = synchronized(disposables) {
        disposables.add(disposable)
    }

    /**
     * Adds all [disposables] from given collection to this container.
     *
     * Returns `true` if the contents of this container have changed as a result of the operation.
     */
    fun addAll(disposables: Collection<GlimpseDisposable>): Boolean = synchronized(disposables) {
        this.disposables.addAll(disposables)
    }

    /**
     * Disposes all disposables in this container.
     *
     * @throws IllegalStateException if any of the disposables in this container has previously been disposed.
     */
    override fun dispose(gl: GlimpseAdapter) {
        synchronized(disposables) {
            for (disposable in disposables) {
                disposable.dispose(gl)
            }
            disposables.clear()
        }
    }

    /**
     * Disposes all undisposed disposables in this container.
     */
    fun disposeUndisposed(gl: GlimpseAdapter) {
        synchronized(disposables) {
            for (disposable in disposables) {
                if (!disposable.isDisposed) {
                    disposable.dispose(gl)
                }
            }
            disposables.clear()
        }
    }
}
