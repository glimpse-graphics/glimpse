plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("io.gitlab.arturbosch.detekt") version "1.14.2"
}

repositories {
    google()
}

kotlin {
    android()
    jvm(name = "desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.1")
            }
        }
        val desktopMain by getting
        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

detekt {
    input = files(kotlin.sourceSets.flatMap { it.kotlin.sourceDirectories })
    config = files(".config/detekt.yml")
    buildUponDefaultConfig = true
    reports {
        xml {
            enabled = true
            destination = file("$buildDir/reports/detekt.xml")
        }
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt.html")
        }
    }
}

android {
    compileSdkVersion(apiLevel = 30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)
    }
}
