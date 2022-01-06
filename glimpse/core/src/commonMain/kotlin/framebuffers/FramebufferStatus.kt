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

package graphics.glimpse.framebuffers

/**
 * Framebuffer completeness status.
 *
 * @since v1.1.0
 */
enum class FramebufferStatus {

    /**
     * Framebuffer is complete.
     */
    COMPLETE,

    /**
     * Any of the attachment points are incomplete
     */
    INCOMPLETE_ATTACHMENT,

    /**
     * No image attached to the framebuffer.
     */
    INCOMPLETE_MISSING_ATTACHMENT,

    /**
     * Combination of internal formats of the images is unsupported.
     */
    UNSUPPORTED,

    /**
     * Invalid multisample configuration.
     */
    INCOMPLETE_MULTISAMPLE,

    /**
     * Unknown status.
     */
    UNKNOWN_STATUS
}
