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

package graphics.glimpse.hud.text

import android.graphics.Typeface

/**
 * Font wrapper.
 *
 * @since v1.2.0
 */
actual data class Font(internal val typeface: Typeface, internal val textSize: Float) {

    actual companion object {

        private const val DEFAULT_FONT_SIZE = 24f

        /**
         * Default font.
         */
        actual val DEFAULT: Font =
            Font(typeface = Typeface.SANS_SERIF, DEFAULT_FONT_SIZE)
    }
}
