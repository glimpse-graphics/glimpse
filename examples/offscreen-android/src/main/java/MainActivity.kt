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

package graphics.glimpse.examples.offscreen.android

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import graphics.glimpse.createBitmap
import graphics.glimpse.examples.offscreen.AppResources
import graphics.glimpse.examples.offscreen.QuadRenderer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * Main _Offscreen_ example Activity.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    /**
     * Renders an image in background thread.
     */
    override fun onResume() {
        super.onResume()
        MainScope().launch(Dispatchers.IO) {
            QuadRenderer(AppResources(this@MainActivity)) { gl ->
                val bitmap = gl.createBitmap(
                    width = QuadRenderer.IMAGE_SIZE,
                    height = QuadRenderer.IMAGE_SIZE
                )
                MainScope().launch {
                    findViewById<ImageView>(R.id.image).setImageBitmap(bitmap)
                }
            }.render()
        }
    }
}
