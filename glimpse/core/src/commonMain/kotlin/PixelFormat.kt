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

package graphics.glimpse

/**
 * Format of pixel data read from frame buffer.
 */
enum class PixelFormat(val bytesPerPixel: Int) {

    /**
     * Read alpha channel only.
     */
    ALPHA(bytesPerPixel = 1),

    /**
     * Read red, green and blue channels.
     */
    RGB(bytesPerPixel = 3),

    /**
     * Read all channels.
     */
    RGBA(bytesPerPixel = 4)
}
