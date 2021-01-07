plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose") version "0.0.0-unmerged-build21"
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    implementation(project(":examples:triangle"))
}

detekt { setUpDetekt(project, kotlin.sourceSets.flatMap { it.kotlin.sourceDirectories }) }

android {
    compileSdkVersion(apiLevel = 30)
    defaultConfig {
        applicationId = "graphics.glimpse.examples.triangle.android"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = project.version.toString()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}