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

package graphics.glimpse.hud.dsl

import graphics.glimpse.GlimpseAdapter
import graphics.glimpse.hud.GlimpseHudCallback

/**
 * Builds a new [GlimpseHudCallback], containing tree of elements defined in the [init] block.
 */
fun GlimpseAdapter.glimpseHud(
    width: Int = 1,
    height: Int = 1,
    init: HudElementsBuilder.() -> Unit
): GlimpseHudCallback =
    GlimpseHudBuilder(gl = this)
        .apply(init)
        .build()
        .apply {
            onCreate(gl = this@glimpseHud)
            onResize(gl = this@glimpseHud, width = width, height = height)
        }
