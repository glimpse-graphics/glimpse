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

import android.graphics.Bitmap
import java.nio.ByteBuffer

/**
 * Creates a new [Bitmap] from the rendered image.
 */
fun GlimpseAdapter.createBitmap(
    x: Int = 0,
    y: Int = 0,
    width: Int,
    height: Int
): Bitmap {
    val pixels = glReadPixels(x, y, width, height, format = PixelFormat.RGBA)
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(pixels))
    return bitmap
}
