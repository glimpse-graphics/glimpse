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

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import graphics.glimpse.shaders.ShaderType
import java.util.*

/**
 * Application resources provider.
 */
actual class AppResources {

    private val bundle = ResourceBundle.getBundle(javaClass.simpleName, Locale.getDefault());

    /**
     * Returns application name.
     */
    actual fun getAppName(): String = bundle.getString("appName")

    /**
     * Returns application theme colors.
     */
    actual fun getThemeColors(): Colors = lightColors(
        primary = Color(color = bundle.getHexLong("colors.primary")),
        primaryVariant = Color(color = bundle.getHexLong("colors.primaryVariant")),
        secondary = Color(color = bundle.getHexLong("colors.secondary")),
        secondaryVariant = Color(color = bundle.getHexLong("colors.secondaryVariant"))
    )

    private fun ResourceBundle.getHexLong(key: String): Long =
        getString(key).toLong(radix = 16)

    /**
     * Returns source of a shader of a given [type].
     */
    actual fun getShaderSource(type: ShaderType): String =
        javaClass.getResourceAsStream(getShaderResourceName(type))
            .use { it.bufferedReader().readText() }

    private fun getShaderResourceName(type: ShaderType): String =
        "/${type.name.toLowerCase(Locale.ENGLISH)}.glsl"
}
