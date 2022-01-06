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

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("com.google.devtools.ksp") version "1.6.10-1.0.2"
    id("org.jetbrains.compose")
    id("graphics.glimpse.detekt")
}

kotlin {
    android()

    jvm(name = "desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":glimpse:core"))
                api(project(":glimpse:obj"))
                api(project(":glimpse:ui-compose"))
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("com.google.android.material:material:1.4.0")
                api("androidx.appcompat:appcompat:1.4.0")
                api("androidx.activity:activity-compose:1.4.0") {
                    exclude(group = "androidx.compose.animation")
                    exclude(group = "androidx.compose.foundation")
                    exclude(group = "androidx.compose.material")
                    exclude(group = "androidx.compose.runtime")
                    exclude(group = "androidx.compose.ui")
                }
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val desktopMain by getting {
            kotlin.srcDir(file("$buildDir/generated/ksp/desktopMain/kotlin"))
            resources.srcDir("src/commonAssets")
            dependencies {
                implementation("org.jogamp.jogl:jogl-all-main:2.3.2")
                implementation("org.jogamp.gluegen:gluegen-rt-main:2.3.2")
                implementation("org.slf4j:slf4j-api:1.7.32")
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDir("src/androidMain/res")
        assets.srcDir("src/commonAssets")
    }
    sourceSets["debug"].apply {
        java.srcDir(File("$buildDir/generated/ksp/androidDebug/kotlin"))
    }
    sourceSets["release"].apply {
        java.srcDir(File("$buildDir/generated/ksp/androidRelease/kotlin"))
    }
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}

dependencies {
    add("kspMetadata", project(":glimpse:processor-ksp"))
    add("kspAndroid", project(":glimpse:processor-ksp"))
    add("kspDesktop", project(":glimpse:processor-ksp"))
}
