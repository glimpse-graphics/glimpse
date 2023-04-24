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

package graphics.glimpse.hud.layouts

/**
 * Alignment of an element inside a layout.
 */
sealed class Alignment(
    internal val x: Float,
    internal val y: Float
) {

    /**
     * Top-left alignment.
     */
    object TopLeft : Alignment(x = -1f, y = -1f)

    /**
     * Top alignment.
     */
    object Top : Alignment(x = 0f, y = -1f)

    /**
     * Top-right alignment.
     */
    object TopRight : Alignment(x = 1f, y = -1f)

    /**
     * Left alignment.
     */
    object Left : Alignment(x = -1f, y = 0f)

    /**
     * Center alignment.
     */
    object Center : Alignment(x = 0f, y = 0f)

    /**
     * Right alignment.
     */
    object Right : Alignment(x = 1f, y = 0f)

    /**
     * Bottom-left alignment.
     */
    object BottomLeft : Alignment(x = -1f, y = 1f)

    /**
     * Bottom alignment.
     */
    object Bottom : Alignment(x = 0f, y = 1f)

    /**
     * Bottom-right alignment.
     */
    object BottomRight : Alignment(x = 1f, y = 1f)
}

/**
 * Horizontal alignment of an element inside a layout.
 */
sealed class HorizontalAlignment(internal val x: Float) {

    /**
     * Left alignment.
     */
    object Left : HorizontalAlignment(x = -1f)

    /**
     * Center alignment.
     */
    object Center : HorizontalAlignment(x = 0f)

    /**
     * Right alignment.
     */
    object Right : HorizontalAlignment(x = 1f)
}

/**
 * Vertical alignment of an element inside a layout.
 */
sealed class VerticalAlignment(internal val y: Float) {

    /**
     * Top alignment.
     */
    object Top : VerticalAlignment(y = -1f)

    /**
     * Center alignment.
     */
    object Center : VerticalAlignment(y = 0f)

    /**
     * Bottom alignment.
     */
    object Bottom : VerticalAlignment(y = 1f)
}
