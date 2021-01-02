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

import android.content.Context
import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import java.lang.ref.WeakReference

/**
 * Application resources provider.
 */
actual class AppResources(context: Context) {

    private val contextRef = WeakReference(context)
    private val context: Context get() = checkNotNull(contextRef.get())

    /**
     * Returns application name.
     */
    actual fun getAppName(): String = context.getString(R.string.app_name).orEmpty()

    /**
     * Returns application theme colors.
     */
    actual fun getThemeColors(): Colors = lightColors(
        primary = Color(color = context.getColor(R.color.primaryColor)),
        primaryVariant = Color(color = context.getColor(R.color.primaryColorVariant)),
        secondary = Color(color = context.getColor(R.color.secondaryColor)),
        secondaryVariant = Color(color = context.getColor(R.color.secondaryColorVariant))
    )
}
