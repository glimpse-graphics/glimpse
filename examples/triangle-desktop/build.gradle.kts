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

import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("graphics.glimpse.detekt")
}

kotlin {
    jvm(name = "desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":examples:triangle-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.jogamp.jogl:jogl-all-main:2.3.2")
                implementation("org.jogamp.gluegen:gluegen-rt-main:2.3.2")
                implementation("org.slf4j:slf4j-api:1.7.33")
                implementation("ch.qos.logback:logback-core:1.2.10")
                implementation("ch.qos.logback:logback-classic:1.2.10")
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "graphics.glimpse.examples.triangle.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Glimpse Triangle Example"

            // Compose apps require correct DMG and MSI version format (X.X.X):
            val glimpseVersion: String by project
            packageVersion = glimpseVersion

            windows {
                iconFile.set(project.file("icon.ico"))
            }
            linux {
                iconFile.set(project.file("icon.png"))
            }
        }
        jvmArgs.add("--add-exports=jogl.all/com.jogamp.opengl.util=ALL-UNNAMED")
        jvmArgs.add("--add-exports=jogl.all/com.jogamp.opengl.util.texture=ALL-UNNAMED")
    }
}
