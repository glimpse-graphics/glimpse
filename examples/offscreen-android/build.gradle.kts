plugins {
    id("com.android.application")
    kotlin("android")
    id("graphics.glimpse.detekt")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0-native-mt")
    implementation(project(":examples:offscreen-common"))
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "graphics.glimpse.examples.offscreen.android"
        minSdk = 19
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
