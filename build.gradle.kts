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

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        // JB Composable dev versions:
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
        classpath("org.jetbrains.compose:compose-gradle-plugin:1.2.0-alpha01-dev745")
        classpath("com.android.tools.build:gradle:7.2.1")
    }
}

allprojects {

    val glimpseGroupId: String by project
    val glimpseVersion: String by project
    val glimpseVersionSuffix: String by project

    group = glimpseGroupId
    version =
        if (glimpseVersionSuffix.isBlank()) glimpseVersion
        else "$glimpseVersion-$glimpseVersionSuffix"

    repositories {
        google()
        mavenCentral()
        // JB Composable dev versions:
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}

plugins {
    id("org.jetbrains.changelog") version "1.3.1"
    id("org.jetbrains.dokka")
}

changelog {
    version.set(project.version.toString())
}
