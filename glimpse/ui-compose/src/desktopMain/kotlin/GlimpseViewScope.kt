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

package graphics.glimpse.ui.compose

import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.ui.GlimpsePanel

/**
 * Receiver scope used by [GlimpseView].
 */
actual class GlimpseViewScope(internal val component: GlimpsePanel) {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    /**
     * Controls whether the surface of the [GlimpseView] is placed on top of its window.
     *
     * Has no effect in desktop applications.
     */
    actual var zOrderOnTop: Boolean = false
        set(value) {
            field = value
            logger.warn("\"zOrderOnTop = $value\" will have no effect in desktop application")
        }
}
