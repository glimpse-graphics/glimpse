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

package graphics.glimpse.examples.triangle

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import graphics.glimpse.ui.compose.GlimpseView

/**
 * Composable application layout.
 */
@Composable
fun App(resources: AppResources) {
    MaterialTheme(colors = resources.getThemeColors()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopAppBar(title = { Text(text = resources.getAppName()) })
            Box(modifier = Modifier.weight(weight = 1f)) {
                Row(modifier = Modifier.fillMaxSize()) {
                    GlimpseView(
                        callback = TriangleCallback(resources),
                        zOrderOnTop = true
                    )
                }
            }
        }
    }
}
