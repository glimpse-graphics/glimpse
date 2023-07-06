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

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.4.2")
    implementation("com.android.tools.build:gradle-api:7.4.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
    implementation("org.jetbrains.dokka:dokka-core:1.8.10")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.8.10")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
}
