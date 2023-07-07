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
    id("com.android.library")
    kotlin("multiplatform")
    id("com.google.devtools.ksp") version "1.9.0-1.0.11"
    id("graphics.glimpse.internal.detekt")
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
                api(project(":glimpse:offscreen"))
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
                api("com.google.android.material:material:1.8.0")
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
                implementation("org.jogamp.jogl:jogl-all-main:2.4.0")
                implementation("org.jogamp.gluegen:gluegen-rt-main:2.4.0")
                implementation("org.slf4j:slf4j-api:2.0.7")
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
    compileSdk = 33
    namespace = "graphics.glimpse.examples.offscreen"
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    add("kspAndroid", project(":glimpse:processor-ksp"))
    add("kspDesktop", project(":glimpse:processor-ksp"))
}
