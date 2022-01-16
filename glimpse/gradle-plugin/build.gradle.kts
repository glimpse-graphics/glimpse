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
    `java-gradle-plugin`
    kotlin("jvm")
    id("graphics.glimpse.internal.test.logging")
    id("graphics.glimpse.internal.detekt")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.19.0"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("com.android.tools.build:gradle:7.0.4")
    testImplementation("junit:junit:4.13.1")
}

gradlePlugin {
    plugins.create("glimpseJvmGradlePlugin") {
        id = "${group}.jvm"
        displayName = "Glimpse JVM Gradle Plugin"
        description = "Glimpse Gradle plugin for Kotlin JVM projects"
        implementationClass = "graphics.glimpse.gradle.GlimpseJvmGradlePlugin"
    }
    plugins.create("glimpseAndroidGradlePlugin") {
        id = "${group}.android"
        displayName = "Glimpse Android Gradle Plugin"
        description = "Glimpse Gradle plugin for Android projects"
        implementationClass = "graphics.glimpse.gradle.GlimpseAndroidGradlePlugin"
    }
    pluginBundle {
        website = "https://glimpse.graphics/"
        vcsUrl = "https://github.com/glimpse-graphics/glimpse.git"
        tags = listOf("glimpse", "glimpse-graphics", "opengl")
    }
}

val functionalTest by sourceSets.creating
gradlePlugin.testSourceSets(functionalTest)

configurations[functionalTest.implementationConfigurationName].extendsFrom(configurations.testImplementation.get())

val functionalTestTask = tasks.register<Test>("functionalTest") {
    testClassesDirs = functionalTest.output.classesDirs
    classpath = configurations[functionalTest.runtimeClasspathConfigurationName] + functionalTest.output
}

tasks.test {
    dependsOn(functionalTestTask)
}
