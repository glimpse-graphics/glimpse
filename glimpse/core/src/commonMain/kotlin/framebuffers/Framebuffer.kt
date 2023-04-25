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
import graphics.glimpse.GlimpseDisposable
import graphics.glimpse.WithHandle
import graphics.glimpse.textures.Texture

/**
 * A framebuffer.
 *
 * @since v1.1.0
 */
interface Framebuffer : GlimpseDisposable, WithHandle {

    /**
     * Framebuffer handle.
     */
    override val handle: Int

    /**
     * Renderbuffers attached to this framebuffer.
     */
    val renderbuffers: Map<FramebufferAttachmentType, Renderbuffer>

    /**
     * Textures attached to this framebuffer.
     */
    val textures: Map<FramebufferAttachmentType, Texture>

    /**
     * Tells the given [OpenGL adapter][gl] to use this framebuffer and draw
     * into its color attachments.
     */
    fun use(gl: GlimpseAdapter)

    /**
     * An interface for a framebuffer builder.
     *
     * @since v1.1.0
     */
    interface Builder {

        /**
         * Adds a [renderbuffer] attachment.
         *
         * Any previously added attachment of the given [type] is removed.
         */
        fun attachRenderbuffer(type: FramebufferAttachmentType, renderbuffer: Renderbuffer): Builder

        /**
         * Adds a [texture] image attachment.
         *
         * Any previously added attachment of the given [type] is removed.
         */
        fun attachTexture(type: FramebufferAttachmentType, texture: Texture): Builder

        /**
         * Builds a framebuffer with the added attachments.
         */
        fun build(): Framebuffer

        companion object {

            /**
             * Gets a new instance of a framebuffer builder.
             */
            fun getInstance(gl: GlimpseAdapter): Builder = FramebufferBuilderImpl(gl)
        }
    }
}
