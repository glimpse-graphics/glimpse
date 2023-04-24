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
    application
    kotlin("jvm")
    id("graphics.glimpse.internal.detekt")
}

dependencies {
    implementation(project(":examples:offscreen-common"))
    implementation("org.jogamp.jogl:jogl-all-main:2.4.0")
    implementation("org.jogamp.gluegen:gluegen-rt-main:2.4.0")
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-core:1.4.7")
    implementation("ch.qos.logback:logback-classic:1.4.7")
}

application {
    mainClass.set("graphics.glimpse.examples.offscreen.MainKt")
    applicationDefaultJvmArgs = listOf(
        "--add-exports=jogl.all/com.jogamp.opengl.util=ALL-UNNAMED",
        "--add-exports=jogl.all/com.jogamp.opengl.util.awt=ALL-UNNAMED",
        "--add-exports=jogl.all/com.jogamp.opengl.util.texture=ALL-UNNAMED"
    )
}

tasks.withType<Tar> { duplicatesStrategy = DuplicatesStrategy.INCLUDE }
tasks.withType<Zip> { duplicatesStrategy = DuplicatesStrategy.INCLUDE }
