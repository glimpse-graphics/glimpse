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

package graphics.glimpse.hud

/**
 * HUD element wrapper changing visibility of the wrapped element.
 */
class VisibilityWrapper(

    /**
     * Element wrapped by this wrapper.
     */
    element: HudElement,

    /**
     * Function returning current visibility of the wrapped element.
     */
    private val visibilityProvider: () -> Boolean
) : BaseHudElementWrapper(element) {

    /**
     * `true` the wrapped element is visible.
     */
    override var isVisible: Boolean
        get() = visibilityProvider()
        set(_) {
            throw UnsupportedOperationException("Visibility of VisibilityWrapper is read-only")
        }
}
