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

package graphics.glimpse.examples.triangle.android

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.platform.ComposeView
import graphics.glimpse.examples.triangle.App
import graphics.glimpse.examples.triangle.AppResources

/**
 * Main _Triangle_ example Activity.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Sets content view from [App] composable.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(AppResources(context = this))
        }
    }

    /*
     * Temporary workaround. Function implemented in `androidx.activity:activity-compose:1.3.0-alpha01`.
     */
    private fun setContent(
        parent: CompositionContext? = null,
        content: @Composable () -> Unit
    ) {
        val existingComposeView = window.decorView
            .findViewById<ViewGroup>(android.R.id.content)
            .getChildAt(0) as? ComposeView

        if (existingComposeView != null) with(existingComposeView) {
            setParentCompositionContext(parent)
            setContent(content)
        } else ComposeView(this).apply {
            // Set content and parent **before** setContentView
            // to have ComposeView create the composition on attach
            setParentCompositionContext(parent)
            setContent(content)
            setContentView(this, DefaultLayoutParams)
        }
    }

    companion object {
        private val DefaultLayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
