plugins {
    id("com.android.application")
    kotlin("android")
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3-native-mt")
    implementation(project(":examples:offscreen-common"))
}

detekt { setUpDetekt(project, kotlin.sourceSets.flatMap { it.kotlin.sourceDirectories }) }

android {
    compileSdkVersion(apiLevel = 30)
    defaultConfig {
        applicationId = "graphics.glimpse.examples.offscreen.android"
        minSdkVersion(19)
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
