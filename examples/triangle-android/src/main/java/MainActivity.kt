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

package graphics.glimpse.examples.triangle.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import graphics.glimpse.examples.triangle.AppContent
import graphics.glimpse.examples.triangle.AppResources

/**
 * Main _Triangle_ example Activity.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Sets content view from [AppContent] composable.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appResources = AppResources(context = this)
        setContent {
            MaterialTheme(colors = appResources.getThemeColors()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TopAppBar(title = { Text(text = appResources.getAppName()) })
                    AppContent(
                        resources = appResources,
                        onClick = ::onBackPressed,
                        modifier = Modifier.weight(weight = 1f)
                    )
                }
            }
        }
    }
}
