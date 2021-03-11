plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("kapt")
    id("org.jetbrains.compose")
    id("io.gitlab.arturbosch.detekt")
}

val generatedKotlinSources: String = "$projectDir/src/gen/kotlin"

kapt {
    correctErrorTypes = true
    javacOptions {
        option("-Akapt.kotlin.generated=$generatedKotlinSources")
    }
}

kotlin {
    android()

    jvm(name = "desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting {

            // Workaround for lack of Kapt support in multiplatform project:
            dependencies.add("kapt", project(":glimpse:processor-kotlin"))
            kotlin.srcDir(generatedKotlinSources)

            dependencies {
                api(project(":glimpse:core"))
                api(project(":glimpse:obj"))
                api(project(":glimpse:ui-compose"))
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("com.google.android.material:material:1.3.0")
                api("androidx.appcompat:appcompat:1.3.0-beta01")
                api("androidx.activity:activity-compose:1.3.0-alpha04") {
                    exclude(group = "androidx.compose.animation")
                    exclude(group = "androidx.compose.foundation")
                    exclude(group = "androidx.compose.material")
                    exclude(group = "androidx.compose.runtime")
                    exclude(group = "androidx.compose.ui")
                }
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val desktopMain by getting {
            resources.srcDir("src/commonAssets")
            dependencies {
                implementation("org.jogamp.jogl:jogl-all-main:2.3.2")
                implementation("org.jogamp.gluegen:gluegen-rt-main:2.3.2")
                implementation("org.slf4j:slf4j-api:1.7.30")
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

// Compilation of commonMain depends on sources generated by kapt:
afterEvaluate {
    tasks["compileCommonMainKotlinMetadata"].dependsOn(tasks["kaptKotlinDesktop"])
}

detekt { setUpDetekt(project, kotlin.sourceSets.flatMap { it.kotlin.sourceDirectories }) }

android {
    compileSdkVersion(apiLevel = 30)
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDir("src/androidMain/res")
        assets.srcDir("src/commonAssets")
    }
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
    }
}
