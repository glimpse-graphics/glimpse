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

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        // JB Composable dev versions:
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}

rootProject.name = "glimpse-graphics"

include(":glimpse:core")
include(":glimpse:hud")
include(":glimpse:obj")
include(":glimpse:offscreen")
include(":glimpse:processor")
include(":glimpse:processor-java")
include(":glimpse:processor-kotlin")
include(":glimpse:processor-ksp")
include(":glimpse:ui")
include(":glimpse:ui-compose")

include(":examples:triangle-common")
include(":examples:triangle-android")
include(":examples:triangle-desktop")
include(":examples:offscreen-common")
include(":examples:offscreen-android")
include(":examples:offscreen-desktop")
