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
    compileSdk = 31
    defaultConfig {
        applicationId = "graphics.glimpse.examples.triangle.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = project.version.toString()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    packagingOptions {
        resources.excludes.add("META-INF/*.kotlin_module")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}
