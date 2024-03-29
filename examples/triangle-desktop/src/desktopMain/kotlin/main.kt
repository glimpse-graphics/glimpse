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

package graphics.glimpse.examples.triangle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

val resources = AppResources()

/**
 * Runs _Triangle_ example desktop application.
 */
fun main() = application {
    val windowState = rememberWindowState()
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = resources.getAppName(),
        icon = painterResource("icon.png"),
        undecorated = true,
        resizable = false
    ) {
        MaterialTheme(colors = resources.getThemeColors()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                WindowDraggableArea {
                    TopAppBar(
                        title = { Text(text = resources.getAppName()) },
                        navigationIcon = {
                            IconButton(onClick = ::exitApplication) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        }
                    )
                }
                AppContent(
                    resources = resources,
                    onClick = ::exitApplication,
                    modifier = Modifier.weight(weight = 1f)
                )
            }
        }
    }
    windowState.placement = WindowPlacement.Fullscreen
}
