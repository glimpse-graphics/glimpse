plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    implementation(project(":examples:triangle-common"))
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}
