/*
 * Copyright 2020 Slawomir Czerwinski
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

package graphics.glimpse.textures

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.lang.ref.WeakReference

/**
 * Will build a texture source from a given [file].
 */
fun TextureImageSourceBuilder.fromFile(file: File): TextureImageSourceBuilder = this
    .withFilename(file.name)
    .fromBitmap {
        BitmapFactory.decodeFile(file.absolutePath)
    }

/**
 * Will build a texture source from an asset with a given [fileName].
 */
fun TextureImageSourceBuilder.fromAsset(context: Context, fileName: String): TextureImageSourceBuilder = this
    .withFilename(fileName.split(File.separatorChar).last())
    .fromBitmap(
        object : BitmapProvider {

            private val contextRef: WeakReference<Context> = WeakReference(context)
            private val assets: AssetManager? get() = contextRef.get()?.assets

            override fun createBitmap(): Bitmap? =
                BitmapFactory.decodeStream(assets?.open(fileName))
        }
    )
