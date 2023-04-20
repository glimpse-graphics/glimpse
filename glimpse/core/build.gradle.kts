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
    id("graphics.glimpse.internal.multiplatform")
}

kotlin {
    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                compileOnly("androidx.annotation:annotation:1.6.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val desktopMain by getting {
            dependencies {
                compileOnly("org.jogamp.jogl:jogl-all-main:2.4.0")
                compileOnly("org.jogamp.gluegen:gluegen-rt-main:2.4.0")
                compileOnly("org.slf4j:slf4j-api:2.0.7")
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("ch.qos.logback:logback-core:1.4.7")
                implementation("ch.qos.logback:logback-classic:1.4.6")
            }
        }
    }
}

android {
    namespace = "graphics.glimpse"
    defaultConfig {
        minSdk = 14
    }
}
