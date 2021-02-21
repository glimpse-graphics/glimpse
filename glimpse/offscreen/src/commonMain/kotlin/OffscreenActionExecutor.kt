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

package graphics.glimpse.offscreen

/**
 * Executor of offscreen rendering actions.
 */
expect class OffscreenActionExecutor {

    /**
     * Executes a given [action] offscreen.
     */
    fun execute(action: OffscreenAction)

    companion object {

        /**
         * Returns a new instance of [OffscreenActionExecutor] using frame buffer with a given
         * [width] and [height].
         */
        fun getInstance(width: Int, height: Int): OffscreenActionExecutor
    }
}
