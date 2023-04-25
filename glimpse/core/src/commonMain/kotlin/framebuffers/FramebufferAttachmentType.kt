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

package graphics.glimpse.framebuffers

import graphics.glimpse.GlimpseAdapter

/**
 * Framebuffer attachment type.
 *
 * @since v1.1.0
 */
sealed class FramebufferAttachmentType {

    /**
     * Depth attachment.
     */
    object Depth : FramebufferAttachmentType()

    /**
     * Stencil attachment.
     */
    object Stencil : FramebufferAttachmentType()

    /**
     * Color attachment.
     *
     * The minimum [index] is 0.
     *
     * The maximum index is equal to the value returned by
     * [glGetMaxColorAttachments][GlimpseAdapter.glGetMaxColorAttachments] minus one.
     */
    data class Color(

        /**
         * Index of this color attachment.
         */
        val index: Int

    ) : FramebufferAttachmentType() {

        init {
            require(index >= 0) { "The minimum color attachment index value is 0" }
        }

        companion object {

            /**
             * Returns color attachment with the given [index].
             */
            operator fun get(index: Int): Color = Color(index)
        }
    }
}
